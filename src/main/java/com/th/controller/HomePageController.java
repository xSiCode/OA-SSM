package com.th.controller;

import com.th.service.HomePageService;
import com.th.utils.ResponseData;
import org.jboss.logging.Cause;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Class : HomePageController
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/28 15:32
 * @Version : 1.0
 */
@RestController
@RequestMapping("/homePage")
public class HomePageController {

    @Autowired
    private HomePageService homePageService;

    @PostMapping("view")
    public ResponseData view(@RequestBody Map<String, Object> map) {
        Integer userId = (Integer) map.get("userId");
        try {
            //作用是 查询与当前用户相关的待办事项，待参加会议，待执行计划和收到的通知展示到主页中
            List<Object> getView = homePageService.listView(userId);
            if (getView != null) {
                return ResponseData.SUCCESS().extendData("view", getView);
            } else {
                return ResponseData.FAIL();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }

    }
    @PostMapping("timeViewDot")
    public ResponseData timeViewDot(@RequestBody Map<String, Object> map) {
        try {
            //将前端传入的map数据传入service层中解析和处理
            List<Object> getView = homePageService.listTimeViewDot(map);
            if (getView != null) {
                return ResponseData.SUCCESS().extendData("view", getView);
            } else {
                return ResponseData.FAIL();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }

    }


}
