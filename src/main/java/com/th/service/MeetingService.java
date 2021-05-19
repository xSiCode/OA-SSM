package com.th.service;

import com.th.entity.Meeting;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-18
 */
public interface MeetingService extends IService<Meeting> {

    Integer insertMeeting(Map<String, Object> map);

    Integer insertDraft(Map<String, Object> map);

    Meeting getMeetingById(Integer currentId);

}