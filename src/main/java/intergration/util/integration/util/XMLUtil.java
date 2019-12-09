package intergration.util.integration.util;

import intergration.entity.User;
import intergration.util.integration.share.IntegrationSetting;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/10 21:19
 */
public class XMLUtil {
    /**
     * xml文件路径
     */
    private String xmlFilePath;
    /**
     * 配置文件
     */
    private String settingPath = "/setting.xml";

    /**
     * 设置需要查询的xml表的文件路径
     * @param xmlFilePath xml表路径
     */
    public void setXmlFilePath(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    public String getColumnName(String property) throws ParserConfigurationException, IOException, SAXException {
        String columnName = null;
        // 由xml文件创建文档对象
        Document document = getDocument(xmlFilePath);
        // 获得根节点<mapper>
        Element root = document.getDocumentElement();
        // 获得一级子节点列表
        NodeList firstLevelList = root.getChildNodes();
       find: for(int i = 0; i < firstLevelList.getLength(); i++){
            //获取一级子节点<resultMap>
            Node firstLevelNode = firstLevelList.item(i);
            if (firstLevelNode.getNodeType() == Node.ELEMENT_NODE){
                NodeList secondLevelList = firstLevelNode.getChildNodes();
                for(int j = 0; j < secondLevelList.getLength(); j++){
                    //获取二级子节点<属性>
                    Node secondLevelNode = secondLevelList.item(j);
                    //若xml中的标签与传入的属性名相同，则获取标签的值，结束循环
                    if (secondLevelNode.getNodeType() == Node.ELEMENT_NODE) {
                        if (property.equals(secondLevelNode.getNodeName())) {
                            columnName = secondLevelNode.getTextContent();
                            break find;
                        }
                    }
                }
            }
        }
        return columnName;
    }

    public String getColumnList() throws ParserConfigurationException, IOException, SAXException {
        StringBuilder columnList = new StringBuilder();
        columnList.append("(");
        // 由xml文件创建文档对象
        Document document = getDocument(xmlFilePath);
        // 获得根节点<mapper>
        Element root = document.getDocumentElement();
        // 获得一级子节点列表
        NodeList firstLevelList = root.getChildNodes();
        for(int i = 0; i < firstLevelList.getLength(); i++){
            //获取一级子节点<resultMap>
            Node firstLevelNode = firstLevelList.item(i);
            if (firstLevelNode.getNodeType() == Node.ELEMENT_NODE){
                NodeList secondLevelList = firstLevelNode.getChildNodes();
                for(int j = 0; j < secondLevelList.getLength(); j++){
                    //获取二级子节点<属性>
                    Node secondLevelNode = secondLevelList.item(j);
                    //若xml中的标签与传入的属性名相同，则获取标签的值，结束循环
                    if (secondLevelNode.getNodeType() == Node.ELEMENT_NODE) {
                        columnList.append(secondLevelNode.getTextContent() + ",");
                    }
                }
            }
        }
        columnList.replace(columnList.length()-1,columnList.length(),")");
        return columnList.toString();
    }

    public String getColPrimaryKey() throws IOException, SAXException, ParserConfigurationException {
        // 由xml文件创建文档对象
        Document document = getDocument(xmlFilePath);
        // 获得根节点<mapper>
        org.w3c.dom.Element root = document.getDocumentElement();
        // 获得一级子节点列表
        org.w3c.dom.NodeList firstLevelList = root.getChildNodes();
        for(int i = 0; i < firstLevelList.getLength(); i++){
            //获取一级子节点<resultMap>
            Node firstLevelNode = firstLevelList.item(i);
            if (firstLevelNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
                NodeList secondLevelList = firstLevelNode.getChildNodes();
                for(int j = 0; j < secondLevelList.getLength(); j++){
                    //获取二级子节点<属性>
                    Node secondLevelNode = secondLevelList.item(j);
                    //查找primaryKey属性
                    if (secondLevelNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE &&
                            "true".equals(secondLevelNode.getAttributes().getNamedItem("primaryKey").getTextContent())
                    ) {
                        return secondLevelNode.getTextContent();
                    }
                }
            }
        }
        return null;
    }

    public List<String> getDataBases() throws ParserConfigurationException, IOException, SAXException {
        List<String> databases = new ArrayList<String>();
        // 由setting文件创建文档对象
        org.w3c.dom.Document document = getDocument(settingPath);
        // 获得根节点<setting>
        org.w3c.dom.Element root = document.getDocumentElement();
        // 获得一级子节点列表
        org.w3c.dom.NodeList firstLevelList = root.getChildNodes();
        for(int i = 0; i < firstLevelList.getLength(); i++){
            //获取一级子节点<resultMap>
            org.w3c.dom.Node firstLevelNode = firstLevelList.item(i);
            //判断子节点类型
            if (firstLevelNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                NamedNodeMap namedNodeMap = firstLevelNode.getAttributes();
                //获取已知名为id的属性值
                String id = namedNodeMap.getNamedItem("id").getTextContent();
                databases.add(id);
            }
        }
        return databases;
    }

    public List<String> getXmlPath(String tableName) throws ParserConfigurationException, IOException, SAXException {
        List<String> xmlPath = new ArrayList<String>();
        // 由setting文件创建文档对象
        Document document = getDocument(settingPath);
        // 获得根节点<setting>
        Element root = document.getDocumentElement();
        // 获得一级子节点列表
        NodeList firstLevelList = root.getChildNodes();
        for(int i = 0; i < firstLevelList.getLength(); i++){
            //获取一级子节点<resultMap>
            Node firstLevelNode = firstLevelList.item(i);
            //判断子节点类型
            if (firstLevelNode.getNodeType() == Node.ELEMENT_NODE) {
                NodeList secondLevelList = firstLevelNode.getChildNodes();
                for(int j = 0; j < secondLevelList.getLength(); j++){
                    Node secondLevelNode = secondLevelList.item(j);
                    if(secondLevelNode.getNodeType() == Node.ELEMENT_NODE &&
                            tableName.equals(secondLevelNode.getAttributes().getNamedItem("id").getTextContent())){
                        xmlPath.add(secondLevelNode.getTextContent());
                    }
                }
            }
        }
        return xmlPath;
    }


    public boolean isVarchar(String columnName) throws IOException, SAXException, ParserConfigurationException {
        // 由xml文件创建文档对象
        Document document = getDocument(xmlFilePath);
        // 获得根节点<mapper>
        org.w3c.dom.Element root = document.getDocumentElement();
        // 获得一级子节点列表
        org.w3c.dom.NodeList firstLevelList = root.getChildNodes();
        for(int i = 0; i < firstLevelList.getLength(); i++){
            //获取一级子节点<resultMap>
            Node firstLevelNode = firstLevelList.item(i);
            if (firstLevelNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
                NodeList secondLevelList = firstLevelNode.getChildNodes();
                for(int j = 0; j < secondLevelList.getLength(); j++){
                    //获取二级子节点<属性>
                    Node secondLevelNode = secondLevelList.item(j);
                    //查找primaryKey属性
                    if (secondLevelNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE &&
                            columnName.equals(secondLevelNode.getTextContent()) ) {
                        return "VARCHAR".equalsIgnoreCase(secondLevelNode.getAttributes().getNamedItem("jdbcType").getTextContent());
                    }
                }
            }
        }
        return false;
    }


    public List<IntegrationSetting> getIntegrationSettingList(String className) throws ParserConfigurationException, IOException, SAXException {
        List<IntegrationSetting> integrationSettingList = new ArrayList<IntegrationSetting>();
        // 由setting文件创建文档对象
        Document document = getDocument(settingPath);
        // 获得根节点<setting>
        Element root = document.getDocumentElement();
        // 获得一级子节点列表
        NodeList firstLevelList = root.getChildNodes();
        for(int i = 0; i < firstLevelList.getLength(); i++){
            //获取一级子节点<database>
            Node firstLevelNode = firstLevelList.item(i);
            //判断子节点类型
            if (firstLevelNode.getNodeType() == Node.ELEMENT_NODE) {
                //创建集成配置类
                IntegrationSetting setting = new IntegrationSetting();
                //设置数据库名
                setting.setDatabaseName(firstLevelNode.getAttributes().getNamedItem("id").getTextContent());
                //获取二级子节点列表
                NodeList secondLevelList = firstLevelNode.getChildNodes();
                for(int j = 0; j < secondLevelList.getLength(); j++){
                    Node secondLevelNode = secondLevelList.item(j);
                    if(secondLevelNode.getNodeType() == Node.ELEMENT_NODE &&
                            className.equals(secondLevelNode.getAttributes().getNamedItem("entityType").getTextContent())){
                        setting.setTableName(secondLevelNode.getAttributes().getNamedItem("id").getTextContent());
                        setting.setXmlPath(secondLevelNode.getTextContent());
                    }
                }
                integrationSettingList.add(setting);
            }
        }
        return integrationSettingList;
    }


    private Document getDocument(String path) throws ParserConfigurationException, IOException, SAXException {
        // 相对路径解析
        InputStream instream = this.getClass().getResourceAsStream(path);
        // 创建文档构建器工厂(采用单例模式)
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 创建文档构建器
        DocumentBuilder builder = factory.newDocumentBuilder();
        // 根据path对应文件创建文档对象
        Document document = builder.parse(instream);
        return document;
    }
}
