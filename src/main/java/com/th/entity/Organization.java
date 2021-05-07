package com.th.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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

    /**
     * 当前机构/职位id    没再额外使用自增id    id值 全局唯一
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 上一级机构id
     */
    private Integer pid;

    /**
     * 当前机构/职位等级
     */
    private Integer level;

    /**
     * 当前机构/职位 名
     */
    private String name;

    //当前职位id 下的 子路径
    @TableField(exist = false)
    private List<Organization> children;

    //当前id的完整 父路径
    @TableField(exist = false)
    private List<Organization> parent;

    //当前职位id 对应的 用户
    @TableField(exist = false)
    private List<User> users;


    public Organization() {
    }

    public Organization(Integer id, Integer pid, Integer level, String name) {
        this.id = id;
        this.pid = pid;
        this.level = level;
        this.name = name;
    }

    public Organization(Integer id, Integer pid, Integer level, String name,
                        List<Organization> children, List<Organization> parent, List<User> users) {
        this.id = id;
        this.pid = pid;
        this.level = level;
        this.name = name;
        this.children = children;
        this.parent = parent;
        this.users = users;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Organization> getChildren() {
        return children;
    }

    public void setChildren(List<Organization> children) {
        this.children = children;
    }

    public List<Organization> getParent() {
        return parent;
    }

    public void setParent(List<Organization> parent) {
        this.parent = parent;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Organization{" +
        "id=" + id +
        ", pid=" + pid +
        ", level=" + level +
        ", name=" + name +
        "}";
    }
}
