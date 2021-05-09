package service;

import com.th.entity.User;
import com.th.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class : UserServiceTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/9 09:28
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class UserServiceTest {
    @Autowired
    UserService userService;



    @Test
    public void listUserOrganizationStringByUserId(){
        User user= new User();
        user.setUserId(2388);
        user.setOrganizationId(224);
        List<User> objects = userService.listUserOrganizationStringByUserId(user);
        System.out.println(objects);
    }
    @Test
    public void listUserOrganizationStringByUserIdBatch(){
        User user1= new User();
        user1.setUserId(2388);
        user1.setOrganizationId(224);
        User user2= new User();
        user2.setUserId(1004);
        user2.setOrganizationId(201);
        User user3= new User();
        user3.setUserId(1011);
        user3.setOrganizationId(206);
        List<User> users=new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        List<User> objects = userService.listUserOrganizationStringByUserId(users);
        System.out.println(objects);

    }
    @Test
    public void listUserFullLikeKey(){
        String key ="校";
        List<User> objects = userService.listUserFullLikeKey(key);
        System.out.println(objects);
    }

    @Test
    public void getUserByOrganizationName(){
        String key ="校";
        List<User> userByOrganizationName = userService.getUserByOrganizationName(key);
        System.out.println(userByOrganizationName);

    }

}
