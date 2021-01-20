import Vue from 'vue'
import App from './App.vue'
import axios from './lib/axios'
import router from './router'
import store from './store'
import FastClick from 'fastclick'
import Vant from 'vant'
import 'vant/lib/index.css'
import './assets/css/base.css'
import wx from 'weixin-jsapi';

Vue.use(Vant)

Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.prototype.$wx = wx;

FastClick.attach(document.body)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
