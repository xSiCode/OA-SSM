package com.th.controller;

import com.th.entity.TimeView;
import com.th.service.TimeViewService;
import com.th.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Class : TimeView
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/27 10:59
 * @Version : 1.0
 */
@RestController
@RequestMapping("/timeView")
public class TimeViewController {
    @Autowired
    private TimeViewService timeViewService;

    @PostMapping("listTimeView")
    public ResponseData listTimeView(@RequestBody Map<String, Object> map) {
        try {
            List<TimeView> timeViews = timeViewService.listTimeView(map);
            if (timeViews != null) {
                return ResponseData.SUCCESS().extendData("timeViews", timeViews);
            } else {
                return ResponseData.FAIL();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
    }

}
