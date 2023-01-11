// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
// 导入ElementUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
// 导入控制左侧菜单栏展开与否的vuex脚本
import store from '@/store';
// 导入axios
import axios from 'axios';

// 配置ElementUI
Vue.use(ElementUI)
Vue.config.productionTip = false

// 配置axios
Vue.prototype.$http = axios//修改内部的$http为axios
axios.defaults.baseURL="http://localhost:8086/ws"


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,//挂载Router实例
  store,//挂载store实例
  components: { App },
  template: '<App/>'
})
