package com.baizhi.conteroller;

import com.baizhi.entity.Admin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  //声明该类为一个Controller
@RequestMapping("/shiro") //对外访问包名
public class ShiroConteroller {

    //登录功能
    @RequestMapping("/login")
    public String login(Admin admin) {
        //获取一个主题对象直接使用shrio提供的工具类
        Subject subject = SecurityUtils.getSubject();
        //创建一个令牌对象
        UsernamePasswordToken token =
                new UsernamePasswordToken(admin.getUsername(), admin.getPassword());
        //处理结果
        try {
            //登录测试 将令牌对象传入
            subject.login(token);
            return "shiro/main";
        } catch (UnknownAccountException e) {
            System.out.println("------用户名错误-------");
            return "shiro/login";
        } catch (IncorrectCredentialsException e) {
            System.out.println("------密码错误------");
            return "shiro/login";
        }
    }
}
