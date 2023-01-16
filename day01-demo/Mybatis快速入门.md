# Mybatis快速入门

### 首先建立一个Maven工程，在pom.xml中导入需要的坐标

```xml
<dependencies>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.5</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.46</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.20</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>

        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
            <version>1.2.3</version>
        </dependency>

    </dependencies>
```

### 然后编写mybatis-config.xml文件

```java
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <!--数据库连接信息-->
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf-8&amp;useSSL=false"/>
                <property name="username" value="root"/>
                <property name="password" value="root123"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <!--加载sql映射文件-->
<!--        <mapper resource="com/itheima/mapper/StuMapper.xml"/>-->
        <package name="com.itheima.mapper"/>
    </mappers>
</configuration>
```

### 然后配置对应的logback.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!--
        CONSOLE ：表示当前的日志信息是可以输出到控制台的。
    -->
    <appender name="Console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>[%level]  %blue(%d{HH:mm:ss.SSS}) %cyan([%thread]) %boldGreen(%logger{15}) - %msg %n</pattern>
        </encoder>
    </appender>

    <logger name="com.itheima" level="DEBUG" additivity="false">
        <appender-ref ref="Console"/>
    </logger>


    <!--

      level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF
     ， 默认debug
      <root>可以包含零个或多个<appender-ref>元素，标识这个输出位置将会被本日志级别控制。
      -->
    <root level="DEBUG">
        <appender-ref ref="Console"/>
    </root>
</configuration>

```

### 在java目录下建立com.itheima.pojo和com.itheima.dao

### 然后编写对应的实体类（这里以Brand类为例）

```java
package com.itheima.pojo;

/**
 * 品牌
 *
 * alt + 鼠标左键：整列编辑
 *
 * 在实体类中，基本数据类型建议使用其对应的包装类型
 */

public class Brand {
    // id 主键
    private Integer id;
    // 品牌名称
    private String brandName;
    // 企业名称
    private String companyName;
    // 排序字段
    private Integer ordered;
    // 描述信息
    private String description;
    // 状态：0：禁用  1：启用
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", ordered=" + ordered +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}

```



### 编写对应的BrandMapper接口

### 注意一定要在resources目录下建立com/itheima/dao

### 编写对应的BrandMapper.xml文件放在目录下

### 在BrandMapper中编写对应的方法

```java
public interface BrandMapper {
List<Brand> selectAll();
}
```

### 在BrandMapper.xml设置对应的sql语句（通过配置文件）

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.itheima.dao.BrandMapper">
    //因为实体类的属性和数据库中字段的属性不一致，所以这里使用起别名的方式
    <resultMap id="brandResultMapper" type="com.itheima.pojo.Brand">
        <result column="brand_name" property="brandName"/>
        <result column="company_name" property="companyName"/>
    </resultMap>
    <select id="selectAll" resultMap="brandResultMapper">
        select * from tb_brand;
    </select>
</mapper>
```

### 接口中定义方法查询有3种传参方式

```java
public interface BrandMapper {
Brand selectById(@Param("id")int id);
Brand selectById(Brand brand);
Brand selectById(Map map);
}
```

下面是分别演示

```java
@Test
    public void testSelectByID() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        Brand brand = brandMapper.selectById(2);
        System.out.println(brand);
        sqlSession.close();
    }
```

```java
@Test
    public void testSelectByID2() throws IOException {
        Brand brand=new Brand();
        int id=2;
        brand.setId(id);
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        Brand brands = brandMapper.selectById2(brand);
        System.out.println(brands);
        sqlSession.close();
    }
```



```java
 @Test
    public void testSelectByID3() throws IOException {
        Map hashmap = new HashMap();
        hashmap.put("id", 2);
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        Brand brands = brandMapper.selectById3(hashmap);
        System.out.println(brands);
        sqlSession.close();
    }
```

### 多条件模糊查询

```java
List<Brand> selectByCondition(Brand brand);
```



```xml
<select id="selectByConditions" resultMap="brandResultMap">
        select *
        from tb_brand
        <where>
            <if test="brandName!= null and brandName!=''">
                and brand_name like #{brandName}
            </if>
            <if test="companyName!= null and companyName!=''">
              and  company_name like #{companyName};
            </if>
            <if test="status!=null">
                and status like #{status}
            </if>
        </where>
    </select>
```



```java
 @Test
    public void testSelectByConditions() throws IOException {
        Brand brand=new Brand();
        brand.setStatus(1);
        brand.setBrandName("%华为%");
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        Brand brands = brandMapper.selectByConditions(brand);
        System.out.println(brands);
        sqlSession.close();
    }
```

