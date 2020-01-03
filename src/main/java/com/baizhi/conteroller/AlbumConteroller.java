package com.baizhi.conteroller;

import com.baizhi.services.AlbumServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@RequestMapping("/album")
@Controller
public class AlbumConteroller {
    //注入dao
    @Resource
    private AlbumServices albumServices;

    //分页查询
    @ResponseBody
    @RequestMapping("/selectPage")
    public Map<String, Object> selectPage(Integer page, Integer rows) {
        //调用业务
        return albumServices.selectAlbumAll(page, rows);
    }
}
