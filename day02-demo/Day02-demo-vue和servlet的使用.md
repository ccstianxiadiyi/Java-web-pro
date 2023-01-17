# Day02-demo-vue和servlet的使用

### 总结

```xml
今天做了增删改查，因为对vue很不熟悉，所以耽误了很多时间，前面的查询和增加都是照着黑马视频里面做的，后面的编辑和删除都是我自己边搜边做，我感觉搜的他们都没有做到数据回显，还有数据回显是有bug的需要处理的，就是你编辑之后在添加你会发现回显的数据在增加的对话框里面出现了，所以要做打开对话框后对对话框清空数据的操作，我这做的还是有bug，就是那个状态的选择，搞不明白这个傻逼状态到底是怎么回事，默认值竟然是空值不是未选，真他妈傻逼，我看黑马的后面课程还要对servlet的代码进行优化，我正是服了，嘎嘎累啊


今天的收获是：

```

![image-20230117171306316](C:\Users\ccs\AppData\Roaming\Typora\typora-user-images\image-20230117171306316.png)

做web项目一般都有这几个目录，mapper层也就是dao层，pojo就是实体类，service就是mybatis的代码，为了方便以后调用mybatis的代码util层呢就是为了mybatis那个sqlsessionfactory的那三行代码方便简单的使用，web层就是放servlet的代码，

今天就是熟悉了一下servlet的结构

更多的收获实在前端方面，axios和vue确实收获挺多，axios的ajax做的是真的牛逼比jquery的还要方便

我就把前端的代码放在md里面吧，太累了不想写了草

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

        <el-button type="danger" plain>批量删除</el-button>
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
            :total="400">
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
            deleteOneData(row) {
                var _this=this;
                axios({
                    method: "get",
                    url: "http://localhost:8080/brand-case/deleteServlet" + "?id=" + row.id,
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
                    url: "http://localhost:8080/brand-case/editServlet",
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
                    url: "http://localhost:8080/brand-case/selectByIdServlet" + "?id=" + row.id
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
                var _this = this;
                axios({
                    method: "get",
                    url: "http://localhost:8080/brand-case/selectAllServlet"
                }).then(function (res) {
                    _this.tableData = res.data;
                })
            },
            // 查询方法
            onSubmit() {
                console.log(this.brand);
            },
            // 添加数据
            addBrand() {
                _this = this;
                axios({
                    method: "post",
                    url: "http://localhost:8080/brand-case/addServlet",
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
                console.log(`当前页: ${val}`);
            }

        },
        data() {
            return {
                //11
                dialogVisible1: false,
                row: {},

                // 当前页码
                currentPage: 4,
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

