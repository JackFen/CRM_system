<template>
    <div>
        <el-row>
            <el-col :span="8">
                <el-card class="box-card">
                    <div class="user">
                        <img src="@/assets/images/user1.png" />
                        <div class="user-info">
                            <p class="name">Admin</p>
                            <p class="access">超级管理员</p>
                        </div>
                    </div>
                    <div class="login-info">
                        <p>上次登录的日期:<span>2023-1-7</span></p>
                        <p>上次登录的地点:<span>河北石家庄</span></p>
                    </div>
                </el-card>
                <el-card class="box-card" style="margin-top:20px">
                    <el-table :data="tableData" stripe style="width: 100%">
                        <el-table-column prop="date" label="日期" width="110">
                        </el-table-column>
                        <el-table-column prop="name" label="姓名" width="110">
                        </el-table-column>
                        <el-table-column prop="address" label="地址">
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
            <el-col :span="16">
                <div class="grid-content bg-purple-light"></div>
            </el-col>
        </el-row>
    </div>
</template>
<script>
export default {
    name: 'firstPage',
    data() {
        return {
            tableData: [{
                date: '2016-05-02',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-04',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1517 弄'
            }, {
                date: '2016-05-01',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1519 弄'
            }, {
                date: '2016-05-03',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1516 弄'
            }]
        }
    }
}
</script>
<style lang="less" scoped>
    .user {
        padding-bottom: 20px;
        border-bottom: 1px solid #999;
        margin-bottom: 20px;
        display: flex;
        align-items: center;

        img {
            margin-left: 40px;
            width: 100px;
            height: 100px;
            border-radius: 50%;
        }

        .user-info {
            margin-left: 40px;
            .name {
                font-size: 32px;
                margin-bottom: 10px;
            }

            .access {
                color: #999;
                margin-top: 0xp;
            }
        }
    }

    .login-info {
        p {
            font-size: 14px;
            color: #999999;
            line-height: 28px;
        }

        span {
            color: #666666;
            margin-left: 20px;
        }
    }
</style>