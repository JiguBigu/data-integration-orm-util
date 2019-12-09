package intergration.example.Service.impl;

import intergration.example.Service.UserService;
import intergration.example.entity.User;
import intergration.example.mapper.UserMapper;
import intergration.util.integration.share.IntegrationSetting;
import intergration.util.integration.util.XMLUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/10 21:12
 */
public class UserServiceImpl implements UserService {
    private List<IntegrationSetting> integrationSettingList;
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
    public List<User> getAllUser() throws ParserConfigurationException, SAXException, IOException {
        List<User> userList = new ArrayList<User>();
        for(IntegrationSetting setting: integrationSettingList){
            UserMapper userMapper = new UserMapper(setting, User.class);
            userList.addAll(userMapper.getAll());
        }
        return userList;
    }

    /**
     * 通过用户学号查询用户信息
     * @param id 学号
     * @return 学生
     */
    public User getUserById(String id) throws ParserConfigurationException, SAXException, IOException {
        User user;
        for(IntegrationSetting setting: integrationSettingList){
            UserMapper userMapper = new UserMapper(setting, User.class);
            user = userMapper.getOne(id);
            if(user.getId() != null && user.getId().length() > 0){
                return user;
            }
        }
        return null;
    }

    /**
     * 向数据库中插入学生信息
     * @param user 学生实体
     * @param databaseName 数据库名
     * @return 成功标志
     */
    public boolean insertUser(User user, String databaseName) throws ParserConfigurationException, SAXException, IOException {
        if (databaseName == null || databaseName.length() <= 0){
            throw new RuntimeException("未指定数据库");
        }
        if(user.getId() == null || user.getId().length() <= 0){
            throw new RuntimeException("为指定插入学生的学号");
        }
        if(("华中农业大学".equals(databaseName))){
            databaseName = integrationSettingList.get(0).getDatabaseName();
        }else {
            databaseName = integrationSettingList.get(1).getDatabaseName();
        }

        for(IntegrationSetting setting: integrationSettingList){
            if(databaseName.equals(setting.getDatabaseName())) {
                UserMapper userMapper = new UserMapper(setting, User.class);
                if(userMapper.insert(user) > 0){
                    return  true;
                }else{
                    throw new RuntimeException("插入数据失败");
                }
            }
        }
        throw new RuntimeException("无匹配的数据库");
    }

    /**
     * 更新学生信息
     * @param user 学生实体
     * @return 成功标志
     */
    public boolean updateUser(User user) throws IOException, ParserConfigurationException, SAXException, IllegalAccessException {
        if (user.getId() == null || user.getId().length() <= 0){
            throw new RuntimeException("更新错误：未传入用户id");
        }
        for(IntegrationSetting setting: integrationSettingList){
            UserMapper userMapper = new UserMapper(setting, User.class);
            if(userMapper.update(user) > 0){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据学号删除学生
     * @param id 学号
     * @return 成功标志
     */
    public boolean deleteUserById(String id) throws ParserConfigurationException, SAXException, IOException {
        if (id == null || id.length() <= 0){
            throw new RuntimeException("删除错误：未传入用户id");
        }
        for(IntegrationSetting setting: integrationSettingList){
            UserMapper userMapper = new UserMapper(setting, User.class);
            if(userMapper.delete(id) > 0){
                return true;
            }
        }
        return false;
    }
}
