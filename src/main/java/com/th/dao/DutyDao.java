package com.th.dao;

import com.th.bean.Duty;

import java.util.List;

/**
 * @Class : DutyDao
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/16 20:22
 * @Version : 1.0
 */
public interface DutyDao {
    Duty selectByKey(Integer id);  //用于模糊查询,可以只查一个
    List<Duty> selectDutyList() ;           //用于获取所有

    //预留接口
    //int insert(Duty duty);

    //int update(Duty duty);

    //int delete(Duty duty);

}
