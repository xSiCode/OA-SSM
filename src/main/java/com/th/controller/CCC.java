package com.th.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.th.entity.User;
import com.th.service.UserService;
import com.th.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Class : CCC
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/7 02:56
 * @Version : 1.0
 */
@Controller
public class CCC {

    @Autowired(required = false)
    private UserService userService;

    @ResponseBody
    @GetMapping("a/b")
    public ResponseData b(){

        System.out.println("------------------------------------");
        PageHelper.startPage(  3,8);
        List<User> users = userService.list();

        PageInfo page= new PageInfo(users,8);
        return ResponseData.SUCCESS().extendData("usersListPageInfo",page);
    }

}
