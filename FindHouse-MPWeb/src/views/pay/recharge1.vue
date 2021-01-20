<template>

  <div id="Vue">
    <p class="recharge_hurg_price">
      充值金额：
      <span>{{thePrice}}.00</span><span style="color: black;">元</span>
    </p>
    <van-radio-group v-model="radio">
      <van-cell-group>
        <van-cell title="包月套餐" clickable @click="radio = '1'">
          <span>20天</span>
          <template #right-icon>
            <van-radio name="1"/>
          </template>
        </van-cell>
        <van-cell title="包季套餐" clickable @click="radio = '2'">
          <template #right-icon>
            <van-radio name="2"/>
          </template>
        </van-cell>
        <van-cell title="半年套餐" clickable @click="radio = '3'">
          <template #right-icon>
            <van-radio name="3"/>
          </template>
        </van-cell>
        <van-cell title="全年套餐" clickable @click="radio = '4'">
          <template #right-icon>
            <van-radio name="4"/>
          </template>
        </van-cell>
      </van-cell-group>
    </van-radio-group>
    <!--    <div class="house">-->
    <!--      <div-->
    <!--        v-for="(item, index) in list"-->
    <!--        :key="index"-->
    <!--        :class="['type', typeActive('area', item)]"-->
    <!--      >{{ item }}-->
    <!--      </div>-->
    <!--    </div>-->
    <!--    <div class="back">-->
    <!--      <div class="choice">-->
    <!--        <p>请选择充值的金额</p>-->
    <!--      </div>-->
    <!--      <div class="recharge_hurg">-->
    <!--        <ul>-->
    <!--          <li :class="[index == status ? 'bechoice':'']" v-for="(item,index) in list" :key="index"-->
    <!--              @click="choicePrice(index,item.price)">{{item.price}}元-->
    <!--          </li>-->
    <!--          <p class="recharge_hurg_price">-->
    <!--            充值金额：-->
    <!--            <span>{{thePrice}}.00</span><span style="color: black;">元</span>-->
    <!--          </p>-->
    <!--          <div class="recharge_btn" @click="wxpaly">微信充值</div>-->
    <!--        </ul>-->
    <!--      </div>-->
    <!--    </div>-->
  </div>

</template>

<script>
    export default {
        name: "recharge",
        data() {
            return {
                status: 0,
                thePrice: 10,
                list: [
                    {price: 15},
                    {price: 40},
                    {price: 100},
                ],
                radio: '1',
                activeIcon: 'https://img.yzcdn.cn/vant/user-active.png',
                inactiveIcon: 'https://img.yzcdn.cn/vant/user-inactive.png',
            }
        },
        watch: {
            // 如果 `question` 发生改变，这个函数就会运行
            question: function (newQuestion, oldQuestion) {
                //如果第一位数为0时
                if (this.question[0] == "0") {
                    console.log(this.question[0])
                    this.question = ""
                    this.thePrice = 0
                }
                //如果question为空时
                if (this.question == "") {
                    this.thePrice = 0
                } else {
                    this.thePrice = this.question
                    this.question = this.question.replace(/\D/g, '')
                }
            }
        },
        methods: {
            choicePrice(index, price) {
                this.status = index
                this.thePrice = price
            },
            choiceother() {
                this.status = null
                //当`question`为空时
                if (this.question == "") {
                    this.thePrice = 0
                }
            },
            wxpaly() {
                // console.log(this.confirm = parseInt(this.thePrice))
                if (this.name == "" || this.phone == "" || this.card == "") {
                    alert("请填写空余信息")
                } else {
                    console.log(this.confirm = parseInt(this.thePrice))
                    alert("你要充值的金额为：" + this.confirm)
                }


            },
            typeActive(list, obj) {
                return 'active'
            },
        },
        created() {

        }
    }
</script>

<style lang="scss" scoped>
  /** {*/
  /*  margin: 0;*/
  /*  padding: 0;*/
  /*}*/

  .house {
    margin-top: 20px;
    clear: both;
    overflow: hidden;

    div {
      margin-bottom: 10px;
    }

    .type {
      padding: 8px;
      width: 80px;
      text-align: center;
      // border:1 px solid #ccc;
      background: rgba(204, 204, 204, .5);
      font-size: 16px;
      float: left;
      margin-right: 10px;
    }

    .active {
      background: rgba(25, 137, 250, .3);
      color: #1989fa;
    }
  }


  body {
    background-color: #F4F4F4;
  }

  .back {
    background-color: white;
    width: 96%;
    border-radius: 10px;
    margin-left: 2%;
    margin-top: 2%;
    padding-bottom: 2%;
    padding-top: 2%;
  }

  .bechoice {
    background: green !important;
    color: white !important;
  }


  .information span {
    width: 95%;
    font-size: 1.1rem;
    margin-left: 2%;
  }

  .information input {
    width: 95%;
    border-radius: 7px;
    height: 2rem;
    text-align: center;
    line-height: 2rem;
    outline: none;
    color: green;
    font-size: 1.1rem;
    border: 2px solid;
    margin-top: 2.5%;
    margin-left: 2%;
    margin-bottom: 2.5%;
  }

  .choice {
    width: 100%;
    margin-top: 1rem;
    margin-bottom: 1rem;
  }

  .choice p {
    font-size: 1.1rem;
    margin-left: 2%;
  }

  .recharge_hurg ul {
    flex-direction: row;
    width: 100%;
    flex-wrap: wrap;
    display: flex;
    justify-content: center;
  }

  .recharge_hurg ul li {
    list-style-type: none;
    width: 29.5%;
    margin-right: 2%;
    border-radius: 0.5rem;
    height: 2.5rem;
    text-align: center;
    line-height: 2.5rem;
    margin-bottom: 1rem;
    border: 2px green solid;
    color: green;
  }

  .recharge_hurg ul li:nth-child(3n) {
    margin-right: 0 !important;
  }

  .recharge_hurg_price {
    width: 100%;
    text-align: center;
    margin: 1.25rem 0;
  }

  .recharge_hurg_price span {
    color: green;
    font-weight: 600;
    font-size: 23px;
  }

  .recharge_btn {
    width: 96%;
    background: green;
    color: white;
    height: 2.5rem;
    border-radius: 0.5rem;
    text-align: center;
    line-height: 2.5rem;
    font-size: 1.25rem;
  }

  .recharge_hurg ul input {
    list-style-type: none;
    width: 100%;
    margin-right: 2%;
    border-radius: 7px;
    height: 35px;
    text-align: center;
    line-height: 40px;
    border-style: none;
    text-shadow: none;
    -webkit-appearance: none;
    box-shadow: none;
    outline: none;
    color: green;
    font-size: 1.1rem;
  }

  ::-webkit-input-placeholder {
    /* WebKit, Blink, Edge */
    color: #aaa;
  }

  :-moz-placeholder {
    /* Mozilla Firefox 4 to 18 */
    color: #aaa;
    opacity: 1;
  }

  ::-moz-placeholder {
    /* Mozilla Firefox 19+ */
    color: #aaa;
    opacity: 1;
  }

  :-ms-input-placeholder {
    /* Internet Explorer 10-11 */
    color: #aaa;
  }
</style>
