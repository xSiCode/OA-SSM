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
    //查看指定事项详情的时候  (用于 代发/已发 事项  )

    @PostMapping("/getMatterByMatterId")
    public ResponseData getMatterByMatterId(@RequestBody Map<String,Integer> map){
        Integer currentId = map.get("currentMatterId");

        Matter currentMatter= matterService.getMatterByMatterId(currentId);

        return ResponseData.SUCCESS().extendData("matter",currentMatter);
    }

    @PostMapping("/getMatterByMatterName")
    public ResponseData getMatterByMatterName(@RequestBody Map<String,Object> map){
        List<Map<String,Object>> currentMatters= null;
        try {
            PageHelper.startPage( (Integer)map.get("needPage") ,7  );
            currentMatters = matterService.getMatterByMatterName(map);
            if(currentMatters !=null){
                PageInfo page = new PageInfo(currentMatters, 7);
                return ResponseData.SUCCESS().extendData("matter",page);
            }else {
                                                                //对象存在，值为空
                return ResponseData.SUCCESS().extendData("matter",matter);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseData.ERROR().extendData("msg","我的我的");
        }

    }


    //查看事项视图，待办视图 （ id<隐藏>，name,creator,handler ,startTime,deadlineTime ,status ）
        // status :待办，已办
    @PostMapping("getMatterByHandler")
    public ResponseData getMatterByHandler(@RequestBody Map<String,String> map){
        Integer currentUserId=Integer.parseInt( map.get("userId")   );
        String currentStatus= map.get("currentStatus");
        Integer needPage = Integer.parseInt(map.get("needPage"));

        PageHelper.startPage(needPage, 7);
        List<Map<String, Object>> listMap = null;
        try {
            listMap = matterService.getMatterHandlerBriefByUser(currentUserId, currentStatus);
            System.out.println(JSON.toString(listMap));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.FAIL();
        }

        PageInfo page = new PageInfo(listMap, 7);
        return ResponseData.SUCCESS().extendData("matterHandler", page);

    }
        //status:draft,submit
    @PostMapping("getMatterByCreator")
    public ResponseData getMatterByCreator(@RequestBody Map<String,String> map){
        Integer needPage = Integer.parseInt(map.get("needPage"));
        Integer currentUserId=Integer.parseInt( map.get("userId")   );
        String currentStatus= map.get("currentStatus");

        PageHelper.startPage(needPage, 7);
        List<Map<String, Object>> listMap = null;
        try {
            listMap = matterService.getMatterCreatorBriefByUser(currentUserId, currentStatus);
            System.out.println(JSON.toString(listMap));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.FAIL();
        }
        PageInfo page = new PageInfo(listMap, 7);
        return ResponseData.SUCCESS().extendData("matterHandler", page);

    }

    @PostMapping("insertMatter")
    public ResponseData insertMatter(@RequestBody Map<String,Object> map){
        //1.将传入的 map 在service层处理
        Integer matterId = null;
        try {
            matterId = matterService.insertMatter(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR().extendData("msg","插入失败，后端处理错误");
        }
        if(matterId>0){
             //2，插入成功后，根据插入成功后返回的ID，再返回当前事项的详情。
            // Matter insertMatter= matterService.getMatterByMatterId( matterId );
             return  ResponseData.SUCCESS().extendData("matterInsertedId",matterId);//extendData("insertMatter",insertMatter);
         }else {
             return ResponseData.FAIL().extendData("msg","插入成功，返回失败");
         }
    }

    @PostMapping("insertDraft")
    public ResponseData insertDraft(@RequestBody Map<String,Object> map ){
        //1.将传入的 map 在service层处理
        Integer matterId = null;
        try {
            matterId = matterService.insertDraft(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR().extendData("msg","保存草稿失败，后端处理错误");
        }
        if(matterId>0){
            return  ResponseData.SUCCESS().extendData("matterInsertedId",matterId);//extendData("insertMatter",insertMatter);
        }else {
            return ResponseData.FAIL().extendData("msg","保存成功，返回失败");
        }
    }
   // @PostMapping("updateDraft")   用 matterId  进入事项详情，即可修改   （  只有事项状态是 草稿 才能修改）

   //删除，指定事项id, userId。
    @PostMapping("deleteMatterBatch")
    public ResponseData deleteMatterBatch( @RequestBody List< Map<String,Object> > listMap ){
        //1.将传入的 map 在service层处理
        Integer flag = null;
        try {
            flag = matterService.deleteMatterBatch(listMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR().extendData("msg","删除事项失败，后端处理错误");
        }
        if(flag>0){
            return  ResponseData.SUCCESS().extendData("matterInsertedId",flag);//extendData("insertMatter",insertMatter);
        }else {
            return ResponseData.FAIL().extendData("msg","删除成功，返回失败");
        }
    }




}

