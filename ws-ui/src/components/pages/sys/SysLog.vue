<template>
    <el-card class="box-card">
        <div class="mod-role">
            <el-form :inline="true" :model="dataForm" class="demo-form-inline">
                <el-form-item>
                    <el-input placeholder="用户名/用户操作" v-model="dataForm.msg" clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="getDataList()">查询</el-button>
                </el-form-item>                
            </el-form>
            <el-table :data="dataList" border style="width: 100%">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="id" label="ID" width="50px">
                </el-table-column>
                <el-table-column prop="username" label="用户名">
                </el-table-column>
                <el-table-column prop="operation" label="用户操作">
                </el-table-column>
                <el-table-column prop="method" label="请求方法">
                </el-table-column>
                <el-table-column prop="params" label="请求参数">
                </el-table-column>
                <el-table-column prop="time" label="执行时长(毫秒)">
                </el-table-column>
                <el-table-column prop="ip" label="IP地址">
                </el-table-column>
                <el-table-column prop="createDate" label="创建时间">
                </el-table-column>
            </el-table>
            <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle"
                :current-page="pageIndex" :page-sizes="[5, 10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                layout="total, sizes, prev, pager, next, jumper" style="margin-top:30px;">
            </el-pagination>
        </div>
    </el-card>
</template>
<script>
export default {
    name: 'sysRole',
    data() {
        return {
            dataForm: {
                msg:'',
            },
           
            dataList: [],
            pageIndex: 1,
            pageSize: 5,
            totalPage: 0,
            // 防止“查询”多次提交
            dataListLoading: false,
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
                    msg: this.dataForm.msg
                }
            }
            //获取数据
            this.$http.get('/sys/sysLog/list', params).then((res) => {
                this.dataList = res.data.data.list;
                this.totalPage = res.data.data.totalCount;
                this.dataListLoading = false;
            });
        }
    },
    mounted() {
        this.getDataList();
    }
}
</script>