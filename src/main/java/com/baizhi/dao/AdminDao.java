package com.baizhi.dao;

import com.baizhi.entity.Admin;

public interface AdminDao {
    //登录
    public Admin login(Admin admin);

    //通过用户名查询用户
    public Admin selectAdminName(String name);
}
