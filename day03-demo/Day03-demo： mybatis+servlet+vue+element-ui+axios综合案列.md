## Day03-demo： mybatis+servlet+vue+element-ui+axios综合案列

### 总结:

```xml
首先对这个案列介绍一下吧，
先创建一个web的maven项目，在pom.xml里面导入里面要使用的依赖坐标，然后就是mybatis-config.xml的配置文件，在java目录下建立com.itheima，然后分别建立mapper(dao),pojo,service.impl,util,web这5层，dao层就是mybatis那一套业务，pojo就是放实体类的地方，service层就是调用mybatis的代码返回数据
，util就是为了偷懒，简化mybatis的那个sqlsessionFactory的代码，web层就是放servlet的地方。
```

### 实体类：

Brand

```java
package com.itheima.pojo;

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
    //逻辑视图
    public String getStatusStr(){
        if (status == null){
            return "未知";
        }
        return status == 0 ? "禁用":"启用";
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

### PageBean(做分页用的)

```java
package com.itheima.pojo;

import java.util.List;

public class PageBean<T> {
    //总记录数
    private int totalCount;
    //当前页数据
    private List<T> rows;

    public PageBean() {
    }

    public PageBean(int totalCount, List<T> rows) {
        this.totalCount = totalCount;
        this.rows = rows;
    }

    /**
     * 获取
     * @return totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 设置
     * @param totalCount
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 获取
     * @return rows
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * 设置
     * @param rows
     */
    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public String toString() {
        return "PageBean{totalCount = " + totalCount + ", rows = " + rows + "}";
    }
}

```

### mapper层

```java
package com.itheima.mapper;

import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandMapper {
    //查询所有数据
    List<Brand> selectAll();
    //增加数据
    void add(Brand brand);
    //查询一个数据，为了编辑操作的数据回显
    Brand selectById(Brand brand);
    //更新操作
    void edit(Brand brand);
    //单个删除
    void delete(Brand brand);
    //批量删除
    void deleteMore(@Param("ids") int[] ids);
    //分页
    List<Brand> selectByPage(@Param("begin")int begin,@Param("size")int size);
    //查询总条数
    int selectTotalCount();
    //多条件查询
    List<Brand> selectByConditions(@Param("brand") Brand brand, @Param("size") int size, @Param("begin") int begin);
    //查询有条件的总条数
    int selectionTotalCondition(Brand brand);
}

```

### service(采用接口为了方便管理)

```java
package com.itheima.service;

import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandService {
    List<Brand> selectAll();
    void add(Brand brand);
    Brand selectById(Brand brand);
    void edit(Brand brand);
    void delete(Brand brand);
    void deleteMore(int[] ids);
    PageBean<Brand> selectByPage(int currentPage,int pageSize);
    PageBean<Brand> selectByConditions(int size, int begin,Brand brand);

}

```

```java
package com.itheima.service.impl;
//接口的实现
import com.itheima.mapper.BrandMapper;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.BrandService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class BrandServiceimpl implements BrandService {
    @Override
    public List<Brand> selectAll() {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        List<Brand> brands = mapper.selectAll();
        sqlSession.close();
        return brands;
    }

    @Override
    public void add(Brand brand) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        mapper.add(brand);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public Brand selectById(Brand brand) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        Brand brand1 = mapper.selectById(brand);

        sqlSession.close();
        return brand1;
    }

    @Override
    public void edit(Brand brand) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        mapper.edit(brand);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delete(Brand brand) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        mapper.delete(brand);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteMore(int[] ids) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        mapper.deleteMore(ids);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public PageBean<Brand> selectByPage(int currentPage, int pageSize) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        List<Brand> brands = mapper.selectByPage((currentPage-1)*pageSize, pageSize);
        int i = mapper.selectTotalCount();
        PageBean<Brand> pageBean=new PageBean<>();
        pageBean.setRows(brands);
        pageBean.setTotalCount(i);
        return pageBean;

    }

    @Override
    public PageBean<Brand> selectByConditions(int size, int begin,Brand brand) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        if(brand.getBrandName()!=null&&brand.getBrandName().length()>0){
            brand.setBrandName("%"+brand.getBrandName()+"%");
        }
        if(brand.getCompanyName()!=null&&brand.getCompanyName().length()>0){
            brand.setCompanyName("%"+brand.getCompanyName()+"%");
        }
        List<Brand> brands = mapper.selectByConditions(brand,size,begin);
        int totalCount=mapper.selectionTotalCondition(brand);
        PageBean<Brand> pageBean=new PageBean<>();
        pageBean.setRows(brands);
        pageBean.setTotalCount(totalCount);
        sqlSession.close();
        return pageBean;
    }


}

