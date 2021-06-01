package com.th.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.th.service.ToolService;
import com.th.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Class : ToolController
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/28 15:34
 * @Version : 1.0
 */
@RestController
@RequestMapping("/tool")
public class ToolController {
    @Autowired
    private ToolService toolService;


    /* - - - - - - - - - - - - -- -    search  - - - - - - - - - - - - - - -  - - */
    @PostMapping("search")
    public ResponseData search(@RequestBody Map<String, Object> map) {
        PageHelper.startPage(  (Integer) map.get("needPage"),10  );
        try {
            List<Map<String, Object>> result = toolService.searchByTitle( (Integer) map.get("userId"),  (String) map.get("key"));
            if (result != null) {
                PageInfo page = new PageInfo( result,10  );
                return ResponseData.SUCCESS().extendData("result", page);
            } else {
                return ResponseData.FAIL();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
    }


    /* - - - - - - - - - - - - -- -    contact  - - - - - - - - - - - - - - -  - - */
    @PostMapping("contact")
    public ResponseData contact(@RequestBody Map<String, Integer> map) {
        PageHelper.startPage(  map.get("needPage"),8  );
        try {
            List<Map<String, Object>> getContacts = toolService.listContactByOrgId(map.get("orgId"));
            if (getContacts != null) {
                PageInfo page = new PageInfo( getContacts,8  );
                return ResponseData.SUCCESS().extendData("contact", page);
            } else {
                return ResponseData.FAIL();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }

    }


}
