<template>
  <div>

    <van-nav-bar
      title="帮助中心"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
    />
<!--    <h4>1.58出租隐私保护房源电话打不了？</h4>-->
<!--    <div>拨打时页面提示“房东已开启中介勿扰模式”，您的手机号绑定的58账号是经纪人就会被58网站功能拦截，换个不是经纪人的58账号及手机号拨打即可。</div>-->
<!--    <h4>2.为什么页面加载不出来？</h4>-->
<!--    <div>当出现页面加载不完整或加载不出来时，你可以清除缓存。</div>-->
<!--    <h4>3.为什么房源量比其他软件少？</h4>-->
<!--    <div>当出现页面加载不完整或加载不出来时，你可以清除缓存。</div>-->
<!--    <h4>4.黑客找房是怎么收费的？</h4>-->
<!--    <div>采用月会员制度，在服务正常运转中，无限制推送房源给会员，若出现系统不可用或无发提供服务，则会免费续期</div>-->
    <van-cell
      title="1.58出租隐私保护房源电话打不了？"
      title-class="title-class"
      :border=true
      clickable
      size="large">
      <template #label>
        <div>拨打时页面提示“房东已开启中介勿扰模式”，您的手机号绑定的58账号是经纪人就会被58网站功能拦截，换个不是经纪人的58账号及手机号拨打即可。</div>
      </template>
    </van-cell>
    <van-cell
      title="2.为什么页面加载不出来？"
      title-class="title-class"
      :border=true
      clickable
      size="large">
      <template #label>
        <div>当出现页面加载不完整或加载不出来时，你可以清除缓存。</div>
      </template>
    </van-cell>
    <van-cell
      title="3.为什么房源量比其他软件少？"
      title-class="title-class"
      :border=true
      clickable
      size="large">
      <template #label>
        <div>所推送的房源是经过AI筛选的真实房源。</div>
      </template>
    </van-cell>
    <van-cell
      title="4.黑客找房是怎么收费的？"
      title-class="title-class"
      :border=true
      clickable
      size="large">
      <template #label>
        <div>采用购币制度，每推送一次则扣除一个币</div>
      </template>
    </van-cell>
    <van-cell
      title="5.想订阅的城市但没有订阅选择怎么办？"
      title-class="title-class"
      :border=true
      clickable
      size="large">
      <template #label>
        <div>添加微信联系客服，让客服进行后台添加</div>
      </template>
    </van-cell>
    <van-cell
      title="6.收不到推送怎么办？"
      title-class="title-class"
      :border=true
      clickable
      size="large">
      <template #label>
        <div>由于微信公众号限制，发送任意内容至公众号，则48小时内可接受到推送信息。</div>
      </template>
    </van-cell>
  </div>
</template>

<script>
    export default {
        name: "index",
        methods:{
            onClickLeft() {
                this.$router.go(-1)
            },
        }
    }
</script>

<style scoped>

</style>
