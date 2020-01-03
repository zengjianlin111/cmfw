package com.baizhi.services;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleServices {
    //分页查询按专辑id查询章节
    public Map<String, Object> selectArticeAll(int qishi, int count);

    //添加章节
    public void inset(Article article);

    //修改章节
    public void update(Article article);

    //批量删除
    public void delete(String[] ids);

    //分页查询3条
    public List<Map<String, Object>> selectArticle();

    //查询所有文章
    public List<Map<String, Object>> selectAll();

    //通过上师id查询文章
    public List<Map<String, Object>> selectShangId(String id);
}
