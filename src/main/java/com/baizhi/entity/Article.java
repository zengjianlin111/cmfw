package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data  //get set toString 方法
@NoArgsConstructor   //无参数构造
@AllArgsConstructor     //所有参数的构造
public class Article {
    //章节表的实体类
    //id
    private String id;
    private String title; //标题
    private String author; //作者
    private String content;  //内容
    private String guru_id; //大师id
    private Date create_date; //上传时间
    private String status;   //激活状态
    private String other;   //预留字段
}
