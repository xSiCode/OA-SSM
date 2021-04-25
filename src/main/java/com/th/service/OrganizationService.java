package com.th.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.th.bean.Organization;

import java.util.List;

/**
 * @Interface : Organization
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/24 22:49
 * @Version : 1.0
 */
public interface OrganizationService extends IService<Organization> {

     List<Organization> listWithTree();

     List<Integer> listIdPaths(Integer currentId);

}
