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
import java.util.Objects;

/**
 * <p>
 * 
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-12
 */

@Component
@TableName("t_matter_handler")
public class MatterHandler implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * id 事项的处理
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 1，事项的ID
     */
    private Integer matterId;

    /**
     * n,事项的处理人
     */
    private Integer handlerId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String handlerName;

    /**
     * 事项的完成时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime completedTime;

    /**
     * 事项处理状态-，按时完成，超时完成，未完成。
     */
    private String matterStatus;

    public MatterHandler() {
    }

    public MatterHandler(Integer id, Integer matterId, Integer handlerId,
                         LocalDateTime completedTime, String matterStatus) {
        this.id = id;
        this.matterId = matterId;
        this.handlerId = handlerId;
        this.completedTime = completedTime;
        this.matterStatus = matterStatus;
    }

    public MatterHandler(Integer id, Integer matterId, Integer handlerId,
                         String handlerName, LocalDateTime completedTime, String matterStatus) {
        this.id = id;
        this.matterId = matterId;
        this.handlerId = handlerId;
        this.handlerName = handlerName;
        this.completedTime = completedTime;
        this.matterStatus = matterStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMatterId() {
        return matterId;
    }

    public void setMatterId(Integer matterId) {
        this.matterId = matterId;
    }

    public Integer getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Integer handlerId) {
        this.handlerId = handlerId;
    }

    public LocalDateTime getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(LocalDateTime completedTime) {
        this.completedTime = completedTime;
    }

    public String getMatterStatus() {
        return matterStatus;
    }

    public void setMatterStatus(String matterStatus) {
        this.matterStatus = matterStatus;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    @Override
    public String toString() {
        return "MatterHandler{" +
                "id=" + id +
                ", matterId=" + matterId +
                ", handlerId=" + handlerId +
                ", handlerName='" + handlerName + '\'' +
                ", completedTime=" + completedTime +
                ", matterStatus='" + matterStatus + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatterHandler that = (MatterHandler) o;
        return id.equals(that.id) &&
                matterId.equals(that.matterId) &&
                handlerId.equals(that.handlerId) &&
                handlerName.equals(that.handlerName) &&
                completedTime.equals(that.completedTime) &&
                matterStatus.equals(that.matterStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matterId, handlerId, handlerName, completedTime, matterStatus);
    }
}
