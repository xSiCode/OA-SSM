package dao;

import com.th.dao.MatterContentConfigMapper;
import com.th.dao.MatterHandlerMapper;
import com.th.entity.Matter;
import com.th.entity.MatterContentConfig;
import com.th.entity.MatterHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Class : MatterAttachmentTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/12 23:51
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class MatterHandlerTest {
    @Autowired
    MatterHandler matterHandler;

    @Autowired
    MatterHandlerMapper matterHandlerMapper;

    @Autowired
    Matter matter;

    @Test
    public void selectHandlerByMatterId(){
        MatterHandler matterHandler = matterHandlerMapper.selectHandlerByMatterId(10);
        System.out.println(matterHandler);
    }

    @Test
    public void selectHandlersByMatterId(){
        List<MatterHandler> matterHandlers = matterHandlerMapper.selectHandlersByMatterId(4);
        System.out.println(matterHandlers);
    }
    @Test
    public void selectHandlersEntityByMatterId(){
        List<MatterHandler> matterHandlers = matterHandlerMapper.selectHandlersEntityByMatterId(4);
        System.out.println(matterHandlers);
    }
    @Test
    public void getTodoMatterIdsByUserId(){
        List<Integer> todoMatterIdsByUserId = matterHandlerMapper.getMatterIdsByUserId(1223);
        System.out.println(todoMatterIdsByUserId);
    }



}
