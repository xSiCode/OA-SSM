package dao;

import com.th.dao.NoticeReceiverMapper;
import com.th.entity.NoticeReceiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Class : NoticeTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/22 10:04
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class NoticeTest {
    @Autowired
    private NoticeReceiverMapper noticeReceiverMapper;

    @Test
    public void selectNoticeReceiverByNoticeId(){
        List<NoticeReceiver> noticeReceivers = noticeReceiverMapper.selectNoticeReceiverByNoticeId(1);
        System.out.println(noticeReceivers);

    }

}
