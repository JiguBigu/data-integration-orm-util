# 数据集成ORM工具库实现

目标：将多个数据库多张字段名不同的表进行集成。

实现：通过模仿ORM框架Mybatis的XML标签结构和Jap的api，将配置的xml文件标签映射到一个实体类中进行增删改查。

实现思路：通过泛型的视同、Java反射机制和对XML文件的解析，动态生成泛型对象和sql语句，并装载数据，最终形成增删改查的通用api。

数据库连接池：HikariCP

流程： 

1. servlet收到http请求后实例化相应的服务类（XxxService）

2. 实例化服务类时，构造方法会调用XMLUtil进行第一次XML解析，获取数据库名、表名、和描述表的XML文件路径传入集成配置类（IntegrationSetting）的列表。

3. 服务类创建映射类（XxxMapper）实例，并传入配置类对象。传入一个配置配置类对象只能操作一个数据库的一张表，因此在服务类中要遍历配置类列表进行增删改查操作。

4. 在映射类（XxxMapper）中，通过继承MapperUtil来调用通用映射api。

映射工具使用：

1.  创建全局配置文件setting.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<setting>
    <database id="test1">
        <table id="users" entityType="intergration.example.entity.User">/xml/database1/users.xml</table>
        <table id="lesson" entityType="intergration.example.entity.Lesson">/xml/database1/lesson.xml</table>
    </database>

    <database id="test2">
        <table id="users" entityType="intergration.example.entity.User">/xml/database2/users.xml</table>
        <table id="lesson" entityType="intergration.example.entity.Lesson">/xml/database2/lesson.xml</table>
    </database>
</setting>
```

2.为全局配置文件中每个表各创建一个xml表，如database1的users.xml(注意：主键必须放在<resultMap>下的第一行) 。标签为属性名，标签的值为字段名。
```xml
<?xml version="1.0" encoding="UTF-8"?>
<mapper>
    <resultMap type="intergration.example.entity.User" >
        <id jdbcType="BIGINT" primaryKey="true">id</id>
        <userName jdbcType="VARCHAR">userName123</userName>
        <userSex jdbcType="VARCHAR">sex</userSex>
        <className jdbcType="VARCHAR">class</className>
    </resultMap>
</mapper>
```
3.实体类继承IntegrationSetting接口，返回主键对应的属性名。
```java
public class User implements Serializable, IntegrationEntity {
    private String id;
    
    public String getPrimary() {
            return "id";
        }
}
```
4.创建Mapper类，继承MapperUtil，泛型<T,P>，T为实体类，P为主键对应的属性的类型
```java
public class UserMapper extends MapperUtil<User, String> {

    public UserMapper() {
    }

    public UserMapper(IntegrationSetting setting, Class<User> tclass) {
        super(setting, tclass);
    }
}
```

5.在Service类或客户端类中调用XMLUtil解析setting文件，创建XxxMapper类对象，将返回的IntegrationEntity配置对象和实体类.class传入XxxMapper中。最后，调用通用的api完成增删改查。
```java
/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/10 21:12
 */
public class UserServiceImpl implements UserService {
    /**
     * 配置类列表
     */
    private List<IntegrationSetting> integrationSettingList;
    /**
     * 当前类类名
     */
    private static final String CLASS_NAME = User.class.getName();

    public UserServiceImpl(){
        XMLUtil xmlUtil = new XMLUtil();
        try {
            integrationSettingList = xmlUtil.getIntegrationSettingList(CLASS_NAME);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有数据库中的用户数据
     * @return 学生列表
     */
    public List<User> getAllUser() {
        List<User> userList = new ArrayList<User>();
        for(IntegrationSetting setting: integrationSettingList){
            UserMapper userMapper = new UserMapper(setting, User.class);
            userList.addAll(userMapper.getAll());
        }
        return userList;
    }
    
}
```

映射工具库实现过程：

1. 创建描述setting.xml总XML文件的配置类 IntegrationSetting，及setter、getter方法

2. 编写XML工具类对XML文件进行解析，实现根据实体类属性名获取字段名、获取字段列表、获取主键值、获取配置类列表等方法。

3. 创建连接池工具类HikariCPUtil

4. 编写通用的MapperUtil类，根据泛型和反射机制，运用XML工具类，动态生成泛型对象和sql语句，并装载数据，最终实现增删改查的通用api（查询所有、查询一个、删除、插入、修改）。