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

    /*结果集 级联查看 封装位 Matter .可用于 个人查看自己的事项详情，管理员查看所有指定事项详情*/
    //查看指定事项详细信息(用于 代发/已发事项 )根据传送过来的 matterId 找到该 事项详情
    Matter selectMatterByMatterId(Integer matterId);
    //上面这个是以Java entity为主体映射，下面这个是以数据库表位映射主体。
    Matter selectMatterEntityByMatterId(Integer matterId);


    /*用于事项的视图，默认打开待办事项。根据传入的userId 和 状态【待办，已办，draft,submit】 查看所有与自己相关的事项*/
    //查看指定 用户的 事项缩略信息(用于 代发/已发事项 )根据传送过来的 userId 找到该用户 的 事项详情  代办/已办
    List<Map<String,Object>> selectMatterHandlerBriefByUser(
            @Param("userId") Integer userId,@Param("matterStatus") String matterStatus );
    //查看指定用户的 事项缩略图（用于 draft) 根据传送过来的 userId   找到对应的 事项
    List<Map<String,Object>> selectMatterCreatorDraftBriefByUser(
            @Param("userId") Integer userId );
    //查看指定用户的 事项缩略图（用于 submit) 根据传送过来的 userId    找到对应的 事项
    List<Map<String,Object>> selectMatterCreatorSubmitBriefByUser(
            @Param("userId") Integer userId );


    List<Map<String, Object>> selectMatterCreatorDraftBriefByUserLike(
            @Param("userId") Integer currentUserId, @Param("matterName") String needMatterName);

    List<Map<String, Object>> selectMatterCreatorSubmitBriefByUserLike(
            @Param("userId") Integer currentUserId, @Param("matterName")  String needMatterName);

    List<Map<String, Object>> selectMatterHandlerBriefByUserLike(
            @Param("userId") Integer currentUserId,
            @Param("matterStatus") String matterStatus,
            @Param("matterName")  String needMatterName);
    /*级联插入属性值，级联给数据库表赋值（3个表），额外的用户表作为参数  只涉及到一个事项的操作*/


}
