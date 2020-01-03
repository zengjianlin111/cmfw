package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data   //get set 和toString等方法
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //所有参数的构造函数
//使用注解版esaypoi
public class Banner implements Serializable {
    //轮播图实体
    //id
    @Excel(name = "编号") //name属性值代表列名
    private String id;
    //标题
    @Excel(name = "标题")
    private String title;
    //图片路径  type表示他的类型 1表示导出类型文本 2是图片 3是函数 10数字
    //如果要设置保存图片的位置可以在这里设置保存的路径 添加第三个参数
    @Excel(name = "图片", type = 2)
    private String img;
    //图片上传的时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "时间", format = "yyyy-MM-dd")  //format指定时间格式
    private Date createDate;
    //状态
    @Excel(name = "状态")
    private String status;
    //预留字段
    @ExcelIgnore  //表示该字段不参与导出
    private String other;
}
