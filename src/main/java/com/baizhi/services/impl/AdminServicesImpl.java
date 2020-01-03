package com.baizhi.services.impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.services.AdminServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service        //该类是一个服务层的类
@Transactional   //事务交由spring控制
public class AdminServicesImpl implements AdminServices {
    //注入dao
    @Resource
    private AdminDao adminDao;

    //登录
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)  //登录属于查询不需要事务控制
    public Admin login(Admin admin) {
        return adminDao.login(admin);
    }
}
