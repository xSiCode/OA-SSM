package com.th.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.th.entity.Meeting;
import com.th.dao.MeetingMapper;
import com.th.entity.MeetingAttendees;
import com.th.entity.MeetingRoom;
import com.th.service.MeetingAttendeesService;
import com.th.service.MeetingRoomService;
import com.th.service.MeetingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.th.utils.DataTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.RowSetMetaDataImpl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-18
 */
@Service
public class MeetingServiceImpl extends ServiceImpl<MeetingMapper, Meeting> implements MeetingService {

    @Autowired
    private Meeting meeting;
    @Autowired
    private MeetingAttendees meetingAttendees;
    @Autowired
    private MeetingRoom meetingRoom;
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private MeetingAttendeesService meetingAttendeesService;
    @Autowired
    private MeetingRoomService meetingRoomService;


    /* = = = = = = = = = = =      以下为  添加会议      = = = = = = = = = = =     */
    @Override
    public Integer insertMeeting(Map<String, Object> map) {
        //若获取到了id 则该数据之前是草稿，则需要的处理为 覆盖
        Integer ifId = (Integer) map.get("id");//只有他 可能没有数据
        //一次性获取必定能得到的数据
        String title = (String) map.get("title");
        String content = (String) map.get("content");
        LocalDateTime startTime = DataTransfer.parseStringToDate((String) map.get("startTime"));
        LocalDateTime endTime = DataTransfer.parseStringToDate((String) map.get("endTime"));
        String mode = (String) map.get("mode");
        Integer hostId = (Integer) map.get("hostId");
        Integer recorderId = (Integer) map.get("recorderId");
        String status = (String) map.get("status"); // 会议状态  待开
        String note = (String) map.get("note");
        Integer roomRoomId = (Integer) map.get("roomRoomId");
        String roomName = (String) map.get("roomName");//数据库表中没有，但Java对象，即前端传入参数有。
        //创建表中对象
        Meeting currentMeeting = new Meeting();  //创建 meeting对象
        MeetingRoom currentMeetingRoom = new MeetingRoom();//创建 meetingRoom对象
        MeetingAttendees currentAttendees = new MeetingAttendees();
        //根据 是否为首次插入分流
        if (ifId == null) {
            //首次新增，直接插入
            //判断线上还是线下，线下要判断 使用的会议室是否时间冲突
            if ("线下".equals(mode)) {
                //新增-线下
                /*     === = = = = = = = ==   room  = = = = = = = = = == = = = = = = = */
                currentMeetingRoom.setRoomId(roomRoomId);
                currentMeetingRoom.setName(roomName);
                currentMeetingRoom.setStartTime(startTime);
                currentMeetingRoom.setEndTime(endTime);
                currentMeetingRoom.setStatus("使用中");
                //判断会议室 是否有占用情况
                List<MeetingRoom> listRoom = meetingRoomService.list(new QueryWrapper<MeetingRoom>()
                        .eq("room_id", roomRoomId)
                        .eq("status", "使用中"));
                if (listRoom != null) {
                    //可能有冲突时间段
                    for (MeetingRoom currentSqlRoom : listRoom) {
                        int a = endTime.compareTo(currentSqlRoom.getStartTime());// <0 能插入
                        int b = startTime.compareTo(currentSqlRoom.getEndTime()); //> 0 能插入
                        if (!(a < 0 || b > 0)) {
                            //不能插入
                            System.out.println("需要插入的时间冲突");
                            return -1970;
                        }
                    }
                }
                //能走到这一步，说明没有时间冲突
                boolean saveRoom = meetingRoomService.save(currentMeetingRoom);
                if (saveRoom == false) {
                    System.out.println("会议室插入失败");
                    return -1;
                }
            } else if ("线上".equals(mode)) {
                //新增-线上  //2.4没有会议室相关信息。 没有操作，但留着
            } else {
                //新增-模式错误
                System.out.println("会议模式发送错误");
                return -1;
            }
            /*     === = = = = = = = ==   meeting  = = = = = = = = = == = = = = = = = */
            currentMeeting.setTitle(title);
            currentMeeting.setContent(content);
            currentMeeting.setStartTime(startTime);
            currentMeeting.setEndTime(endTime);
            currentMeeting.setMode(mode);
            currentMeeting.setHostId(hostId);
            currentMeeting.setRecorderId(recorderId);
            currentMeeting.setStatus("待开");  //写死，不用前端传过来的数据
            currentMeeting.setNote(note);
            currentMeeting.setRoomId(currentMeetingRoom.getId());
            boolean saveMeeting = meetingService.save(currentMeeting);
            if (saveMeeting == false) {
                System.out.println("会议插入失败");
                return -1;
            }
            /*     === = = = = = = = ==   attendees  = = = = = = = = = == = = = = = = = */
            //2.3获取参会人员 + 插入参会人员
            List<Integer> attendees = (List<Integer>) map.get("attendees");
            if (attendees != null) {
                //有参会人员
                for (Integer currentUserId : attendees) {
                    currentAttendees.setMeetingId(currentMeeting.getId());
                    currentAttendees.setUserId(currentUserId);
                    currentAttendees.setStartTime(startTime);
                    currentAttendees.setEndTime(endTime);
                    //循环插入数据库中
                    boolean saveAttendees = meetingAttendeesService.save(currentAttendees);
                    if (saveAttendees == false) {
                        System.out.println("批量插入参会人员失败");
                        return -1;
                    }
                }
            }
            //插入完成，返回插入后的 meetingId
        } else {
            //之前保存为草稿，下面将做数据替换
            Integer ifRoomId = (Integer) map.get("roomId");
            /*     === = = = = = = = ==   room  = = = = = = = = = == = = = = = = = */
            currentMeetingRoom.setId(ifRoomId);
            currentMeetingRoom.setRoomId(roomRoomId);
            currentMeetingRoom.setName(roomName);
            currentMeetingRoom.setStartTime(startTime);
            currentMeetingRoom.setEndTime(endTime);
            currentMeetingRoom.setStatus("使用中");
            boolean saveRoom = meetingRoomService.updateById(currentMeetingRoom);
            if (saveRoom == false) {
                System.out.println("会议室插入失败");
                return -1;
            }
            /*     === = = = = = = = ==   meeting  = = = = = = = = = == = = = = = = = */
            currentMeeting.setId(ifId);
            currentMeeting.setTitle(title);
            currentMeeting.setContent(content);
            currentMeeting.setStartTime(startTime);
            currentMeeting.setEndTime(endTime);
            currentMeeting.setMode(mode);
            currentMeeting.setHostId(hostId);
            currentMeeting.setRecorderId(recorderId);
            currentMeeting.setStatus(status);
            currentMeeting.setNote(note);
            currentMeeting.setRoomId(ifRoomId);
            boolean saveMeeting = meetingService.updateById(currentMeeting);
            if (saveMeeting == false) {
                System.out.println("会议插入失败");
                return -1;
            }
            /*     === = = = = = = = ==   attendees  = = = = = = = = = == = = = = = = = */
            //由于attendees可以有多个，为了避免存草稿时，参会人员多，发送时，参会人员少造成的问题，这里先删除原来保存为草稿的参会人员信息
            boolean deleteAttendees = meetingAttendeesService.
                    remove(new QueryWrapper<MeetingAttendees>().eq("meeting_id", ifId));
            if (deleteAttendees == false) {
                System.out.println("清除原来的参会人员失败");
                return -1;
            }
            List<Integer> attendees = (List<Integer>) map.get("attendees");
            if (attendees != null) {
                //有参会人员
                for (Integer currentUserId : attendees) {
                    currentAttendees.setMeetingId(currentMeeting.getId());
                    currentAttendees.setUserId(currentUserId);
                    currentAttendees.setStartTime(startTime);
                    currentAttendees.setEndTime(endTime);
                    //循环插入数据库中
                    boolean saveAttendees = meetingAttendeesService.save(currentAttendees);
                    if (saveAttendees == false) {
                        System.out.println("批量插入参会人员失败");
                        return -1;
                    }
                }
            }
        }
        //插入完成，返回插入后的 meetingId
        return currentMeeting.getId();
    }
    /* = = = = = = = = = = =      以下为  添加会议保存为草稿      = = = = = = = = = = =     */
    @Override
    public Integer insertDraft(Map<String, Object> map) {
        //若获取到了id 则该数据之前是草稿，则需要的处理为 覆盖
        Integer ifId = (Integer) map.get("id");//只有他 可能没有数据 null值
        //以下 都有可能不能获取到数据
        String title = (String) map.get("title");                                               // json参数中必须有
        String content = (String) map.get("content");
        LocalDateTime startTime = DataTransfer.parseStringToDate((String) map.get("startTime"));
        LocalDateTime endTime = DataTransfer.parseStringToDate((String) map.get("endTime"));
        String mode = (String) map.get("mode");                                                // json参数中必须有
        Integer hostId = (Integer) map.get("hostId");
        Integer recorderId = (Integer) map.get("recorderId");
        String status = (String) map.get("status"); // 会议状态 ： 待发起
        String note = (String) map.get("note");
        Integer roomRoomId = (Integer) map.get("roomRoomId");                                  //  json参数中必须有
        String roomName = (String) map.get("roomName");//数据库表中没有，但Java对象，即前端传入参数有。
        //创建表中对象
        Meeting currentMeeting = new Meeting();  //创建 meeting对象
        MeetingRoom currentMeetingRoom = new MeetingRoom();//创建 meetingRoom对象
        MeetingAttendees currentAttendees = new MeetingAttendees();
        //根据 是否为首次 保存为草稿
        if (ifId == null) {
            //首次新增，直接插入
            //判断线上还是线下，线下要判断 使用的会议室是否时间冲突
            if ("线下".equals(mode) && roomRoomId!=null ) {
                //新增-线下
                /*     === = = = = = = = ==   room  = = = = = = = = = == = = = = = = = */
                currentMeetingRoom.setRoomId(roomRoomId);
                currentMeetingRoom.setName(roomName);
                currentMeetingRoom.setStartTime(startTime);
                currentMeetingRoom.setEndTime(endTime);
                currentMeetingRoom.setStatus("空闲");
                //判断会议室 是否有占用情况
                List<MeetingRoom> listRoom = meetingRoomService.list(new QueryWrapper<MeetingRoom>()
                        .eq("room_id", roomRoomId)
                        .eq("status", "使用中"));
                if (listRoom != null) {
                    //可能有冲突时间段
                    for (MeetingRoom currentSqlRoom : listRoom) {
                        int a = endTime.compareTo(currentSqlRoom.getStartTime());// <0 能插入
                        int b = startTime.compareTo(currentSqlRoom.getEndTime()); //> 0 能插入
                        if (!(a < 0 || b > 0)) {
                            //不能插入
                            System.out.println("需要插入的时间冲突");
                            return -1970;
                        }
                    }
                }
                //能走到这一步，说明没有时间冲突
                boolean saveRoom = meetingRoomService.save(currentMeetingRoom);
                if (saveRoom == false) {
                    System.out.println("会议室插入失败");
                    return -1;
                }
            } else if ("线上".equals(mode)) {
                //新增-线上  //2.4没有会议室相关信息。 没有操作，但留着
            } else {
                //新增-模式错误
                System.out.println("会议模式发送错误");
                return -1;
            }
            /*     === = = = = = = = ==   meeting  = = = = = = = = = == = = = = = = = */
            currentMeeting.setTitle(title);
            currentMeeting.setContent(content);
            currentMeeting.setStartTime(startTime);
            currentMeeting.setEndTime(endTime);
            currentMeeting.setMode(mode);
            currentMeeting.setHostId(hostId);
            currentMeeting.setRecorderId(recorderId);
            currentMeeting.setStatus("待发起");                   //待发起
            currentMeeting.setNote(note);
            currentMeeting.setRoomId(currentMeetingRoom.getId());
            boolean saveMeeting = meetingService.save(currentMeeting);
            if (saveMeeting == false) {
                System.out.println("会议插入失败");
                return -1;
            }
            /*     === = = = = = = = ==   attendees  = = = = = = = = = == = = = = = = = */
            //2.3获取参会人员 + 插入参会人员
            List<Integer> attendees = (List<Integer>) map.get("attendees");
            if (attendees != null) {
                //有参会人员
                for (Integer currentUserId : attendees) {
                    currentAttendees.setMeetingId(currentMeeting.getId());
                    currentAttendees.setUserId(currentUserId);
                    currentAttendees.setStartTime(startTime);
                    currentAttendees.setEndTime(endTime);
                    //循环插入数据库中
                    boolean saveAttendees = meetingAttendeesService.save(currentAttendees);
                    if (saveAttendees == false) {
                        System.out.println("批量插入参会人员失败");
                        return -1;
                    }
                }
            }
        } else {
            //之前保存为草稿，下面将做数据替换
            Integer ifRoomId = (Integer) map.get("roomId");
            /*     === = = = = = = = ==   room  = = = = = = = = = == = = = = = = = */
            currentMeetingRoom.setId(ifRoomId);
            currentMeetingRoom.setRoomId(roomRoomId);
            currentMeetingRoom.setName(roomName);
            currentMeetingRoom.setStartTime(startTime);
            currentMeetingRoom.setEndTime(endTime);
            currentMeetingRoom.setStatus("空闲");
            boolean saveRoom = meetingRoomService.updateById(currentMeetingRoom);
            if (saveRoom == false) {
                System.out.println("会议室插入失败");
                return -1;
            }
            /*     === = = = = = = = ==   meeting  = = = = = = = = = == = = = = = = = */
            currentMeeting.setId(ifId);
            currentMeeting.setTitle(title);
            currentMeeting.setContent(content);
            currentMeeting.setStartTime(startTime);
            currentMeeting.setEndTime(endTime);
            currentMeeting.setMode(mode);
            currentMeeting.setHostId(hostId);
            currentMeeting.setRecorderId(recorderId);
            currentMeeting.setStatus(status);
            currentMeeting.setNote(note);
            currentMeeting.setRoomId(ifRoomId);
            boolean saveMeeting = meetingService.updateById(currentMeeting);
            if (saveMeeting == false) {
                System.out.println("会议插入失败");
                return -1;
            }
            /*     === = = = = = = = ==   attendees  = = = = = = = = = == = = = = = = = */
            //由于attendees可以有多个，为了避免存草稿时，参会人员多，发送时，参会人员少造成的问题，这里先删除原来保存为草稿的参会人员信息
            boolean deleteAttendees = meetingAttendeesService.
                    remove(new QueryWrapper<MeetingAttendees>().eq("meeting_id", ifId));
            if (deleteAttendees == false) {
                System.out.println("清除原来的参会人员失败");
                return -1;
            }
            List<Integer> attendees = (List<Integer>) map.get("attendees");
            if (attendees != null) {
                //有参会人员
                for (Integer currentUserId : attendees) {
                    currentAttendees.setMeetingId(currentMeeting.getId());
                    currentAttendees.setUserId(currentUserId);
                    currentAttendees.setStartTime(startTime);
                    currentAttendees.setEndTime(endTime);
                    //循环插入数据库中
                    boolean saveAttendees = meetingAttendeesService.save(currentAttendees);
                    if (saveAttendees == false) {
                        System.out.println("批量插入参会人员失败");
                        return -1;
                    }
                }
            }
        }
        //插入保存为草稿完成，返回插入后的 meetingId
        return currentMeeting.getId();
    }


    @Override
    public Meeting getMeetingById(Integer currentId) {
        Meeting currentMeeting = baseMapper.selectMeetingById(currentId);
        return currentMeeting;
    }
}