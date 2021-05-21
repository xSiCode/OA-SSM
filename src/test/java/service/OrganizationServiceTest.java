package service;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.th.dao.OrganizationMapper;
import com.th.dao.UserMapper;
import com.th.entity.OrgUser;
import com.th.entity.Organization;
import com.th.entity.User;
import com.th.service.OrganizationService;
import com.th.service.UserService;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.aspectj.weaver.SourceContextImpl;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    OrganizationMapper organizationMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Test
    public void listWithTree(){
        List<Organization> organizations =organizationService.listWithTree();
        System.out.println(organizations);
    }

    @Test
    public void listWithTreeById(){
        List<Organization> organizations =organizationService.listWithTreeById(11);
        System.out.println(organizations);
    }

    @Test
    public void listParentPathsById(){
        List<Integer> ids =organizationService.listParentPathsById(221);
        System.out.println(ids);
    }

    @Test
    public void listParentPathsWithTreeById(){
        List<Organization> organizations = organizationService.listParentPathsWithTreeById(222);
        System.out.println(organizations);
    }

    @Test
    public void listUsersByOrganizationId(){
        List<Map<Integer, String>> maps = organizationService.listUsersByOrganizationId(222);
        System.out.println(maps);

    }



    @Test
    public void listParentPathsWithStringById(){
        String s = organizationService.listParentPathsWithStringById(222);
        System.out.println(s);

    }

    @Test
    public void getOrganizationByName(){
        String key ="校";
        List<Organization> organizationNameByKey = organizationService.getOrganizationByName(key);
        System.out.println(organizationNameByKey);
    }

    @Test
    public void formatMapList() {
        Map<Integer, String> maps = new HashMap<>();
        maps.put(1, "2222");
        maps.put(2, "2222");
        maps.put(3, "2222");
        maps.put(4, "2222");
        maps.put(5, "2222");
        System.out.println(maps);
        List<String> values = maps.values().stream().collect(Collectors.toList());
        List<Map.Entry<Integer, String>> collect = maps.entrySet().stream().collect(Collectors.toList());
        System.out.println(collect);

    }


    @Test
    public void tes(){
        List<Organization> organizations = organizationService.listWithTree();
        System.out.println(organizations);
    }



    @Test
    public void selectOrgUser(){
        List<OrgUser> orgUsers = organizationMapper.selectOrgUser();
        System.out.println(orgUsers);
    }
    @Test
    public void listOrgUser(){
        List<OrgUser> orgUsers = organizationService.listOrgUser();
        System.out.println(orgUsers);
        System.out.println("=================");
        System.out.println(JSON.toString(orgUsers));
    }

    @Test
    public void selectOrgByUserId(){
        Integer integer = organizationMapper.selectOrgByUserId(1004);
        System.out.println(integer);
    }

}
