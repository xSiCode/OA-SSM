package com.th.dao;

import com.th.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-22
 */
@Repository
public interface NoticeMapper extends BaseMapper<Notice> {

    Notice selectCreatNoticeDetailById( @Param("currentId") Integer currentId);

    List<Map<String, Object>> selectNoticeByReceiver( @Param("currentId") Integer receiverId);

    Notice selectReceiverNoticeDetailById(   Integer currentId,Integer userId);

    List<Notice> selectNoticeByCreator(Integer currentCreatorId);
}
