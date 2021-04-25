package com.th.test.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.th.bean.Organization;
import com.th.dao.OrganizationMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * @Class : OrganizationTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/24 17:12
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class OrganizationTest {
    @Autowired
    private Organization organization;
    @Autowired
    private OrganizationMapper organizationMapper;


    @Test
    public void selectOrganization(){
         organization = organizationMapper.selectById(303);
        System.out.println(organization);
        //
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - ");
    }
    @Test
    public void selectListOrganization(){
        List<Organization> organizations = organizationMapper.selectList(
                new QueryWrapper<Organization>()
                        .ne("id", 1));
        System.out.println(organizations);
        //
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - ");
    }
    @Test
    public void selectOne(){
        organization=organizationMapper.selectOne(
                new QueryWrapper<Organization>()
                .eq("id",322)
                .eq("pid",121)
                .eq("level",4)  );
        System.out.println(organization);
        //
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - ");
    }
    @Test
    public void getParentPaths(){

        int [] a ={1,2,3,4,5,6,7,8,9};
        List<String>b =new ArrayList<>();
        b.add("aaaaaaaaaa");
        b.add("bbbbbbbbbb");
        b.add("cccccccccc");
        System.out.println(a);
        System.out.println(b);

        //
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - ");
    }

}
