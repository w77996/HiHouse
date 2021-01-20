import store from '@/utils/store/cookie'
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const LOGIN_KEY = 'login_status'

export default new Vuex.Store({
  state: {
    user: {},
    token: store.get(LOGIN_KEY) || null,
    isFooter: true,
    showCreate: false
  },
  getters: {
    user: state => state.user,
    token: state => state.token,
    isFooter: state => state.isFooter,
    showCreate: state => state.showCreate
  },
  mutations: {
    setState (state, payload) {
      Object.keys(payload).forEach((key) => {
        if (key === 'tokey') {
          store.set(LOGIN_KEY, payload[key])
        }
        state[key] = payload[key]
      })
    },
    loginOut: state => {
      store.remove(LOGIN_KEY)
      state.token = null
    },
    updateShowCreate: state => {
      state.showCreate = true
    }
  },
  actions: {
  },
  modules: {
  }
})
