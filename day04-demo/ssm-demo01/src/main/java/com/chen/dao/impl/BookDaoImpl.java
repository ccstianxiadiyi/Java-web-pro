package com.chen.dao.impl;

import com.chen.dao.BookDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("bookDao")
//控制是否为单例
//@Scope("prototype") singleton
public class BookDaoImpl implements BookDao {
    //为简单类型注入
    @Value("ccs")
//    @Value("${name}") 装配引用类型
    private String name;
    public void save() {
        System.out.println("book dao save~~~~"+name);
    }
//    生命周期相关
//    @PostConstruct
//    public void init(){
//        System.out.println("被执行了~~~");
//    }
//    @PreDestroy
//    public void destroy(){
//        System.out.println("destroy被执行了");
//    }

}
