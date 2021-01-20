<template>
  <div>
    <van-tabs v-model="active">
      <!--      <van-tab title="出租">出租</van-tab>-->

      <van-tab title="出售">
        <sale-index></sale-index>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
    import SaleIndex from '../../components/cloud/saleIndex'
    import localStorage from '@/utils/store/localStorage'
    import {Dialog} from 'vant';
    import {mapGetters} from 'vuex'
    export default {
        components: {SaleIndex},
        data() {
            return {
                active: 0
            }
        },
        computed: {
            ...mapGetters(['showCreate'])
        },
        created() {

            console.log("create")

            let user = localStorage.get("user")
            if(user != null && this.showCreate == false){
                if(user.thirdDayFree == true){
                    Dialog.alert({
                        title: '新用户优惠',
                        message: `新用户注册黑客找房，已免费赠送100次推送机会`,
                    })
                }

            }
            this.$store.commit('updateShowCreate');
        }
    }
</script>

<style>
  ::-webkit-scrollbar {
    width: 0 !important;
  }

  ::-webkit-scrollbar {
    width: 0 !important;
    height: 0;
  }

</style>
