package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //set和get
@AllArgsConstructor //所有参数构造
@NoArgsConstructor //无参构造
public class Permissio {
    //权限的实体类
    private String id;//id
    private String perms;//权限内容
}
