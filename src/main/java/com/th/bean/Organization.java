package com.th.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Class : Organization
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/24 17:01
 * @Version : 1.0
 */

@Component
@TableName(value = "t_organization")
public class Organization {
    @TableId(value = "id",type = IdType.NONE)
    private int id;
    private int pid;
    private int level;
    private String name;

    //当前id 下的子路径
    @TableField(exist = false)
    private List<Organization> children;

    //当前id 的完整路径
    @TableField(exist = false)
    private List<Integer> organizationLevelId;


    public Organization() {
    }

    public Organization(int id, int pid, int level, String name, List<Organization> children, List<Integer> organizationLevelId) {
        this.id = id;
        this.pid = pid;
        this.level = level;
        this.name = name;
        this.children = children;
        this.organizationLevelId = organizationLevelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
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

    public List<Integer> getOrganizationLevelId() {
        return organizationLevelId;
    }

    public void setOrganizationLevelId(List<Integer> organizationLevelId) {
        this.organizationLevelId = organizationLevelId;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", pid=" + pid +
                ", level=" + level +
                ", name='" + name + '\'' +
                ", children=" + children +
                ", organizationLevelId=" + organizationLevelId +
                '}';
    }
}
