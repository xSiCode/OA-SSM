package com.th.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.th.entity.Matter;
import com.th.entity.Meeting;
import com.th.entity.MeetingAttendees;
import com.th.entity.MeetingRoom;
import com.th.service.MeetingAttendeesService;
import com.th.service.MeetingRoomService;
import com.th.service.MeetingService;
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
 * @since 2021-05-18
 */
@RestController()
@RequestMapping("/meeting")
public class MeetingController {
    @Autowired
    private Meeting meeting;
    @Autowired
    private MeetingAttendees meetingAttendees;
    @Autowired
    private MeetingRoom meetingRoom;
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private MeetingAttendeesService meetingAttendeesService;
    @Autowired
    private MeetingRoomService meetingRoomService;

    /* = = = = = = = = = = =      以下为  添加会议      = = = = = = = = = = =     */
    //添加会议
    @PostMapping("insertMeeting")
    public ResponseData insertMeeting(@RequestBody Map<String, Object> map) {
        //将数据放入service层处理，并返回成功插入后的会议id
        Integer getMeetingId = null;
        try {
            getMeetingId = meetingService.insertMeeting(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
        if (getMeetingId > 0) {
            //插入成功，返回插入后的会议id
            return ResponseData.SUCCESS().extendData("meetingId", getMeetingId);
        } else {
            return ResponseData.FAIL().extendData("msg", "时间冲突了");
        }
    }

    //添加会议的草稿
    @PostMapping("insertDraft")
    public ResponseData insertDraft(@RequestBody Map<String, Object> map) {
        //将数据放入service层处理，并返回成功插入后的会议id
        Integer getMeetingId = null;
        try {
            getMeetingId = meetingService.insertDraft(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
        if (getMeetingId > 0) {
            //插入成功，返回插入后的会议id
            return ResponseData.SUCCESS().extendData("meetingId", getMeetingId);
        } else {
            return ResponseData.FAIL().extendData("msg", "时间冲突了");
        }
    }

    /* = = = = = = = = = = =      以下为 缩略图查询      = = = = = = = = = = =     */
    @PostMapping("listMeetingByReceive")
    public ResponseData listMeetingByReceive(@RequestBody Map<String, String> map) {
        Integer needPage = Integer.parseInt(map.get("needPage"));       //分页参数
        //做为参会人员查询缩略图  状态分为 ： 待参会 /已参会
        Integer currentUserId = Integer.parseInt(map.get("userId"));  // attendees 之一的一个   + 一个纪要人
        String currentMeetingStatus = map.get("currentStatus");   //  meetingStatus:待开/已开 ---》 attendees:待参会/已参会
        //类似于定时器的功能，再执行分页查询之前执行该函数，作用是 修改会议状态 ，   显示为： 待开，已开，开会中
        meetingService.setMeetingStatus();
        PageHelper.startPage(needPage, 7);
        try {
            List<Map<String, Object>> listMap = meetingService.getMeetingReceiveByUser(currentUserId, currentMeetingStatus);
            if (listMap != null) {
                PageInfo page = new PageInfo(listMap, 7);
                return ResponseData.SUCCESS().extendData("meetingHandler", page);
            }else {
                return ResponseData.SUCCESS().extendData("msg","没有该会议对应状态的信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
    }

    @PostMapping("listMeetingByCreator")
    public ResponseData listMeetingByCreator(@RequestBody Map<String, String> map) {
        //做为会议发起人查询缩略图  状态分为 ： 已发起/待发起
        Integer needPage = Integer.parseInt(map.get("needPage"));       //分页参数
        Integer currentUserId = Integer.parseInt(map.get("userId"));  // attendees 之一的一个   + 一个纪要人
        String currentMeetingStatus = map.get("currentStatus");   //  meetingStatus:待开/已开 ---》 attendees:待参会/已参会
        meetingService.setMeetingStatus();
        PageHelper.startPage(needPage, 7);
        try {
            List<Map<String, Object>> listMap = meetingService.getMeetingCreatorByUser(currentUserId, currentMeetingStatus);
            if (listMap != null) {
                PageInfo page = new PageInfo(listMap, 7);
                return ResponseData.SUCCESS().extendData("meetingHandler", page);
            }else {
                return ResponseData.SUCCESS().extendData("msg","没有该会议对应状态的信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
    }

    /* = = = = = = = = = = =      以下为 查看会议详情      = = = = = = = = = = =     */
    //根据会议id 查询
    @PostMapping("getMeetingById")
    public ResponseData getMeetingById(@RequestBody Map<String, Integer> map) {
        Integer currentId = map.get("currentMeetingId");
        Meeting currentMeeting = meetingService.getMeetingById(currentId);
        if (currentMeeting != null) {
            return ResponseData.SUCCESS().extendData("meeting", currentMeeting);
        } else {
            return ResponseData.FAIL().extendData("msg", "查无该会议");
        }
    }

    /* = = = = = = = = = = =      以下为 按条件搜索       = = = = = = = = = = =     */
    @PostMapping("getMeetingByMeetingName")
    public ResponseData getMeetingByMeetingName(@RequestBody Map<String, Object> map) {
        //根据  会议状态 + 会议名 | 人员名字
        try {
            PageHelper.startPage( (Integer)map.get("needPage") ,7  );
            List<Map<String,Object>> currentMeetings  = meetingService.getMeetingByMeetingName(map);
            if(currentMeetings !=null){
                PageInfo page = new PageInfo(currentMeetings, 7);
                return ResponseData.SUCCESS().extendData("meetingHandler",page);
            }else {
                return ResponseData.FAIL().extendData("msg","没有该会议对应状态的信息");   //对象存在，值为空
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseData.ERROR().extendData("msg","我的我的");
        }
    }

    /* = = = = = = = = = = =      以下为 删除会议       = = = = = = = = = = =     */
    @PostMapping("deleteMeeting")
    public ResponseData deleteMeeting( @RequestBody List< Map<String,Object> > listMap) {
        //host: 待发起  已发起（待开/已开）   receiver: 待开  已开
        //1.将传入的 map 在service层处理
        Integer flag = null;
        try {
            flag = meetingService.deleteMeetingBatch(listMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR().extendData("msg","删除事项失败，后端处理错误");
        }
        if(flag>0){
            return  ResponseData.SUCCESS().extendData("deleteNums",flag);//extendData("insertMatter",insertMatter);
        }else {
            return ResponseData.FAIL().extendData("msg","删除成功，返回失败");
        }
    }


    /* = = = meetingRoom = = = =       meetingRoom      = = = = = meetingRoom = = = =     */
    /* = = = = = = = = = = =      以下为  会议室查询      = = = = = = = = = = =     */
    //会议室有哪些，作为选项列表展示
    @PostMapping("/getRoomDistinct")
    public ResponseData getRoom() {
        List<Map<String, Object>> list = meetingRoomService.listRoomDistinct();
        return ResponseData.SUCCESS().extendData("room", list);
    }

    //整个会议室的使用详情。使用的详情。
    @PostMapping("/getRoomList")
    public ResponseData getRoomList(@RequestBody Map<String, Object> map) {
        Integer needPage = (Integer) map.get("needPage");
        PageHelper.startPage(needPage, 7);
        List<MeetingRoom> list = meetingRoomService.list();
        return ResponseData.SUCCESS().extendData("room", list);
    }
}

