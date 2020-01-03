package com.baizhi.conteroller;

import com.baizhi.entity.User;
import com.baizhi.services.AlbumServices;
import com.baizhi.services.ArticleServices;
import com.baizhi.services.BannerServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cmfz")
public class CmfzConteroller {

    //注入轮播图的services
    @Resource
    private BannerServices bannerServices;
    //注入专辑
    @Resource
    private AlbumServices albumServices;
    //注入文章
    @Resource
    private ArticleServices articleServices;

    //接口文档值分页
    @ResponseBody
    @RequestMapping("/first_page")
    //第一个参数用户id 第二个参数是用户池操作  第三个是操作为思是的操作
    public Map<String, Object> firstpage(String uid, String type, String sub_type, HttpServletRequest request) {
        //创建一个map集合
        Map<String, Object> map = new HashMap<>();

        //通过request获取请求头(http)
        String http = request.getScheme();
        //获取本机地址(需要以/切割取下标为1的值 因为取出的原值PC-20190718ZLAM/192.168.1.156)
        String address = null;
        try {
            address = InetAddress.getLocalHost().toString().split("/")[1];
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //获取端口
        int port = request.getServerPort();
        //获取项目名 自带/
        String contextname = request.getContextPath();
        //拼接url
        String url = http + "://" + address + ":" + port + contextname + "/img/";

        //判断操作
        if ("all".equals(type)) //判断操作
        {

            //调用查询轮播图的方法
            List<Map<String, String>> selectpage = bannerServices.selectpage(url);


            //调用查询专辑
            List<Map<String, Object>> alubms = albumServices.selectAlubm(url);

            //文章查询
            List<Map<String, Object>> articles = articleServices.selectAll();
            //向集合中添加数据
            map.put("header", selectpage);//轮播图
            map.put("album", alubms);//专辑
            map.put("artical", articles);//文章

        }
        //是否查询所有专辑
        else if ("wen".equals(type)) {
            //查询所有的专辑
            List<Map<String, Object>> list = albumServices.selectAlubm(url);
            //将集合存入map中
            map.put("body", list);
        } else if ("si".equals(type)) {
            //获取session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            user = new User();
            user.setGuru_id("1");
            //判断是否查询自身上师的文章还是查询所有文章
            if ("ssyj".equals(sub_type) && user != null) {
                //查询用户上师的文章
                //获取到当前登录用户的对象

                //通过用户的上师id查询文章
                List<Map<String, Object>> list = articleServices.selectShangId(user.getGuru_id());
                //将数据添加到集合中
                map.put("body", list);
            } else {
                //查询所有文章文章
                List<Map<String, Object>> list = articleServices.selectAll();
                //将集合存入map集合
                map.put("body", list);
            }

        } else {
            //数据错误
            map = null;
        }
        return map;
    }

    //通过专辑id查询专辑的信息
    @RequestMapping("/wen")
    @ResponseBody
    public Map<String, Object> wen(String id, String uid, HttpServletRequest request) {
        //通过request获取请求头(http)
        String http = request.getScheme();
        //获取本机地址(需要以/切割取下标为1的值 因为取出的原值PC-20190718ZLAM/192.168.1.156)
        String address = null;
        try {
            address = InetAddress.getLocalHost().toString().split("/")[1];
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //获取端口
        int port = request.getServerPort();
        //获取项目名 自带/
        String contextname = request.getContextPath();
        //拼接url
        String url = http + "://" + address + ":" + port + contextname;
        //通过专辑id查询专辑
        Map<String, Object> map = albumServices.selectAlbumParticulars(uid, url);
        return map;
    }
}
