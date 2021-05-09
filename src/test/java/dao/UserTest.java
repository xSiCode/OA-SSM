package dao;

import com.th.dao.UserMapper;
import com.th.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * @Class : UserTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/8 22:34
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class UserTest {
    Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        List<Map<Integer, String>> maps = userMapper.selectUsersIdNameByOrganizationId(204);
        log.info("klsdddddddddd");
        System.out.println(maps);

    }

    @Test
    public void selectUsersFullLikeKey(){
        String s="干事";
        List<User> users = userMapper.selectUsersFullLikeKey(s);
        System.out.println(users.size());
        System.out.println(users);

    }
}
