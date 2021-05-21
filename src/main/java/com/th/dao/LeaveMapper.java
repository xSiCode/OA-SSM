package com.th.dao;

import com.th.entity.Leave;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-21
 */
@Repository
public interface LeaveMapper extends BaseMapper<Leave> {

    //查看请假详情
    Leave selectLeaveById( @Param("leaveId") Integer currentId);
    //1,遍历出所有 请假信息中 审核状态为“待审核  的用户id
    List<Integer> listApplicantIds ();
}
