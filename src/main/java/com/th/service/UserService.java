package com.th.service;

import com.th.bean.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Class : User
 * @Describe :
 * @Author : xSi
 * @Date : 2021/3/31 19:24
 * @Version : 1.0
 */
public interface UserService {
    public User userLogin(Integer id,String password,String role);


}
