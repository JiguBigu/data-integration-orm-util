package intergration.Service;

import intergration.entity.User;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/24 16:13
 */
public interface UserService {
    List<User> getAllUser() throws ParserConfigurationException, SAXException, IOException;

    User getUserById(String id) throws ParserConfigurationException, SAXException, IOException;

    boolean insertUser(User user, String databaseName) throws ParserConfigurationException, SAXException, IOException;

    boolean updateUser(User user) throws IOException, ParserConfigurationException, SAXException, IllegalAccessException;

    boolean deleteUserById(String id) throws ParserConfigurationException, SAXException, IOException ;

}