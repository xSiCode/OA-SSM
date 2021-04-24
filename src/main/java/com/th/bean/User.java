package com.th.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;

@Component
public class User {

    @Pattern(regexp="(^[0-9]{4}$)" ,message="用户名长度只能是4位整数")
    private Integer userId;

    @Pattern(regexp="(^[a-zA-Z0-9_-]{6,16}$)",message="密码只能是6-16位的字母，数字")
    private String userPassword;

    @Pattern(regexp ="A|T" ,message = "权限只能是 管理员或教师")
    private String userRole;

    @Pattern(regexp = "(^[\u2E80-\u9FFF]{2,5})",message = "名字只能是2-5个中文汉字")
    private String userName;

    @Pattern(regexp = "^1[0-9]{10}$",message = "电话号码得是11位")
    private String userTel;

/*
 @DatetimeFormat(pattern=“yyyy-MM-dd”)是将String转换成Date，一般前台给后台传值时用
 @JsonFormat(pattern=“yyyy-MM-dd”) 将Date转换成String 一般后台传值给前台时
 此处注意：@JsonFormat会让时间以0区时间显示。如果直接使用会少了8小时（我所在的是北京时区）修改为
 @JsonFormat(pattern=“yyyy-MM-dd”,timezone=“GMT+8”)
 @NumberFormat(pattern="#,###") 用来格式化货币（这样前端得传形如1,000。而不能是1000了哟，其实这个用得一般还比较少一点）
*/
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{4,16}[a-zA-Z0-9]@[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$"
            ,message = "邮箱格式错误，前面是6-18位，后面也请输正确")
    private String userEmail;

    @Pattern(regexp ="男|女" ,message = "性别只能二选一")
    private String userSex;

   // 序列号 与反序列化 格式： “yyyy -MM -dd"
   @JsonDeserialize(using = LocalDateDeserializer.class)
   @JsonSerialize(using = LocalDateSerializer.class)
   //json -> 对象  ； 对象到json
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    private LocalDate userBirth;

    @Pattern(regexp = "^\\d{15}|^\\d{17}([0-9]|X|x)$",message = "身份证不对哦")
    private String userIdCard;

    private DutyInstitution userDutyInstitutionId;

    @Autowired
    public User() {
    }

    public User(Integer userId, String userPassword, String userRole) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    public User(Integer userId, String userPassword, String userRole, String userName, String userTel,
                String userEmail, String userSex, LocalDate userBirth, String userIdCard) {
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
    public User(Integer userId, String userPassword, String userRole, String userName, String userTel,
                String userEmail, String userSex, LocalDate userBirth,
                String userIdCard,DutyInstitution dutyInstitution) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userName = userName;
        this.userTel = userTel;
        this.userEmail = userEmail;
        this.userSex = userSex;
        this.userBirth = userBirth;
        this.userIdCard = userIdCard;
        this.userDutyInstitutionId=dutyInstitution;
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
        this.userPassword = userPassword;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public LocalDate getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(LocalDate userBirth) {
        this.userBirth = userBirth;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public DutyInstitution getDutyInstitution() {
        return userDutyInstitutionId;
    }

    public void setDutyInstitution(DutyInstitution dutyInstitution) {
        this.userDutyInstitutionId = dutyInstitution;
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
                ", dutyInstitution=" + userDutyInstitutionId +
                '}';
    }
}