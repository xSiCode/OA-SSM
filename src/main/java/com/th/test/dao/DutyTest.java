package com.th.test.dao;

import com.th.bean.Duty;
import com.th.dao.DutyDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

/**
 * @Class : DutyTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/16 20:56
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class DutyTest {

    @Autowired
    Duty duty;

    @Autowired
    DutyDao dutyDao;

    @Test
    public void testDutySelectByKey(){
        Integer id=4;
        System.out.println( dutyDao.selectByKey(id) );
    }
    @Test
    public void selectDutyList(){
        Integer id=4;
        System.out.println( dutyDao.selectDutyList() );
    }


    @Test
    public void dateTime(){
        
    }

}
