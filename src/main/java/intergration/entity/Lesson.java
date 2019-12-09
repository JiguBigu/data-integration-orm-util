package intergration.entity;

import intergration.util.integration.ifs.IntegrationEntity;

import java.io.Serializable;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/13 11:54
 */
public class Lesson implements Serializable, IntegrationEntity {
    /**
     * 课程号
     */
    private String lessonId;
    /**
     * 课程名
     */
    private String lessonName;
    /**
     * 任课教师名
     */
    private String teacherName;
    /**
     * 课时
     */
    private String hours;

    public Lesson() {
    }

    public Lesson(String lessonId, String lessonName, String teacherName, String hours) {
        this.lessonId = lessonId;
        this.lessonName = lessonName;
        this.teacherName = teacherName;
        this.hours = hours;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId='" + lessonId + '\'' +
                ", lessonName='" + lessonName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", hours='" + hours + '\'' +
                '}';
    }

    public String getPrimary() {
        return "lessonId";
    }
}
