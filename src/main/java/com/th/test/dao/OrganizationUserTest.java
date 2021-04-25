package com.th.test.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.th.bean.Organization;
import com.th.bean.OrganizationUser;
import com.th.dao.OrganizationMapper;
import com.th.dao.OrganizationUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
public class OrganizationUserTest {
    @Autowired
    private OrganizationUser organizationUser;
    @Autowired
    private OrganizationUserMapper organizationUserMapper;


    @Test
    public void selectById(){
        organizationUser = organizationUserMapper.selectById(303);
        System.out.println(organizationUser);
        //
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - ");
    }
    @Test
    public void selectListOrganization(){
        List<OrganizationUser> organizations = organizationUserMapper.selectList(
                new QueryWrapper<OrganizationUser>()
                        .ne("id", 1));
        System.out.println(organizations);
        //
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - ");
    }
    @Test
    public void selectOne(){
        organizationUser=organizationUserMapper.selectOne(
                new QueryWrapper<OrganizationUser>()
                .eq("id",322)
                .eq("pid",121)
                .eq("level",4)  );
        System.out.println(organizationUser);
        //
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - ");
    }


}
