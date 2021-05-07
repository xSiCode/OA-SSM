package com.th.service;

import com.th.entity.Organization;
import com.baomidou.mybatisplus.extension.service.IService;
import com.th.entity.User;

import java.util.List;

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

    // 得到 当前 id  父路径 id串
    List<Integer> listIdPaths(Integer organizationId);

    //得到当前 id 的 树状 父路径
    List<Organization> listParentPath(Integer organizationId);

    //得到当前 id 的 树状 子路径
    List<Organization> listChildrenPath(Integer organizationId);

    //得到当前 id 下的 所有 用户
    List<User> listUsers(Integer organizationId);
}
