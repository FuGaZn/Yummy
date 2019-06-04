<template>
  <div>
    <img src="../../assets/back.jpg" style="position: fixed; left: 0; top: 0; width: 100%; height: 100%"/>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(255,255,255,0.2);"></div>
    <div>
      <merchant-login :to-register="toMerchantRegister" style="position: absolute; left: 50%; margin-left: -175px; top: 100px"></merchant-login>
    </div>
  </div>
</template>

<script>
  import MerchantLogin from '../../components/merchant/MerchantLogin'
  export default {
    components:{MerchantLogin},
    name: "merchant-login-page",
    methods:{
      toMerchantRegister(){
        this.$router.push({path:'/merchant/register'})
      },

    }
  }
</script>

<style scoped>

</style>
