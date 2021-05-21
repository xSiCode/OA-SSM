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
 * @since 2021-05-21
 */
@Component
@TableName("t_leave")
public class Leave implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 请假表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 1:请假人
     */
    private Integer applicantId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String applicantName;


    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    /**
     * 请假的原因
     */
    private String reason;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 在外地点
     */
    private String site;

    /**
     * 请假类别【病假，产假，婚假，事假，出差，其他】
     */
    private String category;

    /**
     * 1：工作承接人
     */
    private Integer recipientId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String recipientName;
    /**
     * 1，审批人，上级
     */
    private Integer approveId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String approveName;
    /**
     * 审核意见
     */
    private String auditOpinion;

    /**
     * 审批状态【待审核，通过，未通过】
     */
    private String status;


    public Leave() {
    }

    public Leave(Integer id, Integer applicantId, LocalDateTime startTime, LocalDateTime endTime,
                 String reason, String tel, String site, String category,
                 Integer recipientId,Integer approveId, String auditOpinion, String status) {
        this.id = id;
        this.applicantId = applicantId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.tel = tel;
        this.site = site;
        this.category = category;
        this.recipientId = recipientId;
        this.approveId = approveId;
        this.auditOpinion = auditOpinion;
        this.status = status;
    }

    public Leave(Integer id, Integer applicantId, String applicantName,
                 LocalDateTime startTime,LocalDateTime endTime, String reason,
                 String tel, String site, String category,
                 Integer recipientId, String recipientName, Integer approveId,
                 String approveName, String auditOpinion, String status) {
        this.id = id;
        this.applicantId = applicantId;
        this.applicantName = applicantName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.tel = tel;
        this.site = site;
        this.category = category;
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.approveId = approveId;
        this.approveName = approveName;
        this.auditOpinion = auditOpinion;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }

    public Integer getApproveId() {
        return approveId;
    }

    public void setApproveId(Integer approveId) {
        this.approveId = approveId;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
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

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getApproveName() {
        return approveName;
    }

    public void setApproveName(String approveName) {
        this.approveName = approveName;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "id=" + id +
                ", applicantId=" + applicantId +
                ", applicantName='" + applicantName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", reason='" + reason + '\'' +
                ", tel='" + tel + '\'' +
                ", site='" + site + '\'' +
                ", category='" + category + '\'' +
                ", recipientId=" + recipientId +
                ", recipientName='" + recipientName + '\'' +
                ", approveId=" + approveId +
                ", approveName='" + approveName + '\'' +
                ", auditOpinion='" + auditOpinion + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
