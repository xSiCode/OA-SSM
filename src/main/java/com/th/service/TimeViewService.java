package com.th.service;

import com.th.entity.TimeView;

import java.util.List;
import java.util.Map;

/**
 * @Interface : TimeViewService
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/27 11:07
 * @Version : 1.0
 */
public interface TimeViewService {


    List<TimeView> listTimeView(Map<String, Object> map);
}
