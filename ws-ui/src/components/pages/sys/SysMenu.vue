<template>
    <el-card class="box-card">
        <div class="mod-role">
            <el-form :inline="true" :model="dataForm" class="demo-form-inline">
                <el-form-item>
                    <el-input placeholder="菜单名" v-model="dataForm.label" clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="none" @click="getDataList()">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="openDialog()">新增</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="dataList" border style="width: 100%" row-key="menuId"
                :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="menuId" label="ID">
                </el-table-column>
                <el-table-column prop="name" label="菜单名称">
                </el-table-column>
                <el-table-column prop="url" label="路由地址">
                </el-table-column>
                <el-table-column prop="icon" label="图标">
                </el-table-column>
                <el-table-column prop="parentId" label="父菜单编号">
                </el-table-column>
                <el-table-column prop="orderNum" label="序号">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary"
                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger"
                            @click="handleDelete(scope.$index, scope.row)" 
                            v-if="scope.row.canBeDeleted">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle"
                :current-page="pageIndex" :page-sizes="[5, 10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                layout="total, sizes, prev, pager, next, jumper" style="margin-top:30px;">
            </el-pagination>
            <el-dialog :title="dataDialogForm.menuId === 0 ? '新增菜单' : '更新菜单'" width="35%"
                :visible.sync="dialogFormVisible" @close="closeDialog()">
                <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm">
                    <el-form-item label="菜单名" label-width="120px" prop="name">
                        <el-input v-model="dataDialogForm.name" placeholder="菜单名称" style="width: 250px;"></el-input>
                    </el-form-item>
                    <el-form-item label="路由地址" label-width="120px" prop="url">
                        <el-input v-model="dataDialogForm.url" placeholder="路由地址" style="width: 250px;"></el-input>
                    </el-form-item>
                    <el-form-item label="图标" label-width="120px" prop="icon">
                        <el-input v-model="dataDialogForm.icon" placeholder="图标名称" style="width: 250px;"></el-input>
                    </el-form-item>
                    <el-form-item label="序号" label-width="120px" prop="orderNum">
                        <el-input v-model="dataDialogForm.orderNum" placeholder="序号" type="number"
                            style="width: 250px;"></el-input>
                    </el-form-item>
                    <el-form-item label="父菜单" label-width="120px" prop="parentId">
                        <el-select v-model="dataDialogForm.parentId" placeholder="请选择-不选择就为父菜单" clearable>
                            <el-option v-for="item in options" :key="item.menuId" :label="item.name"
                                :value="item.menuId"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="handleSubmitFormData('ruleForm')">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </el-card>
</template>
<script>
export default {
    name: 'sysMenu',
    data() {
        return {
            dataForm: {
                name: "",
            },
            dataList: [],
            pageIndex: 1,
            pageSize: 5,
            totalPage: 0,
            // 防止“查询”多次提交
            dataListLoading: false,
            dataDialogForm: {
                menuId: 0,
            },
            dialogFormVisible: false,
            dialogFormSubmitVisible: false,
            options: [],
            //校验规则
            rules: {
                name: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
                orderNum: [{ required: true, message: '请输入排序数组', trigger: 'blur' }],
            }
        };
    },
    methods: {
        //每页多少条
        sizeChangeHandle(val) {
            this.pageSize = val;
            this.pageIndex = 1;
            this.getDataList();
        },
        //当前页
        currentChangeHandle(val) {
            this.pageIndex = val;
            this.getDataList();
        },
        getDataList() {
            if (this.dataListLoading) {
                return;
            }
            this.dataListLoading = true;
            // 声明params 查询的参数
            const params = {
                params: {
                    pageIndex: this.pageIndex,
                    pageSize: this.pageSize,
                    name: this.dataForm.name,
                }
            };
            // console.log(this.dataForm.userName)
            //获取数据
            this.$http.get('/sys/sysMenu/list', params).then((res) => {
                this.dataList = res.data.data.list;
                this.totalPage = res.data.data.totalCount;
                this.dataListLoading = false;
            });
        },
        openDialog() {
            //获取所有的父菜单的数据
            this.$http.get('/sys/sysMenu/listParent').then((res) => {
                this.options = res.data.data;
                //打开窗口
                this.dialogFormVisible = true;
            });

        },
        handleSubmitFormData(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.dialogFormSubmitVisible) {
                        return;
                    }
                    this.dialogFormSubmitVisible = true;
                    // console.log(this.dataDialogForm)
                    this.$http.post('/sys/sysMenu/save', this.dataDialogForm).then((res) => {
                        // 关闭窗口
                        this.dialogFormVisible = false;
                        this.dataDialogForm = {
                            menuId: 0,
                        };
                        this.dialogFormSubmitVisible = false;
                        // 刷新数据
                        this.getDataList();
                    });
                } else {
                    // console.log('error submit!!');
                    return false;
                }
            });
        },
        handleEdit(index, row) {
            //更新数据 根据菜单编号查询出菜单信息
            // alert(row.userId)
            // this.dataDialogForm.userId=row.userId;
            //根据用户编号查询出用户信息。然后回写到弹出窗口中
            this.$http.get('/sys/sysMenu/queryMenuById?menuId=' + row.menuId).then((res) => {
                // console.log(res);
                //获取Id对应的数据
                var menu = res.data.data.menu;
                //获取所有的父菜单信息
                this.options = res.data.data.parents;
                this.dataDialogForm = {
                    menuId: menu.menuId,//弹出窗口绑定 菜单编号-->更新处理
                    name: menu.name,
                    url: menu.url,
                    icon: menu.icon,
                    orderNum: menu.orderNum,
                    parentId: menu.parentId
                }
                //打开窗口
                this.dialogFormVisible = true;
            });
        },
        handleDelete(index, row) {
            // 删除角色信息
            this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                if (this.dialogFormSubmitVisible) {
                    return
                }
                this.dialogFormSubmitVisible = true;
                this.$http.get('/sys/sysMenu/deleteMenu?menuId=' + row.menuId).then((res) => {
                    // console.log(res)
                    if (res.data.data === "0") {
                        //表示数据不能被删除、
                        this.$message({
                            type: 'warning',
                            message: '该条数据不允许删除!'
                        });
                    }
                    else {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                    }
                    this.dialogFormSubmitVisible = false;
                    //刷新数据
                    this.getDataList();
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        closeDialog() {
            this.dataDialogForm = {
                menuId: 0,
            };
        }
    },
    mounted() {
        this.getDataList();
    }
}
</script>