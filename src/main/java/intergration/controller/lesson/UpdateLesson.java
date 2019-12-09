package intergration.controller.lesson;

import com.alibaba.fastjson.JSON;
import intergration.Service.LessonService;
import intergration.Service.impl.LessonServiceImpl;
import intergration.entity.Lesson;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/13 20:45
 */
@WebServlet("/lesson/updateLesson")
public class UpdateLesson extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Lesson lesson = new Lesson(req.getParameter("lessonId"), req.getParameter("lessonName"),
                req.getParameter("teacherName"), req.getParameter("hours"));

        Map<String, Object> modelMap = new HashMap<String, Object>();
        LessonService lessonService = new LessonServiceImpl();
        boolean success = false;
        try {
            if(lessonService.updateLesson(lesson)){
                success = true;
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        modelMap.put("success", success);

        //数据转换成json向浏览器发送
        String outStr = JSON.toJSONString(modelMap);
        PrintWriter out = resp.getWriter();
        out.println(outStr);
        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        doPost(req, resp);
    }
}
