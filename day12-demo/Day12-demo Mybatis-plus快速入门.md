# Day12-demo Mybatis-plus快速入门

```txt
		今天学习了Mybatis-plus，工作流程是，写domain，然后写dao层，dao层的接口要继承BaseMapper<domain>,再加上@Mapper注解，然后用yml当配置文件，就是配置datasource，driver，url，username，password，这一类的东西，想要使用分页功能还要写拦截器，以及乐观锁，代码如下：
		@Configuration
public class MpConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor=new MybatisPlusInterceptor();
        //分页拦截
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        //乐观锁拦截器
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}


-----------------------------------------------------------------------
最后的代码生成器没听，应为还有一门课程是更加深入的讲解mybatis-plus的，我打算在那一门课程里面继续学习，。
```

