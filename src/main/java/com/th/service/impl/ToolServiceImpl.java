package com.th.service.impl;

import com.th.dao.SearchMapper;
import com.th.dao.TimeViewMapper;
import com.th.dao.UserMapper;
import com.th.entity.TimeView;
import com.th.service.OrganizationService;
import com.th.service.TimeViewService;
import com.th.service.ToolService;
import com.th.utils.DataTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.rtf.RTFEditorKit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Class : TimeViewwServiceImpl
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/27 11:07
 * @Version : 1.0
 */
@Service
public class ToolServiceImpl implements ToolService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private SearchMapper searchMapper;

    @Override
    public List<Map<String, Object>> listContactByOrgId( Integer orgId) {

        //在用户表中 查找  姓名和电话，再添加对应的职位。
        List<Map<String,Object>> getContact = userMapper.selectUserContactByOrgId(orgId);
        if(getContact !=null){
            //添加对应的职位、这里都是同一个。
            String orgName = organizationService.listParentPathsWithStringById(orgId);
            for(Map<String,Object> current : getContact){
                current.put("orgName",orgName);
            }
            return getContact;
        }else
        return null;
    }

    @Override
    public List<Map<String, Object>> searchByTitle( Integer userId, String key) {
        // 在事项中  、 在计划中， 在会议中，在公告中。搜标题， 返回字段，ID，title，类别   . 根据用户id
        List<Map<String, Object>> resultByTitle = searchMapper.getResultByTitle(userId, key);
        if(resultByTitle !=null){
            return resultByTitle;
        }else
            return null;
    }
}
