<template>
  <div class="hello">
    <el-container>
      <el-aside width="200px">
        <Aside></Aside>
      </el-aside>
      <el-container>
        <el-header>Header</el-header>
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
import { RouterView } from 'vue-router';
export default {
  name: 'HelloWorld',
  data() {
    return {
      msg: 'Welcome to Your Vue.js App'
    };
  },components:{
    Aside,
    RouterView
}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
