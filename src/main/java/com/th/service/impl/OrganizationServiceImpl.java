package com.th.service.impl;

import com.th.entity.Organization;
import com.th.dao.OrganizationMapper;
import com.th.entity.User;
import com.th.service.OrganizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-07
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {
    @Autowired
    private Organization organization;

    @Autowired
    private User user;

    //以下用于读取数据库organization 并转为 json对象 ,javabean  整个组织树状图
    @Override
    public List<Organization> listWithTree() {
        //1,查找处所有分类.
        List<Organization> organizations = baseMapper.selectList(null);
        //2 组装成父子的树形结构
        //2.1 找到所有的一级分类
        List<Organization> levelMenus = organizations
                .stream()
                .filter(organization -> organization.getPid() == 0)
                .map((menu) -> {
                    menu.setChildren(getSonMenu(menu, organizations));
                    return menu;
                })
                .collect(Collectors.toList());
        return levelMenus;
    }

    @Override
    public List<Integer> listIdPaths(Integer currentId) {
        List<Integer> paths= new ArrayList<>();
        List<Integer> parentPaths= getParentPaths(currentId,paths);
        Collections.reverse(parentPaths);
        return parentPaths;
    }

    @Override
    public List<Organization> listParentPath(Integer organizationId) {
        return null;
    }

    @Override
    public List<Organization> listChildrenPath(Integer organizationId) {
        return null;
    }

    @Override
    public List<User> listUsers(Integer organizationId) {
        return null;
    }


    private List<Integer> getParentPaths(Integer currentId, List<Integer> paths) {
        //1，收集当前节点id
        paths.add(currentId);
        organization = this.getById(currentId);
        if (organization.getPid() != 0) {
            getParentPaths(organization.getPid(), paths);
        }
        return paths;
    }


    private List<Organization> getSonMenu(Organization menuRoot, List<Organization> sonAll) {
        List<Organization> childrenOrganizations = sonAll.stream().filter(organization -> {
            return organization.getPid() == menuRoot.getId();
        }).map(organization -> {
            //1.找到子菜单
            organization.setChildren(getSonMenu(organization, sonAll));
            return organization;
        }).collect(Collectors.toList());
        return childrenOrganizations;
    }
}
