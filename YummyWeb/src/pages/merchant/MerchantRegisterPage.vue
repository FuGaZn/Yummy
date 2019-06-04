<template>
  <div>
    <img src="../../assets/back.jpg" style="position: fixed; left: 0; top: 0; width: 100%; height: 100%"/>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(255,255,255,0.2);"></div>
    <div style="position: absolute; left: 50%; margin-left: -300px; top: 60px">
      <merchant-register></merchant-register>
    </div>
  </div>

</template>

<script>
  import MerchantRegister from "../../components/merchant/MerchantRegister"
   export default {
    components:{MerchantRegister},
        name: "merchant-register-page"
    }
</script>

<style scoped>

</style>
