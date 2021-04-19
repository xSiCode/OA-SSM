package com.th.test.service;

import com.th.bean.User;
import com.th.dao.UserDao;
import com.th.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Class : UserService
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/11 10:20
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})

public class UserServiceTest {
    @Autowired
    User user;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;


    @Test
    public void selectByKey(){
        List<User> users = userService.selectByKey("1999");

        System.out.println(users);

    }
    @Test
    public void insert(){
        int insert = userService.insert(user);
        System.out.println(user);
        System.out.println(insert);
    }
    @Test
    public void update() throws Exception{
        user.setUserPassword("98765432呀1");
        user.setUserId(2502);
        LocalDate localDate=LocalDate.of(2222,11,11);
        user.setUserBirth(localDate);
        int i= userService.update(user);
        System.out.println(i+":"+user);
    }
    @Test
    public void deleteBatch(){
        List<Integer> ids=new ArrayList<>();
        ids.add(2498);
        ids.add(2499);
        ids.add(2501);
        ids.add(2502);
        int i = userDao.deleteBatch(ids);
        System.out.println(i+":"+ids);
    }
}
