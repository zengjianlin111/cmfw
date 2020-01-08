package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  //get set toString 等
@AllArgsConstructor  //所有参数的构造
@NoArgsConstructor //无参数构造
public class Role {
    //角色表实体类
    private String id;  //id
    private String name; //角色名
}
