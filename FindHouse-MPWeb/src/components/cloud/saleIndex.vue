<template>
  <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
  <van-list
    v-model="loading"
    :finished="finished"
    finished-text="没有更多了"
    @refersh="onRefresh"
    @load="onLoad"
    offset="300"
    :error.sync="error"
    error-text="请求失败，点击重新加载">
    <house-item v-for='(item, index) in list' :key="index" :houseItem="item">
<!--      <div>{{item.title}}-->
<!--      </div>-->
<!--      <house-item ></house-item>-->
    </house-item>
  </van-list>
  </van-pull-refresh>
</template>

<script>
    import houseItem from '../house/index'

    export default {
        name: 'saleIndex',
        components: { houseItem },
        data() {
            return {
                refreshing: false,
                loading: false, 		// 是否处在加载状态
                finished: false, 		// 是否已加载完成
                error: false, 		// 是否加载失败
                list: [],				// 列表
                page: 1,				// 分页
                size: 30,		// 每页条数
                total: 0				// 数据总条数
            }
        },
        methods: {
            // 获取列表数据方法
            async getList() {
                const {data} = await this.$axios.get('/sale/list', {
                    params: {
                        page: this.page,
                        size: this.size
                    }
                })
                console.log('res', data)
                // if(data.code != 200){
                //     this.finished = true
                //     this.loading = false
                //     this.refreshing = false
                //     return;
                // }
                // let {data: res} = await informList({
                //     page: this.page,
                //     size: this.size,
                // })
                if(data.data == null ){
                    this.finished = true
                    this.loading = false
                    this.refreshing = false
                    return;
                }
                if (data.data.length === 0) { // 判断获取数据条数若等于0
                    // this.list = [];				// 清空数组
                    this.finished = true		// 停止加载
                    this.refreshing = false
                }
                // 若数据条数不等于0
                // this.total = res.total;		// 给数据条数赋值
                this.list.push(...data.data)	// 将数据放入list中
                this.loading = false			// 加载状态结束
                this.refreshing = false
                // 如果list长度大于等于总数据条数,数据全部加载完成
                // if (this.list.length >= res.total) {
                //     this.finished = true;		// 结束加载状态
                // }
            },
            // 被 @load调用的方法
            onLoad() { // 若加载条到了底部
                // const timer = setTimeout(() => {	// 定时器仅针对本地数据渲染动画效果,项目中axios请求不需要定时器
                this.getList()					// 调用上面方法,请求数据
                this.page++					// 分页数加一
                //   this.finished && clearTimeout(timer)// 清除计时器
                // }, 100)
            },
            // 加载失败调用方法
            onRefresh() {
                this.finished = false 		// 清空列表数据
                this.loading = true 			// 将 loading 设置为 true，表示处于加载状态
                this.page = 1				// 分页数赋值为1
                this.list = []				// 清空数组
                this.onLoad() 				// 重新加载数据
            },

            async getSaleList() {
                const {data} = await this.$axios.get('/sale/list', {
                    params: {
                        page: this.page,
                        size: this.size
                    }
                })
                if (data.code === 200) {
                    console.log('object', data)
                    this.city = data.data
                    console.log('city', this.city)
                }
            }
        }
    }
</script>

<style scoped>
  ::-webkit-scrollbar {
    width: 0 !important;
  }

  ::-webkit-scrollbar {
    width: 0 !important;
    height: 0;
  }

</style>
