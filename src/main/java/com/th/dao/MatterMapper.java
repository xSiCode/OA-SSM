package com.th.dao;

import com.th.entity.Matter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-12
 */
@Repository
public interface MatterMapper extends BaseMapper<Matter> {

    Matter selectMatterByMatterId(Integer matterId);


    //查看指定事项详细信息(用于 代发/已发事项 )根据传送过来的 matterId 找到该 事项详情
    Matter selectMatterEntityByMatterId(Integer matterId);

    //查看指定 用户的 事项缩略信息(用于 代发/已发事项 )根据传送过来的 userId 找到该用户 的 事项详情  代办/已办
    List<Map<String,Object>> selectMatterHandlerBriefByUser(
            @Param("userId") Integer userId,@Param("matterStatus") String matterStatus );

    //查看指定用户的 事项缩略图（用于 draft) 根据传送过来的 userId   找到对应的 事项
    List<Map<String,Object>> selectMatterCreatorDraftBriefByUser(
            @Param("userId") Integer userId );

    //查看指定用户的 事项缩略图（用于 submit) 根据传送过来的 userId    找到对应的 事项
    List<Map<String,Object>> selectMatterCreatorSubmitBriefByUser(
            @Param("userId") Integer userId );

    //查看指定用户的 事项详细信息(用于 代发/已发事项 )根据传送过来的 userId 找到该用户 的 事项详情
    List<Map<String,Object> > selectMatterHandlerDetailByUser(
            @Param("userId") Integer userId,@Param("matterStatus") String matterStatus);

//    //查看指定用户的 事项缩略图（用于 draft) 根据传送过来的 userId   找到对应的 事项
//    List<Map<String,Object>> selectMatterCreatorBriefByUser(
//            @Param("userId") Integer userId );

}
