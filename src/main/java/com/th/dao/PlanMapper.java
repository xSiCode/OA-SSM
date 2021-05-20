package com.th.dao;

import com.th.entity.Plan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-20
 */
@Repository
public interface PlanMapper extends BaseMapper<Plan> {
    List<Plan> selectListAll();

    Plan selectPlanOne(Integer id);
}
