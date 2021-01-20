<template>
  <div>
    <van-nav-bar
      title="房源详情"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
    />
    <div class="content">
      <van-image
        width="100%"
        height="3rem"
        fit="fill"
        @click="onWubaDetail(house.sourceUrl)"
        :src="house.pic">
        <template v-slot:loading>
          <van-loading type="spinner" size="20"/>
        </template>
      </van-image>
      <p class="title">
        {{house.title}}
      </p>
      <!--      <p>-->
      <!--        发布时间-->
      <!--      </p>-->
      <!--      <van-divider-->
      <!--        :style="{ color: '#1989fa', borderColor: '#5c5e5d'}"-->
      <!--        hairline/>-->
      <van-divider/>
      <!-- 每个元素的两侧间隔相等 -->
      <!--      <van-grid :gutter="10" :column-num="3">-->
      <!--        <van-grid-item class="house-info">-->
      <!--          <span>售价</span>-->
      <!--          <div>{{house.price}}</div>-->
      <!--        </van-grid-item>-->
      <!--        <van-grid-item class="house-info">-->
      <!--          <span>户型</span>-->
      <!--          <div>{{house.huxing}}</div>-->
      <!--        </van-grid-item>-->
      <!--        <van-grid-item class="house-info">-->
      <!--          <span>建筑面积</span>-->
      <!--          <div>{{house.mainji}}</div>-->
      <!--        </van-grid-item>-->
      <!--      </van-grid>-->
      <van-row type="flex" justify="space-around">
        <van-col span="6" :border="false" class="house-info">
          <span>售价</span>
          <div>{{house.price}}</div>
        </van-col>
        <van-col span="6" :border="false" class="house-info">
          <span>户型</span>
          <div>{{house.huxing}}</div>
        </van-col>
        <van-col span="6" :border="false" class="house-info">
          <span>建筑面积</span>
          <div>{{house.mainji}}</div>
        </van-col>
      </van-row>
      <!--      <van-divider-->
      <!--        :style="{ color: '#1989fa', borderColor: '#5c5e5d',}"-->
      <!--      />-->
      <van-divider/>
      <van-grid direction="horizontal" :border="false" :column-num="2">
        <van-grid-item :border="false"
                       class="house-info-else-item">
          <p>单价:</p>
          <div>{{house.mainji}}</div>
        </van-grid-item>
        <van-grid-item :border="false" class="house-info-else-item">
          <p>朝向:</p>
          <div>{{house.toward}}</div>
        </van-grid-item>
        <van-grid-item :border="false" class="house-info-else-item">
          <p>楼层:</p>
          <div>{{house.floor}}</div>
        </van-grid-item>
        <van-grid-item :border="false" class="house-info-else-item">
          <p>装修:</p>
          <div>{{house.fittype}}</div>
        </van-grid-item>
      </van-grid>
      <!-- 左对齐 -->
      <!--          <div class="van-hairline&#45;&#45;bottom">-->
      <!--            <van-row type="flex">-->
      <!--              <van-col span="2"></van-col>-->
      <!--              <van-col span="12" class="house-info-else-item">-->
      <!--                <p>单价:</p>-->
      <!--                <div>{{house.mainji}}</div>-->
      <!--              </van-col>-->
      <!--              <van-col span="12" class="house-info-else-item">-->
      <!--                <p>朝向:</p>-->
      <!--                <div>{{house.toward}}</div>-->
      <!--              </van-col>-->
      <!--              <van-col span="2"></van-col>-->
      <!--            </van-row>-->
      <!--            <van-row type="flex">-->
      <!--              <van-col span="2"></van-col>-->
      <!--              <van-col span="12" class="house-info-else-item">-->
      <!--                <p>楼层:</p>-->
      <!--                <div>{{house.floor}}</div>-->
      <!--              </van-col>-->
      <!--              <van-col span="12" class="house-info-else-item">-->
      <!--                <p>装修:</p>-->
      <!--                <div>{{house.fittype}}</div>-->
      <!--              </van-col>-->
      <!--              <van-col span="2"></van-col>-->
      <!--            </van-row>-->
      <!--          </div>-->
      <!--      <van-divider-->
      <!--        :style="{ color: '#1989fa', borderColor: '#5c5e5d',}"-->
      <!--      />-->
      <van-divider/>
      <div>
        <van-row type="flex">

          <van-col span="12" class="house-info-else-item" style="margin-left: 10px">
            <p>业主:</p>
            <div>{{house.trueName}}</div>
          </van-col>
        </van-row>
      </div>
      <div class="footer">
        <van-tabbar>
          <van-button
            v-if="house.phone != null && house.phone !=''"
            size="large"
            type="info"
            class="step"
            icon="phone-o"
            @click="callPhone(house)">拨打电话
          </van-button>
          <van-button
            v-else="house.phone != null || house.phone !=''"
            size="large"
            type="info"
            class="step"
            disabled
            icon="phone-o"
            @click="callPhone(house)">无电话
          </van-button>
        </van-tabbar>
      </div>
    </div>
  </div>

</template>

<script>
    import {Dialog} from 'vant';

    export default {
        name: "detail",
        data() {
            return {
                pic: 'https://img.yzcdn.cn/vant/cat.jpeg',
                house: Object,
                phone: '',
            }
        },
        created() {
            const id = this.$route.query.id
            this.getDetailInfo(id)
        },
        methods: {
            async getDetailInfo(val) {
                const {data} = await this.$axios.get('/sale/detail?id=' + val)
                if (data.code === 200) {
                    console.log('object', data)
                    this.house = data.data
                    this.phone = this.house.phone
                    this.pic = this.house.pic
                    this.houseTitle = this.house.title
                    console.log('house', this.house)
                }
            },
            callPhone(house) {
                console.log("拨打电话", house)
                let phone = house.phone
                Dialog.confirm({
                    title: '点击号码拨打电话',
                    message: `<a href="tel:${this.phone}">  ${this.phone}  </a>`,
                }).then(() => {
                    // on confirm

                }).catch(() => {
                        // on cancel
                });
            },

            onClickLeft() {
                this.$router.push({
                    name: 'Cloud'
                })
            },
            onWubaDetail(sourceUrl) {
                window.location.href = sourceUrl
            }
        }
    }
</script>

<style lang="scss" scoped>
  .content {
    min-height: 100%;
    width: 100%
  }

  .title {
    display: block;
    overflow: hidden;
    font-size: 18px;
    margin-left: 10px;
    margin-top: 5px;
  }

  .house-info {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: flex-start;
    margin-bottom: 5px;

    span {
      font-size: 12px;
    }

    div {
      font-size: 20px;
      color: red;
    }
  }

  .house-info-else-row {
    margin-right: 5px;
  }

  .house-info-else-item {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: flex-start;
    font-size: 16px;

    /*color:#cccccc;*/
    p {
      margin-right: 10px;
      font-size: 16px;
    }
  }

  .footer {
    float: end;

    van-button {
      margin: 15px;
    }
  }
</style>
