package com.th.service;

import com.th.entity.Matter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-12
 */
public interface MatterService extends IService<Matter> {

    // //查看指定事项详情的时候  以事项为主
    Matter getMatterByMatterId(Integer matterId);

    //得到事项处理人的缩略图 发起的请求，发送过来userId  事项状态【代办，已办】
    List<Map<String,Object>> getMatterHandlerBriefByUser(Integer currentId , String matterStatus );

    List<Map<String,Object>> getMatterCreatorBriefByUser(Integer currentId , String matterStatus );

    //得到事项发起人的缩略图， 发送userId,事项状态【submit,draft】

   // List<Map<String,Object>> getMatterCreatorBriefByUser(Integer currentId , String matterStatus );

}
