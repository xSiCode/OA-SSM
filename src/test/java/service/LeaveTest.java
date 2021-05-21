package service;

import com.th.dao.LeaveMapper;
import com.th.entity.Leave;
import com.th.service.LeaveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Class : LeaveTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/21 11:35
 * @Version : 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class LeaveTest {
    @Autowired
    private Leave leave;
    private LeaveService leaveService;
    private LeaveMapper leaveMapper;

    @Test
    public void getLeaveById(){
        Leave leaveById = leaveService.getLeaveById(1);
        System.out.println(leaveById);
    }

}
