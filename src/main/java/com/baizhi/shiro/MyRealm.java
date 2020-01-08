package com.baizhi.shiro;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Permissio;
import com.baizhi.entity.Role;
import com.baizhi.util.AppLoctionTextUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.List;

public class MyRealm extends AuthorizingRealm {
    //该方法用于授权 注意该方法只有当页面使用了shrio标签时才会生效触发
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //先获取主身份(身份可以有多个 但是主身份可以有多个)
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        //授权 获取授权对象(该对象也可以授权等)
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //通过工具类获取dao对象
        AdminDao adminDao = (AdminDao) AppLoctionTextUtil.getBeanClass(AdminDao.class);
        //调用dao将用户名传入查询到用户信息
        Admin admin = adminDao.selectAdminName(primaryPrincipal);
        //通过用户id查询用户的身份信息
        List<Role> roles = adminDao.selectRole(admin.getId());

        //防止权限重复创建一个set集合用于过滤重复权限(因为一个人可以有多个角色 权限可能重复)
        HashSet<String> set = new HashSet<>();
        //遍历集合将角色信息设置到对象中  并且通过角色去查询权限
        for (Role role : roles) {
            //将角色添加到SimpleAuthorizationInfo对象中
            info.addRole(role.getName());
            //调用业务通过角色的id查询权限
            List<Permissio> permissios = adminDao.selectRoleId(role.getId());
            //遍历权限集合
            for (Permissio permissio : permissios) {
                //将权限集合添加到set集合中
                set.add(permissio.getPerms());
            }
        }
        //将set集合添加info对象中(权限)
        info.addStringPermissions(set);
        return info;
    }

    //该方法用于认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //通过工具类获取dao对象
        AdminDao adminDao = (AdminDao) AppLoctionTextUtil.getBeanClass(AdminDao.class);
        //获取用户名
        String principal = (String) authenticationToken.getPrincipal();
        //调用查询的方法
        Admin admin = adminDao.selectAdminName(principal);
        //创建一个AuthenticationInfo对象
        SimpleAuthenticationInfo simp = null;
        //判断是否存在数据
        if (admin != null) {
            //构造SimpleAuthenticationInfo对象
            simp = new SimpleAuthenticationInfo(admin.getUsername(), admin.getPassword(), this.getName());
        }
        return simp;
    }
}
