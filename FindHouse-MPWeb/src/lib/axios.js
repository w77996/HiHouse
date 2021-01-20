import axios from 'axios'
import localStorage from '@/utils/store/localStorage'
import store from '@/store/index'
import router from '@/router/index'
import {Dialog} from 'vant'
// axios 配置
axios.defaults.timeout = 30000
axios.defaults.baseURL = '/api/'

// http request 拦截器
axios.interceptors.request.use(
  config => {
    console.log("token",localStorage.get('token'))
    if (localStorage.get('token')) {
      let token = localStorage.get('token')
      config.headers.Authorization = `Bearer ${token}`
    }
    //   config.headers.Authorization = `Bearer admin`
    // console.log("token",store.state.token)
    // if (localStorage.get('token')) {
    //   console.log("token")
    //   config.headers.Authorization = `Bearer ${localStorage.get('token')}`
    //   // config.headers.Authorization = `Bearer admin`
    // }
    return config
  },
  err => {
    return Promise.reject(err)
  }
)

// http response 拦截器
axios.interceptors.response.use(
  response => {
    console.log('axios intercept', response)
    // if (response.data.code === 401) {
    //       // 401 清除token信息并跳转到登录页面
    //       store.commit('loginOut')
    //       // 只有在当前路由不是登录页面才跳转
    //       router.currentRoute.path !== 'login' &&
    //       router.replace({
    //         path: '/',
    //         query: { redirect: router.currentRoute.path }
    //       })
    // } else if (response.data.code !== 200 ) {
    //   Dialog({ message: response.data.message })
    // }
    if (response.data.code !== 200) {
      let msg = response.data.message
      if (msg == null) {
        msg = '系统错误'
      }
      Dialog({message: msg})
    }
    return response
  },
  error => {
    if (error.response) {
      console.log('进入rejected')
      switch (error.response.status) {
        case 401:
          // 401 清除token信息并跳转到登录页面
          store.commit('loginOut')
          localStorage.remove("token")
          localStorage.remove("openId")
          localStorage.remove("user")
          // 只有在当前路由不是登录页面才跳转
          router.currentRoute.path !== 'login' &&
          router.replace({
            path: '/',
            query: {redirect: router.currentRoute.path}
          }).catch(err => {
            console.log('reject router replace', err)
          })
      }
    }
    // console.log(JSON.stringify(error));//console : Error: Request failed with status code 402
    return Promise.reject(error)
  }
)

export default axios
