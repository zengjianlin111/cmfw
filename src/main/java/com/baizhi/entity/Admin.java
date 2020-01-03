package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data   //get set方法
@AllArgsConstructor //有参
@NoArgsConstructor //无参
public class Admin implements Serializable {
    private String id;  //id
    private String username;    //用户名
    private String password;    //密码密码
    private String other;       //预留字段
}
