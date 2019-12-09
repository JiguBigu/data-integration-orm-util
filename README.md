# 数据集成ORM实现

目标：将多个数据库多张字段名不同的表进行集成。

实现：通过模仿ORM框架Mybatis，将配置的xml文件标签映射到一个实体类中进行增删改查。

实现思路：通过Java反射机制和对XML文件的解析，动态的生成sql语句增删改查并保存结果。

数据库连接池：HikariCP

流程： 

1. servlet收到http请求后实例化相应的服务类（XxxService）

2. 实例化服务类时，构造方法会调用XMLUtil进行第一次XML解析，获取数据库名、表名、和描述表的XML文件路径传入集成配置类（IntegrationSetting）的列表。

3. 服务类创建映射类（XxxMapper）实例，并传入数据库名、表名和XML文件路径。设置一次配置只能操作一个数据库的一张表，因此在服务类中要遍历配置类进行配置，
从而进行增删改查操作。

4. 在映射类（XxxMapper）中，通过反射获取JavaBean属性和XMLUtil的getColumnName（传入JavaBean属性名，工具类解析Xml获取数据库表中的对应字段名）方法动态
生成sql进行增产改查并保存数据。