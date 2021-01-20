<template>
  <div>

    <van-nav-bar
      title="设置"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
    />
    <van-cell center title="订阅推送">
      <template #right-icon>
        <van-switch :value="checked" size="24" @change="onOpenPush"/>
      </template>
    </van-cell>
    <van-cell center title="设置订阅" is-link to="/setting/chooseCity">
      <template #right-icon>
      </template>
    </van-cell>
    <van-cell center title="清除缓存" is-link  @click="onClear">
      <template #right-icon>
      </template>
    </van-cell>
  </div>

</template>

<script>
    import localStorage from '@/utils/store/localStorage'
    import {Toast} from 'vant';

    export default {

        name: "index",
        data() {
            return {
                userInfo: null,
                checked: false
            }
        },
        created() {
            this.getUserInfo()
        },
        methods: {
            getUserInfo() {
                let data = localStorage.get("user");
                this.userInfo = data;
                if (this.userInfo.openMsgPush) {
                    this.checked = true
                }
            },
            onClickLeft() {
                this.$router.go(-1)
            },
            async onOpenPush(value) {
                console.log(value)
                this.checked = value
                console.log(this.checked)
                const param = {
                    open: value
                }
                const {data} = await this.$axios.get('/mp/user/openPush?open=' + value)
                if (data.code === 200) {
                    console.log('ret', data)
                    this.userInfo.openMsgPush = value
                    console.log('userInfo', this.userInfo)
                    localStorage.set('user', this.userInfo)
                    if (value) {
                        Toast('开启订阅推送成功');
                    } else {
                        Toast('关闭订阅推送成功');
                    }
                }
            },
            onClear(){
                localStorage.remove("token")
                localStorage.remove("openId")
                localStorage.remove("user")
            }
        }

    }
</script>

<style scoped>

</style>
