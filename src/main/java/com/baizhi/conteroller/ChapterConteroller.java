package com.baizhi.conteroller;

import com.baizhi.entity.Chapter;
import com.baizhi.services.ChapterServices;
import com.baizhi.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller  //声明该类是一个controlle
@RequestMapping("/chapter")
public class ChapterConteroller {
    //注入serivcel
    @Resource
    private ChapterServices articleServices;

    //分页查询
    @ResponseBody
    @RequestMapping("/selectPage")
    public Map<String, Object> selectPage(Integer page, Integer rows, String albmid) {
        return articleServices.selectChapterAll(page, rows, albmid);
    }


    //添加 删除 修改的方法 第一个参数用于接收值 第二个参数用于判断状态 第三个参数批量删除时的id
    @ResponseBody
    @RequestMapping("/addupdatedel")
    public Map<String, Object> addupdatedel(Chapter article, String oper, String[] id, String albmid, HttpServletRequest request) {
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
            //设置专辑id
            article.setAlbum_id(albmid);
            //调用添加业务
            String inset = articleServices.inset(article);
            //将id设置到map集合中
            map.put("chapterId", inset);
            //将集合返回
            return map;
        }
        //判断是否是修改
        if ("edit".equals(oper)) {
            //调用修改方法
            articleServices.update(article);
            System.out.println("-----------:" + article.getSrc());
            //判断修改是否上传文件
            if (!"".equals(article.getSrc())) {
                //将id存入map集合
                map.put("chapterId", article.getId());
                return map;
            }
            return map;
        }
        //判断是否删除
        if ("del".equals(oper)) {

            //遍历集合
            for (String s : id) {
                //通过id查询
                Chapter chapter = articleServices.selectId(s);
                //获取绝对路径(存放的目录) 当前的mp3目录
                String realPath = request.getSession().getServletContext().getRealPath("/mp3");
                //创建文件对象 文件名字在Chapter对象中
                File file = new File(realPath + "/" + chapter.getSrc());
                //删除文件
                file.delete();
            }
            //d调用批量删除的方法
            articleServices.delete(id);
        }

        return null;
    }

    //章节上传
    @RequestMapping("/articUpload")
    public void articUpload(MultipartFile src, String chapterId, HttpServletRequest request) {
        //获取文件名字和后缀
        String originalFilename = src.getOriginalFilename();
        //获取绝对路径(存放的目录) 当前的mp3目录
        String realPath = request.getSession().getServletContext().getRealPath("/mp3");
        //文件时长
        String duration = null;
        //文件大小
        String length = null;
        //复制文件
        try {
            originalFilename = new Date().getTime() + "_" + originalFilename;
            File file = new File(realPath + "/" + originalFilename);
            //拷贝文件
            src.transferTo(file);
            //获取文件大小
            length = FileUtil.readableFileSize(file.length());

            //调用工具方法获取音频时间
            duration = FileUtil.getDuration(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //修改文件的真实名字
        //创建一个章节对象
        Chapter article = new Chapter();
        //设置章节的时间
        article.setDuration(duration);
        //设置章节文件大小
        article.setSize(length);

        //设置章节对象的id
        article.setId(chapterId);
        //设置章节对象的名字
        article.setSrc(originalFilename);
        //调用修改方法
        articleServices.update(article);
    }


    //文件下载
    @RequestMapping("/fileonload")
    public void fileonload(String filename, HttpServletRequest request, HttpServletResponse response) {
        //获取下载目录的路径
        String s = request.getSession().getServletContext().getRealPath("/mp3");
        //文件读取
        FileInputStream fileInputStream = null;
        //文件响应
        ServletOutputStream outputStream = null;

        System.out.println("----文件名----:" + filename);
        //切割文件名
        filename = filename.split("mp3/")[1];

        //使用文件流
        try {
            fileInputStream =
                    new FileInputStream(new File(s, filename));
            //等价于path+"/"+filename

            //设置响应类型
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/force-download");

            //设置下载的文件名以_切割
            String loadFileName = filename.split("_")[1];
            //设置响应头 以附件形式
            response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(loadFileName, "UTF-8"));

            //通过响应流向客户端打印数据
            outputStream = response.getOutputStream();

            //定义一个字节数组
            byte[] bytes = new byte[2048];
            //向客户端写文件
            while (true) {
                //返回的是读取的字节数据返回-1表示文件读到末尾
                int read = fileInputStream.read(bytes, 0, bytes.length);
                //判断是否读到默认
                if (read <= -1)
                    break;
                //向外响应
                outputStream.write(bytes, 0, read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                fileInputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
