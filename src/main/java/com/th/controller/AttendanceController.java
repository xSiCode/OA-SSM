package com.th.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.th.dao.AttendanceMapper;
import com.th.entity.Attendance;
import com.th.entity.Plan;
import com.th.service.AttendanceService;
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
 * @since 2021-05-20
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private Attendance attendance;
    @Autowired
    AttendanceMapper attendanceMapper;
    @Autowired
    AttendanceService attendanceService;

    /* - - - - - -- - - - -   查看打卡详情 - -  - - - - - - - - -  -*/
    @PostMapping("getAttendanceById")
    public ResponseData getAttendanceById(@RequestBody Map<String,Integer> map){
        Attendance currentAttendance = attendanceMapper.selectAttendanceOne( map.get("attendanceId"));
        if(currentAttendance!=null){
            return ResponseData.SUCCESS().extendData("currentAttendance",currentAttendance);
        }
        return ResponseData.FAIL()  ;
    }

    /* - - - - - -- - - - -   查看打卡列表 - -  - - - - - - - - -  -*/
    @PostMapping("getAttendanceList")
    public ResponseData getAttendanceList(@RequestBody Map<String,Integer> map){
        PageHelper.startPage(map.get("needPage"),7);
        List<Attendance> currentAttendance = attendanceMapper.selectAttendance(  map.get("userId")   );
        if(currentAttendance!=null){
            PageInfo page = new PageInfo(currentAttendance,7);
            return ResponseData.SUCCESS().extendData("plans",page);
        }
        return ResponseData.FAIL()  ;
    }
    /* - - - - - -- - - - -   补打卡 - -  - - - - - - - - -  -*/
    @PostMapping("punchCard")
    public ResponseData punchCard(@RequestBody Attendance currentAttendance){
        boolean save = attendanceService.save(currentAttendance);
        if(save){
            return ResponseData.SUCCESS().extendData("planId",currentAttendance.getId());
        }
        return ResponseData.FAIL()  ;


    }

}

