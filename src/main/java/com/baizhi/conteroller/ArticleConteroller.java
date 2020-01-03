package com.baizhi.conteroller;

import com.baizhi.entity.Article;
import com.baizhi.services.impl.ArticleServicesImpl;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller  //声明该类是一个controlle
@RequestMapping("/article")
public class ArticleConteroller {
    //注入serivcel
    @Resource
    private ArticleServicesImpl articleServices;

    //分页查询
    @ResponseBody
    @RequestMapping("/selectPage")
    public Map<String, Object> selectPage(Integer page, Integer rows) {
        return articleServices.selectArticeAll(page, rows);
    }


    //添加 删除 修改的方法 第一个参数用于接收值 第二个参数用于判断状态 第三个参数批量删除时的id
    @ResponseBody
    @RequestMapping("/addupdatedel")
    public Map<String, Object> addupdatedel(Article article, String oper, String[] id) {
        //创建一个map用于传值
        Map<String, Object> map = new HashMap<>();
        /*
            user:数据
            oper:状态
                add:添加
                del:删除
                edit:修改
        */
        //判断是否是添加
        if ("add".equals(oper)) {
            //调用添加业务
            articleServices.inset(article);
        }
        //判断是否是修改
        if ("edit".equals(oper)) {
            //调用修改方法
            articleServices.update(article);
        }
        //判断是否是删除
        if ("del".equals(oper)) {
            //调用删除方法
            articleServices.delete(id);
        }
        return null;
    }

    //添加文章
    @ResponseBody
    @RequestMapping("/suit")
    public void summit(Article article) {
        //调用添加业务
        articleServices.inset(article);
    }

    //章节上传
    @RequestMapping("/articUpload")
    public void articUpload(MultipartFile title, String artcleId, HttpServletRequest request) {
        //获取文件名字和后缀
        String originalFilename = title.getOriginalFilename();
        //获取绝对路径(存放的目录) 当前的mp3目录
        String realPath = request.getSession().getServletContext().getRealPath("/mp3");
        //复制文件
        try {
            title.transferTo(new File(realPath + "/" + originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //修改文件的真实名字
        //创建一个章节对象
        Article article = new Article();
        //设置章节对象的id
        article.setId(artcleId);
        //设置章节对象的名字
        article.setTitle(originalFilename);
        //调用修改方法
        articleServices.update(article);
    }


    //富文本图片上传
    @ResponseBody
    @RequestMapping("/richtext")
    public Map<String, Object> richtext(MultipartFile img, HttpServletRequest request) {

        //创建一个Map集合
        Map<String, Object> map = new HashMap<>();
        //获取文件名字和后缀
        String originalFilename = img.getOriginalFilename();
        //设置文件名字
        String filename = new Date().getTime() + "_" + originalFilename;
        //获取绝对路径(存放的目录) 当前的richtext目录
        String realPath = request.getSession().getServletContext().getRealPath("/richtext");
        //判断路径是否存入
        File file = new File(realPath);
        if (file.exists()) {
            file.mkdir();
        }
        //复制文件
        try {
            img.transferTo(new File(realPath + "/" + filename));
            /*
             *需要返回一个这个
             * {"error":0,"url":"\/ke4\/attached\/W020091124524510014093.jpg"}
             * */
            //向集合中添加数据
            map.put("error", 0);//没有错误
            //通过request获取请求头(http)
            String http = request.getScheme();
            //获取本机地址(需要以/切割取下标为1的值 因为取出的原值PC-20190718ZLAM/192.168.1.156)
            String address = InetAddress.getLocalHost().toString().split("/")[1];
            //获取端口
            int port = request.getServerPort();
            //获取项目名 自带/
            String contextname = request.getContextPath();
            //拼接url
            String url = http + "://" + address + ":" + port + contextname + "/richtext/" + filename;
            //将url添加到map中
            map.put("url", url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    //文章修改
    @ResponseBody
    @RequestMapping("/updatearticle")
    public void updatearticle(Article article) {
        System.out.println(article);
        System.out.println("=============================");
        //调用修改方法
        articleServices.update(article);

    }


    /*
   *  //外部是一个大的map集合 里面在套一个List该List里面套一个map
   * {
   "moveup_dir_path": "",
   "current_dir_path": "",
   "current_url": "/ke4/php/../attached/",
   "total_count": 5,
   "file_list": [
       {
           "is_dir": false,
           "has_file": false,
           "filesize": 208736,
           "dir_path": "",
           "is_photo": true,
           "filetype": "jpg",
           "filename": "1241601537255682809.jpg",
           "datetime": "2018-06-06 00:36:39"
       }
    ]
}
   * */
    //图片空间
    @ResponseBody
    @RequestMapping("/imagespace")
    public Map<String, Object> imagespace(HttpServletRequest request) {
        //创建一个map集合(外部的大map)
        Map<String, Object> map = new HashMap<>();
        //设置前几个默认的空参数
        map.put("moveup_dir_path", "");
        map.put("current_dir_path", "");


        //获取目录的路径拼接(因为有多个图片只需要到目录就行不需要文件名字)
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
        String url = http + "://" + address + ":" + port + contextname + "/richtext/";


        //向map中添加url
        map.put("current_url", url);

        //获取到当前存放图片空间的目录路径
        String path = request.getSession().getServletContext().getRealPath("richtext");
        //获取文件中的所有图片对象(创建目录对象)
        File file = new File(path);
        //将该目录的文件封装(文件名数组)
        String[] imgs = file.list();
        //创建一个List用于存放图片的信息
        List<Object> list = new ArrayList<>();
        //遍历集合
        for (String imgname : imgs) {
            //创建一个Map集合用于存储单个图片文件的信息
            Map<String, Object> map1 = new HashMap<>();
            //先添加不需要改动的数据
            map1.put("is_dir", false); //是否是目录
            map1.put("has_file", false);
            map1.put("dir_path", "");
            map1.put("is_photo", true);


            //将所有文件封装为file对象
            File img = new File(path, imgname);
            //设置文件大小
            map1.put("filesize", img.length());

            //获取文件的类型后缀使用FileNameUtils
            String suffix = FilenameUtils.getExtension(imgname);
            //将文件后缀名添加到map1中
            map1.put("filetype", suffix);

            //添加文件名字
            map1.put("filename", imgname);

            //切割文件名字获取上传的时间(毫秒数) 字符串
            String lo = imgname.split("_")[0];
            //将该毫秒数字符串转换为long
            Long aLong = Long.valueOf(lo);
            //在创建时间对象将该long值传入
            Date date = new Date(aLong);
            //创建时间转换对象 将格式传入
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            //转换时间
            String format1 = format.format(date);
            //将时间格式添加map1集合中
            map1.put("datetime", format1);
            //将map1添加到List集合中
            list.add(map1);
        }
        //将集合添加到大的map集合中
        map.put("file_list", list);
        //将集合返回
        return map;
    }
}
