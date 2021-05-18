package com.th.service.impl;

import com.th.entity.MeetingRoom;
import com.th.dao.MeetingRoomMapper;
import com.th.service.MeetingRoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-18
 */
@Service
public class MeetingRoomServiceImpl extends ServiceImpl<MeetingRoomMapper, MeetingRoom> implements MeetingRoomService {

    @Override
    public List<Map<String,Object> >  listRoomDistinct() {
        List<Map<String,Object>> roomId_Name= baseMapper.selectRoomDistinct();
        return roomId_Name;
    }
}
