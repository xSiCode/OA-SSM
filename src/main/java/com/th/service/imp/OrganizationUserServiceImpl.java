package com.th.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.th.bean.Organization;
import com.th.bean.OrganizationUser;
import com.th.dao.OrganizationMapper;
import com.th.dao.OrganizationUserMapper;
import com.th.service.OrganizationService;
import com.th.service.OrganizationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Class : OrganizationImpl
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/24 22:49
 * @Version : 1.0
 */

@Service
public class OrganizationUserServiceImpl extends ServiceImpl<OrganizationUserMapper, OrganizationUser> implements OrganizationUserService {


}
