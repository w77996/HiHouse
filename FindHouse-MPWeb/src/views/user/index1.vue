<template>
  <div>
    <div class="money_box">
      <div class="top">
        <div class="top_left">
          <div class="top_left_c1">
            <text class="yuan">￥</text>
<!--            {{params.cashMoney}}-->
            10
          </div>
          <div class="subhead">可提现金额</div>
        </div>
        <div class="top_right_btn" hover-class="com_hover_target">去提现</div>
      </div>
      <div class="bottom">
        <div class="bottom_left">
          <div class="bottom_left_c1">
            <text class="yuan">￥</text>
<!--            {{params.agentAmount}}-->
            10
          </div>
          <div class="subhead">当日收益</div>
        </div>
        <div class="bottom_right">
          <div class="bottom_right_c1">
            <text class="yuan">￥</text>
<!--            {{params.totalMoney}}-->
            10
          </div>
          <div class="subhead">累计收益</div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
    // import home_pic_card from "../../static/images/home_pic_card.png";

    export default {

        name: "user",
        data() {
            return {
                userInfo: null,
                bgImg: './assets/static/images/home_pic_card.png'
            }
        },
        created() {
            // this.getUserInfo()
        },
        methods: {
            async getUserInfo() {
                const {data} = await this.$axios.get('/mp/user/getUserInfoByToken')
                if (data.code === 200) {
                    console.log('object', data)
                    this.userInfo = data.data
                    console.log('userInfo', this.userInfo)
                }
            }
        }

    }
</script>

<style  lang="scss" scoped>
  .money_box {
    width: 100%;
    padding: 65px;
    box-sizing: border-box;
    background: url('../../assets/static/images/home_pic_card.png') no-repeat;
    background-size: 100% 100%;
    margin: 0 auto;
    .top,
    .bottom {
      display: flex;
      justify-content: space-between;
    }

    .subhead {
      font-family: PingFangSC-Regular;
      font-size: 23px;
      line-height: 23px;
      color: #999;
    }

    .top {
      .top_left_c1 {
        font-family: PingFangSC-Medium;
        font-size: 60px;
        color: #333;
      }

      .top_right_btn {
        width: 100px;
        height: 70px;
        border: 1.2px solid #E24F48;
        border-radius: 35px;
        font-size: 20px;
        color: #E24F48;
        box-sizing: border-box;
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }

    .bottom {
      margin-top: 40px;

      .bottom_right {
        width: 50%;
        padding-left: 24px;
        box-sizing: border-box;
        border-left: 1px solid #E5E5E5;
      }

      .bottom_left_c1,
      .bottom_right_c1 {
        font-family: PingFangSC-Medium;
        font-size: 36px;
        line-height: 36px;
        color: #333333;
        margin-bottom: 10px;
      }
    }

    .yuan {
      font-family: PingFangSC-Regular;
      font-size: 28px;
      color: #333;
    }
  }

  /*.mode_white {*/
  /*  .subhead {*/
  /*    color: #F7F4F4;*/
  /*  }*/

  /*  .top {*/
  /*    .top_left_c1 {*/
  /*      color: #fff;*/
  /*    }*/

  /*    .top_right_btn {*/
  /*      border: 1.2px solid #fff;*/
  /*      color: #fff;*/
  /*    }*/
  /*  }*/

  /*  .bottom {*/
  /*    .bottom_right {*/
  /*      border-left: 1px solid rgba(247, 244, 244, 0.50);*/
  /*    }*/

  /*    .bottom_left_c1,*/
  /*    .bottom_right_c1 {*/
  /*      color: #fff;*/
  /*    }*/
  /*  }*/

  /*  .yuan {*/
  /*    color: #fff;*/
  /*  }*/
  /*}*/
</style>
