package com.th.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.th.dao.UserMapper;
import com.th.entity.Organization;
import com.th.dao.OrganizationMapper;
import com.th.entity.User;
import com.th.service.OrganizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javafx.beans.binding.StringBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
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
    private UserMapper userMapper;

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
                    menu.setChildren(getChildrenMenuPaths(menu, organizations));
                    return menu;
                })
                .collect(Collectors.toList());
        return levelMenus;
    }

    @Override
    public List<Organization> listWithTreeById(Integer id) {
        //1,查找处所有分类.
        List<Organization> organizations = baseMapper.selectList(null);
        //2 组装成父子的树形结构
        //2.1 找到所有的一级分类
        List<Organization> levelMenus = organizations
                .stream()
                .filter(organization -> organization.getPid() == id)
                .map((menu) -> {
                    menu.setChildren(getChildrenMenuPaths(menu, organizations));
                    return menu;
                })
                .collect(Collectors.toList());
        return levelMenus;
    }

    @Override
    public List<Integer> listParentPathsById(Integer currentId) {
        List<Integer> paths= new ArrayList<>();
        List<Integer> parentPaths= getParentIdPaths(currentId,paths);
        Collections.reverse(parentPaths);
        return parentPaths;
    }

    @Override
    public List<Organization> listParentPathsWithTreeById(Integer organizationId) {
        organization= baseMapper.selectById(organizationId);
        List<Organization> paths=new ArrayList<>();
        List<Organization> parentPaths = getParentOrganizationPaths(organization,paths);
        Collections.reverse(parentPaths);
        return parentPaths;
    }

    @Override
    public String listParentPathsWithStringById(Integer organizationId) {
        StringBuilder paths=new StringBuilder();
        StringBuilder parentPaths=getParentString(organizationId,paths);
        String builderToString = parentPaths.deleteCharAt(parentPaths.length() - 1).toString();//干事-组织人事处-职能部门-  删除最后一个“-”
        String[] parentPathsArray=builderToString.split("-");//通过“-”切割为数组
        List<String> parentPathsList= Arrays.asList(parentPathsArray);//数组转为list
        Collections.reverse(parentPathsList);//list反转
        String parentPathsAsc =String.join("-",parentPathsList);//将list拼接为 string
        return parentPathsAsc ;
    }

    private StringBuilder getParentString(Integer organizationId, StringBuilder paths) {
        organization=this.getById(organizationId);
        String organizationName= organization.getName();
        paths.append(organizationName+"-");
        if( organization.getLevel()!=1){
            getParentString( organization.getPid(),paths );
        }
        return paths;
    }


    @Override
    public List<Map<Integer,String>> listUsersByOrganizationId(Integer organizationId) {
        List<Map<Integer, String>> maps = userMapper.selectUsersIdNameByOrganizationId(organizationId);
        return maps;
    }

    @Override
    public List<Organization> getOrganizationNameByKey(String key) {
        List<Organization> organizations= baseMapper.selectList(new QueryWrapper<Organization>()
                .like("name",key)
        );
        return organizations;
    }


    private List<Integer> getParentIdPaths(Integer currentId, List<Integer> paths) {
        //1，收集当前节点id
        paths.add(currentId);
        organization = this.getById(currentId);
        if (organization.getPid() != 0) {
            getParentIdPaths(organization.getPid(), paths);
        }
        return paths;
    }
    private List<Organization> getParentOrganizationPaths(Organization currentOrganization, List<Organization> paths) {
        paths.add(currentOrganization);
        if(organization.getPid()!=0){
     /*       getById 根据ID 返回该实体对象
            public T getById(Serializable id) {
                return this.baseMapper.selectById(id);
            }*/
            organization=this.getById(organization.getPid());
            getParentOrganizationPaths( organization ,paths);
        }
        return paths;
    }

    private List<Organization> getChildrenMenuPaths(Organization menuRoot, List<Organization> sonAll) {
        List<Organization> childrenOrganizations = sonAll
                .stream()
                .filter(organization -> organization.getPid() == menuRoot.getId() )
                .map(organization -> {
                    //1.找到子菜单
                    organization.setChildren(getChildrenMenuPaths(organization, sonAll));
                    return organization;
        }).collect(Collectors.toList());
        return childrenOrganizations;
    }
}
