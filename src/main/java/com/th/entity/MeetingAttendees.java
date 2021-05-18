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
 * @since 2021-05-18
 */
@Component
@TableName("t_meeting_attendees")
public class MeetingAttendees implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 一个会议的参会人员表，id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 1,会议id,
     */
    private Integer meetingId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String meetingTitle;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String meetingRoomName;

    /**
     * n,参会人员id,   一个会议可以有多个人参与
     */
    private Integer userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String userName;

    /**
     * 开始时间
     */

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    public MeetingAttendees() {
    }

    public MeetingAttendees(Integer id, Integer meetingId, Integer userId, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.meetingId = meetingId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public MeetingAttendees(Integer id, Integer meetingId, String meetingTitle, String meetingRoomName,
                            Integer userId, String userName, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.meetingId = meetingId;
        this.meetingTitle = meetingTitle;
        this.meetingRoomName = meetingRoomName;
        this.userId = userId;
        this.userName = userName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public String getMeetingRoomName() {
        return meetingRoomName;
    }

    public void setMeetingRoomName(String meetingRoomName) {
        this.meetingRoomName = meetingRoomName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "MeetingAttendees{" +
                "id=" + id +
                ", meetingId=" + meetingId +
                ", meetingTitle='" + meetingTitle + '\'' +
                ", meetingRoomName='" + meetingRoomName + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
