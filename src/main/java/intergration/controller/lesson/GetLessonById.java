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
 * @date 2019/10/13 20:30
 */
@WebServlet("/lesson/getLessonById")
public class GetLessonById extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("lessonId");

        LessonService lessonService = new LessonServiceImpl();
        Map<String, Object> modelMap = new HashMap<String, Object>();
        boolean success = false;
        Lesson lesson = null;
        try {
            lesson = lessonService.getLessonById(id);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        if(lesson != null){
            success = true;
        }
        modelMap.put("success", success);
        modelMap.put("lesson", lesson);

        //数据转换成json向浏览器发送
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html,charset=UTF-8");
        String outStr = JSON.toJSONString(modelMap);
        PrintWriter out = resp.getWriter();
        out.println(outStr);
        out.flush();
        out.close();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
