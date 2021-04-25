package com.th.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.stereotype.Component;

/**
 * @Class : OrganizationUser
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/25 22:00
 * @Version : 1.0
 */
@Component
@TableName(value = "f_organization_user")
public class OrganizationUser {
    @TableId(value = "id",type = IdType.UUID)
    private Integer id;
    private Integer userId;
    private Integer organizationId;

    @TableField(exist = false)
    private String userName;

    public OrganizationUser() {
    }

    public OrganizationUser(Integer id, Integer userId, Integer organizationId, String userName) {
        this.id = id;
        this.userId = userId;
        this.organizationId = organizationId;
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "OrganizationUser{" +
                "id=" + id +
                ", userId=" + userId +
                ", organizationId=" + organizationId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
