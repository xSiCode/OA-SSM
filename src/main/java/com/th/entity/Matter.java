package com.th.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * @since 2021-05-12
 */
@TableName("t_matter")
// 在返回的实体类上添加注解，忽略不能序列化的属性:
//@JsonIgnoreProperties(value = "handler")
@Component
public class Matter implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 事项唯一 id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 事项标题
     */
    private String title;

    /**
     * 事项的内容,纯文本
     */
    private String contentText;

    /**
     * 事项的格式文件，json格式 字符串
     */
    private Integer contentConfigId;

    /**
     * 发送时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime sendTime;

    /**
     * 截止日期
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime deadlineTime;

    /**
     * 1，事项发起者,事项创建者
     */
    private Integer creatorId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String creatorName;

    /**
     * 事项状态【draft 草稿，已发送 submit】
     */
    private String status;

    //以下 对应事项的 一个文本内容的格式对象，多个附件，多个处理人
/*
*若整个属性为空，则不序列化
*
*service中执行过 mapper层  的方法后,返回的实体类被做了代理,如下:
*被代理后的类多了一个handler的属性,之后Jackson在对该代理类做序列化时,由于找不到对应的getter,异常就抛出来辣!
*解决方法:
*在被代理的Order类上加上 @JsonIgnoreProperties(value = "handler") 注解,让Jackson序列化时忽略handler属性:
* */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private MatterContentConfig contentConfig;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private List<MatterAttachment> attachments;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private List<MatterHandler> handlers;




    public Matter() {
    }

    public Matter(Integer id, String title, String contentText, Integer contentConfigId,
                  LocalDateTime sendTime, LocalDateTime deadlineTime, Integer creatorId, String status) {
        this.id = id;
        this.title = title;
        this.contentText = contentText;
        this.contentConfigId = contentConfigId;
        this.sendTime = sendTime;
        this.deadlineTime = deadlineTime;
        this.creatorId = creatorId;
        this.status = status;
    }

    public Matter(Integer id, String title, String contentText, Integer contentConfigId,
                  LocalDateTime sendTime, LocalDateTime deadlineTime, Integer creatorId,
                  String status, MatterContentConfig contentConfig,
                  List<MatterAttachment> attachments, List<MatterHandler> handlers) {
        this.id = id;
        this.title = title;
        this.contentText = contentText;
        this.contentConfigId = contentConfigId;
        this.sendTime = sendTime;
        this.deadlineTime = deadlineTime;
        this.creatorId = creatorId;
        this.status = status;
        this.contentConfig = contentConfig;
        this.attachments = attachments;
        this.handlers = handlers;
    }

    public Matter(Integer id, String title, String contentText, Integer contentConfigId,
                  LocalDateTime sendTime, LocalDateTime deadlineTime, Integer creatorId,
                  String creatorName, String status, MatterContentConfig contentConfig,
                  List<MatterAttachment> attachments, List<MatterHandler> handlers) {
        this.id = id;
        this.title = title;
        this.contentText = contentText;
        this.contentConfigId = contentConfigId;
        this.sendTime = sendTime;
        this.deadlineTime = deadlineTime;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.status = status;
        this.contentConfig = contentConfig;
        this.attachments = attachments;
        this.handlers = handlers;
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

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public Integer getContentConfigId() {
        return contentConfigId;
    }

    public void setContentConfigId(Integer contentConfigId) {
        this.contentConfigId = contentConfigId;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public LocalDateTime getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(LocalDateTime deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public MatterContentConfig getContentConfig() {
        return contentConfig;
    }

    public void setContentConfig(MatterContentConfig contentConfig) {
        this.contentConfig = contentConfig;
    }

    public List<MatterAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MatterAttachment> attachments) {
        this.attachments = attachments;
    }

    public List<MatterHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<MatterHandler> handlers) {
        this.handlers = handlers;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Override
    public String toString() {
        return "Matter{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contentText='" + contentText + '\'' +
                ", contentConfigId=" + contentConfigId +
                ", sendTime=" + sendTime +
                ", deadlineTime=" + deadlineTime +
                ", creatorId=" + creatorId +
                ", creatorName='" + creatorName + '\'' +
                ", status='" + status + '\'' +
                ", contentConfig=" + contentConfig +
                ", attachments=" + attachments +
                ", handlers=" + handlers +
                '}';
    }
}
