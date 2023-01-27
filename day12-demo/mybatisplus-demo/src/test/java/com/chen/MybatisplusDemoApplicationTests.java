package com.chen;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.dao.StuDao;
import com.chen.domain.Stu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Wrapper;
import java.util.List;

@SpringBootTest
class MybatisplusDemoApplicationTests {
    @Autowired
    private StuDao stuDao;
    @Test
    void testAdd(){
        Stu stu=new Stu();
        stu.setId(4);
        stu.setName("dj");
        stu.setAge(50);
        int insert = stuDao.insert(stu);
        System.out.println(insert);
    }
    @Test
    void testDelete(){
        int i = stuDao.deleteById(4);
        System.out.println(i);
    }

    @Test
    void testGetAll() {
//        QueryWrapper<Stu> wrapper=new QueryWrapper<Stu>();
//        方式一 wrapper.lt("age","18");
//        方式二 wrapper.lambda().lt(Stu::getAge,18);
//        List<Stu> stus = stuDao.selectList(wrapper);
//        System.out.println(stus);
//        方式三
        LambdaQueryWrapper<Stu> lqw=new LambdaQueryWrapper<>();
        lqw.lt(Stu::getAge,18);
        List<Stu> stus = stuDao.selectList(lqw);
        System.out.println(stus);
    }
    @Test
    void testGetByPage(){
        IPage page=new Page(1,2);
        stuDao.selectPage(page,null);
        System.out.println(page.getRecords());
    }

}
