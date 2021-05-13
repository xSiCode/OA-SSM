package dao;

import com.th.dao.MatterAttachmentMapper;
import com.th.dao.MatterContentConfigMapper;
import com.th.entity.Matter;
import com.th.entity.MatterAttachment;
import com.th.entity.MatterContentConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Class : MatterAttachmentTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/12 23:51
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class MatterContentConfigTest {
    @Autowired
    MatterContentConfig matterContentConfig;

    @Autowired
    MatterContentConfigMapper matterContentConfigMapper;

    @Autowired
    Matter matter;

    @Test
    public void selectAttachmentByMatterId(){
        MatterContentConfig matterContentConfig = matterContentConfigMapper.selectContentConfigByConfigId(1);
        System.out.println(matterContentConfig);
    }

}
