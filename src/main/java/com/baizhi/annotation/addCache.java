package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)   //注解使用的位置 为方法上
@Retention(RetentionPolicy.RUNTIME)  //该注解运行时生效
//该注解用于添加缓存(在切入点时使用)
public @interface addCache {
}
