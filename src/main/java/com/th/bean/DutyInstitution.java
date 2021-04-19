package com.th.bean;

import org.springframework.stereotype.Component;

/**
 * @Class : DutyInstitution
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/16 20:13
 * @Version : 1.0
 */
@Component
public class DutyInstitution {
    private Integer id;
    private Integer dutyId;
    private Integer institutionId;
    private String dutyName;
    private String institutionName;

    public DutyInstitution(Integer id, Integer dutyId, Integer institutionId, String dutyName, String institutionName) {
        this.id = id;
        this.dutyId = dutyId;
        this.institutionId = institutionId;
        this.dutyName = dutyName;
        this.institutionName = institutionName;
    }

    public DutyInstitution() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDutyId() {
        return dutyId;
    }

    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }

    public Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    @Override
    public String toString() {
        return "DutyInstitution{" +
                "id=" + id +
                ", dutyId=" + dutyId +
                ", institutionId=" + institutionId +
                ", dutyName='" + dutyName + '\'' +
                ", institutionName='" + institutionName + '\'' +
                '}';
    }
}
