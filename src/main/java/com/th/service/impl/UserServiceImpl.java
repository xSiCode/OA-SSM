package com.th.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.th.entity.Organization;
import com.th.entity.User;
import com.th.dao.UserMapper;
import com.th.service.OrganizationService;
import com.th.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.th.utils.DataVerification;
import javafx.beans.binding.StringBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    User user;

    @Autowired
    OrganizationService organizationService;

    @Override
    public List<Object> ListUserOrganizationByUserId(User user) {
        List<Organization> organizations = organizationService.listParentPathsWithTreeById(user.getOrganizationId());
        List<Object> userOrganization = new ArrayList<>();
        userOrganization.add(user);
        userOrganization.add(organizations);

        return userOrganization;
    }

    @Override
    public List<Object> listUserOrganizationStringByUserId(User user) {
        String currentOrganization=organizationService.listParentPathsWithStringById(user.getOrganizationId());//得到职能部门-组织人事处-干事
        Map<String,String> userCurrentOrganizationMap=new HashMap<>();
        userCurrentOrganizationMap.put("currentOrganization",currentOrganization);//封装为map
        List<Object> userOrganizationList=new ArrayList<>();
        userOrganizationList.add(user);
        userOrganizationList.add(userCurrentOrganizationMap);
        return userOrganizationList;
    }

    @Override
    public List<Object> listUserOrganizationStringByUserId(List<User> users) {
        List<Object> objects=new ArrayList<>();
        for(User currentUser:users){
            //找出list中的每一个user,并找到他们的 职位string  最后添加到list中
            objects.add(listUserOrganizationStringByUserId(currentUser) );
        }
        return objects;
    }

    @Override
    public List<Object> listUserOrganizationStringByKey(String searchKey) {

        List<User> users = this.getUserByKey(searchKey);//将符合user表中字段查询的结果的users 传入list中
        users.addAll(this.getUserByOrganizationName(searchKey) );//将符合organization中name查询结结果 的 user 传入list中
        //java8 新特性，去重    以上两个表查询的结果 可能有重复，去重
        List<User> usersDistinct = users.stream().distinct().collect(Collectors.toList());
        ////根据在user表中查找到的数据，再加上 组织-职位
        List<Object> objects = this.listUserOrganizationStringByUserId(usersDistinct);

        return objects;
    }

    @Override
    public List<User> getUserByKey(String key) {
        //如果参数不能转为整数，则id查询时用 -1查询，即查不到. 。如果能转为整数，则使用转后的数值
        boolean flag = DataVerification.IfStrParseInt(key);
        String idKey="-1";
        if(flag ==true){//不能转
            idKey=key;
        }
        //按输入的条件，分别进行 id,name,email,tel,idCard 模糊查询
        List<User> users = baseMapper.selectList(new QueryWrapper<User>()
                .eq("user_id", idKey)
                .or()
                .like("user_name", key)
                .or()
                .like("user_email", key)
                .or()
                .like("user_tel", key)
                .or()
                .like("user_id_card", key)
        );
        return users;
    }

    @Override
    public List<User> getUserByOrganizationName(String organizationName) {
        //通过传入的 职位名字，查找出 对应的 user
        //1.找出所有 职位的organizations,没有重复OrganizationId,职位名称有
        List<Organization> organizations = organizationService.getOrganizationNameByKey(organizationName);
        List<User> users =new ArrayList<>();
        List<User> usersTemp =new ArrayList<>();
        //将每一个 职位 下对应的 user 都遍历出来，放入list中
        for(Organization currentOrganization:organizations){
            //遍历organizations,根据每一个orgId,再遍历user表， 有m*n 的感觉
            usersTemp = baseMapper.selectList(new QueryWrapper<User>()
                    .eq("organization_id", currentOrganization.getId() ));
            users.addAll(usersTemp);
        }
        //java8 新特性，去重 不需要去重，现在设计的是一个人只有一个职位
      //  List<User> usersDistinct = users.stream().distinct().collect(Collectors.toList());
        return users;
    }


}
