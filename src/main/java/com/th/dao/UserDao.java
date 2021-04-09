package com.th.dao;

import com.th.bean.User;
import org.apache.ibatis.annotations.Param;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * @Class : UserDao
 * @Describe :
 * @Author : xSi
 * @Date : 2021/3/27 22:23
 * @Version : 1.0
 */
public interface UserDao {
//    retrieve
    //登录功能，参数只有三个   :A,T

    User login(@Param("userId")Integer userId,
               @Param("userPassword")String userPassword,
               @Param("userRole") String userRole);
    //


    //用户列表，批量展示用户所有信息   ：A
    List<User> getUsersList();

//    update
    //信息完善 &修改信息，参数有9个，是个user   :A,T
    User updateInfo(User user);

//    create
    //新增用户      ：A
    int addUser(User user);

//    delete
    //删除指定用户    ：A
    int deleteUser(Integer id);

    //批量删除指定用户：A
    List<User> deleteUsersList();
}
