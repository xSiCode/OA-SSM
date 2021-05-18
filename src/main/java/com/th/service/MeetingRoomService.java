package com.th.service;

import com.th.entity.MeetingRoom;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-18
 */
public interface MeetingRoomService extends IService<MeetingRoom> {

    List<Map<String,Object>> listRoomDistinct();
}