```

### util层

```java
package com.itheima.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryUtils {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        //静态代码块会随着类的加载而自动执行，且只执行一次
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static SqlSessionFactory getSqlSessionFactory(){
        return sqlSessionFactory;
    }
}

```

### servlet层代码优化,用到了反射的知识断言那种的

```java
package com.itheima.web.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        int index=uri.lastIndexOf('/');
        String methodName=uri.substring(index+1);
        Class<? extends BaseServlet> cls=this.getClass();
        try{
            Method method= cls.getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(this,req,resp);
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}


-------------------------------------------------------
    package com.itheima.web.servlet;

import com.alibaba.fastjson.JSON;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.BrandService;
import com.itheima.service.impl.BrandServiceimpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/brand/*")
public class BrandServlet extends BaseServlet {
    private BrandService brandService=new BrandServiceimpl();

    public void selectAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Brand> brands = brandService.selectAll();
        String s = JSON.toJSONString(brands);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(s);
    }
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br=request.getReader();
        String s = br.readLine();
        Brand brand= JSON.parseObject(s, Brand.class);
        brandService.add(brand);
        response.getWriter().write("success");
    }
    public void selectByIdServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Brand brand = new Brand();
        brand.setId(Integer.valueOf(request.getParameter("id")));
        Brand brand1 = brandService.selectById(brand);
        String s = JSON.toJSONString(brand1);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(s);
    }
    public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br=request.getReader();
        String s = br.readLine();
        Brand brand= JSON.parseObject(s, Brand.class);
        brandService.edit(brand);
        response.getWriter().write("success");
    }
    public void deleteOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Brand brand = new Brand();
        brand.setId(Integer.valueOf(request.getParameter("id")));
        brandService.delete(brand);
        PrintWriter writer = response.getWriter();
        writer.write("success");

    }
    public void deleteMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        BufferedReader reader = request.getReader();
        String s = reader.readLine();
        int[] ints = JSON.parseObject(s, int[].class);
        brandService.deleteMore(ints);
        response.getWriter().write("success");
    }
    public void selectByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageBean<Brand> pageBean = brandService.selectByPage(Integer.valueOf(request.getParameter("currentPage")), Integer.valueOf(request.getParameter("pageSize")));
        String s = JSON.toJSONString(pageBean);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(s);
    }
    public void selectByConditions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageSize = request.getParameter("pageSize");
        String currentPage = request.getParameter("currentPage");
        BufferedReader reader = request.getReader();
        String s1 = reader.readLine();
        Brand brand = JSON.parseObject(s1, Brand.class);
        PageBean<Brand> pageBean = brandService.selectByConditions(Integer.parseInt(pageSize), Integer.parseInt(currentPage), brand);
        String s = JSON.toJSONString(pageBean);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(s);
    }



}

```

### 前端

### element-ui+vue.js+axios(ajax的简化)

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        .el-table .warning-row {
            background: oldlace;
        }

        .el-table .success-row {
            background: #f0f9eb;
        }
    </style>

</head>
<body>
<div id="app">

    <!--搜索表单-->
    <el-form :inline="true" :model="brand" class="demo-form-inline">

        <el-form-item label="当前状态">
            <el-select v-model="brand.status" placeholder="当前状态">
                <el-option label="启用" value="1"></el-option>
                <el-option label="禁用" value="0"></el-option>
            </el-select>
        </el-form-item>

        <el-form-item label="企业名称">
            <el-input v-model="brand.companyName" placeholder="企业名称"></el-input>
        </el-form-item>

        <el-form-item label="品牌名称">
            <el-input v-model="brand.brandName" placeholder="品牌名称"></el-input>
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
    </el-form>

    <!--按钮-->

    <el-row>

        <el-button type="danger" plain @click="deleteMore">批量删除</el-button>
        <el-button type="primary" plain @click="addTanChu">新增</el-button>

    </el-row>
    <!--添加数据对话框表单-->
    <el-dialog

            title="新增品牌"
            :visible.sync="dialogVisible"
            width="30%"
    >

        <el-form ref="form" :model="brand" label-width="80px">
            <el-form-item label="品牌名称">
                <el-input v-model="brand.brandName"></el-input>
            </el-form-item>

            <el-form-item label="企业名称">
                <el-input v-model="brand.companyName"></el-input>
            </el-form-item>

            <el-form-item label="排序">
                <el-input v-model="brand.ordered"></el-input>
            </el-form-item>

            <el-form-item label="备注">
                <el-input type="textarea" v-model="brand.description"></el-input>
            </el-form-item>

            <el-form-item label="状态">
                <el-switch v-model="brand.status"
                           active-value="1"
                           inactive-value="0"
                ></el-switch>
            </el-form-item>


            <el-form-item>
                <el-button type="primary" @click="addBrand">提交</el-button>
                <el-button @click="dialogVisible = false">取消</el-button>
            </el-form-item>
        </el-form>

    </el-dialog>
    <!--    编辑品牌对话框-->
    <el-dialog

            title="编辑品牌"
            :visible.sync="dialogVisible1"
            width="30%"
    >

        <el-form ref="form" :model="brand" label-width="80px">
            <el-form-item label="品牌名称">
                <el-input v-model="brand.brandName"></el-input>
            </el-form-item>

            <el-form-item label="企业名称">
                <el-input v-model="brand.companyName"></el-input>
            </el-form-item>

            <el-form-item label="排序">
                <el-input v-model="brand.ordered"></el-input>
            </el-form-item>

            <el-form-item label="备注">
                <el-input type="textarea" v-model="brand.description"></el-input>
            </el-form-item>

            <el-form-item label="状态">
                <el-switch v-model="brand.status"
                           active-value="1"
                           inactive-value="0"
                ></el-switch>
            </el-form-item>


            <el-form-item>
                <el-button type="primary" @click="updateBrand">提交</el-button>
                <el-button @click="dialogVisible = false">取消</el-button>
            </el-form-item>
        </el-form>

    </el-dialog>

    <!--表格-->
    <template>
        <el-table
                :data="tableData"
                style="width: 100%"
                :row-class-name="tableRowClassName"
                @selection-change="handleSelectionChange"
        >
            <el-table-column
                    type="selection"
                    width="55">
            </el-table-column>
            <el-table-column
                    type="index"
                    width="50">
            </el-table-column>

            <el-table-column
                    prop="brandName"
                    label="品牌名称"
                    align="center"
            >
            </el-table-column>
            <el-table-column
                    prop="companyName"
                    label="企业名称"
                    align="center"
            >
            </el-table-column>
            <el-table-column
                    prop="ordered"
                    align="center"
                    label="排序">
            </el-table-column>
            <el-table-column
                    prop="status"
                    align="center"
                    label="当前状态">
            </el-table-column>

            <el-table-column
                    align="center"
                    label="操作">

                <el-row slot-scope="scope">
                    <el-button type="primary" @click="getOneData(scope.row)">修改</el-button>
                    <el-button type="danger" @click="deleteOneData(scope.row)">删除</el-button>
                </el-row>

            </el-table-column>

        </el-table>
    </template>

    <!--分页工具条-->
    <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="5"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalCount">
    </el-pagination>

</div>


<script src="js/vue.js"></script>
<script src="element-ui/lib/index.js"></script>
<script src="js/axios-0.18.0.js"></script>
<link rel="stylesheet" href="element-ui/lib/theme-chalk/index.css">

<script>
    new Vue({
        el: "#app",
        mounted() {
            this.selectAll();
        },
        methods: {
            deleteMore(){
                var _this=this;
              for(let i=0;i<this.multipleSelection.length;i++){
                  let selectionElement=this.multipleSelection[i];
                  this.multipleSelectIds[i]=selectionElement.id;
              }
              axios({
                  method:"post",
                  url:"http://localhost:8080/brand-case/brand/deleteMore",
                  data:_this.multipleSelectIds
              }).then(function (res) {
                  if(res.data="success"){
                      _this.selectAll();
                      _this.$message({
                          message: '删除成功',
                          type: 'success'
                      });
                  }
              })
            },
            deleteOneData(row) {
                var _this=this;
                axios({
                    method: "get",
                    url: "http://localhost:8080/brand-case/brand/deleteOne" + "?id=" + row.id,
                }).then(function (res) {
                    if (res.data == "success"){
                        _this.dialogVisible1 = false;
                        _this.selectAll();
                        _this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                    }
                        })
            },
            updateBrand() {
                _this = this;
                axios({
                    method: "post",
                    url: "http://localhost:8080/brand-case/brand/edit",
                    data: _this.brand
                }).then(function (resp) {
                    if (resp.data == "success") {
                        _this.dialogVisible1 = false;
                        _this.selectAll();
                        _this.$message({
                            message: '修改成功',
                            type: 'success'
                        });
                    }
                })
            },
            addTanChu() {
                this.dialogVisible = true;
                this.brand = {
                    status: '',
                    brandName: '',
                    companyName: '',
                    id: "",
                    ordered: "",
                    description: ""
                }


            },
            getOneData(row) {
                this.dialogVisible1 = true;
                var _this = this;
                axios({
                    method: "get",
                    url: "http://localhost:8080/brand-case/brand/selectByIdServlet" + "?id=" + row.id
                }).then(function (res) {
                    _this.brand = res.data;
                    _this.brand.status = _this.brand.status.toString();
                })

            },

            tableRowClassName({row, rowIndex}) {
                if (rowIndex === 1) {
                    return 'warning-row';
                } else if (rowIndex === 3) {
                    return 'success-row';
                }
                return '';
            },

            // 复选框选中后执行的方法
            handleSelectionChange(val) {
                this.multipleSelection = val;

                console.log(this.multipleSelection)
            },
            selectAll() {
                // var _this = this;
                // axios({
                //     method: "get",
                //     url: "http://localhost:8080/brand-case/brand/selectAll"
                // }).then(function (res) {
                //     _this.tableData = res.data;
                // })
                var _this = this;
                axios({
                    method:"post",
                    url: "http://localhost:8080/brand-case/brand/selectByConditions?currentPage="+_this.currentPage+"&pageSize="+_this.pageSize,
                    data:_this.brand
                }).then(function (res) {
                    _this.tableData = res.data.rows;
                    _this.totalCount=res.data.totalCount;
                })
            },
            // 查询方法
            onSubmit() {
                this.selectAll();
            },
            // 添加数据
            addBrand() {
                _this = this;
                axios({
                    method: "post",
                    url: "http://localhost:8080/brand-case/brand/add",
                    data: _this.brand
                }).then(function (resp) {
                    if (resp.data == "success") {
                        _this.dialogVisible = false;
                        _this.selectAll();
                        _this.$message({
                            message: '恭喜你，添加成功',
                            type: 'success'
                        });
                    }
                })
            },

            //分页
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage=val;
                this.selectAll();
            }

        },
        data() {
            return {
                //11
                pageSize:10,
                totalCount:100,
                dialogVisible1: false,
                row: {},

                // 当前页码
                currentPage: 1,
                // 添加数据对话框是否展示的标记
                dialogVisible: false,

                // 品牌模型数据
                brand: {
                    status: '',
                    brandName: '',
                    companyName: '',
                    id: "",
                    ordered: "",
                    description: ""
                },
                // 复选框选中数据集合
                multipleSelection: [],
                multipleSelectIds: [],
                // 表格数据
                tableData: [{
                    brandName: '华为',
                    companyName: '华为科技有限公司',
                    ordered: '100',
                    status: "1"
                }, {
                    brandName: '华为',
                    companyName: '华为科技有限公司',
                    ordered: '100',
                    status: "1"
                }, {
                    brandName: '华为',
                    companyName: '华为科技有限公司',
                    ordered: '100',
                    status: "1"
                }, {
                    brandName: '华为',
                    companyName: '华为科技有限公司',
                    ordered: '100',
                    status: "1"
                }]
            }
        }
    })

</script>

</body>
</html>
```



### 注意：

```html
				 _this = this;
                axios({
                    method: "post",
                    url: "http://localhost:8080/brand-case/brand/add",
                    data: _this.brand
                }).then(function (resp) {
                    if (resp.data == "success") {
                        _this.dialogVisible = false;
                        _this.selectAll();
                        _this.$message({
                            message: '恭喜你，添加成功',
                            type: 'success'
                        });
                    }
                })
```

可以简化成

```html
_this = this;
                axios({
                    method: "post",
                    url: "http://localhost:8080/brand-case/brand/add",
                    data: _this.brand
                }).then(resp=> {
                    if (resp.data == "success") {
                        this.dialogVisible = false;
                        this.selectAll();
                        this.$message({
                            message: '恭喜你，添加成功',
                            type: 'success'
                        });
                    }
                })
用了=>就像lambda表达式一样，this的作用域会提升到axios的回调函数中去，
```

