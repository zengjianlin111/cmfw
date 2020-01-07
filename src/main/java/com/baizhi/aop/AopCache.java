//package com.baizhi.aop;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//@Component    //该类交由springboot管理
//@Aspect   //声明该类是一个切面类
//public class AopCache{
//    @Autowired
//    private RedisTemplate redisTemplate;  //该类用于操作redis
//
//    //添加缓存的方法(使用环绕通知)
//    @Around("@annotation(com.baizhi.annotation.addCache)")  //该该注解用于切入注解
//    public Object  around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable //该对象用于接收参数
//    {
//        //通过操作redis的对象获取一个hash对象
//        HashOperations hashOperations = redisTemplate.opsForHash();
//
//        //(存入的值还是和以前一样使用大键和小键的形式 类名作为大键 方法名+参数作为小键)
//        //通过ProceedingJoinPoint获取触发该切入点的类名(相当于mapper文件中的namespase)
//        String namespase = proceedingJoinPoint.getTarget().getClass().getName(); //namespase
//        //通过ProceedingJoinPoint获取方法名
//        String methodname = proceedingJoinPoint.getSignature().getName();
//        //通过ProceedingJoinPoint获取参数列表
//        Object[] args = proceedingJoinPoint.getArgs();
//        //创建一个StringBuffer容器用于存储参数
//        StringBuffer stringBuffer = new StringBuffer();
//        //先将方法名添加到容器中
//        stringBuffer.append(namespase);
//        //将方法名和参数进行拼接 遍历参数集合
//        for (Object arg : args) {
//            stringBuffer.append(arg).toString();
//        }
//
//        //判断内存中数据是否存在(直接使用大键和小键精确查询)
//        if(hashOperations.hasKey(namespase,stringBuffer))
//        {
//            //存在将数据取出
//            Object o = hashOperations.get(namespase, stringBuffer);
//            //直接数据取出
//            return o;
//        }
//        //不存在从数据查询 执行方法
//        Object proceed = proceedingJoinPoint.proceed();
//
//        //添加缓存
//        hashOperations.put(namespase,methodname,proceed);
//
//        return proceed;
//    }
//
//
//    //清空redis
//    @After("@annotation(com.baizhi.annotation.ClearCache)") //使用后置通知  该方法用于清除redis中的数据
//    public void after(JoinPoint proceedingJoinPoint) //要使用 JoinPoint 不然项目启动不了
//    {
//        //获取类名(大键)
//        String name = proceedingJoinPoint.getTarget().getClass().getName();
//        //清除大键
//        redisTemplate.delete(name);
//    }
//}
