package com.baizhi.services;

import com.baizhi.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerServices {
    //分页查询所有轮播图
    public Map<String, Object> selectBannerAll(int qishi, int count);

    //添加啊轮播图
    public String insetBanner(Banner banner);

    //修改轮播图
    public void updateBanner(Banner banner);

    //批量删除和删除
    public void delete(String[] id);

    //通过id查询
    public Banner selectid(String id);

    //查询一年内的 轮播图数据
    public List<Integer> selectDateBanner();

    //分页查询轮播图
    public List<Map<String, String>> selectpage(String path);
}
