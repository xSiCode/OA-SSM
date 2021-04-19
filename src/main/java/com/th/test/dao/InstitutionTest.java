package com.th.test.dao;

import com.th.bean.Institution;
import com.th.dao.InstitutionDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Class : InstitutionTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/16 21:16
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class InstitutionTest {
    @Autowired
    Institution institution;

    @Autowired
    InstitutionDao institutionDao;

    @Test
    public void selectByKey(){
        institution=institutionDao.selectByKey(2);
        System.out.println(institution);
    }

    @Test
    public void selectList(){
        System.out.println( institutionDao.selectInstitutionList()  );


    }

}
