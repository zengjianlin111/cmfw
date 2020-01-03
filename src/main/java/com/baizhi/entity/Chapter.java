package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {
    private String id;//id
    private String title;//标题
    private String album_id; //专辑id
    private String size; //大小
    private String duration; //时间
    private String src; //路径
    private String status;//状态
    private String other; //预留字段
}
