import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/pages/LoginPage.vue'
import MerchantLogin from '@/pages/merchant/MerchantLoginPage.vue'
import MerchantRegister from '@/pages/merchant/MerchantRegisterPage.vue'
import BaseInfo from '@/pages/merchant/MerchantBaseInfoPage.vue'
import MerchantOrderManage from '@/pages/merchant/OrderManagePage.vue'
import GoodsManage from '@/pages/merchant/GoodsManagePage.vue'
import GoodModify from '@/pages/merchant/GoodModifyPage.vue'
import FinanceManage from '@/pages/merchant/FinanceManagePage.vue'
import AddInfo from '@/pages/merchant/AddInfoPage.vue'

import Shop from '@/pages/member/MerchantPage.vue'
import PersonalMainPage from '@/pages/member/PersonalMainPage.vue'
import OrderManage from '@/pages/member/OrderManagePage.vue'
import OrderDetails from '@/pages/member/OrderDetailsPage.vue'
import Account from '@/pages/member/AccountManagePage.vue'
import Data from '@/pages/member/DataManagePage.vue'
import CheckOut from '@/pages/member/CheckOutPage.vue'
import Market from '@/pages/member/MarketPage.vue'

import ManagerApprove from '@/pages/manager/MerchantApprovePage.vue'
import ManagerStatistics from '@/pages/manager/StatisticPage.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/merchant/login',
      name: 'MerchantLogin',
      component: MerchantLogin
    },
    {
      path: '/merchant/register',
      name:"MerchantRegister",
      component:MerchantRegister
    },
    {
      path:'/main-page',
      name:"PersonalMainPage",
      component:PersonalMainPage
    },
    {
      path:'/order',
      name:"OrderManage",
      component:OrderManage
    },
    {
      path:'/order/:orderId',
      name:"OrderDetails",
      component:OrderDetails
    },
    {
      path:'/account',
      name:"Account",
      component:Account
    },
    {
      path:'/data',
      name:'Data',
      component:Data
    },
    {
      path:'/checkout',
      name:"CheckOut",
      component:CheckOut
    },
    {
      path:'/market',
      name:'Market',
      component:Market
    },
    {
      path:'/shop/:id',
      name:"Shop",
      component:Shop
    },
    {
      path:'/merchant/base-info',
      name:'BaseInfo',
      component:BaseInfo
    },
    {
      path: '/merchant/order-manage',
      name:"MerchantOrderManage",
      component:MerchantOrderManage
    },
    {
      path:'/merchant/finance',
      name:"FinanceManage",
      component:FinanceManage
    },
    {
      path: '/merchant/goods',
      name:"GoodsManage",
      component:GoodsManage
    },
    {
      path: '/merchant/add',
      name: 'Add',
      component:AddInfo
    },
    {
      path: "/merchant/goods/modify",
      name:"GoodModify",
      component:GoodModify
    },
    {
      path:'/manager/approve',
      name:"ManagerApprove",
      component:ManagerApprove
    },
    {
      path:'/manager/statistics',
      name:"ManagerStatistics",
      component:ManagerStatistics
    },

  ]
})
