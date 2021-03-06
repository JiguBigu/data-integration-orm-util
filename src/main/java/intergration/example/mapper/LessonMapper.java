package intergration.example.mapper;

import intergration.example.entity.Lesson;
import intergration.util.integration.share.IntegrationSetting;
import intergration.util.integration.util.MapperUtil;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/13 20:54
 */
public class LessonMapper extends MapperUtil<Lesson,String> {
    public LessonMapper(IntegrationSetting setting, Class<Lesson> tclass) {
        super(setting, tclass);
    }
}
