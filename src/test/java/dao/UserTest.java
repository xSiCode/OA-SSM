package dao;

import com.th.dao.UserMapper;
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
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        List<Map<Integer, String>> maps = userMapper.selectUsersIdNameByOrganizationId(204);

        System.out.println(maps);

    }
}
