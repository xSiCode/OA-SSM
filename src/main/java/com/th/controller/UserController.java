package com.th.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.th.entity.Organization;
import com.th.entity.User;
import com.th.service.OrganizationService;
import com.th.service.UserService;
import com.th.utils.DataVerification;
import com.th.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-07
 */
@RestController()
@RequestMapping("/user")
public class UserController {
    @Autowired
    private User user;

    @Autowired
    private UserService userService;

    @Autowired
    private Organization organization;

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseData login(@RequestBody Map<String, Object> userMap) {

        //获取传过来的参数
        Integer id = Integer.parseInt(String.valueOf(userMap.get("userId")));
        String password = String.valueOf(userMap.get("userPassword"));
        String role = String.valueOf(userMap.get("userRole"));
        user.setUserId(id);
        user.setUserPassword(password);
        user.setUserRole(role);
        System.out.println(user);
        try {
            user = userService.getOne(new QueryWrapper<User>()
                    .eq("user_id", user.getUserId())
                    .eq("user_password", user.getUserPassword())
                    .eq("user_role", user.getUserRole()));
            if (user != null) {
                System.out.println("登录成功,预计返回用户信息和他所对应的 组织-职位  ");//职能部门-组织人事处-干事
                List<Object> objects = userService.listUserOrganizationStringByUserId(user);
                return ResponseData.SUCCESS().extendData("userOrganization", objects);
            } else {
                System.out.println("账号或密码错误");
                return ResponseData.FAIL();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
    }

    @PostMapping("/getUsersList")
    public ResponseData getUsersList(@RequestBody Map<String, Object> map) {

        PageHelper.startPage((int) map.get("currentPage"), 8);
        List<User> users = userService.list();//这个结果已经分页了
        List<Object> objects = userService.listUserOrganizationStringByUserId(users);
        PageInfo page = new PageInfo(users, 8);
        return ResponseData.SUCCESS().extendData("userOrganization", objects);
    }

    @PostMapping("selectByKey")
    @ResponseBody
    public ResponseData selectBykey(@RequestBody Map<String, String> map) {
        String searchKey = map.get("searchKey");
        Integer needPage = Integer.parseInt(map.get("needPage"));
        PageHelper.startPage(needPage, 8);
        List<Object> objects = userService.listUserOrganizationStringByKey(searchKey);
        PageInfo pageInfo = new PageInfo(objects, 8);
        return ResponseData.SUCCESS().extendData("UserListPage", pageInfo);

    }

}

