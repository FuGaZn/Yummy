<template>
  <div>
    <img src="../assets/back.jpg" style="position: fixed; left: 0; top: 0; width: 100%; height: 100%"/>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(255,255,255,0.2);"></div>
    <div>
      <div style="position: absolute; left: 50%; margin-left: -175px; top: 100px">
        <register v-show="isRegister" :to-login="toLogin" style="position: absolute; left: 0px; top: 0px;"></register>
        <login v-show="!isRegister && !forgetPasswd" :reset-passwd="toResetPasswd" :to-register="toRegister" style="position: absolute; left: 0px; top: 0px;"></login>
        <reset-passwd v-show="forgetPasswd" :to-login="toLogin" style="position: absolute; left: 0px; top: 0px;"></reset-passwd>
      </div>
    </div>
  </div>
</template>

<script>
  import Login from "../components/common/Login"
  import Register from '../components/common/Register'
  import ResetPasswd from '../components/common/ResetPasswd'
  export default {
    components: {Login,Register,ResetPasswd},
    name: "login-page",

    data(){
      return{
        isRegister:false,
        forgetPasswd:false
      }
    },
    methods:{
      toRegister(){
        this.isRegister=true
      },
      toLogin(){
        this.isRegister=false
        this.forgetPasswd=false
      },
      toResetPasswd(){
        this.forgetPasswd=true
      }
    }

  }
</script>

<style scoped>

</style>
