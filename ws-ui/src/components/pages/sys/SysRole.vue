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
            <el-dialog title="新增角色" width="35%" :visible.sync="dialogFormVisible">
                <el-form :model="dataDialogForm" :rules="rules">
                    <el-form-item label="角色名称" label-width="120px" prop="roleName">
                        <el-input v-model="dataDialogForm.roleName" placeholder="角色名称" style="width: 250px;"></el-input>
                    </el-form-item>
                    <el-form-item label="描述信息" label-width="120px" prop="remark">
                        <el-input type="textarea" v-model="dataDialogForm.remark" style="width: 250px;"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="handleSubmitFormData()">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </el-card>
</template>
<script>
export default {
    name: 'sysRole',
    data() {
        return {
            dataForm: {
                roleName: '',
            },
            dataDialogForm: {},
            dataList: [],
            pageIndex: 1,
            pageSize: 5,
            totalPage: 0,
            // 防止“查询”多次提交
            dataListLoading: false,
            dialogFormVisible: false,
            //防止“新增”界面重复提交
            dialogFormSubmitVisible: false,
            rules: {
                roleName: [
                    { required: true, message: '请输入角色名称', trigger: 'blur' }
                ],
                remark: [
                    { required: true, message: '请输入描述信息', trigger: 'change' }
                ]
            }
        };
    },
    methods: {
        sizeChangeHandle(val) {
            this.pageSize = val;
            this.pageIndex = 1;
            this.getDataList();
        },
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
            this.$http.get('/sys/sysRole/list', params).then((res) => {
                this.dataList = res.data.data.list;
                this.totalPage = res.data.data.totalCount;
                this.dataListLoading = false;
            });
        },
        handleEdit(index, item) { },
        handleDelete(index, item) { },
        openDialog() {
            // 打开窗口
            this.dialogFormVisible = true;
        },
        handleSubmitFormData() {
            this.addRole();
        },
        addRole() {
            if (this.dialogFormSubmitVisible) {
                return
            }
            this.dialogFormSubmitVisible = true;
            this.$http.post('/sys/sysRole/save', this.dataDialogForm).then((res) => {
                console.log(res);
                // 关闭窗口
                this.dialogFormVisible = false;
                // 刷新数据
                this.getDataList();
            })
        }
    },
    mounted() {
        this.getDataList();
    }
}
</script>