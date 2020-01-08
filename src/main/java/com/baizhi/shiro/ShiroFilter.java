package com.baizhi.shiro;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/*
 * 该类为自定义的shrio过滤器
 * */
@Configuration //交由spring管理 类似于xml的配置bean
public class ShiroFilter {

    /*
     *声明一个bean有工厂自动调用该方法
     *
     * DefaultWebSecurityManager该对象必须要有
     * 该方法名固定shiroFilterFactoryBean
     *   注意此处不能使用注入 只能使用参数的方式传递
     *   该方法只有设置的匿名资源或者认证资源时才会生效
     *   这个方法
     *   factoryBean.setFilterChainDefinitionMap(map);
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        //创建一个map集合用于设置是否为匿名资源和认证资源
        HashMap<String, String> map = new HashMap<>();
        //anon表示匿名资源 authc表示认证资源

        //设置所有样式为匿名资源
        map.put("/richtext/**", "anon");
        map.put("/kindeditor/**", "anon");
        map.put("/mp3/**", "anon");
        map.put("/boot/**", "anon");
        map.put("/img/**", "anon");
        map.put("/jqgrid/**", "anon");
        map.put("/jsp/assets/**", "anon");
        //设置验证码为匿名资源
        map.put("/vcodeimg/img", "anon");
        //设置登录页面让为匿名资源让所有人访问
        map.put("/jsp/login.jsp", "anon");
        //设置登录的conteroll为匿名资源让用户登录判断
        map.put("/admin/shirologin", "anon");
        //其他资源必须全部需要认证才能访问
        map.put("/**", "authc");//所有资源为认证资源


        //创建一个ShiroFilterFactoryBean对象
        //由该对象完成所有操作
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //设置登录页面的路径(shiro默认跳转的路径) 以当前项目为根路径
        //默认它会直接在当前项目的根路径下找login.jsp
        factoryBean.setLoginUrl("/jsp/login.jsp");

        /*
         * 设置哪些资源为匿名资源和匿名资源 必须要设置 项目编译时会判断 如果没有设置
         * 那么该过滤器都不会进入
         * */
        factoryBean.setFilterChainDefinitionMap(map);

        //设置SecurityManager
        factoryBean.setSecurityManager(defaultWebSecurityManager);
        //将对象返回
        return factoryBean;
    }

    //方法用于将DefaultWebSecurityManager对象交由spring管理方便后期使用
    //该类需要一个Realm域对象 直接使用参数传入即可 下面也会将
    //该类交由spring管理
    //使用MemoryConstrainedCacheManager该对象添加缓存
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(MyRealm myRealm, MemoryConstrainedCacheManager memoryConstrainedCacheManager) {
        //创建该类对象
        DefaultWebSecurityManager securityManager =
                new DefaultWebSecurityManager();
        //设置使用的域
        securityManager.setRealm(myRealm);
        //设置缓存
        securityManager.setCacheManager(memoryConstrainedCacheManager);
        //将对象返回
        return securityManager;
    }

    //将自定义的域对象交由工厂管理
    @Bean
    public MyRealm myRealm() {
        //创建自定义的域对象并直接返回
        return new MyRealm();
    }

    //为了减少后台压力添加缓存
    //MemoryConstrainedCacheManager将该对象交由工厂管理
    @Bean
    public MemoryConstrainedCacheManager memoryConstrainedCacheManager() {
        //创建该类对象
        return new MemoryConstrainedCacheManager();
    }
}
