package com.th.test.dao;

import com.th.bean.DutyInstitution;
import com.th.dao.DutyInstitutionDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Class : DutyInstitution
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/16 21:27
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class DutyInstitutionTest {
    @Autowired
    DutyInstitution dutyInstitution;

    @Autowired
    DutyInstitutionDao dutyInstitutionDao;

    @Test
    public void selectByKeys(){
        dutyInstitution =dutyInstitutionDao.selectDutyInstitutionByKeys(15,6);
        System.out.println( dutyInstitution );
    }

    @Test
    public void selectList(){
        List<DutyInstitution> dutyInstitutions = dutyInstitutionDao.selectDutyInstitutionList();
        for(int i=0 ;i<dutyInstitutions.size();i++){
            System.out.println( dutyInstitutions.get(i)  );
        }

        System.out.println(dutyInstitutions);
    }

}
