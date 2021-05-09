package com.th.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-07
 */
@TableName("t_user")
@Component
public class User implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * //6-16位 ，只能是 字母(a-z ，A-Z) ，数字(0-9)
     */
    private String userPassword;

    /**
     *  A  T   枚举类型
     */
    private String userRole;

    /**
     * 姓名，2-5位
     */
    private String userName;

    /**
     * 以 1 开头的 11个数字
     */
    private String userTel;

    /**
     * 必须是 邮箱格式， 累计长度（ 12-24 ）个字符    前缀至少6个字符，后缀至少6个字符
     */
    private String userEmail;

    /**
     * 男，女
     */
    private String userSex;

    /**
     * 出生日期范围和格式   1900-1-1  ~~~ 2100-1-1
     */
    // 序列号 与反序列化 格式： “yyyy -MM -dd"
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    //json -> 对象  ； 对象到json
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    private LocalDate userBirth;

    /**
     * 18位身份证
     */
    private String userIdCard;

    /**
     * 外键，职位id
     */
    private Integer organizationId;
    //表示,如果值为null,则不返回 不封装为json,不序列号使用
    // @JsonInclude 注解可以控制在哪些情况下才将被注解的属性转换成 json，例如只有属性不为 null 时。
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String organizationName;

    public User() {
    }

    public User(Integer userId, String userPassword, String userRole,
                String userName, String userTel, String userEmail,
                String userSex, LocalDate userBirth, String userIdCard,
                Integer organizationId) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userName = userName;
        this.userTel = userTel;
        this.userEmail = userEmail;
        this.userSex = userSex;
        this.userBirth = userBirth;
        this.userIdCard = userIdCard;
        this.organizationId = organizationId;
    }
    public User(Integer userId, String userPassword, String userRole, String userName, String userTel,
                String userEmail, String userSex, LocalDate userBirth, String userIdCard,
                Integer organizationId, String organizationName) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userName = userName;
        this.userTel = userTel;
        this.userEmail = userEmail;
        this.userSex = userSex;
        this.userBirth = userBirth;
        this.userIdCard = userIdCard;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
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

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
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
                ", organizationId=" + organizationId +
                ", organizationName='" + organizationName + '\'' +
                '}';
    }
}
