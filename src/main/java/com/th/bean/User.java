package com.th.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Component
public class User {
    private Integer userId;

    private String userPassword;

    private String userRole;

    private String userName;

    private String userTel;

/*
 @DatetimeFormat(pattern=“yyyy-MM-dd”)是将String转换成Date，一般前台给后台传值时用
 @JsonFormat(pattern=“yyyy-MM-dd”) 将Date转换成String 一般后台传值给前台时
 此处注意：@JsonFormat会让时间以0区时间显示。如果直接使用会少了8小时（我所在的是北京时区）修改为
 @JsonFormat(pattern=“yyyy-MM-dd”,timezone=“GMT+8”)
 @NumberFormat(pattern="#,###") 用来格式化货币（这样前端得传形如1,000。而不能是1000了哟，其实这个用得一般还比较少一点）
*/
    @NumberFormat
    private String userEmail;

    private String userSex;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date userBirth;

    private String userIdCard;

    @Autowired
    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public User(Integer userId, String userPassword, String userRole, String userName, String userTel,
                String userEmail, String userSex, Date userBirth, String userIdCard) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userName = userName;
        this.userTel = userTel;
        this.userEmail = userEmail;
        this.userSex = userSex;
        this.userBirth = userBirth;
        this.userIdCard = userIdCard;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole == null ? null : userRole.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel == null ? null : userTel.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex == null ? null : userSex.trim();
    }

    public Date getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(Date userBirth) {
        this.userBirth = userBirth;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard == null ? null : userIdCard.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userPassword='" + userPassword + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userName='" + userName + '\'' +
                ", userTel='" + userTel + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userBirth=" + userBirth +
                ", userIdCard='" + userIdCard + '\'' +
                '}';
    }
}