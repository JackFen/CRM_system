<template>
  <div class="hello">
    <el-container>
      <el-aside width="auto">
        <Aside></Aside>
      </el-aside>
      <el-container>
        <el-header>
          <Header></Header>
        </el-header>
        <CommonTab></CommonTab>
        <el-main>
          <!-- 路由的出口 -->
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Aside from '@/components/common/Aside.vue'
import Header from '@/components/common/Header.vue'

import CommonTab from '@/components/common/CommonTab.vue'
export default {
  name: 'HelloWorld',
  data() {
    return {
      msg: 'Welcome to Your Vue.js App'
    };
  },components:{
    Aside,
    Header,
    CommonTab
}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="less" scoped>
  .el-header{
    padding: 0;
  }
</style>
