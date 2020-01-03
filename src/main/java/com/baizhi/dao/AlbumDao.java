package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    //专辑的dao
    //分页查询所有专辑 参数1是起始页 count是每页查询的条数
    public List<Album> selectalbumall(@Param("qishi") int qishi, @Param("count") int count);

    //查询总条数
    public int totalNumber();

    //查询所有专辑
    public List<Album> selecrAll();

    //通过专辑id查询专辑信息
    public Album selectAlbumId(String id);
}
