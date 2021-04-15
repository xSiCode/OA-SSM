package com.th.service;

import com.th.bean.User;

import java.util.List;

/**
 * @Class : User
 * @Describe :
 * @Author : xSi
 * @Date : 2021/3/31 19:24
 * @Version : 1.0
// */
/*
//具体的业务代码代码不应该写到控制器里，而是交给service负责实现。
//        比如要验证一个用户名是否存在，应该在控制器里调用Service层的验证的方法，由service去验证用户名是否存在，*/
public interface UserService {

    public User userLogin(Integer id, String password, String role);
    public List<User> getUsersList();
    public List<User> getUserByIdOrName(String idOrName);

    //
    List<User> selectByKey(String key);

    int insert(User user);

    int update(User user);

    int deleteBatch(List<Integer> ids);


}
