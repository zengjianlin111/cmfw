package com.baizhi.conteroller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.entity.Banner;
import com.baizhi.services.BannerServices;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/banner") //对外访问包名
@Controller  //声明该类是一个controller
public class BannerConteroller {

    //注入services
    @Resource
    private BannerServices bannerServices;

    //分页查询
    @RequestMapping("/paging")
    @ResponseBody                       //第一个参数是当前页 后面的参数是每页显示的条数
    public Map<String, Object> bannerpaging(Integer page, Integer rows) {
        Map<String, Object> map = bannerServices.selectBannerAll(page, rows);
        //调用分页查询的业务
        return map;
    }

    //添加
    @ResponseBody
    @RequestMapping("/addbanner")
    public Map<String, Object> addbanner(Banner banner, String oper, String[] id, HttpServletRequest request) {
        //该map用于返回结果
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
            //添加轮播图
            //创建于一个banner对象
            //调用添加方法
            //调用添加方法 将数据添加到数据库
            //获取id
            String s = bannerServices.insetBanner(banner);
            //创建一个url

            //将id存入map中
            map.put("bannerId", s);
            //将集合返回
            return map;
        }
        //判断是否是修改
        if ("edit".equals(oper)) {
            //判断用户是否上传图片
            if (!"".equals(banner.getImg())) {
                //将id存入map中
                map.put("bannerId", banner.getId());
                //将集合返回
                return map;
            }
            //调用修改方法
            bannerServices.updateBanner(banner);

        }

        //判断是否删除
        if ("del".equals(oper)) {
            //遍历id
            for (String s : id) {
                //通过id查询
                Banner selectid = bannerServices.selectid(s);
                //获取ServletContext
                ServletContext servletContext = request.getSession().getServletContext();
                //获取文件放置的路径 ServletContext.getRealPath("相对路径") ==绝对路径;
                String realPath = servletContext.getRealPath("/img");
                //封装file对象
                File file = new File(realPath + "/" + selectid.getImg());
                //删除
                file.delete();
            }

            //调用业务
            bannerServices.delete(id);
        }
        return map;
    }

    //文件上传
    @RequestMapping("/fileload")
    public void fileload(MultipartFile img, String bannerId, HttpServletRequest request) {
        //文件处理 将文件放置到服务器的指定文夹中
        //获取当前上传文件的名字 名字和后缀
        String originalFilename = img.getOriginalFilename();
        //获取ServletContext
        ServletContext servletContext = request.getSession().getServletContext();
        //获取文件放置的路径 ServletContext.getRealPath("相对路径") ==绝对路径;
        String realPath = servletContext.getRealPath("/img");

        //使用MultipartFile对象将文件放到指定位置
        try {
            //修改文件名字
            originalFilename = new Date().getTime() + originalFilename;
            img.transferTo(new File(realPath + "/" + originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
            new RuntimeException("文件上传错误");
        }
        //创建一个banner对象
        Banner banner = new Banner();
        //设置图片的id
        banner.setId(bannerId);
        //设置图片的真实文件名字
        banner.setImg(originalFilename);
        //调用修改方法
        bannerServices.updateBanner(banner);
    }

    //数据导出
    @ResponseBody
    @RequestMapping("/derivedtable")
    public void derivedtable(HttpServletRequest request, HttpServletResponse response) {
        //获取项目的绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/img");
        //获取响应流
        ServletOutputStream outputStream = null;
        Workbook workbook = null;
        try {
            outputStream = response.getOutputStream();
            //查询数据
            Map<String, Object> map = bannerServices.selectBannerAll(1, 100000);
            //将集合中的数据取出
            List<Banner> rows = (List<Banner>) map.get("rows");
            //遍历集合
            for (Banner row : rows) {
                //给所有轮播图设置绝对路径
                row.setImg(realPath + "/" + row.getImg());
            }
            //输出表格给用户
            workbook = ExcelExportUtil.exportBigExcel(
                    new ExportParams("轮播图", "位置"), Banner.class, rows);
            if (workbook != null && outputStream != null) {
                //设置响应类型
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                //设置响应头 以附件形式
                response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode("banner.xls", "UTF-8"));
                //输出表格到用户的响应流中
                workbook.write(outputStream);
            }
            System.out.println("数据:" + workbook);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                if (outputStream != null && workbook != null) {
                    outputStream.close();
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    //表格数据导入
    @ResponseBody
    @RequestMapping("/uploading")
    public void uploading(MultipartFile file) {
        //创建表头对象
        ImportParams importParams = new ImportParams();
        //设置标题行
        importParams.setTitleRows(0);
        //设置表头
        importParams.setHeadRows(1);
        //使用esya工具类导入数据
        try {
            List<Banner> list = ExcelImportUtil.importExcel(file.getInputStream(), Banner.class, importParams);
            //遍历集合将数据存入到数据库
            for (Banner banner : list) {
                bannerServices.insetBanner(banner);
                //切割文件名
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //轮播图数据显示
    @ResponseBody
    @RequestMapping("/bannardate")
    public List<Integer> bannardate() {
        return bannerServices.selectDateBanner();
    }


}