### 单条件模糊查询

```
<select id="selectByConditionSingle" resultMap="brandResultMap">
        select *
        from tb_brand
        <where>
            <choose>
                <when test="status!=null">
                    status=#{status}
                </when>
                <when test="companyName!=null and companyName!=''">
                    company_name like #{companyName}
                </when>
                <when test="brandName!=null and brandName!=''">
                    brand_name like #{brandName};
                </when>
            </choose>
        </where>
    </select>
```



```java
@Test
    public void testSelectByConditionsSingle() throws IOException {
        Brand brand=new Brand();
        brand.setCompanyName("%华为%");
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        Brand brands = brandMapper.selectByConditionSingle(brand);
        System.out.println(brands);
        sqlSession.close();
    }
```

### xml中表示大于小于可以使用Cdata区

```xml
<select id="selectByID" resultType="com.itheima.Student">
        select * from stu where id <![CDATA[
        =
        ]]>
		#{id};
</select>
```

### 增加操作

```java
void add(Brand brand);
```

```xml
    <insert id="add" useGeneratedKeys="true" keyProperty="id">
        insert into tb_brand(brand_name, company_name, ordered, description, status)
        values (#{brandName}, #{companyName}, #{ordered}, #{description}, #{status});
    </insert>
```

```java
@Test
    public void testadd() throws IOException {
        Brand brand=new Brand();
        brand.setCompanyName("爱马仕");
        brand.setBrandName("爱马仕");
        brand.setStatus(1);
        brand.setDescription("高端的产品只为有钱人提供");
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        brandMapper.add(brand);
        sqlSession.commit();
        sqlSession.close();
    }
```

### 编辑操作

```java
void edit(Brand brand);
```

```xml
<update id="edit">
        update tb_brand
        <set>


            <if test="companyName!=null and companyName!=''">
                company_name=#{companyName},
            </if>
            <if test="brandName!=null and brandName!=''">
                brand_name=#{brandName},
            </if>
            <if test="ordered!=null ">
                ordered=#{ordered},
            </if>
            <if test="description!=null and description!=''">
                description=#{description},
            </if>
            <if test="status!=null ">
                status=#{status},
            </if>


        </set>
        where id=#{id};
    </update>
```

```java
@Test
    public void testedit() throws IOException {
        Brand brand=new Brand();
        brand.setCompanyName("爱马仕");
        brand.setBrandName("爱马仕");
        brand.setStatus(1);
        brand.setDescription("高端的产品只为有钱人提供");
        brand.setId(10);
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        brandMapper.edit(brand);
        sqlSession.commit();
        sqlSession.close();
    }
```

### 删除

#### 删除一个

```java
void delete(Brand brand);
```



```xml
<delete id="delete">
    delete
    from tb_brand
    where id = #{id};
</delete>
```

```java
 @Test
    public void testdeleteBYID() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        BrandMapper brandMapper=sqlSession.getMapper(BrandMapper.class);
        brandMapper.deleteByid(1);
        System.out.println("删除成功");
        sqlSession.commit();
        sqlSession.close();
    }
```

#### 删除多个

```java
void deleteMore(@Param("ids") int[] arr);
```

```xml
 <delete id="deleteMore">
        delete
        from tb_brand
        where id in
        <foreach collection="ids" item="id" separator="," open="(" close=")">
            #{id}
        </foreach>
    </delete>
```

```java
测试略
```

### day01总结

```xml
学习了mybatis后感觉最需要注意的一点就是那个dao层对应的xml文件的位置，在resources目录下要创建com/itheima/dao,而不是使用com.itheima.dao,也可以不使用这中配置文件来开发sql，可以使用注解开发，但是老师说以后会主要使用的还是注解开发，所以我这里这里没有使用注解开发.本来今天打算学习ssm的但是突然想到之前的学的东西最好还是要做个笔记梳理一下，所以我就把这个复习梳理的一下哎，之前看的黑马的javaweb课程，让我感觉idea的tomcat是真的傻逼，我在tomcat上部署了web项目之后，用idea只能访问web项目的静态资源。表单提交之后的效果都没有，但是使用了maven插件之后就能解决这个问题，我感觉应该是jdk的问题，我用的jdk17，应该是太高了，明天再复习一下servlet和jsp看看能不能搞一个物流管理那样的项目出来。今天就写到这。
																						--ccs
																						2023/01/16

```

