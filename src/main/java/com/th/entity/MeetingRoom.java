package com.th.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("t_meeting_room")
@Component
public class MeetingRoom implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 1：会议室的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer roomId;

    /**
     * 会议室的名字
     */
    private String name;

    /**
     * n:会议室的状态【使用中，空闲】
     */
    private String status;


    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    public MeetingRoom() {
    }

    public MeetingRoom(Integer id, Integer roomId, String name, String status, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.roomId = roomId;
        this.name = name;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "MeetingRoom{" +
        "id=" + id +
        ", roomId=" + roomId +
        ", name=" + name +
        ", status=" + status +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        "}";
    }
}
