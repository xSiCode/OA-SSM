package com.th.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.th.bean.User;
import com.th.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Class : PostJson
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/3 14:02
 * @Version : 1.0
 */

@Controller
public class TestPostJson {
    @Autowired
    User user;

    @PostMapping("adc/loginJson")
    @ResponseBody
    public ResponseData loginJson(@RequestBody String n){
        System.out.println("loginJson");
        System.out.println(n);
        return ResponseData.SUCCESS().extendData("n",n);
    }

    @PostMapping("adc/aObject")
    @ResponseBody
    public ResponseData aObject(@RequestBody User n){
        System.out.println("aObject");
        System.out.println(n);
        return ResponseData.SUCCESS().extendData("aUser",n);
    }
    @PostMapping("adc/aMap")
    @ResponseBody
    public ResponseData aMap(@RequestBody Map<String,Object> map) throws IOException {
        System.out.println("= = = = = = = = = = = RequestBody map = = = = = = = = = = = ");
        System.out.println(map);

        ObjectMapper mapper = new ObjectMapper();
        String json =mapper.writeValueAsString(map);
        System.out.println(json);
        user=(User) mapper.readValue( json,User.class);
        System.out.println(user);

        return ResponseData.SUCCESS().extendData("aMap",map);
    }
    @PostMapping("adc/aList")
    @ResponseBody
    public ResponseData aMap(@RequestBody List<User> users){

        for(int i=0;i<users.size();i++)
        {
            System.out.println( users.get(i));
        }
        System.out.println("aList");
        System.out.println(users);
        return ResponseData.SUCCESS().extendData("aList",users);
    }

}
