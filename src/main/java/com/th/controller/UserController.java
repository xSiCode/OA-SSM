package com.th.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.th.bean.User;
import com.th.service.UserService;
import com.th.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Class : UserController
 * @Describe :
 * @Author : xSi
 * @Date : 2021/3/27 23:15
 * @Version : 1.0
 */

@CrossOrigin()
@Controller
public class UserController {

        @Autowired(required = false)
        private UserService userService;
        @Autowired(required = false)
        private User user;


    @PostMapping("/user/login")
    @ResponseBody
    public ResponseData login(@RequestBody Map<String,Object> userMap , HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

        Integer id =Integer.parseInt(String.valueOf(userMap.get("userId")));
        String password=String.valueOf(userMap.get("userPassword"));
        String role = String.valueOf(userMap.get("userRole"));

        user = userService.userLogin(id, password, role);
        System.out.println(user);
        if(user !=null){
            System.out.println("登录成功");
            return ResponseData.SUCCESS().extendData("userInfo",user);
        }else {
            System.out.println("账号或密码错误");
            return ResponseData.ERROR();
        }

    }

    @PostMapping("/user/getUsersList")
    @ResponseBody
    public ResponseData getUsersList(@RequestBody Map<String,Object> map , HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

        PageHelper.startPage(  (int)map.get("currentPage"),8);
        List<User> users = userService.getUsersList();

        PageInfo page= new PageInfo(users,8);
        return ResponseData.SUCCESS().extendData("usersListPageInfo",page);
    }


    @PostMapping("/user/getUserIdOrNameLike")
    @ResponseBody
    public ResponseData getUserNameLike(@RequestBody Map<String,Object> map , HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

        userService.getUserByIdOrName(  (String) map.get("searchUserIdOrNameLike")  );

        PageHelper.startPage( 8);
        List<User> users = userService.getUsersList();

        PageInfo page= new PageInfo(users,8);
        return ResponseData.SUCCESS().extendData("usersListPageInfo",page);
    }

}
