package com.th.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-20
 */
@Component
@TableName("t_attendance")
public class Attendance implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 打卡时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime clockTime;

    /**
     * 1:打卡的人
     */
    private Integer userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String userName;
    /**
     * 打卡地点
     */
    private String site;

    public Attendance() {
    }

    public Attendance(Integer id, LocalDateTime clockTime, Integer userId, String site) {
        this.id = id;
        this.clockTime = clockTime;
        this.userId = userId;
        this.site = site;
    }

    public Attendance(Integer id, LocalDateTime clockTime, Integer userId, String userName, String site) {
        this.id = id;
        this.clockTime = clockTime;
        this.userId = userId;
        this.userName = userName;
        this.site = site;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getClockTime() {
        return clockTime;
    }

    public void setClockTime(LocalDateTime clockTime) {
        this.clockTime = clockTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", clockTime=" + clockTime +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
