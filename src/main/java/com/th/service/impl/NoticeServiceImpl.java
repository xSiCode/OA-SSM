package com.th.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.th.dao.NoticeReceiverMapper;
import com.th.entity.Notice;
import com.th.dao.NoticeMapper;
import com.th.entity.NoticeReceiver;
import com.th.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.th.utils.DataTransfer;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-22
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private NoticeReceiverMapper noticeReceiverMapper;
    @Autowired
    private NoticeService noticeService;

    @Override
    public Notice getCreatNoticeDetailById(Integer currentId) {

        return baseMapper.selectCreatNoticeDetailById(currentId);

    }

    @Override
    public Integer insertNotice(Map<String, Object> map) {
        Integer ifId = (Integer) map.get("id"); // 可能null
        Integer creatorId = (Integer) map.get("creatorId");
        String sendingTimeStr = (String) map.get("sendingTime");  //  必须有
        String status = (String) map.get("status");  //             展示 /隐藏
        String title = (String) map.get("title");
        String content = (String) map.get("content");
        LocalDateTime sendingTime =null ;
        if (sendingTimeStr != null && sendingTimeStr.length() > 0) {
            sendingTime = DataTransfer.parseStringToDate(sendingTimeStr);
        }
        List<Integer> receivers  = (List<Integer>) map.get("receiverIds");   // 可能为空

        Notice current =new Notice();
        current.setId( ifId); // null  则为新增
        current.setCreatorId(creatorId );
        current.setSendingTime(sendingTime);
        current.setStatus(status);
        current.setTitle(title);
        current.setContent(content);
        //如果有ID的话 删除对应的参与人员。
        if(   ifId !=null ){
            noticeReceiverMapper.delete( new QueryWrapper<NoticeReceiver>().eq("notice_id",ifId));
        }
        //通知保存到数据库。
        boolean saveOrUpdate = noticeService.saveOrUpdate(current);
        if(!saveOrUpdate){
            System.out.println("新增，修改失败");
        }
        NoticeReceiver currentReceiver =new NoticeReceiver();
        if(receivers!=null){
            Integer currentId = current.getId();
            for (Integer currentReceiverId : receivers){
                currentReceiver.setNoticeId(currentId );
                currentReceiver.setReceiverId( currentReceiverId  );
                noticeReceiverMapper.insert(currentReceiver);
            }
        }


        return current.getId();
    }

    @Override
    public boolean CreatorDeleteNotice(List< Map<String,Object> > listMap) {

        for (Map<String, Object> currentMap : listMap) {
            Integer noticeId = (Integer) currentMap.get("noticeId");
            noticeReceiverMapper.delete(new QueryWrapper<NoticeReceiver>().eq("notice_id", noticeId));
            noticeService.remove(new QueryWrapper<Notice>().eq("id", noticeId).eq("status", "展示") );
        }
        return  true;
    }

    @Override
    public List<Map<String, Object>> listNoticeByReceiver(Integer receiverId) {

        return baseMapper.selectNoticeByReceiver(receiverId);
    }

    @Override
    public Notice getReceiverNoticeDetailById(Map<String,Integer> map   ) {
        Integer noticeId = (Integer) map.get("currentNoticeId");
        Integer receiverId = (Integer) map.get("receiverId");
        return baseMapper.selectReceiverNoticeDetailById(noticeId,receiverId);
    }

    @Override
    public boolean receiverDeleteNotice(List<Map<String, Object>> listMap) {
        //接收人 删除 接收到的通知，根据 通知id 和接收人的id
        for (Map<String, Object> currentMap : listMap) {
            Integer noticeId = (Integer) currentMap.get("noticeId");
            Integer receiverId = (Integer) currentMap.get("receiverId");
            int notice_id = noticeReceiverMapper
                    .delete(new QueryWrapper<NoticeReceiver>()
                            .eq("notice_id", noticeId)
                            .eq("receiver_id", receiverId));
            if (notice_id <1  ) {
                System.out.println("删除接收公告失败");
                return false;
            }
        }
        return  true;
    }

    @Override
    public List<Notice> listNoticeByCreator(Integer currentCreatorId) {
        return baseMapper.selectNoticeByCreator(currentCreatorId);
    }

}
