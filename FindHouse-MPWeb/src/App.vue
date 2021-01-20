<template>
  <div id="app">
    <router-view/>
    <Footer v-if="isFooter"></Footer>
  </div>
</template>
<script>
    import Footer from '@/components/Footer'
    import {mapGetters} from 'vuex'
    import localStorage from '@/utils/store/localStorage'
    import {config} from '@/utils/wechat.js'
    import {closePage} from '@/utils/utils.js'

    export default {
        data() {
            return {
                code: '',
                isShowFooterList: [
                    'Cloud',
                    'Favorites',
                    'User'
                ]
            }
        },
        computed: {
            isFooter() {
                return this.isShowFooterList.includes(this.$route.name)
            },
            ...mapGetters([''])
        },
        components: {
            Footer
        },
        created() {
            let code = this.getUrlCode().code;
            console.log('query', this.$route.query);
            console.log(code)
            console.log("getUrlParame",this.getUrlParaName('code'))
            this.init()
        },
        methods: {
            init() {
                let code = this.getUrlCode().code;
                console.log('进入 init')
                if (localStorage.get('openId')) {
                    console.log("获取到openId")
                    return
                }
                console.log("传入init的code",code)
                if (code != null && code != undefined && code !='') {
                    this.code = code
                    console.log("code", this.code)
                    // debugger
                    this.getOpenId(this.code)
                } else {
                    window.location.href = `https://open.weixin.qq.com/connect/oauth2/authorize?appid=${config.wxAppId}&redirect_uri=${encodeURIComponent('')}&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect`
                }
            },
            async getOpenId(code) {
                console.log('getOpenId',code)
                const {data} = await this.$axios.get('/mp/user/getWxUserInfoByCode?code='+code)
                if (data.code === 200) {
                    console.log('data', data)
                    const user = data.data.user


                    localStorage.set("token", data.data.token)
                    localStorage.set("user",user)
                    console.log('user',this.$store.state.user)
                    console.log('token',this.$store.state.token)
                    console.log('firstRegister',user.firstRegister)
                    if (!user.subscribe) {
                      // 未关注，提示关注再进入
                      return  this.$dialog.alert({
                        title: '提示',
                        message: '请先关注公众号'
                      }).then(() => {
                        closePage()
                      })

                    }else if (user.firstRegister) {
                      // 第一次登录，跳订阅页面
                        console.log("第一次注册进入订阅页面")
                        console.log("this$router",this.$router)
                        return   this.$router.push({
                            name: 'ChooseCity'
                        }).catch(err => {
                            console.log('all good',err)
                        })

                    }else{
                      localStorage.set("openId",user.mpOpenId)
                      this.$store.commit('setState', {user: user})
                      this.$store.commit('setState', {token: data.data.token})
                        // 都不是以上情况，进入首页
                        return   this.$router.push({
                            name: 'Cloud'
                        })
                    }
                }
            },
            // 截取code
            getUrlParaName(parameName) {
                /// 获取地址栏指定参数的值
                /// <param name="parameName">参数名</param>
                // 获取url中跟在问号后面的部分
                var parames = window.location.search
                // 检测参数是否存在
                if (parames.indexOf(parameName) > -1) {
                    var parameValue = ''
                    parameValue = parames.substring(parames.indexOf(parameName), parames.length)
                    // 检测后面是否还有参数
                    if (parameValue.indexOf('&') > -1) {
                        // 去除后面多余的参数, 得到最终 parameName=parameValue 形式的值
                        parameValue = parameValue.substring(0, parameValue.indexOf('&'))
                        // 去掉参数名, 得到最终纯值字符串
                        parameValue = parameValue.replace(parameName + '=', '')
                        return parameValue
                    }
                    return ''
                }
            },
            getUrlCode() { // 截取url中的code方法
                var url = location.search
                this.winUrl = url
                var theRequest = new Object()
                if (url.indexOf("?") != -1) {
                    var str = url.substr(1)
                    var strs = str.split("&")
                    for(var i = 0; i < strs.length; i ++) {
                        theRequest[strs[i].split("=")[0]]=(strs[i].split("=")[1])
                    }
                }
                return theRequest
            }
        }
    }
</script>
<style lang="scss">
  @import "~@/assets/css/reset.css";

  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
    position: relative;
    height: 100%;
  }

  html, body {
    font-family: PingFangSC-Regular, 'Source Sans Pro', Arial, sans-serif;
    height: 100vh;
  }
</style>
