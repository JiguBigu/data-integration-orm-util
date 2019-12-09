package intergration.entity;

import intergration.util.integration.ifs.IntegrationEntity;

import java.io.Serializable;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/10 18:48
 */
public class User implements Serializable, IntegrationEntity {
    /**
     * 学号
     */
    private String id;
    /**
     * 姓名
     */
    private String userName;

    /**
     * 性别
     */
    private String userSex;

    /**
     * 班级
     */
    private String className;

    public User() {
    }

    public User(String id, String userName, String userSex, String className) {
        this.id = id;
        this.userName = userName;
        this.userSex = userSex;
        this.className = className;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Student{" +
                "userName='" + userName + '\'' +
                ", id='" + id + '\'' +
                ", userSex='" + userSex + '\'' +
                ", className='" + className + '\'' +
                '}';
    }

    public String getPrimary() {
        return "id";
    }
}
