package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {
    //查询所有轮播图 分页查询 需要一个起始条数 和查询的条数
    public List<Banner> selectBannerAll(@Param("qishi") int qishi, @Param("count") int count);

    //查询总条数
    public int totalNumber();

    //添加录播图
    public void insert(Banner banner);

    //修改轮播图
    public void update(Banner banner);

    //删除和批量删除
    public void delete(String[] ids);

    //通过id查询
    public Banner selectid(String id);

    //查询12个月内添加的轮播图的数据
    public Integer selectDate(int mthod);

}
