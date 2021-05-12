package com.th.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.th.entity.Organization;
import com.th.entity.User;
import com.th.service.OrganizationService;
import com.th.service.UserService;
import com.th.service.impl.UserServiceImpl;
import com.th.utils.DataVerification;
import com.th.utils.ResponseData;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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


    @PostMapping("/login")
    @ResponseBody
    public ResponseData login(@RequestBody Map<String, Object> userMap) {

        //获取传过来的参数
        Integer id = Integer.parseInt(String.valueOf(userMap.get("userId")));
        String password = String.valueOf(userMap.get("userPassword"));
        String role = String.valueOf(userMap.get("userRole"));
        try {
            user = userService.getOne(new QueryWrapper<User>()
                    .eq("user_id", id)
                    .eq("user_password",password)
                    .eq("user_role", role));

            if (user != null) {
                System.out.println("登录成功,预计返回用户信息和他所对应的 组织-职位  ");//职能部门-组织人事处-干事

                user=userService.getUserOrganizationStringByUserId(user);

                return ResponseData.SUCCESS().extendData("userOrganization", user);
            } else {
                System.out.println("账号或密码错误");

                System.out.println("4444444444444444444"+user);
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
        List<User> users = userService.list();//这个结果已经分页了 ,还差orgName
        List<User> objects = userService.listUserOrganizationStringByUserId(users); //这个是 填充了 对应orgName
        PageInfo page = new PageInfo(objects, 8);
        return ResponseData.SUCCESS().extendData("userOrganization", page);
    }

    @PostMapping("selectByKey")
    public ResponseData selectBykey(@RequestBody Map<String, String> map) {
        String searchKey = map.get("searchKey");
        if (searchKey.length() == 0 || searchKey.length() > 10) {
            return ResponseData.FAIL().extendData("msg", "输入为空或长度过长");
        }
        PageHelper.startPage(Integer.parseInt(map.get("needPage")), 8);
        List<User> users = userService.listUserFullLikeKey(searchKey);
        PageInfo pageInfo = new PageInfo(users, 8);
        return ResponseData.SUCCESS().extendData("UserListPage", pageInfo);
    }

    @PostMapping("insert")
    public ResponseData insert(@RequestBody User currentUser) {
        try {
            boolean save = userService.save(currentUser);
            if (save) {
                return ResponseData.SUCCESS().extendData("insertUserId", currentUser.getUserId());//插入成功，返回插入用户的id
            } else {
                return ResponseData.FAIL().extendData("msg", "插入失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR().extendData("error", "服务端错误");
        }
    }

    @PostMapping("update")
    public ResponseData update(@RequestBody User currentUser) {
        try {
            boolean flag = userService.updateById(currentUser);
            if (flag) {
                currentUser=userService.getUserOrganizationStringByUserId(currentUser);
                return ResponseData.SUCCESS().extendData("updateUser",currentUser);
            } else {
                return ResponseData.FAIL().extendData("updateState", "修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR().extendData("error", "服务端错误");
        }
    }

    @PostMapping("deleteBatch")
    public ResponseData deleteBatch(@RequestBody Map<String, List<Integer>> map) {
        List<Integer> ids = map.get("ids");
        try {
            boolean flag = userService.removeByIds(ids);
            if (flag) {
                return ResponseData.SUCCESS().extendData("deleteBatch", "批量删除成功");
            }else {
                return ResponseData.FAIL().extendData("deleteBatch", "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR().extendData("error", "服务端错误");
        }


    }


    //该请求表现为懒加载，作用是，请求完getTree，再请求该路径，作用表现为，树状选择职位后，展示对应的用户
    @PostMapping("usersMappedByOrganization")
    public ResponseData UsersMappedByOrganization(@RequestBody Map<String,Integer> map){
        Integer currentOrganizationId = map.get("currentOrganizationId");

        try {
            List<Map<Integer, String>> listMaps = userService.selectUsersIdNameByOrganizationId(currentOrganizationId);
            if( listMaps.isEmpty() || listMaps.size()==0 ){
                return ResponseData.FAIL().extendData("msg","映射失败");
            }else {
                return ResponseData.SUCCESS().extendData("users",listMaps);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
    }

}

