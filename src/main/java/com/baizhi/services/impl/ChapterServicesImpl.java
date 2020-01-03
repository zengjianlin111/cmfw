package com.baizhi.services.impl;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.services.ChapterServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service  //声明该类是一个services
@Transactional  //事务控制
public class ChapterServicesImpl implements ChapterServices {
    //注入dao
    @Resource
    private ChapterDao chaptereDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS) //不需要事务
    public Map<String, Object> selectChapterAll(int page, int count, String albumid) {
        //计算起始条数 (当前页-1)*显示条数
        int qishi = (page - 1) * count;

        //查询总条数
        int totalNumber = chaptereDao.totalNumber(albumid);
        //计算总页数
        int totalPage = totalNumber % count == 0 ? totalNumber / count : totalNumber / count + 1;
        //查询数据
        List<Chapter> banners = chaptereDao.selectAllChapter(qishi, count, albumid);
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


        return map;//计算起始条数 (当前页-1)*显示条数
    }

    //添加章节
    @Override
    public String inset(Chapter article) {
        //生成id
        String s = UUID.randomUUID().toString();
        //设置id
        article.setId(s);
        //调用添加方法
        chaptereDao.insert(article);
        return s;
    }

    //修改章节
    @Override
    public void update(Chapter article) {
        //调用修改章节的方法
        chaptereDao.update(article);
    }

    //批量删除
    @Override
    public void delete(String[] ids) {
        //调用批量删除的方法
        chaptereDao.delete(ids);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS) //不需要事务
    public Chapter selectId(String id) {
        //调用方法
        return chaptereDao.selectId(id);
    }
}
