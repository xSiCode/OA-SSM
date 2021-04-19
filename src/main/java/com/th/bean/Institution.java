package com.th.bean;

import org.springframework.stereotype.Component;

/**
 * @Class : Institution
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/16 20:11
 * @Version : 1.0
 */
@Component
public class Institution {
    private Integer institutionId;
    private String institutionName;

    public Institution() {
    }

    public Institution(Integer institutionId, String institutionName) {
        this.institutionId = institutionId;
        this.institutionName = institutionName;
    }

    public Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    @Override
    public String toString() {
        return "Institution{" +
                "institutionId=" + institutionId +
                ", institutionName='" + institutionName + '\'' +
                '}';
    }
}
