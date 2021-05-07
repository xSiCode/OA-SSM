package com.th.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.th.entity.User;
import com.th.service.UserService;
import com.th.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-07
 */
@RestController()
@RequestMapping("/user")
public class UserController {
    @Autowired(required = false)
    private UserService userService;

    @PostMapping("/getUsersList")
    public ResponseData getUsersList(@RequestBody Map<String,Object> map){

        PageHelper.startPage(  (int)map.get("currentPage"),8);
        // List<User> users = userService.getUsersList();
        List<User> users = userService.list();

        PageInfo page= new PageInfo(users,8);
        return ResponseData.SUCCESS().extendData("usersListPageInfo",page);
    }
    @PostMapping("/a")
    public ResponseData a(){

        System.out.println("------------------------------------");
        PageHelper.startPage(  3,8);
        // List<User> users = userService.getUsersList();
        List<User> users = userService.list();

        PageInfo page= new PageInfo(users,8);
        return ResponseData.SUCCESS().extendData("usersListPageInfo",page);
    }

}

