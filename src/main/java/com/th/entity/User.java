package com.th.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
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
    private LocalDate userBirth;

    /**
     * 18位身份证
     */
    private String userIdCard;

    /**
     * 外键，职位id
     */
    private Integer organizationId;

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

    @Override
    public String toString() {
        return "User{" +
        "userId=" + userId +
        ", userPassword=" + userPassword +
        ", userRole=" + userRole +
        ", userName=" + userName +
        ", userTel=" + userTel +
        ", userEmail=" + userEmail +
        ", userSex=" + userSex +
        ", userBirth=" + userBirth +
        ", userIdCard=" + userIdCard +
        ", organizationId=" + organizationId +
        "}";
    }
}
