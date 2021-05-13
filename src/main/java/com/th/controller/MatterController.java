package com.th.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.th.entity.Matter;
import com.th.entity.MatterHandler;
import com.th.entity.User;
import com.th.service.MatterHandlerService;
import com.th.service.MatterService;
import com.th.service.UserService;
import com.th.utils.ResponseData;
import org.apache.ibatis.annotations.ResultMap;
import org.mortbay.util.ajax.JSON;
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
 *  前端控制器
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-12
 */
@RestController
@RequestMapping("/matter")
public class MatterController {

    @Autowired
    Matter matter;

    @Autowired
    MatterService matterService;

    @Autowired
    UserService userService;

    @Autowired
    MatterHandler matterHandler;
    
    @Autowired
    MatterHandlerService matterHandlerService;
    //查看指定事项详情的时候  (用于 代发/已发事项 )
    @PostMapping("/getMatterByMatterId")
    public ResponseData getMatterByMatterId(@RequestBody Map<String,Integer> map){
        Integer currentId = map.get("currentMatterId");

        Matter currentMatter= matterService.getMatterByMatterId(currentId);

        return ResponseData.SUCCESS().extendData("matter",currentMatter);
    }

    //查看事项视图，待办视图 （ id<隐藏>，name,creator,handler ,startTime,deadlineTime ,status ）

    @PostMapping("getMatterByHandler")
    public ResponseData getMatterTodo(@RequestBody Map<String,String> map){
        Integer currentUserId=Integer.parseInt( map.get("userId")   );
        String currentStatus= map.get("currentStatus");
        Integer needPage = Integer.parseInt(map.get("needPage"));

        PageHelper.startPage(needPage, 8);
        List<Map<String, Object>> listMap = null;
        try {
            listMap = matterService.getMatterHandlerBriefByUser(currentUserId, currentStatus);
            System.out.println(JSON.toString(listMap));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.FAIL();
        }

        PageInfo page = new PageInfo(listMap, 8);
        return ResponseData.SUCCESS().extendData("matterHandler", page);

    }

    @PostMapping("getMatterByCreator")
    public ResponseData getMatterByCreator(@RequestBody Map<String,String> map){
        Integer needPage = Integer.parseInt(map.get("needPage"));
        Integer currentUserId=Integer.parseInt( map.get("userId")   );
        String currentStatus= map.get("currentStatus");

        PageHelper.startPage(needPage, 8);
        List<Map<String, Object>> listMap = null;
        try {
            listMap = matterService.getMatterCreatorBriefByUser(currentUserId, currentStatus);
            System.out.println(JSON.toString(listMap));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.FAIL();
        }
        PageInfo page = new PageInfo(listMap, 8);
        return ResponseData.SUCCESS().extendData("matterHandler", page);

    }


}

