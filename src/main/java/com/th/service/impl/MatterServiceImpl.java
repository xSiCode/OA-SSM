package com.th.service.impl;

import com.th.entity.Matter;
import com.th.dao.MatterMapper;
import com.th.service.MatterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class MatterServiceImpl extends ServiceImpl<MatterMapper, Matter> implements MatterService {

    @Autowired
    Matter matter;

    @Autowired
    MatterMapper matterMapper;



    @Override
    public Matter getMatter_matterId(Integer matterId) {

        Matter currentMatter= matterMapper.selectMatter_matterId( matterId);

        return null;
    }
}
