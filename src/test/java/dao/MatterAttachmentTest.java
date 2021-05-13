package dao;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.th.dao.MatterAttachmentMapper;
import com.th.entity.Matter;
import com.th.entity.MatterAttachment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
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
public class MatterAttachmentTest {
    @Autowired
    MatterAttachment matterAttachment;

    @Autowired
    MatterAttachmentMapper matterAttachmentMapper;

    @Autowired
    Matter matter;

    @Test
    public void selectAttachmentByMatterId(){
        MatterAttachment attachment = matterAttachmentMapper.selectAttachmentByMatterId(1);
        System.out.println(attachment);
    }
    @Test
    public void selectAttachmentsByMaterId(){
        List<MatterAttachment> matterAttachments = matterAttachmentMapper.selectAttachmentsByMatterId(4);
        System.out.println(matterAttachments);
    }
    @Test
    public void selectAttachmentsByMaterIds(){
        List<Integer> ids =new ArrayList<>();
        ids.add(1);
        ids.add(4);
        ids.add(9);
        List<MatterAttachment> matterAttachments = matterAttachmentMapper.selectAttachmentsByMaterIds(ids);
        System.out.println(matterAttachments);
    }
}
