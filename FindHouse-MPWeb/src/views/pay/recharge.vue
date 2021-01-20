<template>
  <div>

    <van-nav-bar
      title="充值"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
    />
    <div class="recharge_content">
      <p class="recharge_hurg_price">
        充值金额：
        <span>{{this.thePrice}}</span><span style="color: black;">元</span>
      </p>
      <van-radio-group v-model="radio">
        <van-cell-group>
          <van-cell v-for="(item,index) in productList"
                    :key="index"
                    label-class="right-icon"
                    :title="item.productName"
                    clickable @click="selectProduct(index,item)">
            <template #right-icon>
              <span style="margin-right: 10px">{{Math.round(item.price /100)}}元</span>
              <van-radio :name="index"/>
            </template>
          </van-cell>
          <!--        <van-cell title="包月套餐" clickable @click="selectProduct('1')">-->
          <!--          <span>20天</span>-->
          <!--          <template #right-icon>-->
          <!--            <van-radio name="1"/>-->
          <!--          </template>-->
          <!--        </van-cell>-->
          <!--        <van-cell title="包季套餐" clickable @click="selectProduct('2')">-->
          <!--          <template #right-icon>-->
          <!--            <van-radio name="2"/>-->
          <!--          </template>-->
          <!--        </van-cell>-->
          <!--        <van-cell title="半年套餐" clickable @click="selectProduct('3')">-->
          <!--          <template #right-icon>-->
          <!--            <van-radio name="3"/>-->
          <!--          </template>-->
          <!--        </van-cell>-->
          <!--        <van-cell title="全年套餐" clickable @click="selectProduct('4')">-->
          <!--          <template #right-icon>-->
          <!--            <van-radio name="4"/>-->
          <!--          </template>-->
          <!--        </van-cell>-->
        </van-cell-group>
      </van-radio-group>
      <van-button type="info"
                  class="step"
                  @click="handleRecharge">购买
      </van-button>
    </div>
  </div>


</template>

<script>
    import wx from 'weixin-jsapi'
    import {Dialog} from 'vant';

    export default {
        name: "recharge",
        data() {
            return {
                thePrice: 12,
                radio: '1',
                activeIcon: 'https://img.yzcdn.cn/vant/user-active.png',
                inactiveIcon: 'https://img.yzcdn.cn/vant/user-inactive.png',
                productList: []
            }
        },
        methods: {
            onClickLeft() {
                this.$router.go(-1)
            },
            selectProduct(radio, item) {
                this.radio = radio
                this.thePrice = Math.round(item.price / 100)
            },
            async getProduct() {
                const {data} = await this.$axios.get('/product/list')
                if (data.code === 200) {
                    this.radio = 0
                    console.log('object', data)
                    this.productList = data.data
                    console.log('userInfo', this.productList)
                }
            },
            async handleRecharge() {
                let selectProduct = this.productList[this.radio]
                console.log('selectProduct', selectProduct)
                const param = {
                    productId: selectProduct.id
                }
                const {data} = await this.$axios.post('/mp/pay/js/createOrder', param)
                if (data.code === 200) {
                    console.log('object', data)
                    console.log('进入设置微信支付')
                    let _this = this;
                    if (data.code == 200) {
                        wx.config({
                            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                            appId: data.data.appId, // 必填，公众号的唯一标识
                            timestamp: data.data.timestamp, // 必填，生成签名的时间戳
                            nonceStr: data.data.nonceStr, // 必填，生成签名的随机串
                            signature: data.data.signature, // 必填，签名，见附录1
                            jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                        });
                        wx.ready(function () {
                            console.log("wx ready")
                            wx.chooseWXPay({
                                // appId:data.data.appId,
                                timestamp: data.data.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                                nonceStr: data.data.nonceStr, // 支付签名随机串，不长于 32
                                package: data.data.packageValue, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=\*\*\*）
                                signType: data.data.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
                                paySign: data.data.paySign, // 支付签名
                                success: function (res) {
                                    console.log(res);
                                    //跳转到支付成功页面有这个页面
                                    _this.$router.push({
                                        name:"PaySuccess"
                                    })
                                    console.log('支付成功', res);
                                },
                                cancel: function (res) {//提示引用的是mint-UI里toast
                                    console.log('取消支付', res)

                                    alert("取消支付")
                                    // Dialog({ message: '已取消支付' })
                                },
                                fail: function (res) {
                                    console.log('支付失败', res)
                                    alert("支付失败 " + res)
                                    // Dialog({ message: '支付失败，请重试' })
                                }
                            })
                        })
                    } else {
                        console.log("调用微信支付下单失败")
                    }

                } else {
                    console.log("后台返回状态错误")
                }
            }

        },
        created() {
            this.getProduct()
        }
    }
</script>

<style lang="scss" scoped>

  .recharge_content {
    height: 100%;
  }


  .recharge_hurg_price {
    width: 100%;
    text-align: center;
    margin: 1.25rem 0;
  }

  .recharge_hurg_price span {
    color: #1989fa;
    font-weight: 600;
    font-size: 23px;
  }

  .right-icon{
    span {
      margin-right: 10px;
    }
  }
  .step {
    width: 100%;


    float: bottom;
  }


</style>
