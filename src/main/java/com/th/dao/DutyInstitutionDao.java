package com.th.dao;

import com.th.bean.DutyInstitution;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @Class : DutyInstitution
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/16 20:23
 * @Version : 1.0
 */
public interface DutyInstitutionDao {

    List<DutyInstitution> selectDutyInstitutionList();
    DutyInstitution selectDutyInstitutionByKeys(@Param("dutyId") Integer dutyId,@Param("institutionId") Integer institutionId);

}
