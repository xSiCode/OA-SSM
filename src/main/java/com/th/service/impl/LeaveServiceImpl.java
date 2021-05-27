package com.th.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.th.entity.Leave;
import com.th.dao.LeaveMapper;
import com.th.entity.User;
import com.th.service.LeaveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.th.service.OrganizationService;
import com.th.service.UserService;
import com.th.utils.DataTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-21
 */
@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave> implements LeaveService {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UserService userService;

    @Override
    public Leave getLeaveById(Integer currentId) {

        return baseMapper.selectLeaveById(currentId);
    }

    @Override
    public Integer insertLeave(Leave currentLeave) {
        currentLeave.setRecipientId( currentLeave.getApplicantId()   );//不要 请假承接人

        currentLeave.setStatus( "待审核"  );  //稳妥起见
        //去数据库组织表中，查找当前请假的审核人。以便 用户能直观的看到自己的审核人
        Integer getApproveId = getApproveIdByApplicantId( currentLeave.getApplicantId()  );
        currentLeave.setApproveId( getApproveId  ); //根据当前 申请人ID  去查找 审核人ID 并放入  请假详情审核人中
        boolean saveOrUpdate = leaveService.saveOrUpdate(currentLeave);
        if(saveOrUpdate){
            return currentLeave.getId();
        }else {
            return -1;
        }

//        Integer ifId = (Integer) map.get("id");
//        Integer applicantId = (Integer) map.get("applicantId");
//        LocalDateTime startTime = DataTransfer.parseStringToDate((String) map.get("startTime"));
//        LocalDateTime endTime = DataTransfer.parseStringToDate((String) map.get("endTime"));
//        String reason = (String) map.get("reason");
//        String tel = (String) map.get("tel");
//        String site = (String) map.get("site");
//        String category = (String) map.get("category");      //请假类别【病假，产假，婚假，事假，出差，其他】
//        Integer recipientId = (Integer) map.get("recipientId");
//        Integer approveId = (Integer) map.get("approveId");
//        String auditOpinion = (String) map.get("auditOpinion");
//        String status   = (String) map.get("status");    //待审核  通过  未通过   ，统一为待审
//        // 这个简单，根据 ifId 判断是否新建 还是 修改。
//
    }

    private Integer getApproveIdByApplicantId(Integer applicantId) {
        //根据申请人的ID 去寻找审核人的ID
        //1,根据applicantId 找到它的组织 父路径
        //遍历父路径第二个节点处相同的，且角色为A 的管理员，并返回。
        /*  - - - - -- - - - - - -   解题代码 - - - - - - - - - - - - - - - - - */
        //0,找到自己的组织list。
        //得到当前请假员的 职位id
        Integer applicantOrgId = organizationService.getOrgIdByUserId(applicantId);
        //根据请假人员职位ID 得到 职位 顺序
        List<Integer> applicantOrgIdOrder = organizationService.listParentPathsById(applicantOrgId);
        //根据审核人员的职位顺序 得到 他的第2级 职位 orgId
        Integer applicantIdOrg2th = applicantOrgIdOrder.get(2);
        if(applicantIdOrg2th ==null ){
            return null;
        }
        //得到  管理员用户的 id ,orgId
        List<User> approveList = userService.list(new QueryWrapper<User>()
                .select("user_id", "organization_id").eq("user_role", "A"));
        //遍历管理员
        //准备遍历 审核管理员的ID 是否和当前申请人ID 的组织顺序ID第二位相同
        User currentUser;//暂存用户
        List<Integer> orgIdByUserIdOrder ; //暂存组织ID的顺序

        Iterator <User> userIt = approveList.iterator();
        while (userIt.hasNext()){
            currentUser = userIt.next();
            //根据审核用户 找到他对应的职位orgId
            orgIdByUserIdOrder= organizationService.listParentPathsById(   currentUser.getOrganizationId()   );
            //判断 当前审核人员 职位顺序 第2位 是否 与 待审核用户 的相同，不相同，则从list中删除
            if( ! applicantIdOrg2th.equals(  orgIdByUserIdOrder.get(2)  ) ){
                userIt.remove();
            }
        }
        //要么还剩一个人，要不为空，或者为多。 管理人ID+管理人组织ID  提取出管理人ID
        if(approveList !=null){ //有一个或多个
            for (User approveUser: approveList){
                if( ! approveUser.getUserId().equals( applicantId   )){
                    // //如果当前管理员和申请人的ID不相同则就是他了
                    return approveUser.getUserId();
                }
            }
        }
        System.out.println("applicantId"+applicantId);////////////////////////////////
            //当前没有人能审核你。我们还没设计好组织表
            return applicantId;
    }

   

    @Override
    public List<Leave> listBrief(Map<String, Object> map) {
        Integer userId = (Integer) map.get("userId");
        List<Leave> leaves = baseMapper.selectBrief(userId);


//        String leaveStatus = (String) map.get("leaveStatus");
//        List<Leave>  leaves;
//        if("applicant".equals(leaveStatus)){
//
//            leaves = leaveService.list(  new QueryWrapper<Leave>().eq("applicant_id", userId));
//        }else if ("approve".equals(leaveStatus)){
//            leaves = leaveService.list(  new QueryWrapper<Leave>().eq("approve_id", userId));
//        }else {
//            System.out.println("用户状态错误");
//            return null;
//        }
        return leaves;
    }

    @Override
    public List<Leave> listNeedAuditBrief(Map<String, Object> map) {
        Integer currentApproveId = (Integer) map.get("approveId");
        String currentUserRole = (String) map.get("userRole");  // T A  ,只能A
        if( "A".equals(currentUserRole) ){
            List<Leave> leaves = baseMapper.selectNeedAudit(currentApproveId);
            //返回需要审核的人的 请假缩略图
            return    leaves;
        }else {
            System.out.println("角色不对");
            return  null;
        }
    }

    @Override
    public Integer auditProcess(Map<String, Object> map) {
        //读取前端传过来的关键处理信息
        Integer leaveId = (Integer) map.get("id");
        Integer applicantId = (Integer) map.get("applicantId");
        Integer approveId = (Integer) map.get("approveId");
        String  approveRole =(String) map.get("approveRole");
        String auditOpinion = (String) map.get("auditOpinion");
        String status =(String) map.get("status");
        Leave currentLeave = baseMapper.selectLeaveById(leaveId);  //已经有了当前请假详细信息
        if(currentLeave ==null){
            System.out.println("无此请假");
            return -1;
        }
        if( ! "A".equals(approveRole)){
            System.out.println("审核人不是管理员");
            return -1;
        }
        //再次判断 审核人员 与请假人 的组织顺序的第二级 是否相同，不相同，说明 审核人员缩略图处有误。
              //得到当前审核人员的 职位id     //根据审核人员职位ID 得到 职位 顺序  //根据审核人员的职位顺序 得到 他的第2级 职位 orgId
        Integer approvedOrgId = organizationService.getOrgIdByUserId(approveId);
        List<Integer> approveOrgOrder = organizationService.listParentPathsById(approvedOrgId);
        Integer approveOrg2th = approveOrgOrder.get(2);
            //同理判断 待审核用户
        Integer applicantOrgId = organizationService.getOrgIdByUserId(applicantId);
        List<Integer> applicantOrgOrder = organizationService.listParentPathsById(applicantOrgId);
        Integer applicantOrg2th = applicantOrgOrder.get(2);
        if( ! approveOrg2th.equals(applicantOrg2th)  ){
            System.out.println( "你的代码错误有点多");
            return -1;
        }
        //开始处理了，主要是 写 审核意见 和 处理状态（通过，不通过）
        currentLeave.setApproveId(approveId);
        currentLeave.setAuditOpinion(auditOpinion);
        currentLeave.setStatus(status);
        int i = baseMapper.updateById(currentLeave);
        if(i<0){
            System.out.println("到这一步了，都处理失败");
            return -1;
        }else {
            return leaveId;
        }
    }

    private List<Leave> getNeedAuditBrief(Integer approveId) {









//        事情有变，这个方法下面的步奏 都用不了了
        //返回需要审核的人的 请假缩略图
        /*  - - - - -- - - - - - -   思路 - - - - - - - - - - - - - - - - - */
        // 找到自己所在的组织中的 第3级  判断 是否相同，相同则为自己可以审核的。
        /*  0    1   2   3
            [1, 12, 35, 221]   管理员
            [1, 12, 35, 222]   干事    */
        //0,找到自己的组织list。
        //1,变出所有 请假信息中 审核状态为“待审核  的用户id   管理员一般不用请假....
        //2,遍历 该用户id 并添加对应的 组织list属性
        //3,依次遍历  组织list属性中的 第三个元素 是否与自己的相同
                // 3.1 相同 则返回它对应的请假信息。 不相同则略过

        /*  - - - - -- - - - - - -   解题代码 - - - - - - - - - - - - - - - - - */
        //0,找到自己的组织list。
        //得到当前审核人员的 职位id
        Integer approvedOrgId = organizationService.getOrgIdByUserId(approveId);
        //根据审核人员职位ID 得到 职位 顺序
        List<Integer> approveOrgOrder = organizationService.listParentPathsById(approvedOrgId);
        //根据审核人员的职位顺序 得到 他的第2级 职位 orgId
        Integer approveOrg2th = approveOrgOrder.get(2);
        if(approveOrg2th ==null ){
            return null;
        }
        //1,得到所有 请假信息中 审核状态为“待审核  的 请假id，申请人id,
        List<Leave> applicantLeaves = baseMapper.selectList(new QueryWrapper<Leave>().select("id,applicant_id").eq("status", "待审核"));
        Leave currentLeave ;
        Integer orgIdByUserId ;
        //暂时存放待审核用户的 职位顺序
        List<Integer> orgIdByUserIdOrder;
        //准备遍历 待审核的 用户  是否是 审核员属下的人
        Iterator <Leave> leaveIt = applicantLeaves.iterator();
        while (leaveIt.hasNext()){
            currentLeave = leaveIt.next();
            //根据待审核用户的userId ，找到他对应的职位orgId
             orgIdByUserId = organizationService.getOrgIdByUserId(currentLeave.getApplicantId());
             orgIdByUserIdOrder= organizationService.listParentPathsById(orgIdByUserId);
            //判断 当前待审核用户 职位顺序 第2位 是否 与 审核人员 的相同，不相同，则从list中删除
            if( ! approveOrg2th.equals(  orgIdByUserIdOrder.get(2)  ) ){
                leaveIt.remove();
            }
        }
        // 走到这一步，要么剩下的为null  要么为符合 自己审核的: 请假id+ 申请人id    下面提取处 请假id
        List<Integer> ids=new ArrayList<>();//存储提取的会议ids
        List<Leave> leaves=new ArrayList<>();//存储要返回的 符合要求的请假 缩略图
        if(applicantLeaves!=null){
            //根据剩下的请假，提取处请假ID 。
            for(Leave current : applicantLeaves){
                ids.add(   current.getId()   ) ;
            }
          leaves = baseMapper.selectBatchIds(ids); //将会议ids 集合做参数，查询出符合要求的 请求详情。
        }
        return leaves;
    }
}
