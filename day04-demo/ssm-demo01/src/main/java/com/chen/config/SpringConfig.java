package com.chen.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//设置当前类为配置类
@Configuration
//用于设定扫描路径，此注解只能添加一次，多个数据用数组模式
@ComponentScan({"com.chen.dao", "com.chen.service"})
//@PropertySource("name") name为property文件名
public class SpringConfig {
//    @Bean
//          public DataSource dataSource(){
//              DruidDataSource ds=new DruidDataSource();
//                调用set方法
//       return ds;
//          }
}
