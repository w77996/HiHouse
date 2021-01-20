<template>
  <div>
    <van-nav-bar
      title="重新选择区域"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
    />
    <div class="main">
      <h4 class="title">选择所在的城市</h4>
      <div class="choose">
        <van-collapse v-model="activeNames">
          <van-collapse-item
            :title="item.provinceName"
            :key="index"
            v-for="(item, index) in city"
            :name="index">
            <div class="city">
              <div
                v-for="(city, idx) in item.cityInfo"
                :key="idx"
                :class="['type', typeActive(city.cityId)]"
                @click="handleClickType(city)">{{ city.city }}
              </div>
            </div>
          </van-collapse-item>
        </van-collapse>
        <van-button type="info"
                    class="step"
                    :disabled="disabled"
                    @click="handleClick">下一步
        </van-button>
      </div>
    </div>
  </div>

</template>

<script>
    export default {
        data() {
            return {
                activeNames: ['0'],
                activeCity: {
                    cityId: '',
                    cityName: ''
                },
                city: [
                    // {
                    //   provinceId: 1,
                    //   provinceName: '广东省',
                    //   cityInfo: [
                    //     {
                    //       cityId: 1001,
                    //       cityName: '广州',
                    //       areaInfo: []
                    //     },
                    //     {
                    //       cityId: 1002,
                    //       cityName: '深圳',
                    //       areaInfo: []
                    //     }
                    //   ]
                    // },
                    // {
                    //   provinceId: 2,
                    //   provinceName: '北京市',
                    //   cityInfo: [
                    //     {
                    //       cityId: 2001,
                    //       cityName: '朝阳区',
                    //       areaInfo: []
                    //     },
                    //     {
                    //       cityId: 2002,
                    //       cityName: '海淀区',
                    //       areaInfo: []
                    //     }
                    //   ]
                    // }
                ]
            }
        },
        computed: {
            disabled() {
                return this.activeCity.cityId === '' && this.activeCity.cityName === ''
            }
        },
        created() {
            this.getConfigCity()
        },
        methods: {
            async getConfigCity() {
                const {data} = await this.$axios.get('/city/config')
                if (data.code === 200) {
                    console.log('object', data)
                    this.city = data.data
                    console.log('city', this.city)
                }
            },
            handleClickType(val) {
                this.activeCity = val
            },
            typeActive(val) {
                return this.activeCity.cityId === val ? 'active' : ''
            },
            onClickLeft() {
                this.$router.go(-1)
            },
            handleClick() {
                console.log("this.$router", this.$router)
                this.$router.push({
                    name: 'SettingSubscribe',
                    query: {
                        cityId: this.activeCity.cityId,
                        cityName: this.activeCity.cityName
                    }
                })
            }
        }
    }
</script>

<style lang="scss" scoped>
  .choose {
    margin-top: 20px;
  }

  .city {
    overflow: hidden;

    .type {
      padding: 8px;
      text-align: center;
      // border: 1px solid #ccc;
      background: rgba(204, 204, 204, .5);
      font-size: 16px;
      float: left;
      margin-right: 8px;
    }

    .active {
      background: rgba(25, 137, 250, .3);
      color: #1989fa;
    }
  }

  .step {
    width: 100%;
    margin: 20px 0;
  }
</style>
