package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data   //get set 和toString等方法
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //所有参数的构造函数
public class Album {
    //专辑实体类
    private String id;
    private String title;//标题
    private String img;//图片
    private String score;//星级
    private String author;//作者
    private String broadcaster;//播音员
    private String counts;//集数
    private String brief;//内容简介
    //图片上传的时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date create_date;//时间
    private String status;//状态
    private String other;
}
