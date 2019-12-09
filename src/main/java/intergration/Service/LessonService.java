package intergration.Service;

import intergration.entity.Lesson;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/24 16:18
 */
public interface LessonService {

    List<Lesson> getAllLesson() throws ParserConfigurationException, SAXException, IOException;

    Lesson getLessonById(String id) throws ParserConfigurationException, SAXException, IOException;

    boolean insertLesson(Lesson lesson, String databaseName) throws ParserConfigurationException, SAXException, IOException;

    boolean updateLesson(Lesson lesson) throws IOException, ParserConfigurationException, SAXException, IllegalAccessException;

    boolean deleteLessonById(String id) throws ParserConfigurationException, SAXException, IOException;
}
