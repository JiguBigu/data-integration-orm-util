package intergration.example.controller.lesson;

import com.alibaba.fastjson.JSON;
import intergration.example.Service.LessonService;
import intergration.example.Service.impl.LessonServiceImpl;
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
 * @date 2019/10/13 20:42
 */
@WebServlet("/lesson/deleteLessonById")
public class DeleteLessonById extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("lessonId");

        LessonService lessonService = new LessonServiceImpl();
        Map<String, Object> modelMap = new HashMap<String, Object>();
        boolean success = false;
        try {
            if(lessonService.deleteLessonById(id)){
                success = true;
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
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
        doPost(req, resp);
    }
}
