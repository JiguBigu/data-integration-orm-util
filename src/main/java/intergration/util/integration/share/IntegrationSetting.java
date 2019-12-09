package intergration.util.integration.share;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/24 22:01
 */
public class IntegrationSetting {
    /**
     * 数据库名
     */
    private String databaseName;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 描述表的XML文件路径
     */
    private String xmlPath;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    @Override
    public String toString() {
        return "IntegrationSetting{" +
                "databaseName='" + databaseName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", xmlPath='" + xmlPath + '\'' +
                '}';
    }
}
