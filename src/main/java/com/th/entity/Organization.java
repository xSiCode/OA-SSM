package com.th.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-07
 */
@TableName("t_organization")
@Component
public class Organization implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer pid;

    private Integer level;

    private String name;

    //当前职位id 下的 子路径
    //使用@JsonIgnore注解，忽略此属性，前端不会拿到该属性
    @JsonIgnore
    @TableField(exist = false)
    private List<Organization> children;

    //当前职位id 对应的所有 用户id,用户名
    //使用@JsonIgnore注解，忽略此属性，前端不会拿到该属性
    @JsonIgnore
    @TableField(exist = false)
    private Map<Integer,String> userId_userName;

    public Organization() {
    }

    public Organization(Integer id, Integer pid, Integer level, String name) {
        this.id = id;
        this.pid = pid;
        this.level = level;
        this.name = name;
    }

    public Organization(Integer id, Integer pid, Integer level, String name, List<Organization> children) {
        this.id = id;
        this.pid = pid;
        this.level = level;
        this.name = name;
        this.children = children;
    }

    public Organization(Integer id, Integer pid, Integer level, String name,
                        List<Organization> children, Map<Integer, String> userId_userName) {
        this.id = id;
        this.pid = pid;
        this.level = level;
        this.name = name;
        this.children = children;
        this.userId_userName = userId_userName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Organization> getChildren() {
        return children;
    }

    public void setChildren(List<Organization> children) {
        this.children = children;
    }

    public Map<Integer, String> getUserId_userName() {
        return userId_userName;
    }

    public void setUserId_userName(Map<Integer, String> userId_userName) {
        this.userId_userName = userId_userName;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", pid=" + pid +
                ", level=" + level +
                ", name='" + name + '\'' +
                '}';
    }
}
