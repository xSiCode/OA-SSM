package com.th.controller;

import com.th.bean.User;
import com.th.utils.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Class : PostJson
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/3 14:02
 * @Version : 1.0
 */

@CrossOrigin(origins = "*")
@Controller
public class TestPostJson {
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
    public ResponseData aMap(@RequestBody Map<String,Object> n){
        System.out.println("aObject");
        System.out.println(n);
        return ResponseData.SUCCESS().extendData("aMap",n);
    }
    @PostMapping("adc/aList")
    @ResponseBody
    public ResponseData aMap(@RequestBody List<User> n){

        for(int i=0;i<n.size();i++)
        {
            System.out.println( n.get(i));
        }
        System.out.println("aList");
        System.out.println(n);
        return ResponseData.SUCCESS().extendData("aList",n);
    }

}
