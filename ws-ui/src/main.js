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
//添加Axios的拦截器
axios.interceptors.request.use(config=>{
  //每次发送请求我们都携带token信息
  var token=sessionStorage.getItem('token')
  config.headers['Authorization']=token //请求头带上Token
  return config;
},error=>{
  return Promise.reject(error);
})
/* 使用router钩子函数来处理
   未登陆返回登录页面
*/
router.beforeEach((to,from,next)=>{
  const username=sessionStorage.getItem('username')
  if(to.name!='login'&& !username)next({name:'login'})
  else next()
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,//挂载Router实例
  store,//挂载store实例
  components: { App },
  template: '<App/>'
})
