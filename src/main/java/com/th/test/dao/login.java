package com.th.test.dao;

import com.th.bean.User;
import com.th.dao.UserDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Class : login
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/3 21:41
 * @Version : 1.0
 */
public class login {
    @Autowired
    UserDao userDao;

    @Test
    public void testLogin(){
        System.out.println("userService 走到 这一步了");
        Integer id=2110;
        String password= "897544";
        String role="T";
        User user = userDao.login(id, password, role);
        System.out.println(user);
    }

}
