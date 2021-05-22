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
 * @since 2021-05-22
 */
@Component
@TableName("t_notice")
public class Notice implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 公告id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 1,公告发起者，权限为管理员
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer creatorId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String creatorName;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime sendingTime;

    /**
     * 是否展示【是，否】
     */
    private String status;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String content;

    //用于公告发起者 查看 公告详情
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private List<NoticeReceiver> receivers;


    public Notice() {
    }

    public Notice(Integer id, Integer creatorId, LocalDateTime sendingTime, String status, String title, String content) {
        this.id = id;
        this.creatorId = creatorId;
        this.sendingTime = sendingTime;
        this.status = status;
        this.title = title;
        this.content = content;
    }

    public Notice(Integer id, Integer creatorId, String creatorName, LocalDateTime sendingTime, String status, String title, String content) {
        this.id = id;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.sendingTime = sendingTime;
        this.status = status;
        this.title = title;
        this.content = content;
    }

    public Notice(Integer id, Integer creatorId, String creatorName, LocalDateTime sendingTime, String status, String title, String content, List<NoticeReceiver> receivers) {
        this.id = id;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.sendingTime = sendingTime;
        this.status = status;
        this.title = title;
        this.content = content;
        this.receivers = receivers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(LocalDateTime sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public List<NoticeReceiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<NoticeReceiver> receivers) {
        this.receivers = receivers;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", creatorId=" + creatorId +
                ", creatorName='" + creatorName + '\'' +
                ", sendingTime=" + sendingTime +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", receivers=" + receivers +
                '}';
    }
}
