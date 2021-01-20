import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Cloud',
    component: () => import(/* webpackChunkName: "about" */ '../views/cloud/index.vue')
  },
  {
    path: '/index',
    name: 'Index',
    component: () => import(/* webpackChunkName: "about" */ '../views/cloud/index.vue')
  },
  {
    path: '/favorites',
    name: 'Favorites',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/favorites/index.vue')
  },
  {
    path: '/user',
    name: 'User',
    component: () => import(/* webpackChunkName: "about" */ '../views/user/index.vue')
  },
  {
    path: '/sub/chooseCity',
    name: 'ChooseCity',
    component: () => import(/* webpackChunkName: "about" */ '../views/sub/chooseCity.vue')
  },
  {
    path: '/sub/subscribe',
    name: 'Subscribe',
    component: () => import(/* webpackChunkName: "about" */ '../views/sub/subscribe.vue')
  },
  {
    path: '/house/detail',
    name: 'HouseDetail',
    component: () => import(/* webpackChunkName: "about" */ '../views/house/detail.vue')
  },
  {
    path: '/pay/recharge',
    name: 'Recharge',
    component: () => import(/* webpackChunkName: "about" */ '../views/pay/recharge.vue')
  },
  {
    path: '/setting/subscribe',
    name: 'SettingSubscribe',
    component: () => import(/* webpackChunkName: "about" */ '../views/setting/subscribe.vue')
  },
  {
    path: '/setting/chooseCity',
    name: 'SettingChooseCity',
    component: () => import(/* webpackChunkName: "about" */ '../views/setting/chooseCity.vue')
  },
  {
    path: '/setting',
    name: 'Setting',
    component: () => import(/* webpackChunkName: "about" */ '../views/setting/index.vue')
  },
  {
    path: '/help',
    name: 'Help',
    component: () => import(/* webpackChunkName: "about" */ '../views/help/index.vue')
  },
  {
    path: '/user/subscribeInfo',
    name: 'SubscribeInfo',
    component: () => import(/* webpackChunkName: "about" */ '../views/user/subscribeInfo.vue')
  },
  {
    path: '/pay/result/success',
    name: 'PaySuccess',
    component: () => import(/* webpackChunkName: "about" */ '../views/pay/result/success.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
