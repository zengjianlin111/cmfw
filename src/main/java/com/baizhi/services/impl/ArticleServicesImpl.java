package com.baizhi.services.impl;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.services.ArticleServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service  //声明该类是一个services
@Transactional  //事务控制
public class ArticleServicesImpl implements ArticleServices {
    //注入dao
    @Resource
    private ArticleDao articleDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS) //不需要事务
    public Map<String, Object> selectArticeAll(int page, int count) {
        //计算起始条数 (当前页-1)*显示条数
        int qishi = (page - 1) * count;

        //查询总条数
        int totalNumber = articleDao.totalNumber();
        //计算总页数
        int totalPage = totalNumber % count == 0 ? totalNumber / count : totalNumber / count + 1;
        //查询数据
        List<Article> banners = articleDao.selectAllArticle(qishi, count);
        //创建一个集合用于存入数据
        Map<String, Object> map = new HashMap<>();
        //将数据添加到集合中

        /*
         * jqGrid要求必须要这样返回
         * page:当前页
         * rows:数据
         *   total：总页数
         *   records:总条数
         * */
        map.put("page", page); //当前页
        map.put("rows", banners); //数据
        map.put("total", totalPage); //总页数
        map.put("records", totalNumber);//总条数


        return map;//计算起始条数 (当前页-1)*显示条数
    }

    //分页查询3条文章
    public List<Map<String, Object>> selectArticle() {
        //创建一个List
        List<Map<String, Object>> list = new ArrayList<>();
        //创建一个map
        Map<String, Object> map = new HashMap<>();
        //查询所有结果
        List<Article> articles = articleDao.selectAllArticle(0, 3);
        //遍历集合
        for (Article article : articles) {
            //向map中添加集合
            map.put("thumbnail", ""); //没有图片字段
            map.put("title", article.getTitle()); //标题
            map.put("author", article.getAuthor()); //描述
            map.put("type", 1);//字段为1固定
            map.put("set_count", "");//没有该字段
            map.put("create_date", article.getCreate_date());//创建时间
            //向集合中添加数据
            list.add(map);
        }
        return list;
    }


    //查询所有文章
    public List<Map<String, Object>> selectAll() {
        //创建一个List
        List<Map<String, Object>> list = new ArrayList<>();

        //查询所有结果
        List<Article> articles = articleDao.selectAll();
        //遍历集合
        for (Article article : articles) {
            //创建一个map
            Map<String, Object> map = new HashMap<>();
            //向集合中添加数据
            //向map中添加集合
            map.put("thumbnail", ""); //没有图片字段
            map.put("title", article.getTitle()); //标题
            map.put("author", article.getAuthor()); //描述
            map.put("type", 1);//字段为1固定
            map.put("set_count", "");//没有该字段
            map.put("create_date", article.getCreate_date());//创建时间
            //向集合中添加数据
            list.add(map);
        }
        return list;
    }

    //通过上师id查询文章
    public List<Map<String, Object>> selectShangId(String id) {
        //创建一个List
        List<Map<String, Object>> list = new ArrayList<>();
        //查询所有结果
        List<Article> articles = articleDao.selectShangId(id);
        //遍历集合
        for (Article article : articles) {
            //创建一个map
            Map<String, Object> map = new HashMap<>();
            //向集合中添加数据
            //向map中添加集合
            map.put("thumbnail", ""); //没有图片字段
            map.put("title", article.getTitle()); //标题
            map.put("author", article.getAuthor()); //描述
            map.put("type", 1);//字段为1固定
            map.put("set_count", "");//没有该字段
            map.put("create_date", article.getCreate_date());//创建时间
            //向集合中添加数据
            list.add(map);
        }
        return list;
    }

    //添加章节
    @Override
    public void inset(Article article) {
        //生成id
        String s = UUID.randomUUID().toString();
        //设置id
        article.setId(s);
        //设置时间
        article.setCreate_date(new Date());
        //调用添加方法
        articleDao.insert(article);
    }

    //修改章节
    @Override
    public void update(Article article) {
        //调用修改章节的方法
        articleDao.update(article);
    }

    //批量删除
    @Override
    public void delete(String[] ids) {
        //调用删除方法
        articleDao.delete(ids);
    }
}
