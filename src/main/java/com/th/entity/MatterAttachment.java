package com.th.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-12
 */
@Component
@TableName("t_matter_attachment")
public class MatterAttachment implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 事项的附件
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 附件名字
     */
    private String name;

    /**
     * 附件数据
     */
    private String data;

    /**
     * 1,附件对应的事项id
     */
    private Integer matterId;

    public MatterAttachment() {
    }

    public MatterAttachment(Integer id, String name, String data, Integer matterId) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.matterId = matterId;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getMatterId() {
        return matterId;
    }

    public void setMatterId(Integer matterId) {
        this.matterId = matterId;
    }

    @Override
    public String toString() {
        return "MatterAttachment{" +
        "id=" + id +
        ", name=" + name +
        ", data=" + data +
        ", matterId=" + matterId +
        "}";
    }
}
