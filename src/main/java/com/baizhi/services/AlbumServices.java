package com.baizhi.services;

import java.util.List;
import java.util.Map;

public interface AlbumServices {
    //分页查询所有专辑
    public Map<String, Object> selectAlbumAll(int qishi, int count);

    //分页查询6条数据
    public List<Map<String, Object>> selectAlubm(String url);

    //通过专辑id查询专辑信息
    public Map<String, Object> selectAlbumParticulars(String id, String url);
}
