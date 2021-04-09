package com.th.service.imp;

import com.th.bean.User;
import com.th.dao.UserDao;
import com.th.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Class : UserImpl
 * @Describe :
 * @Author : xSi
 * @Date : 2021/3/31 19:25
 * @Version : 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public User userLogin(Integer id, String password, String role) {
        return  userDao.login(id,password, role);
    }
}
