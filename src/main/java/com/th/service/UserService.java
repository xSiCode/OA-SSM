package com.th.service;

import com.th.entity.Organization;
import com.th.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-07
 */
public interface UserService extends IService<User> {
    // 传入一个完整的user ，通过organizationId 得到 organizationParentPathsTree.。
    // 最后拼接在要返回的User json上，最终返回用户信息 和他所在的 组织-职位（list格式显示）
    List<Object> ListUserOrganizationByUserId(User user); //数据根式并不统一 user+organization
    // ，最终返回用户信息 和他所在的 组织-职位（string格式显示）
    List<Object> listUserOrganizationStringByUserId(User user);
    List<Object> listUserOrganizationStringByUserId(List<User> users);

    //根据输入的 key查询 相关信息，在user表，organization表中查询
    List<Object> listUserOrganizationStringByKey(String searchKey);
    //根据key ,查找user表
    List<User> getUserByKey(String key);
    //根据organizationName 值，查找对应的users
    List<User> getUserByOrganizationName(String organizationName);
}
