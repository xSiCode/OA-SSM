package dao;

import com.th.dao.PlanMapper;
import com.th.entity.Meeting;
import com.th.entity.Plan;
import com.th.service.PlanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Class : PlanTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/20 22:18
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class PlanTest {

    @Autowired
    private Plan plan;

    @Autowired
    private PlanService planService;

    @Autowired
    private PlanMapper planMapper;

    @Test
    public  void selectListAll(){
        List<Plan> meetings = planMapper.selectListAll();
        System.out.println(meetings);
        System.out.println(JSON.toString(meetings));

    }
    @Test
    public  void selectPlanOne(){
        Plan plan= planMapper.selectPlanOne(1);
        System.out.println(plan);
        System.out.println(JSON.toString(plan));

    }


}
