package com.th.dao;

import com.th.entity.Matter;
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
public interface MatterMapper extends BaseMapper<Matter> {

    Matter selectMatter_matterId(Integer matterId);
}
