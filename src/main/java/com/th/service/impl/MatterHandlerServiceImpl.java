package com.th.service.impl;

import com.th.dao.MatterMapper;
import com.th.entity.Matter;
import com.th.entity.MatterHandler;
import com.th.dao.MatterHandlerMapper;
import com.th.service.MatterHandlerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-12
 */
@Service
public class MatterHandlerServiceImpl extends ServiceImpl<MatterHandlerMapper, MatterHandler> implements MatterHandlerService {


    @Override
    public boolean setHandlerStatus_by_matter_handler(String matterStatus,LocalDateTime startTime,
                                                 LocalDateTime deadlineTime,LocalDateTime completeTime) {
        if (matterStatus == null || matterStatus.isEmpty())
            return false;

        if ("draft".equals(matterStatus)) {
            return false;
        } else if ("submit".equals(matterStatus)) {
            setHandlerStatus( startTime,deadlineTime, completeTime);
        }

        return false;
    }

    private void setHandlerStatus(LocalDateTime startTime, LocalDateTime deadlineTime, LocalDateTime completeTime) {
        //前端提交数据时，startTime,deadlineTime 都不为空，  matterHandler.completeTime 也不会为空
        //if(   )
    }
}
