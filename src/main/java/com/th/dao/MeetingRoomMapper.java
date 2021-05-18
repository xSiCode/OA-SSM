package com.th.dao;

import com.th.entity.MeetingRoom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface MeetingRoomMapper extends BaseMapper<MeetingRoom> {

    List<Map<String, Object>> selectRoomDistinct();
}
