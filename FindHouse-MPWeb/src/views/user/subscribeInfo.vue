<template>
  <div>
    <van-nav-bar
      title="订阅区域"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
    />
    <van-cell
      title="订阅城市"
      title-class="title-class"
      clickable
      size="large">
      <template #default>
        <div>{{city}}</div>
      </template>
    </van-cell>
    <van-cell
      title="订阅区域"
      title-class="title-class"
      :border=true
      clickable
      size="large">
      <template #label>
        <div class="house">
          <div
            v-for="(item, index) in list"
            :key="index"
            class="type">{{item}}</div>
        </div>
      </template>
    </van-cell>
  </div>

</template>

<script>
    import localStorage from '@/utils/store/localStorage'

    export default {
        name: "subscribeInfo",
        data() {
            return {
                city: '',
                list: []
            }
        },
        methods: {
            onClickLeft() {
                this.$router.go(-1)
            },
        },
        created() {
            let user = localStorage.get('user')
            let length = user.subscribeInfo.subscribeCityInfo;
            this.city = user.subscribeInfo.subscribeCityInfo[0].city
            user.subscribeInfo.subscribeCityInfo.forEach((item,index) => {
                console.log(index)
                this.list.push(item.business)
                // if( length-1 != index){
                //     this.list  = this.list + item.business + "\n"
                // }else{
                //     this.list  = this.list +item.business
                // }

            })
        }
    }
</script>

<style lang="scss" scoped>
  .house {
    margin-top: 20px;
    clear: both;
    overflow: hidden;

    div {
      margin-bottom: 5px;
    }

    .type {
      padding: 8px;
      width: 50px;
      line-height: 14px;
      text-align: center;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
      // border: 1px solid #ccc;
      background: rgba(204, 204, 204, .5);
      font-size: 12px;
      float: left;
      margin-right: 15px;
    }

  }
</style>
