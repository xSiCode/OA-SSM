package com.th.controller;

import com.th.bean.Organization;
import com.th.service.OrganizationService;
import com.th.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Class : OrganizationController
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/25 13:11
 * @Version : 1.0
 */
@Controller
public class OrganizationController {
    @Autowired
    Organization organization;
    @Autowired
    OrganizationService organizationService;

    //@RequestBody Map<String,Object> userMap
    @PostMapping("/organization/getOrganizationListTree")
    @ResponseBody
    public ResponseData getOrganizationListTree() {
        List<Organization> organizations = null; organizationService.listWithTree();

        try {
            organizations = organizationService.listWithTree();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
            return ResponseData.SUCCESS().extendData("organizationsJson", organizations);

    }
}
