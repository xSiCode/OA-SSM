package com.th.bean;

import org.springframework.stereotype.Component;

/**
 * @Class : Duty
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/16 20:11
 * @Version : 1.0
 */

@Component
public class Duty {
    private Integer dutyId;
    private String dutyName;

    public Duty(Integer dutyId, String dutyName) {
        this.dutyId = dutyId;
        this.dutyName = dutyName;
    }

    public Duty() {
    }

    public Integer getDutyId() {
        return dutyId;
    }

    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    @Override
    public String toString() {
        return "Duty{" +
                "dutyId=" + dutyId +
                ", dutyName='" + dutyName + '\'' +
                '}';
    }
}
