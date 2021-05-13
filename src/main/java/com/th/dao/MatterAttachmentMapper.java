package com.th.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.th.entity.Matter;
import com.th.entity.MatterAttachment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-12
 */
@Repository
public interface MatterAttachmentMapper extends BaseMapper<MatterAttachment> {

    MatterAttachment selectAttachmentByMatterId(Integer matterId);
    List<MatterAttachment> selectAttachmentsByMatterId(Integer matterId);
    List<MatterAttachment> selectAttachmentsByMaterIds(List<Integer> matterIds);

}
