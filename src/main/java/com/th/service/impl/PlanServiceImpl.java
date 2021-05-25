package com.th.service.impl;

import com.th.entity.Plan;
import com.th.dao.PlanMapper;
import com.th.service.PlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.th.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-20
 */
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements PlanService {
    @Autowired
    PlanService planService;

    @Override
    public int deletePlan(List<Map<String, Integer>> list) {
        int deleteNums = 0;
        for (Map<String, Integer> map : list) {
            boolean planId = planService.removeById(map.get("planId"));
            if (planId) {
                deleteNums++;
            } else {
                return -1;
            }
        }
        return deleteNums;
    }

}
