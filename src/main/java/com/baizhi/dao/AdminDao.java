package com.baizhi.dao;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Permissio;
import com.baizhi.entity.Role;

import java.util.List;

public interface AdminDao {
    //登录
    public Admin login(Admin admin);

    //通过用户名查询用户
    public Admin selectAdminName(String name);

    //通过管理的id查询角色
    public List<Role> selectRole(String id);

    //通过角色的id查询用户的权限
    public List<Permissio> selectRoleId(String id);
}
