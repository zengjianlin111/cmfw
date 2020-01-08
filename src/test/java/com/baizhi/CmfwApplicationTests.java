//package com.baizhi;
//
//import cn.afterturn.easypoi.excel.ExcelExportUtil;
//import cn.afterturn.easypoi.excel.ExcelImportUtil;
//import cn.afterturn.easypoi.excel.entity.ExportParams;
//import cn.afterturn.easypoi.excel.entity.ImportParams;
//import com.baizhi.dao.AdminDao;
//import com.baizhi.dao.AlbumDao;
//import com.baizhi.dao.ArticleDao;
//import com.baizhi.dao.BannerDao;
//import com.baizhi.entity.Admin;
//import com.baizhi.entity.Album;
//import com.baizhi.entity.Article;
//import com.baizhi.entity.Banner;
//import com.baizhi.services.AlbumServices;
//import com.baizhi.shiro.MyRealm;
//import com.baizhi.util.FileUtil;
//import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.IncorrectCredentialsException;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.mgt.DefaultSecurityManager;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.subject.Subject;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = CmfwApplication.class)
//public class CmfwApplicationTests {
//
//    //注入admindao
//    @Resource
//    private AdminDao adminDao;
//    //注入BannerDao
//    @Resource
//    private BannerDao bannerDao;
//    //注入Albumservices
//    @Resource
//    private AlbumServices albumServices;
//
//    //注入AlbumDao
//    @Resource
//    private AlbumDao albumDao;
//
//    //注入ArticleDao
//    @Resource
//    private ArticleDao articleDao;
//
//    @Test
//    public void contextLoads() {
//        //调用登录方法
//        Admin admin = new Admin();
//        admin.setUsername("admin");
//        admin.setPassword("admin");
//        Admin login = adminDao.login(admin);
//        System.out.println("结果:" + login);
//    }
//
//    @Test
//    public void setBannerDao() {
//        //分页查询轮播图
//        List<Banner> banners = bannerDao.selectBannerAll(0, 2);
//        for (Banner banner : banners) {
//            System.out.println(banner);
//        }
//    }
//
//
//    //添加轮播图
//    @Test
//    public void insetBanner() {
//        bannerDao.insert(new Banner("2", "mox", "1.jsp", new Date(), "激活", null));
//    }
//
//    //修改轮播图
//    @Test
//    public void updateBanner() {
//        bannerDao.update(new Banner("2", "修改", "2.jsp", new Date(), "未激活", null));
//    }
//
//
//    //分页查询专辑
//    @Test
//    public void selectAllAlbum() {
////        //调用services方法
////        Map<String, Object> map = albumServices.selectAlbumAll(0, 2);
////        System.out.println("集合:"+map);
////        //调用dao
//        List<Album> albums = albumDao.selectalbumall(1, 1);
//        System.out.println(albums);
//    }
//
//    @Test
//    public void zongtiaoshu() {
//        //查询总条数
//        int i = albumDao.totalNumber();
//        System.out.println(i);
//    }
//
//
//    //通过父类id分页查询
//    @Test
//    public void fen() {
//        List<Article> articles = articleDao.selectAllArticle(0, 2);
//        for (Article article : articles) {
//            System.out.println("结果" + article);
//        }
//
//
//        //通过id查询总条数
//        int i = articleDao.totalNumber();
//        System.out.println(i);
//    }
//
//
//    //音频时间转换测试
//    @Test
//    public void shij() {
//        String s = FileUtil.getGapTime(269 * 1000);
//        System.out.println("时间格式:" + s);
//    }
//
//    //exela表格生成 将数据库中的数据导入到表格中
//    @Test
//    public void exelout() {
//        //创建一个表格文件
//        HSSFWorkbook workbook = new HSSFWorkbook();
//
//        //获取表格字体对象
//        HSSFFont font = workbook.createFont();
//        //设置表格的样式
//        //设置字体颜色为红色
//        font.setColor(Font.COLOR_RED);
//        //设置字体加粗
//        font.setBold(true);
//        //设置使用的字体 设置字体大小可以使用font.setFontHeightInPoints((short)值);
//        font.setFontName("楷体");
//
//        //获取一个单元格样式对象
//        HSSFCellStyle cellStyle = workbook.createCellStyle();
//        //将字体样式放入 单元格样式中
//        cellStyle.setFont(font);
//        //设置表格居中
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//        //设置单元格宽度
//
//
//        //创建一个工作簿
//        HSSFSheet sheet1 = workbook.createSheet("sheet1");
//        //设置第4行的宽度为了将时间显示出来20 (要乘上256)
//        sheet1.setColumnWidth(3, 10 * 256);
//        //创建第0行 (下标为0代表第一行)  为标题行
//        HSSFRow row = sheet1.createRow(0);
//
//        //创建单元格
//        HSSFCell cell1 = row.createCell(0); //第一个的标题的单元格
//        HSSFCell cell2 = row.createCell(1);
//        HSSFCell cell3 = row.createCell(2);
//        HSSFCell cell4 = row.createCell(3);
//        HSSFCell cell5 = row.createCell(4);
//
//        //给单元格设置值
//        cell1.setCellValue("id");
//        cell2.setCellValue("标题");
//        cell3.setCellValue("图片路径");
//        cell4.setCellValue("上传时间");
//        cell5.setCellValue("状态");
//
//        //给单元格设置样式
//        cell1.setCellStyle(cellStyle);
//        cell2.setCellStyle(cellStyle);
//        cell3.setCellStyle(cellStyle);
//        cell4.setCellStyle(cellStyle);
//        cell5.setCellStyle(cellStyle);
//
//        //查询所有轮播图
//        List<Banner> banners = bannerDao.selectBannerAll(0, 100);
//        //遍历集合
//        for (int i = 0; i < banners.size(); i++) {
//            //通过集合的下标创建没条数据的行 第一行为标题行 所以下标从1开始
//            //长度要等于数据的长度不然数据会丢失
//            HSSFRow row1 = sheet1.createRow(i + 1);
//            //向数据行中添加数据单元格
//            HSSFCell id = row1.createCell(0);//第一个单元格 id
//            HSSFCell title = row1.createCell(1);//第二个单元格 标题
//            HSSFCell img = row1.createCell(2);//第二个单元格 图片路径
////时间要通过特定的样式
//            //通过表格创建一个时间格式对象
//            HSSFDataFormat dataFormat = workbook.createDataFormat();
//            //设置时间格式
//            short format = dataFormat.getFormat("yyyy-MM-dd");
//            //通过表格创建一个时间格式样式
//            HSSFCellStyle dataFormatStyle = workbook.createCellStyle();
//            //给样式中加入时间格式
//            dataFormatStyle.setDataFormat(format);
//            HSSFCell date = row1.createCell(3);//第二个单元格 上传时间
//            //给时间单元格设置时间样式
//            date.setCellStyle(dataFormatStyle);
//            HSSFCell stats = row1.createCell(4);//第二个单元格 状态
//
//
//            //给单元设置值
//            id.setCellValue(banners.get(i).getId());
//            title.setCellValue(banners.get(i).getTitle());
//            img.setCellValue(banners.get(i).getImg());
//            System.out.println("时间:" + banners.get(i).getCreateDate());
//            date.setCellValue(banners.get(i).getCreateDate());
//            stats.setCellValue(banners.get(i).getStatus());
//        }
//
//        try {
//            //输出表格到指定位置
//            workbook.write(new File("F:/excle/banner.xls"));
//            //关闭资源
//            workbook.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    //通过poi将表格中的数据读出
//    @Test
//    public void readexcle() {
//        HSSFWorkbook workbook = null;
//        //创建一个HSSFWorkbook对象用于读取数据 需要一个输入流将文件读取
//        try {
//            workbook = new HSSFWorkbook(new FileInputStream("F:/excle/banner.xls"));
//
//            //读取指定的工作簿
//            HSSFSheet sheet1 = workbook.getSheet("sheet1");
//            //获取最后一行的下标
//            int lastRowNum = sheet1.getLastRowNum();
//            //获取所有行的数据
//            //创建一个集合用于存储数据
//            ArrayList<Banner> banners = new ArrayList<>();
//            //使用数组遍历
//            for (int i = 1; i <= lastRowNum; i++) {
//                //下标从1开始 第一行是标题不要 要<=因为最后一行数据是要的
//                //通过下标拿到所在行的数据
//                HSSFRow row = sheet1.getRow(i);
//
//                //id
//                String id = row.getCell(0).getStringCellValue();
//                //标题
//                String title = row.getCell(1).getStringCellValue();
//                //图片路径
//                String img = row.getCell(2).getStringCellValue();
//                //创建时间
//                Date createdate = row.getCell(3).getDateCellValue();
//                //激活状态
//                String stats = row.getCell(4).getStringCellValue();
//                //创建一个banner对象用于接收数据
//                Banner banner = new Banner();
//                //给对象中设置值
//                banner.setImg(img);
//                banner.setId(id);
//                banner.setCreateDate(createdate);
//                banner.setStatus(stats);
//                banner.setTitle(title);
//                //将设置行参数的对象添加到集合中
//                banners.add(banner);
//            }
//
//            //遍历集合检测结果
//            for (Banner banner : banners) {
//                System.out.println("测试结果:" + banner);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    //使用esaypoi导出表格
//    @Test
//    public void esaypoiout() {
//        List<Banner> banners = bannerDao.selectBannerAll(0, 10);
//        //遍历集合设置图片的路径
//        for (Banner banner : banners) {
//            banner.setImg("D:\\idea\\sound\\cmfw\\cmfw\\src\\main\\webapp\\img\\" + banner.getImg());
//        }
//        //通过esay工具类 list表示导出的参数的集合 前面的那个类对象是对应实体类的类对象
//        Workbook sheets = ExcelExportUtil.exportBigExcel(
//                new ExportParams("表格标题", "表格副标题")
//                , Banner.class, banners);
//        //写入
//        try {
//            sheets.write(new FileOutputStream("F:/excle/banner.xls"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            sheets.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Test
//    public void test() {
//
//        ImportParams params = new ImportParams();
//        params.setHeadRows(1);
//        params.setTitleRows(1);
//        //开启图片上传
//        params.setNeedSave(true);
//        List<Banner> result = ExcelImportUtil.importExcel(
//                new File("F:/excle/banner.xls"),
//                Banner.class, params);
//        for (int i = 0; i < result.size(); i++) {
//            System.out.println(ReflectionToStringBuilder.toString(result.get(i)));
//        }
//    }
//
//
////    //轮播图工具类测试
////    @Test
////    public void banndto()
////    {
////        //调用业务
////        BannerDto bannerDto = bannerDao.selectDate();
////
////        System.out.println("结果:"+bannerDto);
////    }
//
//
//    //查询所有文章
//    @Test
//    public void selectrt() {
//        List<Article> articles = articleDao.selectAll();
//        for (Article article : articles) {
//            System.out.println("结果:" + article);
//        }
//    }
//
//
//    //shiro测试
//    @Test
//    public void shiro() {
//        //创建MyRealm
//        MyRealm myRealm = new MyRealm();
//        //创建SecurityManager对象
//        SecurityManager manager = new DefaultSecurityManager(myRealm);
//        //通过工具类设置进去
//        SecurityUtils.setSecurityManager(manager);
//        //获取主体
//        Subject subject = SecurityUtils.getSubject();
//        //创建一个token 令牌
//        AuthenticationToken token = new UsernamePasswordToken("admin", "admin");
//        try {
//            //登录测试
//            subject.login(token);
//        } catch (UnknownAccountException e) {
//            System.out.println("用户名错误");
//        } catch (IncorrectCredentialsException e) {
//            System.out.println("密码错误");
//        } finally {
//            //判断是否认证
//            boolean authenticated = subject.isAuthenticated();
//            System.out.println("是否认证成功" + authenticated);
//        }
//    }
//}
