package com.baizhi.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component //该类交由spring容器管理
public class AppLoctionTextUtil implements ApplicationContextAware {
    //工厂类对象
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //由于是静态的这里要使用类名调用
        AppLoctionTextUtil.applicationContext = applicationContext;
    }

    //通过名字获取对象
    public static Object getBeanName(String name) {
        return applicationContext.getBean(name);
    }

    //通过类对象获取对象
    public static Object getBeanClass(Class cls) {
        return applicationContext.getBean(cls);
    }

    //通过类型加名字和类对象获取
    public static Object getBeanType(String name, Class cls) {
        return applicationContext.getBean(name, cls);
    }
}
