package com.th.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.th.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public interface HomePageMapper extends BaseMapper<User> {
    List<Map<String, Object>> selectViewTodo(Integer userId);

    List<Map<String, Object>> selectViewDone(Integer userId);

    List<Map<String, Object>> selectViewNotice(Integer userId);

    List<Map<String, Object>> selectViewPlan(Integer userId);

   Integer countTodo(
           @Param("userId")  Integer userId,
           @Param("start") LocalDateTime startTime,
           @Param("end")  LocalDateTime endTime);

    Integer countMeeting(
            @Param("userId")  Integer userId,
            @Param("start")  LocalDateTime startTime,
            @Param("end")  LocalDateTime endTime);

    Integer countPlan(
            @Param("userId")  Integer userId,
            @Param("start")  LocalDateTime startTime,
            @Param("end")  LocalDateTime endTime);
}
