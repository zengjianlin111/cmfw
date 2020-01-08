package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Permissio;
import com.baizhi.entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfwApplication.class)
public class AAATests {
    //注入dao
    @Autowired
    private AdminDao adminDao;

    //根据用户id查询角色
    @Test
    public void contextLoads() {

        List<Role> roles = adminDao.selectRole("1");
        for (Role role : roles) {
            System.out.println(role);
        }
    }

    //根据角色查询权限
    @Test
    public void selectPermissio() {

        List<Permissio> permissios = adminDao.selectRoleId("1");
        for (Permissio permissio : permissios) {
            System.out.println(permissio);
        }
    }
}
