package com.th.dao;

import com.th.entity.TimeView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Interface : TimeViewMapper
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/27 11:08
 * @Version : 1.0
 */
@Repository
public interface TimeViewMapper {
    //1.得到待办事项：发起时间 + 事项名          超过了截止日期已完成，不提示
    List<Map<String, String>> selectMatter(@Param("userId") Integer userId, @Param("start") LocalDateTime startTime, @Param("end") LocalDateTime endTime);
    //2.得到待参加会议：会议开始时间+会议名       会议时间结束了 不提示
    List<Map<String, String>> selectMeeting(@Param("userId") Integer userId,@Param("start")LocalDateTime startTime,@Param("end") LocalDateTime endTime);
    //3.得到 计划       计划标题+ 开始时间      计划时间结束了，不提示
    List<Map<String, String>> selectPlan(@Param("userId") Integer userId,@Param("start")LocalDateTime startTime,@Param("end") LocalDateTime endTime);
    //4.管理员添加 待审核视图 映射。    待审核请假人数：x    时间，现在。
    List<Map<String, String>> selectAudit(@Param("userId") Integer userId,@Param("start")LocalDateTime startTime,@Param("end") LocalDateTime endTime);


  /*  //1.得到待办事项：发起时间 + 事项名          超过了截止日期已完成，不提示
    List<Map<String, Object>> selectMatter(@Param("userId") Integer userId,@Param("start")LocalDate startTime,@Param("end") LocalDate endTime);
    //2.得到待参加会议：会议开始时间+会议名       会议时间结束了 不提示
    List<Map<String, Object>> selectMeeting(@Param("userId") Integer userId,@Param("start")LocalDate startTime,@Param("end") LocalDate endTime);
    //3.得到 计划       计划标题+ 开始时间      计划时间结束了，不提示
    List<Map<String, Object>> selectPlan(@Param("userId") Integer userId,@Param("start")LocalDate startTime,@Param("end") LocalDate endTime);
    //4.管理员添加 待审核视图 映射。    待审核请假人数：x    时间，现在。
    List<Map<String, Object>> selectAudit(@Param("userId") Integer userId,@Param("start")LocalDate startTime,@Param("end") LocalDate endTime);*/
}
