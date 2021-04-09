package com.th.test;

import com.th.bean.User;
//最让我迷惑的是，Test包，只能是倒入这个包org.junit.Test
import com.th.dao.UserDao;
import com.th.service.UserService;
import com.th.service.imp.UserServiceImpl;
import com.th.utils.ResponseData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Class : crud
 * @Describe :
 * @Author : xSi
 * @Date : 2021/3/19 16:34
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class crud {

    @Autowired
    User user;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;
    @Test
    public void login(){
        System.out.println("userService 走到 这一步了");
        Integer id=2110;
        String password= "897544";
        String role="T";
        user = userService.userLogin(id, password, role);
        System.out.println(user);
        if(user !=null){
            System.out.println("登录成功");

          //  return ResponseData.SUCCESS().extendData("user",userMap);
        }else {
            System.out.println("查无此人");
            System.out.println("2查无此人");
            System.out.println("3查无此人");
            System.out.println("4查无此人");
            System.out.println("5查无此人");
           // return ResponseData.ERROR();
        }
    }


}
