package service;

import com.th.entity.Meeting;
import com.th.entity.Notice;
import com.th.service.NoticeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * @Class : NoticeTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/22 09:54
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class NoticeTest {

    @Autowired
    private NoticeService noticeService;


    @Test
    public void selectCreatNoticeDetailById(){
        Notice creatNoticeDetailById = noticeService.getCreatNoticeDetailById(1);
        System.out.println(creatNoticeDetailById);
    }
    @Test
    public void listNoticeByReceiver(){
        List<Map<String, Object>> maps = noticeService.listNoticeByReceiver(1004);
        System.out.println(maps);
    }
}
