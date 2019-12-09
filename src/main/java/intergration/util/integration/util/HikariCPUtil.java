package intergration.util.integration.util;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/26 17:28
 */
public  class HikariCPUtil {

    /**
     * 日志工具
     */
    private final static Logger logger = LoggerFactory.getLogger(HikariCPUtil.class);
    /**
     * 数据库用户名
     */
    private final static String USERNAME = "root";
    /**
     * 数据库密码
     */
    private final static String PASSWORD = "";
    /**
     * 连接的url
     */
    private static String URL;
    /**
     * 所有数据源
     */
    private static Map<String, HikariDataSource> dataSources = new HashMap<String, HikariDataSource>();
    /**
     * 单例模式的线程池工具类实例
     */
    private static HikariCPUtil hikariCPUtil;

    private HikariCPUtil(){}

    public static HikariCPUtil getHikariCPUtil() {
        if (hikariCPUtil == null) {
            synchronized (HikariCPUtil.class){
                hikariCPUtil = new HikariCPUtil();
                List<String> databases = null;
                try {
                    databases = new XMLUtil().getDataBases();
                    for (String database : databases) {
                        setDataBase(database);
                        HikariConfig hikariConfig = getHikariConfig();
                        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
                        dataSources.put(database, dataSource);
                    }
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
            }
        }
        return hikariCPUtil;
    }

    public  Connection getConnect(String database) {
        HikariDataSource hikariDataSource = dataSources.get(database);
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info( "数据库：" + database + "--获取连接失败");
        }
        return null;
    }

    private static void setDataBase(String dataBase){
        URL = "jdbc:mysql://localhost:3306/"+ dataBase + "?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true";
    }

    /**
     * 配置Hikari数据库连接池
     * @return Hikari配置
     */
    private static HikariConfig getHikariConfig(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(URL);
        hikariConfig.setUsername(USERNAME);
        hikariConfig.setPassword(PASSWORD);
        hikariConfig.setReadOnly(false);
        return  hikariConfig;
    }

}
