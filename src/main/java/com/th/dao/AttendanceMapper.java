package com.th.dao;

import com.th.entity.Attendance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-20
 */
@Repository
public interface AttendanceMapper extends BaseMapper<Attendance> {
    List<Attendance> selectAttendance( Integer userId);
    Attendance selectAttendanceOne( Integer id);
}
