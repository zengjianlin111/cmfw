package com.baizhi.services;

import com.baizhi.entity.Admin;

public interface AdminServices {
    //管理员登录
    public Admin login(Admin admin);

    //通过用户名查询管理员
    public Admin selectAdminName(String name);
}
