package com.th.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Class : ResponseData
 * @Describe :
 * @Author : xSi
 * @Date : 2021/3/27 21:15
 * @Version : 1.0
 */
public class ResponseData {
    private  int code;
    private  String message;
    private  Map<String, Object> data = new HashMap<String, Object>();

    //属性是 final  不能有空参构造器

    private ResponseData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    //只有get方法

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    //扩展返回的json 数据，

    public ResponseData extendData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    //返回状态码 和信息

    public static ResponseData SUCCESS() {
        return new ResponseData(200, "返回成功");
    }
    public static ResponseData SUCCESS(int code,String message){
        return new ResponseData((code), message);
    }

    public static ResponseData FAIL() {
        return new ResponseData(0, "前端传入参数不符");
    }
    public static ResponseData FAIL(int code,String message){
        return new ResponseData((code), message);
    }
    public static ResponseData ERROR() {
        return new ResponseData(400, "后端代码错误");
    }
    public static ResponseData ERROR(int code,String message){
        return new ResponseData((code), message);
    }
/*
    //以下状态码 不常用

    public static ResponseData badRequest() {
        return new ResponseData(400, "服务器错误");
    }

    public static ResponseData forbidden() {
        return new ResponseData(403, "系统错误");
    }

    public static ResponseData unauthorized() {
        return new ResponseData(401, "未经授权");
    }

    public static ResponseData serverInternalError() {
        return new ResponseData(500, "服务器内部错误");
    }

    public static ResponseData customerError(String message) {
        return new ResponseData(1001, message);
    }
*/

}
