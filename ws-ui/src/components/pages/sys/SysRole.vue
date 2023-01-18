<template>
    <el-card class="box-card">
        <div class="mod-role">
            <el-form :inline="true" :model="dataForm" class="demo-form-inline">
                <el-form-item>
                    <el-input placeholder="角色名称" v-model="dataForm.roleName" clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="none" @click="getDataList()">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="openDialog()">新增</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="dataList" border style="width: 100%">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="roleId" label="ID">
                </el-table-column>
                <el-table-column prop="roleName" label="角色名称">
                </el-table-column>
                <el-table-column prop="remark" label="备注">
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary"
                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle"
                :current-page="pageIndex" :page-sizes="[5, 10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                layout="total, sizes, prev, pager, next, jumper" style="margin-top:30px;">
            </el-pagination>
            <el-dialog :title="dataDialogForm.roleId === 0 ? '新增角色' : '更新角色'" width="35%"
                :visible.sync="dialogFormVisible" @close="closeDialog()">
                <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm">
                    <el-form-item label="角色名称" label-width="120px" prop="roleName">
                        <el-input v-model="dataDialogForm.roleName" placeholder="角色名称" style="width: 250px;"></el-input>
                    </el-form-item>
                    <el-form-item label="描述信息" label-width="120px" prop="remark">
                        <el-input type="textarea" v-model="dataDialogForm.remark" style="width: 250px;"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="cancel()">取 消</el-button>
                    <el-button type="primary" @click="handleSubmitFormData('ruleForm')">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </el-card>
</template>
<script>
export default {
    name: 'sysRole',
    data() {
        // 自定义校验规则
        var checkRoleName = (rule, value, callback) => {
            if (this.dataDialogForm.roleId !== 0) {
                //校验是否为空
                if (value === '') {
                    callback(new Error('请输入角色名称'));
                }
                // 说明是更新操作
                callback();
            }
            else if (value === '') {
                callback(new Error('请输入角色名称'));
            } else {
                // 调用后端接口 检查 角色名称是否存在
                this.$http.get('/sys/sysRole/checkRoleName?roleName=' + value).then((res) => {
                    if (res.data.data === 'fail') {
                        // 角色名不存在。可以使用
                        callback();
                    }
                    else {
                        callback(new Error('角色名称重复'));
                    }
                });
            }
        };
        return {
            dataForm: {
                roleName: '',
            },
            dataDialogForm: {
                roleId: 0,
                roleName: '',
                remark: '',
            },
            dataList: [],
            pageIndex: 1,
            pageSize: 5,
            totalPage: 0,
            // 防止“查询”多次提交
            dataListLoading: false,
            dialogFormVisible: false,
            //防止“新增”界面重复提交
            dialogFormSubmitVisible: false,
            //校验规则
            rules: {
                roleName: [
                    { validator: checkRoleName, trigger: 'blur' }
                ],
                remark: [
                    { required: true, message: '请输入描述信息', trigger: 'blur' }
                ]
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
                    roleName: this.dataForm.roleName
                }
            }
            //获取数据
            this.$http.get('/sys/sysRole/list', params).then((res) => {
                this.dataList = res.data.data.list;
                this.totalPage = res.data.data.totalCount;
                this.dataListLoading = false;
            });
        },
        handleEdit(index, item) {
            // 打开更新的窗口
            this.dialogFormVisible = true;
            //绑定需要更新的数据
            this.dataDialogForm.roleId = item.roleId;
            this.dataDialogForm.roleName = item.roleName;
            this.dataDialogForm.remark = item.remark;

        },
        handleDelete(index, item) {
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
                this.$http.get('/sys/sysRole/deleteRole?roleId=' + item.roleId).then((res) => {
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
        openDialog() {
            // 打开窗口
            this.dialogFormVisible = true;

            this.dataDialogForm.roleId = 0;
            this.dataDialogForm.roleName = '';
            this.dataDialogForm.remark = '';

        },
        closeDialog() {
            // 点击关闭时清空form表格的数据
            this.dataDialogForm = {
                roleId: 0,
                roleName: '',
                remark: '',
            };
        },
        // 点击取消时关闭dialog并且清空form表格的数据
        cancel() {
            // 关闭dialog
            this.dialogFormVisible = false
            // 重置form表单
            this.dataDialogForm = {
                roleId: 0,
                roleName: '',
                remark: '',
            };

        },
        handleSubmitFormData(formName) {
            this.updateRole(formName);
        },
        updateRole(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.dialogFormSubmitVisible) {
                        return
                    }
                    this.dialogFormSubmitVisible = true;
                    this.$http.post('/sys/sysRole/save', this.dataDialogForm).then((res) => {
                        // 关闭窗口
                        this.dialogFormVisible = false;
                        // 清空添加数据的表单
                        this.dataDialogForm = {
                            roleId: 0,
                            roleName: '',
                            remark: '',
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
        }
    },
    mounted() {
        this.getDataList();
    }
}
</script>