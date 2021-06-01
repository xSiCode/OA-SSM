package com.th.service.impl;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.th.dao.HomePageMapper;
import com.th.service.HomePageService;
import com.th.utils.DataTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Interface : TimeViewService
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/27 11:07
 * @Version : 1.0
 */
@Service
public class HomePageServiceImpl  implements HomePageService {

    @Autowired
    private HomePageMapper homePageMapper;

    @Override
    public List<Object> listView(Integer userId) {
        /*
            [
                [
                    atodo{ 待办   '','','',....''  },  id, title ,截止日期
                   done { 已办  },  Id ， title,完成日期
                ],
                [
                  notice  { 通知  },  id, title , 发起人
                  plan  { 计划  },  ID ， title ,开始时间
                ],
            ]
         */
        //先把他们四个 读取出来。。
        //             [ [ { 待办  },   title ,截止日期
       List<Map<String,Object>> todo =  homePageMapper.selectViewTodo(userId);
        //             [ [    { 已办  },， title,完成日期
        List<Map<String,Object>> done =  homePageMapper.selectViewDone(userId);
        //             [ [  { 通知  },  , title , 发起人
        List<Map<String,Object>> notice =  homePageMapper.selectViewNotice(userId);
        //             [ [  { 计划  },， title ,开始时间
        List<Map<String,Object>> plan =  homePageMapper.selectViewPlan(userId);
        //再按照前端需要格式组装。

        //拼装todo
        List<String> todoList = new ArrayList<>();
        StringBuilder todoSb = new StringBuilder("");
        for (Map<String,Object> current : todo){
            todoSb.append("待办：").append( current.get("title") ).append("  截止时间：").append(  current.get("time") ) ;  //title + time 拼接 一条
            todoList.add( todoSb.toString() );
            todoSb.delete(0, todoSb.length());
        }
        //拼装done
        List<String> doneList = new ArrayList<>();
        StringBuilder doneSb = new StringBuilder("");
        for (Map<String,Object> current : done){
            doneSb.append("已办：").append( current.get("title") ).append("  完成时间：").append(  current.get("time") ) ;  //title + time 拼接 一条
            doneList.add( doneSb.toString() );
            doneSb.delete(0, doneSb.length());
        }
        //拼装notice
        List<String> noticeList = new ArrayList<>();
        StringBuilder noticeSb = new StringBuilder("");
        for (Map<String,Object> current : notice){
            noticeSb.append("通知：").append( current.get("title") ).append("  通知时间：").append(  current.get("time") ) ;  //title + time 拼接 一条
            noticeList.add( noticeSb.toString() );
            noticeSb.delete(0, noticeSb.length());
        }
        //拼装plan
        List<String> planList = new ArrayList<>();
        StringBuilder planSb = new StringBuilder("");
        for (Map<String,Object> current : plan){
            planSb.append("计划：").append( current.get("title") ).append("  开始时间：").append(  current.get("time") ) ;  //title + time 拼接 一条
            planList.add( planSb.toString() );
            planSb.delete(0, planSb.length());
        }
        // 将以上的 按列 封装
        //第一列。
        List<Object >  list1=new ArrayList<>();
        list1.add( todoList  );
        list1.add(doneList);
        //第二列。
        List<Object >  list2=new ArrayList<>();
        list2.add( noticeList  );
        list2.add(planList);
        // 将第一列和第二列 封装成一个list
        List<Object> listResult = new ArrayList<>();
        listResult.add(list1);
        listResult.add(list2);
        return listResult;
    }

    @Override
    public List<Object> listTimeViewDot(Map<String, Object> map) {
        Integer userId = (Integer) map.get("userId");
        LocalDate startTime = DataTransfer.Str2YYYYmmDD(  (String) map.get("startTime")) ;
        LocalDate endTime = DataTransfer.Str2YYYYmmDD(  (String) map.get("endTime")) ;
        //根据传过来的参数，循环那么多次；
        int startDay = startTime.getDayOfMonth();    // 1
        int endDay = endTime.getDayOfMonth();     //31
        //存放 返回的值
        List<Object> viewDot = new ArrayList<>();

        for (int i =startDay-1 ;i<endDay; i++ ){
           LocalDateTime currentStart = startTime.plusDays(i).atTime(7,0,0);     //5-1   + (0-30 )
           LocalDateTime currentEnd = startTime.plusDays(i).atTime(23, 0, 0)   ;     //7-23 点
            //如果 指定日期有 待办事项，待参会议，待做计划， 则在界面显示红点。
            //待办的事项数目
            Integer todoNum = homePageMapper.countTodo(userId,currentStart,currentEnd);
            //，待参会议
            Integer AttNum = homePageMapper.countMeeting(userId,currentStart,currentEnd);
            //待做计划
            Integer plantNum = homePageMapper.countPlan(userId,currentStart,currentEnd);
            // 只要上面的 有一个，就都在界面显示红点
            Map<String,Object> day =new HashMap();
            day.put("date", currentStart.toLocalDate().toString());
            if(  ( todoNum !=null && todoNum >0)
                    ||( AttNum!=null  && AttNum >0)
                    || (plantNum!=null  && plantNum >0)   ){
                day.put("Is",true);
            }else {
                day.put("Is",false);
            }
            viewDot.add(day);

        }

        return viewDot;
    }
}
