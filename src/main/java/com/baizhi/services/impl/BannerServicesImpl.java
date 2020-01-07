package com.baizhi.services.impl;

import com.baizhi.annotation.ClearCache;
import com.baizhi.annotation.addCache;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.services.BannerServices;
import io.goeasy.GoEasy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional  //事务控制
public class BannerServicesImpl implements BannerServices {
    //注入dao
    @Resource
    private BannerDao bannerDao;

    //分页查询
    @Override
    @addCache  //使用redis做缓存
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectBannerAll(int page, int count) {


        //计算起始条数 (当前页-1)*显示条数
        int qishi = (page - 1) * count;

        //查询总条数
        int totalNumber = bannerDao.totalNumber();
        //计算总页数
        int totalPage = totalNumber % count == 0 ? totalNumber / count : totalNumber / count + 1;
        //查询数据
        List<Banner> banners = bannerDao.selectBannerAll(qishi, count);
        //创建一个集合用于存入数据
        Map<String, Object> map = new HashMap<>();
        //将数据添加到集合中

        /*
         * jqGrid要求必须要这样返回
         * page:当前页
         * rows:数据
         *   total：总页数
         *   records:总条数
         * */
        map.put("page", page); //当前页
        map.put("rows", banners); //数据
        map.put("total", totalPage); //总页数
        map.put("records", totalNumber);//总条数


        return map;
    }

    //添加轮播图
    @Override
    @ClearCache   //清空redis中的数据
    public String insetBanner(Banner banner) {
        //给轮播图设置id
        String s = UUID.randomUUID().toString();
        banner.setId(s);

        //设置上传的时间
        banner.setCreateDate(new Date());
        bannerDao.insert(banner);

        //调用查询实时显示
        List<Integer> list = selectDateBanner();
        System.out.println("实时更新添加数据:" + list.toString());
        //创建goeasy对象(参数1为连接点 国内一般用杭州的)
        //第二个参数为
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-935895745dd747ce98b34c40433d9679");
        goEasy.publish("banner", list.toString());


        //将id返回
        return s;
    }

    //修改轮播图
    @Override
    @ClearCache   //清空redis中的数据
    public void updateBanner(Banner banner) {
        bannerDao.update(banner);

        //调用查询实时显示
        List<Integer> list = selectDateBanner();
        //创建goeasy对象(参数1为连接点 国内一般用杭州的)
        //第二个参数为
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-935895745dd747ce98b34c40433d9679");
        goEasy.publish("banner", list.toString());
    }

    //删除和批量删除
    @Override
    @ClearCache   //清空redis中的数据
    public void delete(String[] id) {
        //方法
        bannerDao.delete(id);
        //调用查询实时显示
        List<Integer> list = selectDateBanner();
        //创建goeasy对象(参数1为连接点 国内一般用杭州的)
        //第二个参数为
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-935895745dd747ce98b34c40433d9679");
        goEasy.publish("banner", list.toString());
    }

    //通过id查询轮播图
    @Override
    @addCache //向redis中添加缓存
    @Transactional(propagation = Propagation.SUPPORTS) //不需要事务
    public Banner selectid(String id) {
        return bannerDao.selectid(id);
    }

    //查询所有一年内的轮播图数据
    @Override
    @addCache //向redis中添加缓存
    public List<Integer> selectDateBanner() {
        //创建一个集合用于存储数据
        List<Integer> list = new ArrayList<>();
        //调用业务查询数据
        for (int i = 1; i <= 12; ++i) {
            //调用业务
            Integer integer = bannerDao.selectDate(i);
            //将集合添加到集合中
            list.add(integer);
        }
        //将数据返回
        return list;
    }


    //分页查询轮播图(参数固定1-5)
    @addCache //向redis中添加缓存
    public List<Map<String, String>> selectpage(String path) {
        //创建一个list用于存储结果
        List<Map<String, String>> list = new ArrayList<>();
        //调用业务
        List<Banner> banners = bannerDao.selectBannerAll(0, 5);

        System.out.println("轮播图的查询0-5条的集合:" + banners);

        //遍历结果处理
        for (Banner banner : banners) {
            //创建一个map用户接受结果
            Map<String, String> map = new HashMap<>();
            //处理结果
            //图片
            map.put("thumbnail", path + banner.getImg());
            //描述
            map.put("desc", banner.getTitle());
            //id
            map.put("id", banner.getId());
            //将map添加到集合中
            list.add(map);
        }
        //返回结果
        return list;
    }

}
