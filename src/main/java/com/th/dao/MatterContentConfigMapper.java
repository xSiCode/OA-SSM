package com.th.dao;

import com.th.entity.MatterContentConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-12
 */

@Repository
public interface MatterContentConfigMapper extends BaseMapper<MatterContentConfig> {
    MatterContentConfig selectContentConfigByConfigId(Integer configId);
}
