package com.baizhi.services.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.services.AlbumServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional  //事务控制
public class AlbumServicesImpl implements AlbumServices {
    //注入dao
    @Resource
    private AlbumDao albumDao;

    //注入章节的dao
    @Resource
    private ChapterDao chapterDao;

    //分页查询
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectAlbumAll(int page, int count) {


        //计算起始条数 (当前页-1)*显示条数
        int qishi = (page - 1) * count;

        //查询总条数
        int totalNumber = albumDao.totalNumber();
        //计算总页数
        int totalPage = totalNumber % count == 0 ? totalNumber / count : totalNumber / count + 1;
        //查询数据
        List<Album> banners = albumDao.selectalbumall(qishi, count);
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


    //分页查询6条数据
    public List<Map<String, Object>> selectAlubm(String url) {
        //创建一个List
        List<Map<String, Object>> list = new LinkedList<>();

        List<Album> selectalbumall = albumDao.selectalbumall(0, 6);
        for (Album album : selectalbumall) {
            //创建map
            Map<String, Object> map = new HashMap<>();
            //取出数据
            //图片
            map.put("thumbnail", url + album.getImg());
            map.put("title", album.getTitle());
            map.put("author", album.getAuthor());
            map.put("type", 0);
            map.put("set_count", album.getCounts());
            map.put("create_date", album.getCreate_date());
            //将数据添加到集合中
            list.add(map);
        }
        //将集合返回
        return list;
    }

    //查询所有专辑
    public List<Map<String, Object>> selectAll(String url) {
        //创建一个集合
        List<Map<String, Object>> list = new ArrayList<>();

        //查询所有的专辑
        List<Album> albums = albumDao.selecrAll();
        //遍历集合
        for (Album album : albums) {
            //创建一个map
            Map<String, Object> map = new HashMap<>();
            //将值添入到集合中
            map.put("thumbnail", url + album.getImg());//图片路径
            map.put("title", album.getTitle());//标题
            map.put("author", album.getAuthor());//作者
            map.put("type", 0);//类型
            map.put("set_count", album.getCounts());//集数
            map.put("create_date", album.getCreate_date()); //创建时间
            //将map添加到集合中
            list.add(map);
        }
        return list;
    }

    //通过专辑id查询专辑信息
    public Map<String, Object> selectAlbumParticulars(String id, String url) {
        //创建一个map用于存储数据
        Map<String, Object> map = new HashMap<>();
        //通过专辑id查询专辑的信息
        Album album = albumDao.selectAlbumId(id);
        //向集合中添加数据
        map.put("thumbnail", url + "/img/" + album.getImg());//图片
        map.put("title", album.getTitle());//标题
        map.put("score", album.getScore());//分数
        map.put("author", album.getAuthor());//作者
        map.put("broadcast", album.getBroadcaster());//播音
        map.put("set_count", album.getCounts());//集数
        map.put("brief", album.getBrief());//简介
        map.put("create_date", album.getCreate_date());//创建时间

        //通过专辑id查询章节
        List<Chapter> chapters = chapterDao.selectAlbumId(id);
        //创建一个list用于存储数据
        List<Map<String, Object>> list = new ArrayList<>();
        //创建一个map用于创建数据
        for (Chapter chapter : chapters) {
            //创建实体类
            Map<String, Object> map1 = new HashMap<>();
            map1.put("title", chapter.getTitle());//标题
            map1.put("download_url", url + "/mp3/" + chapter.getSrc());//url
            map1.put("size", chapter.getSize());//大小
            map1.put("duration", chapter.getDuration());//时长
            //将集合添加到map中
            list.add(map1);
        }
        //将list存入map中
        map.put("list", list);//章节数据
        return map;

    }

}
