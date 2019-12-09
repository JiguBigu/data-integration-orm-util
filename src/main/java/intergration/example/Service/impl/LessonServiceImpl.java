package intergration.example.Service.impl;

import intergration.example.Service.LessonService;
import intergration.example.entity.Lesson;
import intergration.example.mapper.LessonMapper;
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
 * @date 2019/10/13 21:05
 */
public class LessonServiceImpl implements LessonService {
    private List<IntegrationSetting> integrationSettingList;
    private static final String INSERT_ALL_FLAG= "所有库";
    private final String CLASS_NAME = Lesson.class.getName();
    public LessonServiceImpl() {
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
     * 获取所有数据库中的课程数据
     * @return 课程列表
     */
    public List<Lesson> getAllLesson() throws ParserConfigurationException, SAXException, IOException {
        List<Lesson> lessonList = new ArrayList<Lesson>();
        for(IntegrationSetting setting: integrationSettingList){
            LessonMapper lessonMapper = new LessonMapper(setting, Lesson.class);
            lessonList.addAll(lessonMapper.getAll());
        }
        return lessonList;
    }

    /**
     * 通过课程号查询课程信息
     * @param id 课程
     * @return 课程
     */
    public Lesson getLessonById(String id) throws ParserConfigurationException, SAXException, IOException {
        Lesson lesson;
        for(IntegrationSetting setting: integrationSettingList){
            LessonMapper lessonMapper = new LessonMapper(setting, Lesson.class);
            lesson = lessonMapper.getOne(id);
            if(lesson.getLessonId() != null && lesson.getLessonId().length() > 0){
                return  lesson;
            }
        }
        return null;
    }

    /**
     * 向数据库中插入课程信息
     * @param lesson 课程实体
     * @param databaseName 数据库名
     * @return 成功标志
     */
    public boolean insertLesson(Lesson lesson, String databaseName) throws ParserConfigurationException, SAXException, IOException {
        if (databaseName == null || databaseName.length() <= 0){
            throw new RuntimeException("未指定插入数据库");
        }
        if (lesson.getLessonId() == null || lesson.getLessonId().length() <= 0){
            throw new RuntimeException("未指定插入的学号");
        }
        //判断是否插入所有数据库
        if(INSERT_ALL_FLAG.equals(databaseName)){
            int count = 0;
            for(IntegrationSetting setting: integrationSettingList){
                LessonMapper lessonMapper = new LessonMapper(setting, Lesson.class);
                count += lessonMapper.insert(lesson);
            }
            if(count == integrationSettingList.size()){
                return true;
            }
        }
        //插入指定数据库
        else{
            if(("华中农业大学".equals(databaseName))){
                databaseName = integrationSettingList.get(0).getDatabaseName();
            } else {
                databaseName = integrationSettingList.get(1).getDatabaseName();
            }
            for(IntegrationSetting setting: integrationSettingList){
                if(databaseName.equals(setting.getDatabaseName())){
                    LessonMapper lessonMapper = new LessonMapper(setting, Lesson.class);
                    if(lessonMapper.insert(lesson) > 0){
                        return  true;
                    }else{
                        throw new RuntimeException("插入课程数据失败");
                    }
                }
            }
        }
        return false;
    }

    /**
     * 更新课程信息
     * @param lesson 课程实体
     * @return 成功标志
     */
    public boolean updateLesson(Lesson lesson) throws IOException, ParserConfigurationException, SAXException, IllegalAccessException {
        if (lesson.getLessonId() == null || lesson.getLessonId().length() <= 0){
            throw new RuntimeException("更新错误：未传入课程id");
        }
        for(IntegrationSetting setting: integrationSettingList){
            LessonMapper lessonMapper = new LessonMapper(setting, Lesson.class);
            if(lessonMapper.update(lesson) > 0){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据课程号删除课程
     * @param id 课程号
     * @return 成功标志
     */
    public boolean deleteLessonById(String id) throws ParserConfigurationException, SAXException, IOException {
        if (id == null || id.length() <= 0){
            throw new RuntimeException("删除错误：未传入课程id");
        }
        for(IntegrationSetting setting: integrationSettingList){
            LessonMapper lessonMapper = new LessonMapper(setting, Lesson.class);
            if(lessonMapper.delete(id) > 0){
                return true;
            }
        }
        return false;
    }

}
