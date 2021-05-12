package com.th.service;

import com.th.entity.Matter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-12
 */
public interface MatterService extends IService<Matter> {


    Matter getMatter_matterId(Integer matterId);
}
