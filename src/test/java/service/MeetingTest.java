package service;

import antlr.debug.MessageAdapter;
import com.th.entity.Meeting;
import com.th.entity.MeetingAttendees;
import com.th.entity.MeetingRoom;
import com.th.service.MeetingAttendeesService;
import com.th.service.MeetingRoomService;
import com.th.service.MeetingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Class : MeetingTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/18 19:22
 * @Version : 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class MeetingTest {
    @Autowired
    Meeting meeting;

    @Autowired
    MeetingService meetingService;

    @Autowired
    MeetingAttendees meetingAttendees;

    @Autowired
    MeetingAttendeesService meetingAttendeesService;

    @Autowired
    MeetingRoom meetingRoom;

    @Autowired
    MeetingRoomService meetingRoomService;


    @Test
    public void meetingList(){
        List<Meeting> list = meetingService.list();
        System.out.println(list);
    }

    @Test
    public void meetingAttendeesList(){
        List<MeetingAttendees> list = meetingAttendeesService.list();
        System.out.println(list);
    }

    @Test
    public void meetingRoomList(){
        List<MeetingRoom> list = meetingRoomService.list();
        System.out.println(list);
    }
}
