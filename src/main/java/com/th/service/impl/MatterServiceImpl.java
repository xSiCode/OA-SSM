package com.th.service.impl;

import com.sun.javafx.collections.MappingChange;
import com.th.entity.Matter;
import com.th.dao.MatterMapper;
import com.th.service.MatterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.th.service.UserService;
import org.apache.ibatis.executor.statement.StatementUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-12
 */
@Service
public class MatterServiceImpl extends ServiceImpl<MatterMapper, Matter> implements MatterService {

    @Autowired
    Matter matter;

    @Autowired
    MatterMapper matterMapper;

    @Autowired
    UserService userService;

    @Override
    public Matter getMatterByMatterId(Integer matterId) {

        Matter currentMatter = matterMapper.selectMatterEntityByMatterId(matterId);
        return currentMatter;
    }

    @Override
    public List<Map<String, Object>> getMatterHandlerBriefByUser(Integer currentId, String matterStatus) {

        //加个详细的处理状态
        List<Map<String, Object>> listMap = baseMapper.selectMatterHandlerBriefByUser(currentId, matterStatus);

        System.out.println(listMap);

        String timeLimitStr = "";
        String completedTimeStr = "";
        String currentTImeStr = "";
        int flag = 0;
        if ("待办".equals(matterStatus)) {    //处理者 没有完成时间,只能用截止时间跟当前时间比较
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            currentTImeStr = dateTimeFormatter.format(localDateTime);

            for (Map<String, Object> map : listMap) {    //遍历每一个map 并根据时间比较，改变状态
                timeLimitStr =  map.get("timeLimit").toString();
                flag = currentTImeStr.compareTo(timeLimitStr); //<=0 ,代办  >0 未完成
                if(flag>0){
                    map.put("state", "未完成");
                }
            }
        } else if ("已办".equals(matterStatus)) {
            for(Map<String,Object> map : listMap ){
                timeLimitStr = (String) map.get("timeLimit").toString();
                completedTimeStr=(String) map.get("completedTime").toString();
                flag=completedTimeStr.compareTo(timeLimitStr);// <=0 已办  ； >0 截止日期小于完成日期：超时完成。
                if(flag>0){
                    map.put("state","超时完成");
                }

            }
        }


        //  LocalDateTime timeLimit = listMap.get("timeLimit")


        return listMap;
    }

    @Override
    public List<Map<String, Object>> getMatterCreatorBriefByUser(Integer currentId, String matterStatus) {
        String status="";
        List<Map<String, Object>> maps = new ArrayList<>();
        //待发，已发 区分。
        if( "draft".equals(matterStatus) ){
            maps = baseMapper.selectMatterCreatorDraftBriefByUser(currentId);
        }else if ("submit".equals(matterStatus)){
            maps = baseMapper.selectMatterCreatorSubmitBriefByUser(currentId);
        }



        return maps;
    }


}
