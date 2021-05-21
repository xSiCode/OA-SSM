package com.th.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.th.entity.*;
import com.th.dao.MeetingMapper;
import com.th.service.MeetingAttendeesService;
import com.th.service.MeetingRoomService;
import com.th.service.MeetingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.th.utils.DataTransfer;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.ELState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.RowSetMetaDataImpl;
import java.lang.management.MemoryType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
            if (ifRoomId != null) {
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
        //时间判断空
        String startTimeStr = (String) map.get("startTime");
        String endTimeStr = (String) map.get("endTime");
        LocalDateTime startTime =null ;
        LocalDateTime endTime =null ;
        if (startTimeStr != null && startTimeStr.length() > 0) {
             startTime = DataTransfer.parseStringToDate(startTimeStr);
        }
        if (endTimeStr != null && endTimeStr.length() > 0) {
            endTime = DataTransfer.parseStringToDate(endTimeStr);
            System.out.println("endTime="+endTime);
        }
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
            if ("线下".equals(mode) && roomRoomId != null) {
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
            if (roomRoomId != null) {
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

    /* = = = = = = = = = = =      以下为 查看会议详情      = = = = = = = = = = =     */
    @Override
    public Meeting getMeetingById(Integer currentId) {

        return baseMapper.selectMeetingById(currentId);
    }

    /* = = = = = = = = = = =      以下为 缩略图查询 receive      = = = = = = = = = = =     */
    @Override
    public List<Map<String, Object>> getMeetingReceiveByUser(Integer currentUserId, String currentMeetingStatus) {
        //读取出 参会人员 的会议处理状态  待开 / 已开     ----> 显示为 待参会/已参会    接下来要做进一步处理
        List<Map<String, Object>> listMap = baseMapper.selectMeetingReceiveByUser(currentUserId, currentMeetingStatus);
        //加个详细的处理状态   显示为 待参会/已参会    接下来要做进一步处理
        LocalDateTime currentDateTime = LocalDateTime.now();
        String startTimeStr = "";
        String endTimeStr = "";
        String currentTimeStr = "";
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        currentTimeStr = dateTimeFormatter.format(localDateTime);
        if (listMap != null) {
            for (Map<String, Object> currentMap : listMap) {
                if ("待开".equals(currentMap.get("status"))) {
                    startTimeStr = currentMap.get("startTime").toString();
                    endTimeStr = currentMap.get("endTime").toString();
                    if (currentTimeStr.compareTo(startTimeStr) > 0 && currentTimeStr.compareTo(endTimeStr) < 0) {
                        currentMap.put("state", "正在开会");
                    }
                    currentMap.put("status", "待参会");
                } else if ("已开".equals(currentMap.get("status"))) {
                    currentMap.put("status", "已参会");
                } else {
                    System.out.println("会议状态错误 meetingService");
                }
            }
        }
        return listMap;
    }

    @Override
    public List<Map<String, Object>> getMeetingCreatorByUser(Integer currentUserId, String currentMeetingStatus) {
        //读取出 发起人员 的会议处理状态  待发起/已发起{ 待开 / 已开 }    ----> 显示为 待参会/已参会    接下来要做进一步处理
        //如果 meetStatus = 待发起，  待发起         发   ；
        //                 已发起 ，则为 待开已开    开
        String tempStatus = null;
        if ("待发起".equals(currentMeetingStatus)) {
            tempStatus = "起";
        } else if ("已发起".equals(currentMeetingStatus)) {
            tempStatus = "开";
        }
        List<Map<String, Object>> listMap = baseMapper.selectMeetingCreatorByUser(currentUserId, tempStatus);
        if (listMap != null) {
            for (Map<String, Object> currentMap : listMap) {
                if ("待发起".equals(currentMap.get("status"))) {
                    currentMap.put("status", "待发起");
                } else if ("已发起".equals(currentMap.get("status"))) {
                    currentMap.put("status", "已发起");
                } else {
                    System.out.println("会议状态错误 meetingService");
                }
            }
        }
        return listMap;
    }

    @Override
    public List<Map<String, Object>> getMeetingByMeetingName(Map<String, Object> map) {
        //status   待发起-已发起  -       待开 - 已开
        String status = (String) map.get("status");
        String title = (String) map.get("title");
        Integer currentUserId = (Integer) map.get("userId");
        List<Map<String, Object>> maps = new ArrayList<>();
        //根据状态分流
        System.out.println(map);
        if ("待发起".equals(status) || "已发起".equals(status)) {
            String tempStatus;
            if ("待发起".equals(status)) {
                tempStatus = "起";
            } else {
                tempStatus = "开";
            }
            return baseMapper.selectMeetingCreatorByMeetingTitle(currentUserId, title, tempStatus);
            ////////////////////////////////
        } else if ("待开".equals(status) || "已开".equals(status)) {
            //读取出 参会人员 的会议处理状态  待开 / 已开     ----> 显示为 待参会/已参会    接下来要做进一步处理
            List<Map<String, Object>> listMap = baseMapper.selectMeetingReceiveByMeetingTitle(currentUserId, title, status);
            //加个详细的处理状态   显示为 待参会/已参会    接下来要做进一步处理
            if (listMap != null) {
                for (Map<String, Object> currentMap : listMap) {
                    if ("待开".equals(status)) {
                        currentMap.put("status", "待参会");
                    } else {
                        currentMap.put("status", "已参会");
                    }
                }
            }
            return listMap;
        }
        return maps;
    }

    @Override
    public Integer deleteMeetingBatch(List<Map<String, Object>> listMap) {
        //host: 待发起  已发起（待开/已开）   receiver: 待开  已开
        for (Map<String, Object> currentMap : listMap) {
            Integer integer = deleteMeeting(currentMap);
            if (integer < 0) {
                System.out.println("删除事项失败");
                return -1;
            }
        }
        return 1;

    }

    @Override
    public Integer setMeetingStatus() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Integer updateNums = -1;
        List<Meeting> meetings = meetingService.list(new QueryWrapper<Meeting>().eq("status", "待开"));
        if (meetings != null) {
            for (Meeting currentMeeting : meetings) {
                //如果当前时间大于会议的结束时间，则把会议改为 已开
                if (currentDateTime.compareTo(currentMeeting.getEndTime()) > 0) {
                    currentMeeting.setStatus("已开");
                    meetingService.updateById(currentMeeting);
                    updateNums++;
                }
            }
        }

        return updateNums;
    }

    private Integer deleteMeeting(Map<String, Object> map) {
        //host: 待发起   已开
        String status = (String) map.get("status");
        Integer meetingId = (Integer) map.get("meetingId");
        Integer userId = (Integer) map.get("userId");
        if ("待发起".equals(status)) {
            Meeting currentMeeting = meetingService.getById(meetingId);
            // 删除 attendees
            List<MeetingAttendees> currentAttendees = meetingAttendeesService
                    .list(new QueryWrapper<MeetingAttendees>()
                            .eq("meeting_id", currentMeeting.getId()));
            if (currentAttendees != null) {
                boolean meetingAttendees = meetingAttendeesService
                        .remove(new UpdateWrapper<MeetingAttendees>()
                                .eq("meeting_id", currentMeeting.getId()));
                if (meetingAttendees == false) {
                    System.out.println("meetingAttendees delete fail");
                    return -1;
                }
            }
            // 删除 meeting
            boolean removeMeeting = meetingService.remove(new QueryWrapper<Meeting>()
                    .eq("id", meetingId)
                    .eq("status", "待发起")
                    .eq("host_id", userId));
            if (removeMeeting == false) {
                System.out.println("removeMeeting 失败");
                return -1;
            }
            // 删除 room
            MeetingRoom currentMeetingRoom = meetingRoomService.getById(currentMeeting.getRoomId());
            if (currentMeetingRoom != null) {
                boolean b = meetingRoomService.removeById(currentMeeting.getRoomId());
                if (b == false) {
                    System.out.println("meetingRoom delete fail");
                    return -1;
                }
            }

/////////////////////////////////////////////////

        } else if ("已开".equals(status)) {
            Meeting currentMeeting = meetingService.getById(meetingId);
            MeetingAttendees one = meetingAttendeesService
                    .getOne(new QueryWrapper<MeetingAttendees>().eq("user_id", userId)
                            .eq("meeting_id", meetingId));
            if (one != null) {
                boolean b = meetingAttendeesService.removeById(one.getId());
                if (!b) {
                    System.out.println("remove attendees fail");
                    return -1;
                }
            }
        } else {
            System.out.println("参数错误");
            return -1;
        }

        return 1;
    }
}