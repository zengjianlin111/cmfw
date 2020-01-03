package com.baizhi.services.impl;

import com.baizhi.dao.UserDao;
import com.baizhi.services.UserServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServicesImpl implements UserServices {

    //注入dao
    @Resource
    private UserDao userDao;

    //查询最近7天的注册人数
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Integer> selectDate() {
        //创建一个List用户查询数据
        List<Integer> list = new ArrayList<>();
       /* //创建一个map
        Map<String,Object> map=new HashMap<>();*/
        //查询第一天
        Integer data1 = userDao.selectDataUser(1);
        //将第一天的数据添加到集合中
        list.add(data1);

        //查询第二天
        Integer data2 = userDao.selectDataUser(2);
        //将第一天的数据添加到集合中
        if (data2 - data1 > 0) {
            list.add(data2 - data1);
        } else {
            list.add(0);
        }


        //查询第三天
        Integer data3 = userDao.selectDataUser(3);
        //将第一天的数据添加到集合中
        if (data3 - data2 > 0) {
            list.add(data3 - data2);
        } else {
            list.add(0);
        }
        //查询第四天
        Integer data4 = userDao.selectDataUser(4);
        //将第一天的数据添加到集合中
        if (data4 - data3 > 0) {
            list.add(data4 - data3);
        } else {
            list.add(0);
        }
        //查询第五天
        Integer data5 = userDao.selectDataUser(5);
        //将第一天的数据添加到集合中
        if (data5 - data4 > 0) {
            list.add(data5 - data4);
        } else {
            list.add(0);
        }


        //查询第六天
        Integer data6 = userDao.selectDataUser(6);
        //将第一天的数据添加到集合中
        if (data6 - data5 > 0) {
            list.add(data6 - data5);
        } else {
            list.add(0);
        }
        //查询第七天
        Integer data7 = userDao.selectDataUser(7);
        //将第一天的数据添加到集合中
        if (data7 - data6 > 0) {
            list.add(data7 - data6);
        } else {
            list.add(0);
        }

        //将集合返回
        return list;
    }
}
