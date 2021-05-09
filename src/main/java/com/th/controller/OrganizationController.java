package com.th.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.th.entity.Organization;
import com.th.entity.User;
import com.th.service.OrganizationService;
import com.th.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-07
 */
@RestController()
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    OrganizationService organizationService;


    @PostMapping("/getTree")
    public ResponseData getOrganizationTree() {
        List<Organization> organizations = organizationService.listWithTree();
        return ResponseData.SUCCESS().extendData("organizationTree", organizations);
    }
}

