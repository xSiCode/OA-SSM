package com.th.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.th.dao.PlanMapper;
import com.th.entity.Plan;
import com.th.service.PlanService;
import com.th.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-20
 */
@RestController
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    private Plan plan;

    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private PlanService planService;

    /* - - - - - -- - - - -   查看计划详情 - -  - - - - - - - - -  -*/
    @PostMapping("getPlanById")
    public ResponseData getPlanById(@RequestBody Map<String, Integer> map) {
        Plan currentPlan = planMapper.selectPlanOne(map.get("planId"));
        if (currentPlan != null) {
            return ResponseData.SUCCESS().extendData("plans", currentPlan);
        }
        return ResponseData.FAIL();
    }

    /* - - - - - -- - - - -   查询计划 - -  - - - - - - - - -  -*/
    @PostMapping("list")
    public ResponseData list(@RequestBody Map<String, Integer> map) {
        PageHelper.startPage(map.get("needPage"), 8);
        List<Plan> plans = planMapper.selectListAll(map.get("userId"));
        if (plans != null) {
            PageInfo page = new PageInfo(plans, 8);
            return ResponseData.SUCCESS().extendData("plans", page);
        }
        return ResponseData.FAIL();
    }

    /* - - - - - -- - - - -   搜索计划 - -  - - - - - - - - -  -*/
    @PostMapping("listSearch")
    public ResponseData listSearch(@RequestBody Map<String, Object> map) {
        PageHelper.startPage((Integer) map.get("needPage"), 8);
        Integer userId = (Integer) map.get("userId");
        String searchTitle = (String) map.get("searchTitle");
        List<Plan> plans = planMapper.searchTitle(userId, searchTitle);
        if (plans != null) {
            PageInfo page = new PageInfo(plans, 8);
            return ResponseData.SUCCESS().extendData("plans", page);
        }
        return ResponseData.FAIL();
    }

    /* - - - - - -- - - - -   新建计划 - -  - - - - - - - - -  -*/
    @PostMapping("insert")
    public ResponseData insert(@RequestBody Plan currentPlan) {
        boolean save = planService.save(currentPlan);
        if (save) {
            return ResponseData.SUCCESS().extendData("planId", currentPlan.getId());
        }
        return ResponseData.FAIL();
    }

    /* - - - - - -- - - - -   修改计划 - -  - - - - - - - - -  -*/
    @PostMapping("update")
    public ResponseData update(@RequestBody Plan currentPlan) {
        boolean save = planService.updateById(currentPlan);
        if (save) {
            return ResponseData.SUCCESS().extendData("planId", currentPlan.getId());
        }
        return ResponseData.FAIL();
    }

    /* - - - - - -- - - - -   删除计划 - -  - - - - - - - - -  -*/
    @PostMapping("delete")
    public ResponseData delete(@RequestBody List<Map<String, Integer>> list) {
        try {
            int deleteNums = planService.deletePlan(list);
            if (deleteNums > 0) {
                return ResponseData.SUCCESS().extendData("deleteNums", deleteNums);
            } else {
                return ResponseData.FAIL();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
    }

}

