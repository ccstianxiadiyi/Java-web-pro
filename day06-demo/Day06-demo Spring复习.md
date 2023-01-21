# Day06-demo Spring复习

```xml
今天复习了Spring注解开发的一些常用注解，以及Aop和对事务处理的一些知识，
@Component 衍生的三个 @Controller @Service @Repository 就是代替那个xml文件, @ComponentScan 代替的就是的包扫描 ，依赖注入方面 注解只需使用@Autowired就行，这是按类型注入，如果想要使用按名称注入，则需要使用@Qualifier 简单类型的则则直接使用@Value 配置第三方的bean则需要使用@Bean @Scope则是设置是单列模式还是双例模式，以及生命周期相关的@PostConstructor和@PreDestroy;
Aop则是面向切面编程就是在不修改代码的情况下，对代码进行功能的追加
具体是怎么实现的呢，就是写一个类叫通知类，然后写一个私有的任意名称的方法，加上@PointCut（”切入点表达式“） 然后再用生命周期那种的@Before(execute("上面定的方法名"))注解修饰想要追加的功能，
注意再SpringConfig这个类上还要补充@EnableAspectJAutoProxy注解
而在通知类上也要加上@Aspect注解,AOP的通知类型有@before，@after,@round
@afterReturning ,@afterThrowing这五种类型
Aop通知获取参数数据则需要再追加的功能的方法中增加参数 JointPoint jp
再调用jp.getArgs()获得Object数组 也可以使用参数 ProceedingJoin Point pjp 可以使用pjp.getArgs()获取参数数组，也可以使用pjp.proceed()方法获得对象。
Spring的事务管理则是再service层的接口上加上@Transactional（里面还能加上事务属性）注解
在设置事务管理器 
@Bean
public PlatformTransactionManager transactionManager(Datasource dataSource){
DataSourceTransactionManager ptm=new DataSourceTransactionManager();
ptm.setDataSource(datasource);
return ptm;
}
还要再SpringConfig类上加@EnableTransactionManagement
```

