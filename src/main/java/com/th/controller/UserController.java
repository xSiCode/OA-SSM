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
    public ResponseData login(@RequestBody Map<String, Object> userMap) {
        Integer id = Integer.parseInt(String.valueOf(userMap.get("userId")));
        String password = String.valueOf(userMap.get("userPassword"));
        String role = String.valueOf(userMap.get("userRole"));
        try {
            /*  调用service层的方法，作用是将前端输入的数据与数据库中数据进行比对。
            若数据库中存在符合条件的数据，则返回数据库整条字段。这里是：若登录成功，则返回用户的所有信息。 */
            user = userService.getOne(new QueryWrapper<User>()
                    .eq("user_id", id)
                    .eq("user_password", password)
                    .eq("user_role", role));
            /*  这里判断是否登录成功，若登录成功则返回用户更详细的信息。 */
            if (user != null) {
                System.out.println("登录成功,预计返回用户信息和他所对应的 组织-职位  ");//职能部门-组织人事处-干事
                user = userService.getUserOrganizationStringByUserId(user);
                return ResponseData.SUCCESS().extendData("userOrganization", user);
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
        /* 作用是分页功能，根据前端传入参数的currentPage页码值和和分页值8，在下一条语句中实现该作用*/
        PageHelper.startPage((int) map.get("currentPage"), 8);
        /* 数据库查询语句，查询结果为返回指定排序范围的指定数量的用户信息 */
        List<User> users = userService.list();//这个结果已经分页了 ,还差orgName
        /* 上一步查询的用户信息中不包括用户对应的职位名字，这一步加上。 */
        List<User> objects = userService.listUserOrganizationStringByUserId(users); //这个是 填充了 对应orgName
        /*将要返回给前端的数据装入分页信息中*/
        PageInfo page = new PageInfo(objects, 8);
        /*将要返回的对象信息封装到指定的返回类型对象中，并返回。*/
        return ResponseData.SUCCESS().extendData("userOrganization", page);
    }

    @PostMapping("selectByKey")
    public ResponseData selectBykey(@RequestBody Map<String, String> map) {
        String searchKey = map.get("searchKey");
        if (searchKey.length() == 0 || searchKey.length() > 10) {
                                                                    // 值为空
            return ResponseData.SUCCESS().extendData("UserListPage", user);
        }
        PageHelper.startPage(Integer.parseInt(map.get("needPage")), 8);
        List<User> users = userService.listUserFullLikeKey(searchKey);
        PageInfo pageInfo = new PageInfo(users, 8);
        return ResponseData.SUCCESS().extendData("UserListPage", pageInfo);
    }

    @PostMapping("getCurrentUserInfo")
    public ResponseData getCurrentUserInfo(@RequestBody Map<String, Integer> map) {
        try {
            User currentUser = userService.getOne(new QueryWrapper<User>().eq("user_id", map.get("currentUserId")));
            User userFull = userService.getUserOrganizationStringByUserId(currentUser);
            if (userFull != null) {
                return ResponseData.SUCCESS().extendData("userInfo", userFull);//插入成功，返回插入用户的id
            } else {
                return ResponseData.FAIL();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR().extendData("error", "服务端错误");
        }
    }


    /*若前端传入的参数不能正确封装为 User对象，则不能进入该方法里执行，前端会接收到错误*/
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
                currentUser = userService.getUserOrganizationStringByUserId(currentUser);
                return ResponseData.SUCCESS().extendData("updateUser", currentUser);
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
            } else {
                return ResponseData.FAIL().extendData("deleteBatch", "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR().extendData("error", "服务端错误");
        }


    }


    //该请求表现为懒加载，作用是，请求完getTree，再请求该路径，作用表现为，树状选择职位后，展示对应的用户
    @PostMapping("usersMappedByOrganization")
    public ResponseData UsersMappedByOrganization(@RequestBody Map<String, Integer> map) {
        Integer currentOrganizationId = map.get("currentOrganizationId");

        try {
            List<Map<Integer, String>> listMaps = userService.selectUsersIdNameByOrganizationId(currentOrganizationId);
            if (listMaps.isEmpty() || listMaps.size() == 0) {
                return ResponseData.FAIL().extendData("msg", "映射失败");
            } else {
                return ResponseData.SUCCESS().extendData("users", listMaps);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
    }

}

