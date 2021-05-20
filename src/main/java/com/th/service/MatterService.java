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

    //得到事项处理人的缩略图 发起的请求，发送过来userId  事项状态【代办，已办  draft,submit】
    List<Map<String,Object>> getMatterHandlerBriefByUser(Integer currentId , String matterStatus );
    List<Map<String,Object>> getMatterCreatorBriefByUser(Integer currentId , String matterStatus );

    //解析map,插入事项，返回事项的id
    Integer insertMatter(Map<String, Object> map);

    Integer insertDraft(Map<String, Object> map);

    Integer deleteMatterBatch(List<Map<String,Object>> listMap);

    List<Map<String,Object>> getMatterByMatterName(Map<String, Object> map);

    Integer completedMatter(Map<String, Integer> map);

    //得到事项发起人的缩略图， 发送userId,事项状态【submit,draft】

   // List<Map<String,Object>> getMatterCreatorBriefByUser(Integer currentId , String matterStatus );

}
