package com.th.dao;

import com.th.entity.MeetingAttendees;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.aop.MethodMatcher;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-18
 */
@Repository
public interface MeetingAttendeesMapper extends BaseMapper<MeetingAttendees> {
        //无条件 输出 所有的参会人员
        List<MeetingAttendees> selectAttendeesList();
        //根据 事项id 输出 参会人员信息
        List<MeetingAttendees> selectAttendeesByMeetingId( @Param("meetingId") Integer meetingId);
        //根据 用户id  输出 参会信息
        List<MeetingAttendees> selectAttendeesByUserId( @Param("userId") Integer userId);
}
