package intergration.controller.user;

import com.alibaba.fastjson.JSONObject;
import intergration.Service.UserService;
import intergration.Service.impl.UserServiceImpl;
import intergration.entity.User;
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
import java.util.List;
import java.util.Map;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/10 19:22
 */
@WebServlet("/user/getAllUser")
public class GetAllUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        UserService userService = new UserServiceImpl();
        List<User> users = null;
        try {
            users = userService.getAllUser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        modelMap.put("user", users);
        if(users == null){
            modelMap.put("success", false);
        }else {
            modelMap.put("success", true);
        }

        //数据转换成json向浏览器发送
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html,charset=UTF-8");
        JSONObject data = new JSONObject(modelMap);
        PrintWriter out = resp.getWriter();
        out.println(data);
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
