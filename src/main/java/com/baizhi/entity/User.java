package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //用户的实体类
    @Excel(name = "id")
    private String id;
    //电话号码
    @Excel(name = "电话号码")
    private String phone_number;
    //密码
    @Excel(name = "密码")
    private String password;
    //名字
    @Excel(name = "名字")
    private String name;
    //法名
    @Excel(name = "法名")
    private String dharma;
    //头像
    @Excel(name = "头像")
    private String head_img;
    //性别
    @Excel(name = "性别")
    private String sex;
    //地址
    @Excel(name = "地址")
    private String address;
    //签名
    @Excel(name = "签名")
    private String sign;
    //上师id
    @Excel(name = "上师的id")
    private String guru_id;
    //最后登录的时间
    @Excel(name = "登录时间", format = "yyyy-MM-dd")
    private Date last_date;
    //创建的时间
    @Excel(name = "创建的时间", format = "yyyy-MM-dd")
    private Date create_date;
    //状态
    @Excel(name = "状态")
    private String status;
    //盐值
    @ExcelIgnore   //该字段不需要参与表格导入导出
    private String salt;
    //other预留字段
    @ExcelIgnore   //该字段不需要参与表格导入导出
    private String other;
}
