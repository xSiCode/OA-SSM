package com.th.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.th.entity.MatterAttachment;
import com.th.dao.MatterAttachmentMapper;
import com.th.service.MatterAttachmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-12
 */
@Service
public class MatterAttachmentServiceImpl extends ServiceImpl<MatterAttachmentMapper, MatterAttachment> implements MatterAttachmentService {

    @Autowired
    MatterAttachment matterAttachment;


}
