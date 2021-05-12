package com.th.controller;


import com.th.entity.Matter;
import com.th.service.MatterService;
import com.th.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-12
 */
@RestController
@RequestMapping("/matter")
public class MatterController {

    @Autowired
    Matter matter;

    @Autowired
    MatterService matterService;

    //查看事项详情的时候，会用
    @PostMapping("/getMatter_matterId")
    public ResponseData getMatter_matterId(@RequestBody Integer matterId){

        Matter currentMatter= matterService.getMatter_matterId(matterId);

        return ResponseData.SUCCESS().extendData("matter",1);
    }


}

