package com.th.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @Class : TimeView
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/27 11:11
 * @Version : 1.0
 */
@Component
public class TimeView {

    //返回的数据格式是  list<timeView>

    //时间视图的时间  时分秒
    @JsonProperty("")
    private String Time;
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "GMT+8")
//    private LocalTime Time;

    //按周排序：星期一
    @JsonProperty("")
    private String Mon;
    //按周排序：星期二
    @JsonProperty("")
    private String Tue;
    //按周排序：星期三
    @JsonProperty("")
    private String Wed;
    //按周排序：星期四
    @JsonProperty("")
    private String Thu;
    //按周排序：星期五
    @JsonProperty("")
    private String Fri;
    //按周排序：星期六
    @JsonProperty("")
    private String Sat;
    //按周排序：星期日
    @JsonProperty("")
    private String Sun;
    //

    public TimeView() {
    }

    public TimeView(String time, String mon, String tue, String wed, String thu, String fri, String sat, String sun) {
        Time = time;
        Mon = mon;
        Tue = tue;
        Wed = wed;
        Thu = thu;
        Fri = fri;
        Sat = sat;
        Sun = sun;
    }
    @JsonIgnore
    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
    @JsonIgnore
    public String getMon() {
        return Mon;
    }

    public void setMon(String mon) {
        Mon = mon;
    }
    @JsonIgnore
    public String getTue() {
        return Tue;
    }

    public void setTue(String tue) {
        Tue = tue;
    }
    @JsonIgnore
    public String getWed() {
        return Wed;
    }

    public void setWed(String wed) {
        Wed = wed;
    }
    @JsonIgnore
    public String getThu() {
        return Thu;
    }

    public void setThu(String thu) {
        Thu = thu;
    }
    @JsonIgnore
    public String getFri() {
        return Fri;
    }

    public void setFri(String fri) {
        Fri = fri;
    }
    @JsonIgnore
    public String getSat() {
        return Sat;
    }

    public void setSat(String sat) {
        Sat = sat;
    }
    @JsonIgnore
    public String getSun() {
        return Sun;
    }

    public void setSun(String sun) {
        Sun = sun;
    }

    @Override
    public String toString() {
        return "TimeView{" +
                "Time=" + Time +
                ", Mon='" + Mon + '\'' +
                ", Tue='" + Tue + '\'' +
                ", Wed='" + Wed + '\'' +
                ", Thu='" + Thu + '\'' +
                ", Fri='" + Fri + '\'' +
                ", Sat='" + Sat + '\'' +
                ", Sun='" + Sun + '\'' +
                '}';
    }
}
