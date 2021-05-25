package com.th.service;

import com.th.entity.Plan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-20
 */
public interface PlanService extends IService<Plan> {

    int deletePlan(List<Map<String, Integer>> list);
}
