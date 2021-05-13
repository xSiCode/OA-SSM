package com.th.dao;

import com.th.entity.MatterHandler;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface MatterHandlerMapper extends BaseMapper<MatterHandler> {


    //根据matterId 查 很多个处理人。区别是 是否带有 creatorName
    List<MatterHandler> selectHandlersByMatterId(Integer matterId);
    List<MatterHandler> selectHandlersEntityByMatterId(Integer matterId);

    MatterHandler selectHandlerByMatterId(Integer matterId);

    List<MatterHandler> selectHandlersByMatterIds(List<Integer> matterIds);


    //根据 userId  得到他 对应的 matterId
    List<Integer> getMatterIdsByUserId (Integer currentId );



}
