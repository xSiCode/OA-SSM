package dao;

import com.th.dao.MeetingAttendeesMapper;
import com.th.dao.MeetingMapper;
import com.th.entity.Meeting;
import com.th.entity.MeetingAttendees;
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
 * @Date : 2021/5/18 20:12
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class MeetingTest {

    @Autowired
    private Meeting meeting;
    @Autowired
    private MeetingMapper meetingMapper;
    @Autowired
    private MeetingAttendees meetingAttendees;
    @Autowired
    private MeetingAttendeesMapper meetingAttendeesMapper;

    @Test
    public  void selectMeetingList(){
        List<Meeting> meetings = meetingMapper.selectMeetingList();
        System.out.println(meetings);

    }
    @Test
    public  void selectAttendeesList(){
        List<MeetingAttendees> meetings = meetingAttendeesMapper.selectAttendeesList();
        System.out.println(meetings);

    }
}
