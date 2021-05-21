package dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.th.dao.LeaveMapper;
import com.th.entity.Leave;
import com.th.entity.Plan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Class : LeaveTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/21 18:00
 * @Version : 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class LeaveTest {
    @Autowired
    private LeaveMapper leaveMapper;

    @Test
    public  void selectPlanOne(){
        List<Integer> integers = leaveMapper.listApplicantIds();
        System.out.println(integers);


        List<Leave> leaves = leaveMapper.selectList(new QueryWrapper<Leave>().select("id,applicant_id").eq("status", "待审核"));
        System.out.println(leaves);
    }

}
