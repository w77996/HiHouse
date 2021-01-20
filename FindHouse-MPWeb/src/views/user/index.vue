<template>
  <div class="content">
    <div class="useinfo_box">
      <img :src="headImage" class="img_user"></img>
      <!--      <div class="title">{{driverInfo.userName}}</div>-->
      <div class="title">心之所向</div>

    </div>
    <div class="money_box" @click="toRecharge()">
      <div class="top">
        <div class="top_left">
          <div class="top_left_c1">
            {{leftDate}}
          </div>
          <div class="subhead">剩余币</div>
        </div>
        <div class="top_right_btn">去充值</div>
      </div>
    </div>
    <div class="house_setting_info">
      <van-cell-group class="house_setting_group">
        <van-cell title="类型" is-link >
          <template #default>
            出售
          </template>
        </van-cell>
        <van-cell title="用途" is-link>
          <template #default>
            住宅
          </template>
        </van-cell>
        <van-cell title="区域" is-link to="/user/subscribeInfo">
          <template #default>
            {{subscribeSize}}个区域
          </template>
        </van-cell>
      </van-cell-group>
    </div>

    <div class="house_setting_info">
      <van-cell-group class="house_setting_group">
        <van-cell title="设置" is-link to="Setting">
        </van-cell>
        <van-cell title="帮助中心" is-link to="Help">
        </van-cell>
        <van-cell title="专属客服" is-link @click="showKefu">
        </van-cell>
      </van-cell-group>
    </div>
<!--    <div class="house_setting_info">-->
<!--      <van-cell-group class="house_setting_group">-->
<!--        <van-cell title="关于我们" is-link to="Help">-->
<!--        </van-cell>-->
<!--      </van-cell-group>-->
<!--    </div>-->
    <van-popup v-model="showPop"><van-image  :src="require('@/assets/static/images/kefu.jpg')" ></van-image><div style="width: 100%;text-align: center">扫码添加微信</div></van-popup>
  </div>


</template>

<script>
    // import home_pic_card from "../../static/images/home_pic_card.png";
    import localStorage from '@/utils/store/localStorage'
    export default {

        name: "user",
        data() {
            return {
                // kefuImage:require("./assets/static/images/kefu.jpg"),
                leftDate:0,
                subscribeSize: 0,
                headImage: 'http://thirdwx.qlogo.cn/mmopen/9Y5eaB1sxVjPRW7jIv2evRXQBnOfZianFUlvnyPavHRKHfwAdYiaSZAxATzvK3qVTDPotc7dz5iaMaNBA8CHic6r5GLvLnHB7ETw/132',
                userInfo: null,
                active: 1,
                bgImg: './assets/static/images/home_pic_card.png',
                myBg: './assets/static/images/my_bg.png',
                iconfont: './assets/static/images/icons/ic_nextstep.png',
                showPop: false
            }
        },
        created() {
            let user = localStorage.get('user')
            if(user != null){
                this.headImage = user.headImage
            }
            this.getUserInfo()
        },
        methods: {
            async getUserInfo() {
                const {data} = await this.$axios.get('/mp/user/getUserInfoByToken')
                if (data.code === 200) {
                    console.log('object', data)
                    this.userInfo = data.data
                    localStorage.set('user', data.data)
                    console.log('userInfo', this.userInfo)
                    this.subscribeSize = data.data.subscribeInfo.subscribeCityInfo.length
                    this.leftDate = data.data.leftDate
                }
            },
            toRecharge() {
                this.$router.push({
                    name: 'Recharge'
                })
            },
            showKefu(){
              this.showPop = true
            }
        }

    }
</script>

<style lang="scss" scoped>
  .content{
    height: 100%;
  }
  .money_box {
    width: 100%;
    padding: 25px;
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
      font-size: 13px;
      line-height: 13px;
      color: #999;
    }

    .top {
      .top_left_c1 {
        font-family: PingFangSC-Medium;
        font-size: 40px;
        color: #333;
      }

      .top_right_btn {
        width: 70px;
        margin-top: 10px;
        height: 35px;
        border: 1.2px solid #E24F48;
        border-radius: 35px;
        font-size: 10px;
        color: #E24F48;
        box-sizing: border-box;
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }

    .yuan {
      font-family: PingFangSC-Regular;
      font-size: 28px;
      color: #333;
    }
  }

  .useinfo_box {
    width: 100%;
    height: 180px;
    padding-top: 56px;
    box-sizing: border-box;
    color: #FFF;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background: url('../../assets/static/images/my_bg.png') no-repeat;
    background-size: 100% 100%;

    .img_user {
      width: 80px;
      height: 80px;
      border-radius: 120px;
      //@include img_bg_color;
    }


    .title {
      font-family: PingFangSC-Medium;
      font-size: 22px;
      margin: 10px 0;
      line-height: 40px;
    }

    .bottom_box {
      font-family: PingFangSC-Regular;
      display: flex;
      justify-content: center;
      font-size: 32px;

      .icon {
        width: 44px;
        height: 44px;
        margin-right: 10px;
      }
    }
  }

  .house_setting_info {
    /*margin: 35px;*/
    padding: 10px;

    .house_setting_group {
      /*margin: auto 0;*/
      background: #FFFFFF;
      box-shadow: 0 5px 10px 0 rgba(98, 28, 31, 0.05);
      border-radius: 8px;
      overflow: hidden;
    }
  }
</style>
