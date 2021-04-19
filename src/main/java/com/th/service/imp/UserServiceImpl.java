package com.th.service.imp;

import com.th.bean.User;
import com.th.dao.UserDao;
import com.th.service.UserService;
import com.th.utils.DataVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class : UserImpl
 * @Describe :
 * @Author : xSi
 * @Date : 2021/3/31 19:25
 * @Version : 1.0
 */
/*@Service注解是标注在实现类上的，因为@Service是把spring容器中的bean进行实例化，也就是等同于new操作，
            只有实现类是可以进行new实例化的，而接口则不能，所以是加在实现类上的。
@Service注解、@Controller注解以及@Repository注解都是Spring中的注解，加上这些注解的目的是可以区分JavaEE三层架构中
        的三个不同层次，其目的都是控制反转，将Java对象交给Spring容器创建。@Service注解是标注在实现类上的
*/
@Service
public class UserServiceImpl implements UserService {

    /*
    * mybatis通过JDK的动态代理方式，在启动加载配置文件时，根据配置mapper的xml去生成Dao的实现。
    *因此，userdao 没有实现类，也就不能在实现类上加@Reponsitory ，
    *     若 没有 required = false   则会报错
     * */
    @Autowired(required = false)
    private UserDao userDao;

    @Autowired
    private User user;

    @Override
    public User userLogin(Integer id, String password, String role) {

        return  userDao.login(id,password, role);
    }

    @Override
    public List<User> getUsersList(){
        return  userDao.selectUsersList();
    }


// = = = = = = = =  = = = = = = = =  = = = = = = = =  = = = = = = = =
    @Override
    public List<User> selectByKey(String key) {
        if(key==null || key.length() <= 0){
             return null;
        }
        List<User> users=userDao.selectByKey(key);
        return users;
    }

    @Override
    public int insert(User user) {
        if(user==null){
            return -1;
        }
        int insertCount = userDao.insert(user);
        if(insertCount==0){
            return -1;
        }
        return user.getUserId();  //0:fail    >0:ok
    }

    @Override
    public int update(User user)  {
        if(user==null){

            return -1;
        }
        int i = userDao.update(user) ;
        return i;
    }

    @Override
    public int deleteBatch(List<Integer> ids) {
       if(ids ==null){
           return -1;
       }
       int i=userDao.deleteBatch(ids);
        return i;
    }


}
