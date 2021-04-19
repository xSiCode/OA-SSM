package com.th.dao;

import com.th.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class : UserDao
 * @Describe :
 * @Author : xSi
 * @Date : 2021/3/27 22:23
 * @Version : 1.0
 */
public interface UserDao {
    //登录功能，参数只有三个   :A,T
    User login(@Param("userId") Integer userId, @Param("userPassword") String userPassword, @Param("userRole") String userRole);

    //用户列表，批量展示用户所有信息，主页
    List<User> selectUsersList();

    //模糊查询后，批量显示
    List<User> selectByKey(@Param("key") String key);

    int insert(User user);

    int update(@Param("user") User user);

    //批量删除指定用户：
    int deleteBatch(@Param("ids") List<Integer> ids);


    //------------------------------------------------------------批量新增用户     ：主要是为了造数据
    int insertUsers(@Param("users") List<User> users);

    int insertBatch(@Param("users") User user);

    //根据关键词 排序
    // List<User>  selectOrderBy(String key,String asc_or_desc);


}
