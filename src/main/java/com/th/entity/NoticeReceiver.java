package com.th.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-22
 */
@Component
@TableName("t_notice_receiver")
public class NoticeReceiver implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * ID，公告
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 1,公告id
     */
    private Integer noticeId;

    /**
     * n,公告接收者id
     */
    private Integer receiverId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String receiverName;

    public NoticeReceiver() {
    }

    public NoticeReceiver(Integer id, Integer noticeId, Integer receiverId) {
        this.id = id;
        this.noticeId = noticeId;
        this.receiverId = receiverId;
    }

    public NoticeReceiver(Integer id, Integer noticeId, Integer receiverId, String receiverName) {
        this.id = id;
        this.noticeId = noticeId;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Override
    public String toString() {
        return "NoticeReceiver{" +
                "id=" + id +
                ", noticeId=" + noticeId +
                ", receiverId=" + receiverId +
                ", receiverName='" + receiverName + '\'' +
                '}';
    }
}
