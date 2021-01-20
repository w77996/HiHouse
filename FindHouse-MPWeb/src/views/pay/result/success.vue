<template>
	<div class="pay-container">
    <van-nav-bar
      title="充值成功"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
    />
<!--		<title-bar text="支付完成"></title-bar>-->
		<img :src="successPic" class="main-img"></img>
<!--		<img src="../../static/images/pay_fail.png" v-if="status == '2'" class="main-img"></img>-->
		<div class="pay-text">支付完成</div>

	</div>
</template>

<script>
	export default {
		// mixins: [buyMoreGlobal],
		// components: { bottomBtn },
		data() {
			return {
			  successPic:require("@/assets/static/images/pay_success.png"),
			}
		},
		methods: {
        onClickLeft() {
            this.$router.push({
                name:'User'
            })
        },
		}
	}
</script>

<style lang="scss" scoped>
	.pay-container {
		width: 100%;
		height: 100%;

		.main-img {
			width: 100px;
			height: 100px;
			display: block;
			margin: 64px auto 34px;
		}

		.pay-text {
			width: 100%;
			text-align: center;
			margin-bottom: 60px;
		}

		.pay-tip {
			width: 100%;
			text-align: center;
			line-height: 40px;
		}

		.mb {
			margin-bottom: 80px;
		}

		.pay-img {
			display: block;
			width: 536px;
			height: 524px;
			margin: 0 auto;
		}
	}
</style>
