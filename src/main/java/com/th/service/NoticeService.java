package com.th.service;

import com.th.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-22
 */
public interface NoticeService extends IService<Notice> {

    Notice getCreatNoticeDetailById(Integer currentId);

    Integer insertNotice(Map<String, Object> map);

    boolean CreatorDeleteNotice(List< Map<String,Object> > listMap);

    List<Map<String, Object>> listNoticeByReceiver(Integer receiverId);

    Notice getReceiverNoticeDetailById(Map<String,Integer> map);

    boolean receiverDeleteNotice(List<Map<String, Object>> listMap);

    List<Notice> listNoticeByCreator(Integer currentCreatorId);
}
