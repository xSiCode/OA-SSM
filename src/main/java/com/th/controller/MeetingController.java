package com.th.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
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
 *  前端控制器
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
    public ResponseData insertMeeting(){

        return  null;
    }
    //添加会议的草稿
    @PostMapping("insertDraft")
    public ResponseData insertDraft(){

        return  null;
    }
    /* = = = = = = = = = = =      以下为 缩略图查询      = = = = = = = = = = =     */
    @PostMapping("listAttendeesByReceive")
    public ResponseData listAttendeesByReceive(){
    //做为参会人员查询缩略图  状态分为 ： 待参会 /已参会

        return  null;
    }
    @PostMapping("listAttendeesByCreator")
    public ResponseData listAttendeesByCreator(){
    //做为会议发起人查询缩略图  状态分为 ： 已发起/待发起

        return  null;
    }
    @PostMapping("listAttendeesByRecorder")
    public ResponseData listAttendeesByRecorder(){
    //做为纪要人 查询缩略图

        return  null;
    }
    /* = = = = = = = = = = =      以下为 查看会议详情      = = = = = = = = = = =     */
    //根据会议id 查询
    @PostMapping("getMeetingById")
    public ResponseData getMeetingById(){

        return  null;
    }
    /* = = = = = = = = = = =      以下为 按条件搜索       = = = = = = = = = = =     */
    @PostMapping("searchMeeting")
    public ResponseData searchMeeting(){
    //根据  会议状态 + 会议名 | 人员名字

        return  null;
    }
    /* = = = = = = = = = = =      以下为 删除会议       = = = = = = = = = = =     */
    @PostMapping("deleteMeeting")
    public ResponseData deleteMeeting(){
        //根据  会议状态[ 待发起 | 已参会 ] + 会议ids

        return  null;
    }


    /* = = = meetingRoom = = = =       meetingRoom      = = = = = meetingRoom = = = =     */
    /* = = = = = = = = = = =      以下为  会议室查询      = = = = = = = = = = =     */
    //会议室有哪些，作为选项列表展示
    @PostMapping("/getRoomDistinct")
    public ResponseData getRoom(){
        List<Map<String,Object>> list = meetingRoomService.listRoomDistinct();
        return  ResponseData.SUCCESS().extendData("room",list);
    }
    //整个会议室的使用详情。包括使用的，空闲的。
    @PostMapping("/getRoomList")
    public ResponseData getRoomList(@RequestBody Map<String,Object> map){
        Integer needPage = (Integer) map.get("needPage");
        PageHelper.startPage(needPage,7);
        List<MeetingRoom> list = meetingRoomService.list();
        return  ResponseData.SUCCESS().extendData("room",list);
    }

    /* = = = meetingAttendees = = = =     meetingAttendees     = = = = = meetingAttendees = = = =*/
    /* = = = = = = = = = = =      以下为  参会人员查询      = = = = = = = = = = =     */

}

