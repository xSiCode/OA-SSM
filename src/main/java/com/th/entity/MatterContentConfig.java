package com.th.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("t_matter_content_config")
public class MatterContentConfig implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 前端富文本配置
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("font_bgColor")
    private String fontBgcolor;

    private String fontColor;

    private String fontFamily;

    private String fontOblique;

    private String fontSize;

    private String fontWeight;

    private String lineHeight;

    private String textAlign;

    private String textDecoration;

    public MatterContentConfig() {
    }

    public MatterContentConfig(Integer id, String fontBgcolor, String fontColor, String fontFamily,
                               String fontOblique, String fontSize, String fontWeight,
                               String lineHeight, String textAlign, String textDecoration) {
        this.id = id;
        this.fontBgcolor = fontBgcolor;
        this.fontColor = fontColor;
        this.fontFamily = fontFamily;
        this.fontOblique = fontOblique;
        this.fontSize = fontSize;
        this.fontWeight = fontWeight;
        this.lineHeight = lineHeight;
        this.textAlign = textAlign;
        this.textDecoration = textDecoration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFontBgcolor() {
        return fontBgcolor;
    }

    public void setFontBgcolor(String fontBgcolor) {
        this.fontBgcolor = fontBgcolor;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getFontOblique() {
        return fontOblique;
    }

    public void setFontOblique(String fontOblique) {
        this.fontOblique = fontOblique;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public String getLineHeight() {
        return lineHeight;
    }

    public void setLineHeight(String lineHeight) {
        this.lineHeight = lineHeight;
    }

    public String getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public String getTextDecoration() {
        return textDecoration;
    }

    public void setTextDecoration(String textDecoration) {
        this.textDecoration = textDecoration;
    }

    @Override
    public String toString() {
        return "MatterContentConfig{" +
        "id=" + id +
        ", fontBgcolor=" + fontBgcolor +
        ", fontColor=" + fontColor +
        ", fontFamily=" + fontFamily +
        ", fontOblique=" + fontOblique +
        ", fontSize=" + fontSize +
        ", fontWeight=" + fontWeight +
        ", lineHeight=" + lineHeight +
        ", textAlign=" + textAlign +
        ", textDecoration=" + textDecoration +
        "}";
    }
}
