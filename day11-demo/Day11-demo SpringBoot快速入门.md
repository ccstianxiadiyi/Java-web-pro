# Day11-demo SpringBoot快速入门

## yml数据读取方式

### yml文件

```yaml
lesson: SpringBoot
server:
	port: 80
enterprise:
	name: itcast
	age: 16
	tel: 135888
	subject:
	 -java
	 -大数据
	 -前端
	
```

### java程序

```java
package com.chen.controller;

import com.chen.domain.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    @Value("${lesson}")
    private String lesson;
    @Value("${server.port}")
    private Integer port;
    @Value("${enterprise.subject[0]}")
    private String[] subject_00;
    @Autowired
    private Environment environment;
    @Autowired
    private Enterprise enterprise;
    @GetMapping("/{id}")
    public String getOne(@PathVariable Integer id) {
        System.out.println(id);
        System.out.println(lesson);
        System.out.println(port);
        System.out.println(subject_00);
        System.out.println(environment.getProperty("lesson"));
        System.out.println(enterprise);
        return "<a>Hello Spring!!!</a>";
    }
}

```

```java
package com.chen.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix ="enterprise")
public class Enterprise {
    private String name;
    private Integer age;
    private String tel;
    private String[] subject;

    public Enterprise() {
    }



    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取
     * @return tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置
     * @param tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 获取
     * @return subject
     */
    public String[] getSubject() {
        return subject;
    }

    /**
     * 设置
     * @param subject
     */
    public void setSubject(String[] subject) {
        this.subject = subject;
    }

    public String toString() {
        return "Enterprise{name = " + name + ", age = " + age + ", tel = " + tel + ", subject = " + subject + "}";
    }
}

```

### 多环境开发

```yaml
#设置启用的环境
spring:
  profiles:
  active: dev
---    
server:
  port: 8080
spring:
  profiles: dev
    
  
---
server:
  port: 8081
spring:
    profiles: test
  
---
server:
  port: 8082
spring:
  profiles: pro
```

### 多环境命令行启动参数设置

```cmd
#执行install
jar -jar chengxu.jar -- spring.profiles.active=dev
```

### 多环境优先级

maven>springboot

### 配置文件分类

resource的config目录里的application.yaml会覆盖外面的application.yaml在config目录里面再建一个config其里面的application.yaml会覆盖上面的application.yaml

## SpringBoot整合第三方技术

### SpringBoot整合junit

```java
package com.chen;

import com.chen.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootZhengheApplicationTests {
    @Autowired
    private BookService bookService;

    @Test
    void contextLoads() {
        bookService.save();
    }

}

```

SpringBoot整合mybatis

```java
package com.chen.dao;

import com.chen.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface BookDao {
    @Select("select * from tbl_book where id=#{id}")
    Book getByID(Integer id);
}
```

yml

```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/test
    username: root
    password: root123

```

### 总结

```html
今天了解了springboot入门，感觉springboot的强大，就像之前的弹幕里面都在说的白学，哈哈哈。
```

