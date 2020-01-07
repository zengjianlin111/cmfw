package com.baizhi.shiro;

import com.baizhi.entity.Admin;
import com.baizhi.services.AdminServices;
import com.baizhi.util.AppLoctionTextUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;

public class MyRealm extends AuthenticatingRealm {

    //该方法用于获取从数据库查询数据并将数据返回交由shiro管理
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //由于该类是由shiro管理的所以不能直接注入services只能通过工具类从容器中获取
        //获取services
        AdminServices adminServices =
                (AdminServices) AppLoctionTextUtil.getBeanClass(AdminServices.class);
        //获取用户名
        String principal = (String) authenticationToken.getPrincipal();
        //调用services查询数据
        Admin admin = adminServices.selectAdminName(principal);
        //声明一个AuthenticationInfo对象用户接收数据
        AuthenticationInfo authenticationInfo = null;
        //判断查询的对象是否为null
        if (admin != null) {
            //用户存在 向AuthenticationInfo存入数据 初始化对象 第一个参数为用户名
            //第二个参数为密码 第三个参数为当前类名
            authenticationInfo =
                    new SimpleAuthenticationInfo(admin.getUsername(), admin.getPassword(), this.getName());

        }
        //将结果返回
        return authenticationInfo;
    }
}
