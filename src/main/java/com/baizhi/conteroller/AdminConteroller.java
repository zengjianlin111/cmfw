package com.baizhi.conteroller;

import com.baizhi.entity.Admin;
import com.baizhi.services.AdminServices;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller   //声明该类是一个coteroll
@RequestMapping("/admin")
public class AdminConteroller {

    //注入services
    @Resource
    private AdminServices adminServices;

    //管理员登录
    @ResponseBody   //将结果返回一个json串
    @RequestMapping("/login")
    //第一个参数是请求用于获取session中的验证码的获取跳转对象
    // 后面的管理员对象用于接收参数 ,vode用于接收用于输入的验证码
    public String login(HttpServletRequest request, Admin admin, String vode) {
        //获取session
        HttpSession session = request.getSession();
        //获取session中的验证码
        String vcode = (String) session.getAttribute("vcode");
        //判断验证码是否一致
        if (vcode.equals(vode)) {
            //验证码一致登录判断
            Admin login = adminServices.login(admin);
            //判断是否登录成功
            if (login != null) {
                //用户登录成功，将当前登录成功的用于存入session中
                session.setAttribute("user", login);
                return "登录成功";
            }
            return "密码或账户输入有误";
        }
        return "验证码输入有误";
    }


    //使用shrio的登录功能
    //登录功能
    @ResponseBody   //将结果返回一个json串
    @RequestMapping("/shirologin")
    public String shirologin(HttpServletRequest request, Admin admin, String vode) {
        //获取session
        HttpSession session = request.getSession();
        //获取session中的验证码
        String vcode = (String) session.getAttribute("vcode");
        //判断验证码是否正确
        if (vcode.equals(vode)) {
            //获取一个主题对象直接使用shrio提供的工具类
            Subject subject = SecurityUtils.getSubject();
            //创建一个令牌对象
            UsernamePasswordToken token =
                    new UsernamePasswordToken(admin.getUsername(), admin.getPassword());
            //处理结果
            try {
                //登录测试 将令牌对象传入
                subject.login(token);
                return "登录成功";
            } catch (UnknownAccountException e) {
                System.out.println("------用户名错误-------");
                return "密码或账户输入有误";
            } catch (IncorrectCredentialsException e) {
                System.out.println("------密码错误------");
                return "密码或账户输入有误";
            }
        }
        return "验证码输入有误";
    }

    //退出登录
    @RequestMapping("loginout")
    public void loginout() {
        Subject subject = SecurityUtils.getSubject();
        //退出
        subject.logout();
    }
}
