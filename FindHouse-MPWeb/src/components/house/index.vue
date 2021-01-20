<template>
  <van-cell
    :title="house.title"
    title-class="title-class"
    :border=true
    @click="onCellClick(house)"
    clickable
    size="large">
    <template #label>
      <div class="house-info-local">{{house.area}}-{{house.business}} {{house.xiaoquName}}</div>
      <div class="house-info-detail"><span>{{house.huxing}}</span>  <span> {{house.mainji}}</span>  <span> {{house.floor}}</span></div>
    </template>
    <template #default>
      <div class="right-value">
        <div class="item-price">{{house.price}}</div>
        <div class="item-danjia">{{house.danjia}}</div>
      </div>
    </template>
  </van-cell>
</template>

<script>
    export default {
        name: 'index',
        props: ['houseItem'],
        data() {
            return {
                house: this.houseItem
            }
        },
        created() {
            // console.log('house index ',this.houseItem)
        },
        methods: {
            onCellClick(event) {
                // window.location.href = event.url
                console.log(event)
                this.$router.push({
                    name: 'HouseDetail',
                    query: {
                        id: event.id
                    }
                })
            }
        }
    }
</script>

<style scoped lang="scss">
  .title-class {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    min-width: 70%
  }

  .label-class {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .default-class {
    bottom: 0px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .value-class {
    bottom: 0px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .right-value {
    /*display: inline-block;*/
    display: flex;
    flex-direction: column;
    text-align: right;
    justify-content: space-between;
    align-items: flex-end;
    width: 100%;
    height: 100%;


  }

  .item-div {

    line-height: 0.32rem;
    height: 0.24rem;
    width: 100%

  }

  .item-price {

    line-height: 0.52rem;
    height: 0.24rem;
    width: 100%;
    font-size: 20px;
    color: red;

  }

  .house-info-local{
    width: 100%;
    margin-top: 5px;
    font-size: 14px;
  }
  .house-info-detail{
    width: 100%;
    margin-top: 5px;
    span{
      font-size: 12px;
      margin-right: 10px;
    }
  }

</style>
