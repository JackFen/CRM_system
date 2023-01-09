import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/common/Home.vue'
// 导入页面，@表示从根目录开始寻找
import CustomerList from '@/components/pages/customer/CustomerList.vue'
import CommonCustomer from '@/components/pages/customer/CommonCustomer.vue'
import AllOrder from '@/components/pages/order/AllOrder.vue'
import MyOrder from '@/components/pages/order/MyOrder.vue'
import FirstPage from '@/components/pages/FirstPage.vue'
import Login from '@/components/common/Login.vue'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home', //主路由
      component: Home,
      // 重定向到子路由
      redirect:'FirstPage',
      children: [//嵌套的子路由
        {
          path: '/FirstPage',
          name: 'firstPage',
          component: FirstPage
        },
        {
          path: '/CustomerList',
          name: 'customerList',
          component: CustomerList
        },
        {
          path: '/CommonCustomer',
          name: 'commonCustomer',
          component: CommonCustomer
        },
        {
          path: '/AllOrder',
          name: 'allOrder',
          component: AllOrder
        },
        {
          path: '/MyOrder',
          name: 'myOrder',
          component: MyOrder
        }
      ]
    },
    {
      path: '/Login',
      name: 'login', //登录路由
      component: Login,
    }
  ]
})
