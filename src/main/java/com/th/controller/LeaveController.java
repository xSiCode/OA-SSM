package com.th.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.th.dao.LeaveMapper;
import com.th.entity.Leave;
import com.th.entity.Meeting;
import com.th.service.LeaveService;
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
 * @since 2021-05-21
 */
@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    Leave leave;
    @Autowired
    LeaveService leaveService;
    @Autowired
    LeaveMapper leaveMapper;

    /* = = = = = = = = = = =  以下为：通用公用功能        = = = = = = = = = = =     */
    /* = = = = = = = = = = =      查看自己的请假详情 = = = = = = = = = = =     */
    /* = = = = = = = = = = =      查看自己需要审核的请假详情 = = = = = = = = = = =     */
    //根据leaveId 查询
    @PostMapping("getLeaveById")
    public ResponseData getMeetingById(@RequestBody Map<String, Integer> map) {
        Integer currentId = map.get("currentLeaveId");
        try {
            Leave currentLeave = leaveService.getLeaveById(currentId);
            if (currentLeave != null) {
                return ResponseData.SUCCESS().extendData("leave", currentLeave);
            } else {
                return ResponseData.FAIL();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
    }

    /* = = = = = = = =      以下： 针对请假申请人  = = = = = = = = = = =     */
    /* = = = = = = = = = = =      以下为新建请假 /修改请假   = = = = =     */
    @PostMapping("insertLeave")
    public ResponseData insertMeeting(@RequestBody Leave currentLeave) {
        //将数据放入service层处理，并返回成功插入后的会议id
        Integer getLeaveId = null;
        try {
            getLeaveId = leaveService.insertLeave(currentLeave);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
        if (getLeaveId > 0) {
            //插入成功，返回插入后的会议id
            return ResponseData.SUCCESS().extendData("meetingId", getLeaveId);
        } else {
            return ResponseData.FAIL();
        }
    }

    /* = = = = = = = = = = =      以下为删除请假        = = = = = = = = = = =     */
    @PostMapping("deleteLeave")
    public ResponseData deleteLeave(@RequestBody Map<String, List<Integer>> map) {
        List<Integer> ids = map.get("ids");
        try {
            boolean flag = leaveService.removeByIds(ids);
            if (flag) {
                return ResponseData.SUCCESS().extendData("deleteBatch", "批量删除成功");
            } else {
                return ResponseData.FAIL().extendData("deleteBatch", "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR().extendData("error", "服务端错误");
        }
    }

    /* = = = = = = = = = = =      以下为查看 请假历史 缩略图        = = = = = = = = = = =     */
    /* = = = = = = = = = = =      以下为查看自己已经审核了的请假缩略图        = = = = = = = = = = =     */
    @PostMapping("listBrief")
    public ResponseData listBrief(@RequestBody Map<String, Object> map) {
        Integer needPage = (Integer) map.get("needPage");
        PageHelper.startPage(needPage, 7);
        List<Leave> leaves = leaveService.listBrief(map);
        PageInfo page = new PageInfo(leaves, 7);
        return ResponseData.SUCCESS().extendData("listBrief", page);
    }

    /* = = = = = = = =      以下： 针对请假审核人     = = = = = = = = = = =     */
    /* = = = = = = = = = = =      查看自己需要审核的人      = = = = = = = = = = =     */
    @PostMapping("listNeedAuditBrief")
    public ResponseData listNeedAuditBrief(@RequestBody Map<String, Object> map) {
        Integer needPage = (Integer) map.get("needPage");
        PageHelper.startPage(needPage, 7);
        PageInfo page = null;
        try {
            List<Leave> leaves = leaveService.listNeedAuditBrief(map);
            page = new PageInfo(leaves, 7);
            return ResponseData.SUCCESS().extendData("listBrief", page);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.FAIL();
        }
    }

    /* = = = = = = = = = = =      以下为审核处理                = = = = = = = = = = =     */
    @PostMapping("auditProcess")
    public ResponseData auditProcess(@RequestBody Map<String, Object> map) {
        Integer auditedLeaveId = null;
        try {
            auditedLeaveId = leaveService.auditProcess(map);
            if (auditedLeaveId < 0) {
                return ResponseData.FAIL();
            }
            return ResponseData.SUCCESS().extendData("auditedLeaveId", auditedLeaveId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
    }


}

