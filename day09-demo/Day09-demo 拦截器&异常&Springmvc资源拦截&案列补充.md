# Day09-demo 拦截器&异常&Springmvc资源拦截&案列补充

SpingmvcSupport.java

```java
//设置springmvc能够对这些资源放行
package com.chen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class SpringMvcSupport extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/plugins/**").addResourceLocations("/plugins/");
    }
}

```

异常处理

```java
package com.chen.controller;

public class Result {
    //描述统一格式中的数据
    private Object data;
    //描述统一格式中的编码，用于区分操作，可以简化配置0或1表示成功失败
    private Integer code;
    //描述统一格式中的消息，可选属性
    private String msg;

    public Result() {
    }

    public Result(Integer code,Object data) {
        this.data = data;
        this.code = code;
    }

    public Result(Integer code, Object data, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

```

```java
package com.chen.controller;

import com.chen.exception.BusinessException;
import com.chen.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvice {
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException ex){
        //记录日志
        //发送消息给运维
        //发送邮件给开发人员,ex对象发送给开发人员
        return new Result(ex.getCode(),null,ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException ex){
        return new Result(ex.getCode(),null,ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result doOtherException(Exception ex){
        //记录日志
        //发送消息给运维
        //发送邮件给开发人员,ex对象发送给开发人员
        return new Result(Code.SYSTEM_UNKNOW_ERR,null,"系统繁忙，请稍后再试！");
    }
}

```

编写异常类

```java
package com.chen.exception;
//自定义异常处理器，用于封装异常信息，对异常进行分类
public class BusinessException extends RuntimeException{
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}

```

```java
package com.chen.exception;
//自定义异常处理器，用于封装异常信息，对异常进行分类
public class SystemException extends RuntimeException{
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public SystemException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}

```

```java
package com.chen.service.impl;

import com.chen.controller.Code;
import com.chen.dao.BookDao;
import com.chen.domain.Book;
import com.chen.exception.BusinessException;
import com.chen.exception.SystemException;
import com.chen.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    public boolean save(Book book) {
        return bookDao.save(book) ;
    }

    public boolean update(Book book) {
        return bookDao.update(book);
    }

    public boolean delete(Integer id) {
        return bookDao.delete(id);
    }

    public Book getById(Integer id) {
        if(id == 1){
            throw new BusinessException(Code.BUSINESS_ERR,"请不要使用你的技术挑战我的耐性!");
        }
//        //将可能出现的异常进行包装，转换成自定义异常
//        try{
//            int i = 1/0;
//        }catch (Exception e){
//            throw new SystemException(Code.SYSTEM_TIMEOUT_ERR,"服务器访问超时，请重试!",e);
//        }
        return bookDao.getById(id);
    }

    public List<Book> getAll() {
        return bookDao.getAll();
    }
}

```

前端页面

