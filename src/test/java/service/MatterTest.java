package service;

import com.th.entity.Matter;
import com.th.service.MatterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * @Class : MatterTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/13 10:38
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class MatterTest {
    @Autowired
    Matter matter;

    @Autowired
    MatterService matterService;

    @Test
    public void getMatterByMatterId(){
        Matter currentMatter = matterService.getMatterByMatterId(4);
        System.out.println(currentMatter);
    }

    @Test
    public void getMatterHandlerBriefByUser(){
        List<Map<String, Object>> handler = matterService.getMatterHandlerBriefByUser(1011, "待办");
        System.out.println(JSON.toString(handler));
    }

    @Test
    public void getMatterCreatorBriefByUser(){
        List<Map<String, Object>> handler = matterService.getMatterCreatorBriefByUser(1004, "draft");
        System.out.println(JSON.toString(handler));
        System.out.println("=========================");
        List<Map<String, Object>> handler2 = matterService.getMatterCreatorBriefByUser(1016, "submit");
        System.out.println(JSON.toString(handler2));
    }


}
