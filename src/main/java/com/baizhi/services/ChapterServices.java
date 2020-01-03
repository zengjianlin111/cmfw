package com.baizhi.services;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterServices {
    //分页查询按专辑id查询章节
    public Map<String, Object> selectChapterAll(int qishi, int count, String albumid);

    //添加章节
    public String inset(Chapter article);

    //修改章节
    public void update(Chapter article);

    //批量删除
    public void delete(String[] ids);

    //通过id查询
    public Chapter selectId(String id);
}