```html
<!DOCTYPE html>

<html>

<head>

    <!-- 页面meta -->

    <meta charset="utf-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>SpringMVC案例</title>

    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">

    <!-- 引入样式 -->

    <link rel="stylesheet" href="../plugins/elementui/index.css">

    <link rel="stylesheet" href="../plugins/font-awesome/css/font-awesome.min.css">

    <link rel="stylesheet" href="../css/style.css">

</head>

<body class="hold-transition">

<div id="app">

    <div class="content-header">

        <h1>图书管理</h1>

    </div>

    <div class="app-container">

        <div class="box">

            <div class="filter-container">

                <el-input placeholder="图书名称" v-model="pagination.queryString" style="width: 200px;" class="filter-item"></el-input>

                <el-button @click="getAll()" class="dalfBut">查询</el-button>

                <el-button type="primary" class="butT" @click="handleCreate()">新建</el-button>

            </div>

            <el-table size="small" current-row-key="id" :data="dataList" stripe highlight-current-row>

                <el-table-column type="index" align="center" label="序号"></el-table-column>

                <el-table-column prop="type" label="图书类别" align="center"></el-table-column>

                <el-table-column prop="name" label="图书名称" align="center"></el-table-column>

                <el-table-column prop="description" label="描述" align="center"></el-table-column>

                <el-table-column label="操作" align="center">

                    <template slot-scope="scope">

                        <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>

                        <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>

                    </template>

                </el-table-column>

            </el-table>

            <!-- 新增标签弹层 -->

            <div class="add-form">

                <el-dialog title="新增图书" :visible.sync="dialogFormVisible">

                    <el-form ref="dataAddForm" :model="formData" :rules="rules" label-position="right" label-width="100px">

                        <el-row>

                            <el-col :span="12">

                                <el-form-item label="图书类别" prop="type">

                                    <el-input v-model="formData.type"/>

                                </el-form-item>

                            </el-col>

                            <el-col :span="12">

                                <el-form-item label="图书名称" prop="name">

                                    <el-input v-model="formData.name"/>

                                </el-form-item>

                            </el-col>

                        </el-row>


                        <el-row>

                            <el-col :span="24">

                                <el-form-item label="描述">

                                    <el-input v-model="formData.description" type="textarea"></el-input>

                                </el-form-item>

                            </el-col>

                        </el-row>

                    </el-form>

                    <div slot="footer" class="dialog-footer">

                        <el-button @click="dialogFormVisible = false">取消</el-button>

                        <el-button type="primary" @click="handleAdd()">确定</el-button>

                    </div>

                </el-dialog>

            </div>

            <!-- 编辑标签弹层 -->

            <div class="add-form">

                <el-dialog title="编辑检查项" :visible.sync="dialogFormVisible4Edit">

                    <el-form ref="dataEditForm" :model="formData" :rules="rules" label-position="right" label-width="100px">

                        <el-row>

                            <el-col :span="12">

                                <el-form-item label="图书类别" prop="type">

                                    <el-input v-model="formData.type"/>

                                </el-form-item>

                            </el-col>

                            <el-col :span="12">

                                <el-form-item label="图书名称" prop="name">

                                    <el-input v-model="formData.name"/>

                                </el-form-item>

                            </el-col>

                        </el-row>

                        <el-row>

                            <el-col :span="24">

                                <el-form-item label="描述">

                                    <el-input v-model="formData.description" type="textarea"></el-input>

                                </el-form-item>

                            </el-col>

                        </el-row>

                    </el-form>

                    <div slot="footer" class="dialog-footer">

                        <el-button @click="dialogFormVisible4Edit = false">取消</el-button>

                        <el-button type="primary" @click="handleEdit()">确定</el-button>

                    </div>

                </el-dialog> 

            </div>

        </div>

    </div>

</div>

</body>

<!-- 引入组件库 -->

<script src="../js/vue.js"></script>

<script src="../plugins/elementui/index.js"></script>

<script type="text/javascript" src="../js/jquery.min.js"></script>

<script src="../js/axios-0.18.0.js"></script>

<script>
    var vue = new Vue({

        el: '#app',
        data:{
            pagination: {},
            dataList: [],//当前页要展示的列表数据
            formData: {},//表单数据
            dialogFormVisible: false,//控制表单是否可见
            dialogFormVisible4Edit:false,//编辑表单是否可见
            rules: {//校验规则
                type: [{ required: true, message: '图书类别为必填项', trigger: 'blur' }],
                name: [{ required: true, message: '图书名称为必填项', trigger: 'blur' }]
            }
        },

        //钩子函数，VUE对象初始化完成后自动执行
        created() {
            this.getAll();
        },

        methods: {
            //列表
            getAll() {
                //发送ajax请求
                axios.get("/books").then((res)=>{
                    this.dataList = res.data.data;
                });
            },

            //弹出添加窗口
            handleCreate() {
                this.dialogFormVisible = true;
                this.resetForm();
            },

            //重置表单
            resetForm() {
                this.formData = {};
            },

            //添加
            handleAdd () {
                //发送ajax请求
                axios.post("/books",this.formData).then((res)=>{
                    console.log(res.data);
                    //如果操作成功，关闭弹层，显示数据
                    if(res.data.code == 20011){
                        this.dialogFormVisible = false;
                        this.$message.success("添加成功");
                    }else if(res.data.code == 20010){
                        this.$message.error("添加失败");
                    }else{
                        this.$message.error(res.data.msg);
                    }
                }).finally(()=>{
                    this.getAll();
                });
            },

            //弹出编辑窗口
            handleUpdate(row) {
                // console.log(row);   //row.id 查询条件
                //查询数据，根据id查询
                axios.get("/books/"+row.id).then((res)=>{
                    // console.log(res.data.data);
                    if(res.data.code == 20041){
                        //展示弹层，加载数据
                        this.formData = res.data.data;
                        this.dialogFormVisible4Edit = true;
                    }else{
                        this.$message.error(res.data.msg);
                    }
                });
            },

            //编辑
            handleEdit() {
                //发送ajax请求
                axios.put("/books",this.formData).then((res)=>{
                    //如果操作成功，关闭弹层，显示数据
                    if(res.data.code == 20031){
                        this.dialogFormVisible4Edit = false;
                        this.$message.success("修改成功");
                    }else if(res.data.code == 20030){
                        this.$message.error("修改失败");
                    }else{
                        this.$message.error(res.data.msg);
                    }
                }).finally(()=>{
                    this.getAll();
                });
            },

            // 删除
            handleDelete(row) {
                //1.弹出提示框
                this.$confirm("此操作永久删除当前数据，是否继续？","提示",{
                    type:'info'
                }).then(()=>{
                    //2.做删除业务
                    axios.delete("/books/"+row.id).then((res)=>{
                        if(res.data.code == 20021){
                            this.$message.success("删除成功");
                        }else{
                            this.$message.error("删除失败");
                        }
                    }).finally(()=>{
                        this.getAll();
                    });
                }).catch(()=>{
                    //3.取消删除
                    this.$message.info("取消删除操作");
                });
            }
        }
    })

</script>

</html>
```

