package com.th.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Class : OrgUser
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/14 21:13
 * @Version : 1.0
 */
@Component
public class OrgUser {
    private Integer id;

    private String name;

    private Integer pid;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List< Map<String,Object> > users;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OrgUser> children;

    public OrgUser() {
    }

    public OrgUser(Integer id, String name, Integer pid, List<Map<String, Object>> users, List<OrgUser> children) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.users = users;
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public List<Map<String, Object>> getUsers() {
        return users;
    }

    public void setUsers(List<Map<String, Object>> users) {
        this.users = users;
    }

    public List<OrgUser> getChildren() {
        return children;
    }

    public void setChildren(List<OrgUser> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "OrgUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pid=" + pid +
                ", users=" + users +
                ", children=" + children +
                '}';
    }

}
