package com.th.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.th.bean.User;
import com.th.service.UserService;
import com.th.utils.ResponseData;
import org.apache.ibatis.javassist.compiler.ast.FieldDecl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Class : UserController
 * @Describe :
 * @Author : xSi
 * @Date : 2021/3/27 23:15
 * @Version : 1.0
 */

@CrossOrigin(origins = "*")
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


    @PostMapping("user/selectByKey")
    @ResponseBody
    public ResponseData selectBykey(@RequestBody Map<String,String> map, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

        String searchKey=map.get("searchKey");
        Integer needPage= Integer.parseInt( map.get("needPage") );

        PageHelper.startPage( needPage,8);
        List<User> users =userService.selectByKey(searchKey);
        PageInfo pageInfo=new PageInfo(users,8);
        return ResponseData.SUCCESS().extendData("UserListPage",pageInfo);

    }
    @PostMapping("user/insert")
    @ResponseBody
    public ResponseData insert(@RequestBody Map<String,String> map, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        //ObjectMapper  将传入的 json 字符串 自动转为 User对象
        ObjectMapper mapper = new ObjectMapper();
        //
        System.out.println(map);

        String dateStr= mapper.writeValueAsString(map);
        user=(User) mapper.readValue( dateStr,User.class);
        System.out.println(user);
        int insert = userService.insert(user);
        if(insert<=0){
            return ResponseData.ERROR();
        }
        System.out.println(insert);
        return ResponseData.SUCCESS().extendData("insertUserId",insert);
    }
    @PostMapping("user/update")
    @ResponseBody
    public ResponseData update(@RequestBody Map<String,String> map, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

            ObjectMapper mapper = new ObjectMapper();
            String dateStr= mapper.writeValueAsString(map);
            user=(User) mapper.readValue(dateStr,User.class);


        int update = userService.update(user);
        if(update<0){
            return ResponseData.ERROR();
        }
        return ResponseData.SUCCESS().extendData("updateUser",user);
    }
    @PostMapping("user/deleteBatch")
    @ResponseBody
    public ResponseData deleteBatch(@RequestBody Map<String,List<Integer> >  map_list, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

        System.out.println(map_list);
        ObjectMapper mapper=new ObjectMapper();

        String dataStr=mapper.writeValueAsString( map_list.get("ids") );
        List<Integer> ids = (List<Integer>) mapper.readValue(dataStr,List.class);

        int deleteBatch = userService.deleteBatch(ids);
        System.out.println(deleteBatch);
        if(deleteBatch<=0){
            return ResponseData.ERROR();
        }
        return ResponseData.SUCCESS().extendData("AffectNums",deleteBatch);
    }


}
