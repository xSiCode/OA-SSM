package com.th.dao;

import com.th.entity.OrgUser;
import com.th.entity.Organization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
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
public interface OrganizationMapper extends BaseMapper<Organization> {


    //结果集是 组织-用户
    List<OrgUser> selectOrgUser();

    //根据 orgId 得到 userId userName;
    @MapKey("id")
    List< Map<String,String> > getUserIdNameByOrgId(Integer currentOrgId);

    Integer selectOrgByUserId(@Param("userId") Integer userId);
}
