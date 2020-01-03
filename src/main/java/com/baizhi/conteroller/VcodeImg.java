package com.baizhi.conteroller;

import com.baizhi.util.ValidateImageCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;


@Controller         //声明该类是一个Controller
@RequestMapping("/vcodeimg")   //对外访问包名
public class VcodeImg {
    //发送验证码图片(需要一个响应对象用于获取响应流将图片通过流发送出去) 需要一个请求用于获取session
    @RequestMapping("/img") //对外方法访问路径
    public void sendImg(HttpServletResponse response, HttpServletRequest request) {
        //获取随机验证码
        String securityCode = ValidateImageCodeUtils.getSecurityCode();
        //提升作用域
        ServletOutputStream outputStream = null;
        try {
            //将验证码生成图片
            BufferedImage image = ValidateImageCodeUtils.createImage(securityCode);
            outputStream = response.getOutputStream(); //获取响应流
            //通过图片打印流将图片打印到网页中
            //第一个参数是生成的验证码的图片 第二个参数是生成的文件格式
            //第三个参数的打印的目的地
            ImageIO.write(image, "png", outputStream);

            //获取session
            HttpSession session = request.getSession();


            //将验证码存入session中
            session.setAttribute("vcode", securityCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
