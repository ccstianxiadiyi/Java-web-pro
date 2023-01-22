# Day07-demo Springmvc入门

```xml
今天学习了Springmvc的入门 主要学习了一些注解的使用，总结一下，RequestMapping（“”）路径，这个注解一般都是加在方法上面，如果加在了controller层的类上面则表示访问路径应该是controller层上的路径和方法上的路径拼接而成的，以及@ReponseBody设置返回json，接收参数有个注解是@requestParam（“参数名字”），其他参数名称与之对应就行，如果传递json类型的参数则需要：
1.导入坐标
com.fasterxml.jackson.core
jackson-databind
2.9.0
2.在SpringMVCConfig类上加上@EnableWebMvc注解
3.在参数里面也要加上@ReponseBody注解
日期类型的传递则在参数前面加上@DateTimeFormat（pattern="格式"）
响应可以为 return "a.html"
也可以为 return "text data";(加上@ReponseBody注解)
也可以为 return 对象（加上@ReponseBody注解）


```

