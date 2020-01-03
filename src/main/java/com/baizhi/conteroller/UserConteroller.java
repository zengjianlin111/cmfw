package com.baizhi.conteroller;

import com.baizhi.services.UserServices;
import com.baizhi.util.MapDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserConteroller {

    //注入services
    @Resource
    private UserServices userServices;

    @ResponseBody
    @RequestMapping("/getData")
    public List<Map<String, Object>> getData() {
        //先创建一个Map集合用于存储数据
        List<Map<String, Object>> list = new ArrayList<>();

        //创建一个工具类的dto
        List<MapDto> list1 = new ArrayList<>();
        //通过工具类mapdto存储数据
        list1.add(new MapDto("上海", new Random().nextInt(100)));
        list1.add(new MapDto("东莞", new Random().nextInt(100)));
        list1.add(new MapDto("中山", new Random().nextInt(100)));
        list1.add(new MapDto("东营", new Random().nextInt(100)));
        list1.add(new MapDto("临汾", new Random().nextInt(100)));
        list1.add(new MapDto("临沂", new Random().nextInt(100)));
        list1.add(new MapDto("丹东", new Random().nextInt(100)));
        list1.add(new MapDto("丽水", new Random().nextInt(100)));
        list1.add(new MapDto("乌鲁木齐", new Random().nextInt(100)));
        list1.add(new MapDto("佛山", new Random().nextInt(100)));
        list1.add(new MapDto("保定", new Random().nextInt(100)));
        list1.add(new MapDto("兰州", new Random().nextInt(100)));
        list1.add(new MapDto("包头", new Random().nextInt(100)));
        list1.add(new MapDto("北京", new Random().nextInt(100)));
        list1.add(new MapDto("北海", new Random().nextInt(100)));
        list1.add(new MapDto("南京", new Random().nextInt(100)));
        list1.add(new MapDto("南宁", new Random().nextInt(100)));
        list1.add(new MapDto("南昌", new Random().nextInt(100)));
        list1.add(new MapDto("南通", new Random().nextInt(100)));
        list1.add(new MapDto("厦门", new Random().nextInt(100)));
        list1.add(new MapDto("北京", new Random().nextInt(100)));
        list1.add(new MapDto("四川", new Random().nextInt(100)));
        list1.add(new MapDto("天津", new Random().nextInt(100)));
        list1.add(new MapDto("浙江", new Random().nextInt(100)));
        list1.add(new MapDto("辽林", new Random().nextInt(100)));

        //遍历集合
        for (MapDto mapDto : list1) {
            HashMap<String, Object> map = new HashMap<>();
            //向集合中添加数据
            //键
            map.put("name", mapDto.getName());
            //值
            map.put("value", mapDto.getValue());
            //向集合中添加数据
            list.add(map);
        }
        //将数据返回
        return list;
    }


    //查询最近7天注册的人数
    @ResponseBody
    @RequestMapping("/recently")
    public List<Integer> recently() {
        //调用业务
        return userServices.selectDate();
    }
}
