package com.th.dao;

import com.th.entity.Meeting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface MeetingMapper extends BaseMapper<Meeting> {
    List<Meeting> selectMeetingList();

}
