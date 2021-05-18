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
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-18
 */
@Component
@TableName("t_meeting")
public class Meeting implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 会议id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 会议标题
     */
    private String title;

    /**
     * 会议内容
     */
    private String content;

    /**
     * 会议开始时间
     */

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    /**
     * 会议截止时间
     */

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    /**
     * 会议方式【线上，线下】
     */
    private String mode;

    /**
     * 1:会议主持人
     */
    private Integer hostId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String hostName;
    /**
     * 1:会议记录者
     */
    private Integer recorderId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String recorderName;
    /**
     * 会议状态【待开，已开，取消】
     */
    private String status;

    /**
     * 会议备注
     */
    private String note;

    /**
     * 1:会议室
     */
    private Integer roomId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String roomName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private List<MeetingAttendees>attendees;


    public Meeting() {
    }

    public Meeting(Integer id, String title, String content, LocalDateTime startTime, LocalDateTime endTime,
                   String mode, Integer hostId, Integer recorderId, String status, String note, Integer roomId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mode = mode;
        this.hostId = hostId;
        this.recorderId = recorderId;
        this.status = status;
        this.note = note;
        this.roomId = roomId;
    }

    public Meeting(Integer id, String title, String content, LocalDateTime startTime, LocalDateTime endTime,
                   String mode, Integer hostId, String hostName, Integer recorderId, String recorderName,
                   String status, String note, Integer roomId, String roomName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mode = mode;
        this.hostId = hostId;
        this.hostName = hostName;
        this.recorderId = recorderId;
        this.recorderName = recorderName;
        this.status = status;
        this.note = note;
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public Meeting(Integer id, String title, String content, LocalDateTime startTime,
                   LocalDateTime endTime, String mode, Integer hostId, String hostName,
                   Integer recorderId, String recorderName, String status, String note,
                   Integer roomId, String roomName, List<MeetingAttendees> attendees) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mode = mode;
        this.hostId = hostId;
        this.hostName = hostName;
        this.recorderId = recorderId;
        this.recorderName = recorderName;
        this.status = status;
        this.note = note;
        this.roomId = roomId;
        this.roomName = roomName;
        this.attendees = attendees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public Integer getRecorderId() {
        return recorderId;
    }

    public void setRecorderId(Integer recorderId) {
        this.recorderId = recorderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getRecorderName() {
        return recorderName;
    }

    public void setRecorderName(String recorderName) {
        this.recorderName = recorderName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<MeetingAttendees> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<MeetingAttendees> attendees) {
        this.attendees = attendees;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", mode='" + mode + '\'' +
                ", hostId=" + hostId +
                ", hostName='" + hostName + '\'' +
                ", recorderId=" + recorderId +
                ", recorderName='" + recorderName + '\'' +
                ", status='" + status + '\'' +
                ", note='" + note + '\'' +
                ", roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", Attendees=" + attendees +
                '}';
    }
}
