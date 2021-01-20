<template>
  <div id="footer">
    <van-tabbar v-model="active">
      <van-tabbar-item icon="bar-chart-o" to="/">云采集</van-tabbar-item>
      <!--      <van-tabbar-item icon="notes-o" to="/favorites">收藏夹</van-tabbar-item>-->
      <van-tabbar-item icon="user-circle-o" to="/user">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>
<script>
    export default {
        name: 'Footer',
        props: {},
        data: function () {
            return {
                active: 0
            }
        },
        created() {
            if (this.$route.name == "User") {
                this.active = 1;
            }
        },
        methods: {}
    }
</script>
<style lang="scss" scoped>
</style>
