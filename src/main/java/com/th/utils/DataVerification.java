package com.th.utils;

/**
 * @Class : DataVerification
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/11 16:36
 * @Version : 1.0
 */
public class DataVerification {

    public static boolean IfStrParseInt(String str){
        if(str == null || str==""){ //验证是否为空
            return false;
        }
 /*使用正则表达式判断该字符串是否为数字，第一个\是转义符，\d+表示匹配1个或多个连续数字，"+"和"*"类似，"*"表示0个或多个*/
        return str.matches("\\d+");

    }

}
