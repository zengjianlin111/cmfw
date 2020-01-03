package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    //章节的dao
    //分页查询
    public List<Article> selectAllArticle(@Param("qishi") int qishi, @Param("count") int count);

    //查询总条数
    public int totalNumber();

    //添加章节
    public void insert(Article article);

    //修改章节
    public void update(Article article);

    //批量删除
    public void delete(String[] ids);

    //查询所有文章
    public List<Article> selectAll();

    //通过上师id查询文章
    public List<Article> selectShangId(String id);
}
