package com.th.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.th.bean.Organization;
import com.th.dao.OrganizationMapper;
import com.th.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Class : OrganizationImpl
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/24 22:49
 * @Version : 1.0
 */

@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

    @Autowired
    Organization organization;

    //以下用于读取数据库organization 并转为 json对象，还不是json字符串
    @Override
    public List<Organization> listWithTree() {
        //1,查找处所有分类.
        List<Organization> organizations=baseMapper.selectList(null);
        //2 组装成父子的树形结构
        //2.1 找到所有的一级分类
        List<Organization> levelMenus=organizations.stream().filter(organization->organization.getPid() ==0
        ).map( (menu) ->{
            menu.setChildren(getSonMenu(menu,organizations));
            return menu;
        }).collect(Collectors.toList());
        return levelMenus;
    }

    @Override
    public List<Integer> listIdPaths(Integer currentId) {
        List<Integer> paths= new ArrayList<>();
        List<Integer> parentPaths= getParentPaths(currentId,paths);
        Collections.reverse(parentPaths);
        return parentPaths;
    }

    private List<Integer> getParentPaths(Integer currentId, List<Integer> paths) {
        //1，收集当前节点id
        paths.add(currentId);
        organization=this.getById(currentId);
        if(organization.getPid()!=0){
            getParentPaths( organization.getPid(),paths );
        }
        return paths;
    }


    private List<Organization> getSonMenu(Organization menuRoot, List<Organization> sonAll) {
        List<Organization> childrenOrganizations =sonAll.stream().filter(organization->{
            return organization.getPid()==menuRoot.getId();
        }).map(organization->{
            //1.找到子菜单
            organization.setChildren(getSonMenu(organization,sonAll));
            return organization;
        }).collect(Collectors.toList());
        return  childrenOrganizations    ;
    }
}
