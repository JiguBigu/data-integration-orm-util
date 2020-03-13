package intergration.util.integration.util;

import intergration.util.integration.share.IntegrationSetting;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/11/29 21:11
 */
public class MapperUtil<T, P> {
    /**
     * 集成配置
     */
    private IntegrationSetting setting;
    /**
     * 单例连接池工具
     */
    private HikariCPUtil hikariCPUtil = HikariCPUtil.getHikariCPUtil();
    /**
     * 实体类class
     */
    private Class<T> tclass;
    /**
     * xml工具类
     */
    private XMLUtil xmlUtil = new XMLUtil();

    public MapperUtil(){};

    public MapperUtil(IntegrationSetting setting, Class<T> tclass){
        this.setting = setting;
        this.tclass = tclass;
        this.xmlUtil.setXmlFilePath(setting.getXmlPath());
    }

    public void setSetting(IntegrationSetting setting, Class<T> tclass) {
        this.setting = setting;
        this.tclass = tclass;
        this.xmlUtil.setXmlFilePath(setting.getXmlPath());
    }

    public List<T> getAll(){
        //创建查询结果列表
        List<T> resultList = new ArrayList<T>();
        //通过Hikari连接池获取连接
        Connection connection = hikariCPUtil.getConnect(setting.getDatabaseName());
        //生成查询语句
        String sql = "select * from " + setting.getTableName();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            T entity = null;
            while (resultSet.next()){
                setResultList(resultList, resultSet, xmlUtil);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public T getOne(P primaryKey){
        //创建查询结果列表
        List<T> result = new ArrayList<T>();
        //通过Hikari连接池获取连接
        Connection connection = hikariCPUtil.getConnect(setting.getDatabaseName());
        try {
            //配置sql语句
            String sql = "select * from " + setting.getTableName() + " where " +
                    xmlUtil.getColPrimaryKey() + " = " + primaryKey;
            //执行sql
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                setResultList(result, resultSet, xmlUtil);
            }
            //关闭连接
            connection.close();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.get(0);
    }

    public int insert(T entity){
        //创建插入结果标志
        int resultNum = 0;
        //通过Hikari连接池获取连接
        Connection connection = hikariCPUtil.getConnect(setting.getDatabaseName());
        //配置sql语句
        String sql = createInsertSql(entity);
        PreparedStatement statement;
        try {
            assert connection != null;
            statement = connection.prepareStatement(sql);
            resultNum = statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultNum;
    }

    public int update(T entity){
        //创建插入结果标志
        int resultNum = 0;
        //通过Hikari连接池获取连接
        Connection connection = hikariCPUtil.getConnect(setting.getDatabaseName());
        //配置sql语句
        String sql = createUpdateSql(entity);
        PreparedStatement statement;
        assert connection != null;
        try {
            statement = connection.prepareStatement(sql);
            resultNum = statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultNum;
    }

    public int delete(P primaryKey){
        //创建插入结果标志
        int resultNum = 0;
        //通过Hikari连接池获取连接
        Connection connection = hikariCPUtil.getConnect(setting.getDatabaseName());
        //配置sql语句
        String sql;
        try {
            sql = "DELETE FROM " + setting.getTableName() + " WHERE " + xmlUtil.getColPrimaryKey() + "=" + primaryKey;
            PreparedStatement statement = connection.prepareStatement(sql);
            resultNum = statement.executeUpdate();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultNum;
    }

    private String createInsertSql(T entity){
        StringBuilder colName = new StringBuilder();
        StringBuilder valueName = new StringBuilder();
        Field[] fields = entity.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                String name = field.getName();
                String col = xmlUtil.getColumnName(name);
                String methondName = name.substring(0, 1).toUpperCase() + name.substring(1);

                colName.append(col).append(",");
                Method method = tclass.getMethod("get" + methondName);
                String value = (String) method.invoke(entity,  null);
                if (xmlUtil.isVarchar(col)) {
                    valueName.append("'").append(value).append("',");
                } else {
                    valueName.append(value).append(",");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        String resCol = colName.substring(0, colName.length() - 1);
        String resValue = valueName.substring(0, valueName.length() - 1);
        return "insert into " + setting.getTableName() + " (" + resCol + ")" + " values " +
               "(" + resValue + ")";
    }

    private String createUpdateSql(T entity){
        String primaryValue = null;
        StringBuilder updateName = new StringBuilder();
        Field[] fields = entity.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                String name = field.getName();
                String col = xmlUtil.getColumnName(name);
                String methondName = name.substring(0, 1).toUpperCase() + name.substring(1);

                Method method = tclass.getMethod("get" + methondName);
                String value = (String) method.invoke(entity,  null);

                if(col.equalsIgnoreCase(xmlUtil.getColPrimaryKey())){
                    primaryValue = value;
                    continue;
                }
                updateName.append(col).append("=");
                if (xmlUtil.isVarchar(col)) {
                    updateName.append("'").append(value).append("',");
                } else {
                    updateName.append(value).append(",");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        String resUpdate = updateName.substring(0, updateName.length() - 1);
        String sql = "update " + setting.getTableName() +  " set " + resUpdate  + " where ";
        try {
            if(xmlUtil.isVarchar(xmlUtil.getColPrimaryKey())){
                sql += xmlUtil.getColPrimaryKey() + "=" + "'" + primaryValue + "'";
            }else {
                sql += xmlUtil.getColPrimaryKey() + "=" + primaryValue;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return sql;
    }

    private void setResultList(List<T> resultList, ResultSet resultSet, XMLUtil xmlUtil){
        T entity = null;
        try {
            entity =  tclass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Field[] fields = tclass.getDeclaredFields();
        try {
            for (Field field: fields) {
                String name = field.getName();
                String colName = xmlUtil.getColumnName(name);
                String methonName = name.substring(0, 1).toUpperCase() + name.substring(1);

                Class<?> type = tclass.getDeclaredField(name).getType();
                Method method = tclass.getMethod("set" + methonName, type);
                if (type.isAssignableFrom(String.class)) {
                    method.invoke(entity, resultSet.getString(colName));
                } else if (type.isAssignableFrom(int.class)) {
                    method.invoke(entity, resultSet.getInt(colName));
                } else if (type.isAssignableFrom(Boolean.class)) {
                    method.invoke(entity, resultSet.getBoolean(colName));
                } else if (type.isAssignableFrom(BigDecimal.class)) {
                    method.invoke(entity, resultSet.getBigDecimal(colName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        resultList.add(entity);
    }


}
