package com.th.dao;

import com.th.entity.NoticeReceiver;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-22
 */
@Repository
public interface NoticeReceiverMapper extends BaseMapper<NoticeReceiver> {
    List<NoticeReceiver> selectNoticeReceiverByNoticeId(@Param("noticeId") Integer currentNoticeId );
}
