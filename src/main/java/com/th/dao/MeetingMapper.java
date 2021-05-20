package com.th.dao;

import com.th.entity.Meeting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.aop.MethodMatcher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-18
 */
@Repository
public interface MeetingMapper extends BaseMapper<Meeting> {
    List<Meeting> selectMeetingList();

    Meeting selectMeetingById( @Param("meetingId") Integer currentId);

    List<Map<String, Object>> selectMeetingReceiveByUser(
            @Param("currentUserId") Integer currentUserId,
            @Param("currentMeetingStatus") String currentMeetingStatus);

    List<Map<String, Object>> selectMeetingCreatorByUser(
            @Param("currentUserId") Integer currentUserId,
            @Param("currentMeetingStatus") String currentMeetingStatus);

    List<Map<String, Object>> selectMeetingCreatorByMeetingTitle(
            @Param("currentUserId") Integer currentUserId,
            @Param("title") String title,
            @Param("tempStatus") String tempStatus);

    List<Map<String, Object>> selectMeetingReceiveByMeetingTitle(
            @Param("currentUserId") Integer currentUserId,
            @Param("title") String title,
            @Param("satus") String status);
}