今日总结

```xml
需要再多做几个ssm的案例加深对Spring的ioc和aop思想的认识，前面了解的vue+element-ui也要了解。
```

拦截器案例

```java
package com.itheima.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class ServletContainersInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    //乱码处理
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        return new Filter[]{filter};
    }
}

```

```java
package com.itheima.config;

import com.itheima.controller.interceptor.ProjectInterceptor;
import com.itheima.controller.interceptor.ProjectInterceptor2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({"com.itheima.controller"})
@EnableWebMvc
//实现WebMvcConfigurer接口可以简化开发，但具有一定的侵入性
public class SpringMvcConfig implements WebMvcConfigurer {
    @Autowired
    private ProjectInterceptor projectInterceptor;
    @Autowired
    private ProjectInterceptor2 projectInterceptor2;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置多拦截器
        registry.addInterceptor(projectInterceptor).addPathPatterns("/books","/books/*");
        registry.addInterceptor(projectInterceptor2).addPathPatterns("/books","/books/*");
    }
}
```

```java
package com.itheima.config;

import com.itheima.controller.interceptor.ProjectInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class SpringMvcSupport extends WebMvcConfigurationSupport {
    @Autowired
    private ProjectInterceptor projectInterceptor;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //配置拦截器
        registry.addInterceptor(projectInterceptor).addPathPatterns("/books","/books/*");
    }
}

```

```java
package com.itheima.controller.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
//定义拦截器类，实现HandlerInterceptor接口
//注意当前类必须受Spring容器控制
public class ProjectInterceptor implements HandlerInterceptor {
    @Override
    //原始方法调用前执行的内容
    //返回值类型可以拦截控制的执行，true放行，false终止
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String contentType = request.getHeader("Content-Type");
        HandlerMethod hm = (HandlerMethod)handler;
        System.out.println("preHandle..."+contentType);
        return true;
    }

    @Override
    //原始方法调用后执行的内容
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle...");
    }

    @Override
    //原始方法调用完成后执行的内容
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion...");
    }
}

```

```java
package com.itheima.controller.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ProjectInterceptor2 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String contentType = request.getHeader("Content-Type");
//        HandlerMethod hm = (HandlerMethod)handler;
        System.out.println("preHandle...222");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle...222");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion...222");
    }
}

```

```java
package com.itheima.controller;

import com.itheima.domain.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @PostMapping
    public String save(@RequestBody Book book){
        System.out.println("book save..." + book);
        return "{'module':'book save'}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        System.out.println("book delete..." + id);
        return "{'module':'book delete'}";
    }

    @PutMapping
    public String update(@RequestBody Book book){
        System.out.println("book update..."+book);
        return "{'module':'book update'}";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id){
        System.out.println("book getById..."+id);
        return "{'module':'book getById'}";
    }

    @GetMapping
    public String getAll(){
        System.out.println("book getAll...");
        return "{'module':'book getAll'}";
    }
}

```

