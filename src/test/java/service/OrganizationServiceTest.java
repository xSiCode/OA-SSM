package service;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.th.entity.Organization;
import com.th.service.OrganizationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Class : OrganizationServiceTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/24 23:17
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class OrganizationServiceTest {
    @Autowired
    OrganizationService organizationService;
    @Autowired
    Organization organization;



    @Test
    public void organizationServiceSelectOne(){
        QueryChainWrapper<Organization> query = organizationService.query();
        System.out.println(query);
        List<Object> objects = organizationService.listObjs();
        System.out.println(objects);
        List<Organization> list = organizationService.list();
        System.out.println(list);


    }
    @Test
    public void organizationServiceSelectAll(){
        organization.setId(11);
        organization.setPid(1);


    }
    @Test
    public void listIdPaths(){
        List<Integer> listIdPaths= organizationService.listIdPaths(241);
        System.out.println(listIdPaths);


    }
}
