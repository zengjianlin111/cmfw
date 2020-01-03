package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao {
    //章节的dao
    //分页查询
    public List<Chapter> selectAllChapter(@Param("qishi") int qishi, @Param("count") int count, @Param("albumid") String albumid);

    //查询总条数
    public int totalNumber(String albumid);

    //添加章节
    public void insert(Chapter chapter);

    //修改章节
    public void update(Chapter chapter);

    //批量删除
    public void delete(String[] ids);

    //通过id查询
    public Chapter selectId(String id);

    //通过专辑id查询所有章节
    public List<Chapter> selectAlbumId(String id);
}
