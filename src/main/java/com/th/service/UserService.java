package com.th.service;

import com.th.entity.Organization;
import com.th.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-07
 */
public interface UserService extends IService<User> {


    // ，最终返回用户信息 和他所在的 组织-职位（string格式显示）
    List<User> listUserOrganizationStringByUserId(User user);
    List<User> listUserOrganizationStringByUserId(List<User> users);

    //根据keyString ,查找user表
    List<User> getUserByKey(String key);
    //根据organizationName 值，查找对应的users
    List<User> getUserByOrganizationName(String organizationName);

    List<User> listUserFullLikeKey(String key);

    List<Map<Integer,String>> selectUsersIdNameByOrganizationId(Integer organizationId);


}
