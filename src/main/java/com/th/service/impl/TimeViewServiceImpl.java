package com.th.service.impl;

import com.th.dao.TimeViewMapper;
import com.th.entity.TimeView;
import com.th.service.TimeViewService;
import com.th.utils.DataTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Class : TimeViewwServiceImpl
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/27 11:07
 * @Version : 1.0
 */
@Service
public class TimeViewServiceImpl implements TimeViewService {

    @Autowired
    private TimeViewMapper timeViewMapper;


    @Override
    public List<TimeView> listTimeView(Map<String, Object> map) {
        Integer userId = (Integer) map.get("userId");
        String userRole = (String) map.get("userRole");
        LocalDate startTime = DataTransfer.Str2YYYYmmDD((String) map.get("startTime"));
        LocalDate endTime = DataTransfer.Str2YYYYmmDD((String) map.get("endTime"));
        // 已知：前端传过来的 时间数据 始终为 周一至周日，即 0-6 ，8-21
        List<TimeView> timeViews = new ArrayList<>();  //存放要 返回的时间视图数据  有14段
        for (int sdTime = 8; sdTime != 22; sdTime++) {   //8 -21 点   start-end time    划分时间段
            //时间段 拼接  日期 + 时间  每一天的时间段
            // currentTimeView  对象
            TimeView ctv = new TimeView();
            // 属性str
            ctv.setTime(sdTime + ":00 -" + (sdTime + 1) + ":00");
            //以下为 返回的周一至周日的属性值str
            for (int week = 0; week != 7; week++) {
                //更改日期，即 从周一换到周日
                LocalDateTime start = startTime.plusDays(week).atTime(sdTime, 0, 0);
                LocalDateTime end =  startTime.plusDays(week).atTime(sdTime + 1, 0, 0);
                // 1-7周 返回属性
                //将每一个时间段，如 08:00 - 09:00  对应的 周一至周日七段数据，每段4个待办点 填入。
                //1.得到待办事项：事项名          超过了截止日期已完成，不提示   title  Time
                List<Map<String, String>> matter = timeViewMapper.selectMatter(userId, start, end);
                //2.得到待参加会议：会议名       会议时间结束了 不提示
                List<Map<String, String>> meeting = timeViewMapper.selectMeeting(userId, start, end);
                //3.得到 计划      开始时间      计划时间结束了，不提示
                List<Map<String, String>> plan = timeViewMapper.selectPlan(userId, start, end);
                //4.管理员添加 待审核视图 映射。    待审核请假人数：x    时间，现在。
                List<Map<String, String>> audit = timeViewMapper.selectAudit(userId, start, end);
                System.out.println(matter);
                System.out.println(meeting);
                System.out.println(plan);
                System.out.println(audit);
                //以上的数据都是符合条件的数据，将以上数据封装成一个string
                String packTitle; //准备 承接封装内容。
                StringBuilder matterSb = new StringBuilder("");
                StringBuilder meetingSb =new StringBuilder("");
                StringBuilder planSb = new StringBuilder("");
                StringBuilder auditSb = new StringBuilder("");
                //matter
                if ( !(matter == null  || matter.isEmpty() ) ) {
                    matterSb.append(" 待办:");
                    for (Map<String, String> current : matter) {
                        matterSb.append( current.get("title") + ";")  ;
                    }
                }
                //meeting
                if (  !(meeting == null  || meeting.isEmpty() )  ) {
                    meetingSb.append(" 待开:");
                    for (Map<String, String> current : meeting) {
                        meetingSb.append( current.get("title") + ";")  ;
                    }
                }
                //plan
                if ( !(plan == null  || plan.isEmpty() ) ) {
                    planSb.append(" 计划:");
                    for (Map<String, String> current : plan) {
                        planSb.append( current.get("title") + ";")  ;
                    }
                }
                //audit
                if ( !(audit == null  || audit.isEmpty() ) ) {
                    auditSb.append(" 待审:");
                    for (Map<String, String> current : audit) {
                        auditSb.append( current.get("title") + ";")  ;
                    }
                }
                // 将以上 4段 封装到 一起。
                packTitle = matterSb.toString() + meetingSb + planSb + auditSb;
                //将读取到的 内容填充 到  每周中
                switch (week) {
                    case 0:
                        ctv.setMon(packTitle);
                        break;
                    case 1:
                        ctv.setTue(packTitle);
                        break;
                    case 2:
                        ctv.setWed(packTitle);
                        break;
                    case 3:
                        ctv.setThu(packTitle);
                        break;
                    case 4:
                        ctv.setFri(packTitle);
                        break;
                    case 5:
                        ctv.setSat(packTitle);
                        break;
                    case 6:
                        ctv.setSun(packTitle);
                        break;
                    default:
                        System.out.println("星期插入错误");
                        return null;
                }
            }
            //在这一步，将 ctv 插入 要返回的 list 中
            timeViews.add(ctv);

        }
        return timeViews;
    }
}
