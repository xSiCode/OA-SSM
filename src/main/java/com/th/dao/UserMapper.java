package com.th.dao;

import com.th.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-07
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
   //根据职位id,找出对应的 用户id,name
   List< Map<Integer,String> > selectUsersIdNameByOrganizationId(@Param("organizationId") Integer organizationId);
}
