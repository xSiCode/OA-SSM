package com.th.test.dao;

import com.th.bean.User;
//最让我迷惑的是，Test包，只能是倒入这个包org.junit.Test
import com.th.dao.UserDao;
import com.th.service.UserService;
import com.th.utils.RandomValueUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * @Class : crud
 * @Describe :
 * @Author : xSi
 * @Date : 2021/3/19 16:34
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class UserDaoTest {

    @Autowired
    User user;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    //jdk8 date test




    @Test
    public void login(){
        System.out.println("userService 走到 这一步了");
        Integer id=2110;
        String password= "897544";
        String role="T";
        user = userService.userLogin(id, password, role);
        System.out.println(user);
        if(user !=null){
            System.out.println("登录成功");

          //  return ResponseData.SUCCESS().extendData("user",userMap);
        }else {
            System.out.println("查无此人");
            System.out.println("2查无此人");
            System.out.println("3查无此人");
            System.out.println("4查无此人");
            System.out.println("5查无此人");
           // return ResponseData.ERROR();

        }
    }

    @Test
    public void selectById(){
        user=userDao.selectByUserId(1000);
        System.out.println(user);
    }

    @Test
    public void selectUsersList(){
        List<User> users=userDao.selectUsersList();
        System.out.println(users);
    }
    @Test
    public void insertUser(){
        LocalDate localDate=  LocalDate.of(1997,11,8);
        User user=new User(8888,"234567890","T",
                "杨测试","15114049298", "xsicode@qq.com",
                "男", localDate, "511529199711184514");
        int i=userDao.insertUser(user);
        user.getUserId();
        System.out.println(i);
    }



    @Test
    public void insertUsers(){
        //user  9个属性
        // ID 不用，使用自增
        String password="";
        String role="";
        String name="";
        String tel="";
        String email="";
        String sex="";
        LocalDate localDate=null;        //处理日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String birth="";
        String idCard="";
        //存储user列表
        List<User> users= new ArrayList<User>();
        User aNewUser=null; //用于list 集合添加元素使用，这里不用 依赖注入的user
        for(int i=0;i<100;i++){
            //user  9个属性
            // ID 不用，使用自增
            password= RandomValueUtil.getPassword();
            role=RandomValueUtil.getRole();
            name=RandomValueUtil.getChineseName();
            tel=RandomValueUtil.getTel();
            email=RandomValueUtil.getEmail();
            sex=RandomValueUtil.getSex();
            birth=RandomValueUtil.getBirth();  // string: 1997-11-08
            localDate= LocalDate.parse(birth,formatter);  // localDate : 1997-11-05
            idCard=RandomValueUtil.getIdCard(birth,sex);

            aNewUser = new User();
            aNewUser.setUserPassword(password);
            aNewUser.setUserRole(role);
            aNewUser.setUserName(name);
            aNewUser.setUserTel(tel);
            aNewUser.setUserEmail(email);
            aNewUser.setUserSex(sex);
            aNewUser.setUserBirth(localDate);  //日期：  string ---> localDate
            aNewUser.setUserIdCard(idCard);
             users.add(aNewUser);  //list集合每次添加的只是对象的引用值，而非堆空间的实际值
        }
        int i = userDao.insertUsers(users);
        System.out.println(i);
    }
}
