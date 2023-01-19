# Day04-Spring配置Bean

### xml文件配置

```xml
<bean
 id="bookDao"							bean的id
 name="dao bookDaoImpl"					bean的别名
 class="com.chen.dao.impl.BookDaoImpl"	bean的类型，静态工厂类，FactoryBean类
 scope="singleton"						控制bean的实列数量
 init-method="init"						生命周期初始化方法
 destroy-method="destroy"				生命周期销毁方法
 autowire="byType"						自动装配类型
 factory-method="getInstance"			bean工厂方法，应用于静态工厂或实列工厂
 factory-bean="com.chen.factory.BookDaoFactory" 实例工厂bean
 lazy-init="true"                        设置延迟加载
      
      
 />
```



### xml依赖注入相关

```xml
<bean id="BookService" class="com.chen.service.impl.BookServiceImpl">
    <constructor-arg name="bookDao" ref="bookDao"/>
    <constructor-arg name="userDao" ref="userDao"/>
    <constructor-arg name="msg" ref="WARN"/>
    <constructor-arg type="java.lang.String" index="3" value="WARN"/>
    <property name="bookDao" ref="bookDao"/>
    <property name="userDao" ref="userDao"/>
    <property name="msg" ref="WARN"/>
    <property name="names">
    <list>
        <value>itcast</value>
        <ref bean="dataSource"/>
        </list>
    </property>
</bean>
```

### 注解配置bean

```java
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

```

```java
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

```

```java
package com.chen.service.impl;

import com.chen.dao.BookDao;
import com.chen.dao.impl.BookDaoImpl;
import com.chen.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookServiceImpl implements BookService {
//    @Autowired 默认用类型装配
//    @Qualifier("bookDao") 使用按名称注入
//    自动注入
    private BookDao bookDao;
    public void save() {
        System.out.println("book service save~~~");
        bookDao.save();
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}

```

```java
package com.chen;

import com.chen.config.SpringConfig;
import com.chen.dao.BookDao;
import com.chen.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App03 {
    public static void main(String[] args) {
        ApplicationContext ctx=new AnnotationConfigApplicationContext(SpringConfig.class);
        BookDao bookDao = (BookDao) ctx.getBean("bookDao");
        System.out.println(bookDao);
        BookService bookService = ctx.getBean(BookService.class);
        System.out.println(bookService);
    }
}

```

### 注意Spring提供@component衍生的三个注解

```java
@Controller	 用于表现层bean定义
@Service     用于业务层bean定义
@Repository  用于数据层bean定义

```

| 功能           | xml配置                                                      | 注解                                                       |
| -------------- | ------------------------------------------------------------ | ---------------------------------------------------------- |
| 定义Bean       | bean标签 id属性 和class属性                                  | @Component @Controller @Service @Repository @ComponentScan |
| 设置依赖注入   | setter注入（set方法）应用简单 构造器注入（构造方法）引用/简单 自动装配 | @Autowired @Qualifier @Value                               |
| 配置第三方bean | bean标签                                                     | @Bean                                                      |
| 作用范围       | 静态工厂，实列工厂，FactoryBean                              | @Scope                                                     |
| 生命周期       | 标准接口 init-method destroy-method                          | @PostConstructor @PreDestroy                               |

### 总结

```html
今天学习配置文件配置bean的时候非常懵逼，听着听着睡着了，但是后面还有注解开发在复习了一遍，算是有了一点了解，bean就是交给Ioc管理的对象，但是我还是没有理解bean到底在开发中到底有什么作用.
```

