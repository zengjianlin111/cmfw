package com.baizhi.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapDto {
    //用户的工具类
    private String name; //名字
    private Integer value; //值
}
