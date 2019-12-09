package intergration.controller.user;

import com.alibaba.fastjson.JSON;
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
import java.util.Map;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/13 20:50
 */
@WebServlet("/user/insertUser")
public class InsertUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User(req.getParameter("userId"), req.getParameter("userName"),
                req.getParameter("userSex"), req.getParameter("className"));
        String databaseName = req.getParameter("databaseName");

        boolean success = false;
        UserService userService = new UserServiceImpl();
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            if(userService.insertUser(user, databaseName)){
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
}
