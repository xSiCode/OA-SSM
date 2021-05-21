package com.th.service;

import com.th.entity.Leave;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-21
 */
public interface LeaveService extends IService<Leave> {

    Leave getLeaveById(Integer currentId);

    Integer insertLeave(Leave currentLeave);

    List<Leave> listBrief(Map<String, Object> map);

    List<Leave> listNeedAuditBrief(Map<String, Object> map);

    Integer auditProcess(Map<String, Object> map);
}
