package com.th.service;

import com.th.entity.Matter;
import com.th.entity.MatterHandler;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-12
 */
public interface MatterHandlerService extends IService<MatterHandler> {

    //根据传入的事项的 status(draft/submit) 给 handler_status 赋值
  //  boolean setHandlerStatus_MatterStatus(String matterStatus);


    public boolean setHandlerStatus_by_matter_handler(String matterStatus, LocalDateTime startTime,
                                                 LocalDateTime deadlineTime, LocalDateTime completeTime);

}
