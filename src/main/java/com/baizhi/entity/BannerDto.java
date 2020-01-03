package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerDto {
    //轮播图的工具类
    //月份
    private Integer month;
    //数量
    private Integer count;
}
