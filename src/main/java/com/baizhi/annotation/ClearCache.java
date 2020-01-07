package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //表示该注解使用在方法上
@Retention(RetentionPolicy.RUNTIME)  //该注解运行时生效
//该注解用于清除缓存
public @interface ClearCache {
}
