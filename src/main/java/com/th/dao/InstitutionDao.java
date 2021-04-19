package com.th.dao;

import com.th.bean.Institution;

import java.util.List;

/**
 * @Interface : Institution
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/16 20:23
 * @Version : 1.0
 */
public interface InstitutionDao {
    Institution selectByKey(Integer id);
    List<Institution> selectInstitutionList();

}
