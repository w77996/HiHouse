<template>
  <div class="sub">
    <h4 class="title">选择房源类型（多选）</h4>
    <div class="house">
      <div
        v-for="(item, index) in typeList"
        :key="index"
        :class="['type', typeActive('type', item)]"
        @click="handleClickType('type', item)">{{ item.name }}</div>
    </div>
    <h4 class="title">选择房源用途（多选）</h4>
    <div class="house">
      <div
        v-for="(item, index) in userList"
        :key="index"
        :class="['type', typeActive('use', item)]"
        @click="handleClickType('use', item)">{{ item.name }}</div>
    </div>
    <h4 class="title">选择区域（多选）</h4>
    <div class="house">
      <div
        v-for="(item, index) in areaInfo"
        :key="index"
        :class="['type', typeActive('area', item)]"
        @click="handleClickType('area', item)">{{ item.area }}</div>
    </div>
    <van-button type="info"
        class="step"
        :disabled="!disabled"
        @click="handleClick">下一步</van-button>
  </div>
</template>

<script>
    import localStorage from '@/utils/store/localStorage'
import { setTitle } from '@/utils/utils.js'
export default {
  data () {
    return {
      typeList: [
        {
          name: '出售',
          value: 1
        }
        // {
        //   name: '出租',
        //   value: 2
        // }
      ],
      type: [],
      userList: [
        {
          name: '住宅',
          value: 1
        }
        // {
        //   name: '写字楼',
        //   value: 2
        // }
      ],
      use: [],
      areaInfo: [
        {
          areaId: 1001,
          areaName: '南山区',
          businessCircleInfo: []
        },
        {
          areaId: 1002,
          areaName: '罗湖区',
          businessCircleInfo: []
        },
        {
          areaId: 1003,
          areaName: '宝安区',
          businessCircleInfo: []
        }
      ],
      area: []
    }
  },
  computed: {
    disabled () {
      return this.area.length > 0 && this.use.length > 0 && this.type.length > 0
    }
  },
  created () {
    setTitle('订阅区域')
    const cityId = this.$route.query.cityId
    // const cityName = this.$route.query.cityName
    this.getConfigCity(cityId)
  },
  methods: {
    async getConfigCity (val) {
      const { data } = await this.$axios.get('/city/getBusinessByCityId?cityId=' + val)
      if (data.code === 200) {
        console.log('object', data)
        this.areaInfo = data.data
        console.log('city', this.areaInfo)
      }
    },
    handleClickType (list, obj) {
      if (!this[list].includes(obj)) {
        this[list].push(obj)
      } else {
        const typeIndex = this[list].findIndex(item => item.value === obj.value)
        this[list].splice(typeIndex, 1)
      }
      console.log(list, this[list])
    },
    typeActive (list, obj) {
      return this[list].includes(obj) ? 'active' : ''
    },
    async handleClick () {
      const publishTypes = this.type.reduce((prev, cur) => {
        prev.push(cur.value)
        return prev
      }, [])
      const houseTypes = this.use.reduce((prev, cur) => {
        prev.push(cur.value)
        return prev
      }, [])
      const params = {
        publishTypes: publishTypes,
        houseTypes: houseTypes,
        cityId: this.$route.query.cityId,
        cityName: this.$route.query.cityName,
        subscribeCityInfo: this.area
      }
      const { data } = await this.$axios.post('/city/subscribe', params)
      // console.log('object', data)
      if (data.code === 200) {
        console.log(data.data)
        localStorage.get("openId")
        localStorage.get("token")
        let user = localStorage.get("user")
        localStorage.set("openId",user.mpOpenId)
        this.$router.push({
          name: 'Cloud'
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.sub{
  margin: 20px;
  .title{
    font-size: 20px;
    margin-top: 20px;
  }
  .house{
    margin-top: 20px;
    clear: both;
    overflow: hidden;
    div{
      margin-bottom: 10px;
    }
    .type{
      padding: 8px;
      width: 80px;
      text-align: center;
      // border: 1px solid #ccc;
      background: rgba(204, 204, 204, .5);
      font-size: 16px;
      float: left;
      margin-right: 10px;
    }
    .active{
      background: rgba(25, 137, 250, .3);
      color: #1989fa;
    }
  }
  .step{
    width: 100%;
    margin: 20px 0;
  }
}
</style>
