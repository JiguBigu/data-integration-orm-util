package intergration.mapper;


import intergration.entity.User;
import intergration.util.integration.share.IntegrationSetting;
import intergration.util.integration.util.MapperUtil;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2019/10/13 15:10
 */
public class UserMapper extends MapperUtil<User, String> {

    public UserMapper() {
    }

    public UserMapper(IntegrationSetting setting, Class<User> tclass) {
        super(setting, tclass);
    }
}
