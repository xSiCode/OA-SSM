package com.th.service;

import com.th.entity.Organization;
import com.baomidou.mybatisplus.extension.service.IService;
import com.th.entity.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-07
 */
public interface OrganizationService extends IService<Organization> {
    //得到 树状的 组织结构  0 开始的 获取树状子路径
    List<Organization> listWithTree();
    //得到 指定 树状的 子路径
    List<Organization> listWithTreeById(Integer id);

    // 得到 当前 id  父路径 id串
    List<Integer> listParentPathsById(Integer organizationId);

    // 得到 当前 id  父路径 树状
    List<Organization> listParentPathsWithTreeById(Integer organizationId);

    // 得到 当前 id  父路径 拼接成字符串
    String listParentPathsWithStringById(Integer organizationId);


    //得到 当前 organization_id 对应的用户 ids,names
    List<Map<Integer,String>> listUsersByOrganizationId(Integer organizationId);


    //根据key ,查找Organization表 name 的名字
    List<Organization> getOrganizationByName(String key);
}
