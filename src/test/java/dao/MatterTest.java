package dao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.th.dao.MatterMapper;
import com.th.entity.Matter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.Map;

/**
 * @Class : MatterTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/13 01:18
 * @Version : 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class MatterTest {
    @Autowired
    private Matter matter;

    @Autowired
    private MatterMapper matterMapper;

    @Test
    public void selectMatterByMatterId(){
        Matter matter = matterMapper.selectMatterByMatterId(1);
        System.out.println(matter);
    }

    @Test
    public void selectMatterEntityByMatterId(){
        Matter matter = matterMapper.selectMatterEntityByMatterId(1);
        System.out.println(matter);
    }

    @Test
    public void selectMatterHandlerBriefByUser(){
        List<Map<String, Object>> currentUser_matter = matterMapper.selectMatterHandlerBriefByUser(1223, "已办");
        System.out.println(currentUser_matter);
        System.out.println(JSON.toString(currentUser_matter));
    }
    @Test
    public void selectMatterCreatorDraftBriefByUser(){
        List<Map<String, Object>> currentUser_matter = matterMapper.selectMatterCreatorDraftBriefByUser(1031);
        //发现，mybatis 会根据select字段非空才输出字段，
        System.out.println(currentUser_matter);
        System.out.println(JSON.toString(currentUser_matter));
    }
    @Test
    public void selectMatterCreatorSubmitBriefByUser(){
        List<Map<String,Object>>  currentUser_matter = matterMapper.selectMatterCreatorSubmitBriefByUser(1019);
        //发现，mybatis 会根据select字段非空才输出字段，
        System.out.println(currentUser_matter);
        System.out.println("--------------------------------------");
        System.out.println(JSON.toString(currentUser_matter));
    }
}
