<template>
  <div class="label">
    <h1 style="margin-top: 35px">Yummy!</h1>
    <div>
      <el-input placeholder="Email Address" class="input" v-model="email"></el-input>
      <el-input type="password" placeholder="New Password" v-model="password" class="input" ></el-input>
      <el-input type="password" placeholder="Password Again" v-model="passwdAgain" class="input" ></el-input>
      <div style="margin-left: 40px">
        <el-input style="width: 185px; float: left" placeholder="Verify Code" v-model="verify_code" class="input"></el-input>
        <el-button @click="sendVerifyCode" style="float: left; margin-left: 5px; width: 80px" type="primary" :loading="sendingVerify">{{sendingBtnContext}}</el-button>
      </div>
      <el-button class="input" type="primary" @click="resetPasswd">Sure</el-button>
      <div style="font-size: 14px; float: left;margin-left: 43px;margin-top: -20px">
        <el-button class="input" type="text" @click="toLogin">return</el-button>
      </div>
      <el-button @click="toMerchantLogin" style="position: absolute; bottom: 7px; right: 20px" type="text">商家登陆<i style="margin-left: 3px;" class="iconfont">&#xe6c9</i></el-button>

    </div>
  </div>
</template>

<script>
  export default {
    name: "reset-passwd",
    props:{
      toLogin:{
        type:Function,
        default: function() {}
      }
    },
    data() {
      return {
        password:"",
        passwdAgain:"",
        email:"",
        verify_code:"",
        sendingVerify:false,
        sendingBtnContext:"Send"
      }
    },
    methods:{
      toMerchantLogin(){
        var path = '/merchant/login'
        this.$router.push({path: path})
      },
      sendVerifyCode(){
        console.log("here")
        this.resetTime()
      },

      resetTime () {
        var _this = this
        var timer = null
        var t = 60
        var m = 0
        var s = 0
        m = Math.floor(t / 60 % 60)
        m < 10 && (m = '0' + m)
        s = Math.floor(t % 60)
        function countDown () {
          s--
          s < 10 && (s = '0' + s)
          if (s.length >= 3) {
            s = 59
            m = '0' + (Number(m) - 1)
          }
          if (m.length >= 3) {
            m = '00'
            s = '00'
            clearInterval(timer)
          }

          if (parseInt(s) != 0) {
            _this.sendingBtnContext = s + 's'
            _this.sendingVerify = true
          } else {
            _this.sendingBtnContext = 'Resend'
            _this.sendingVerify = false
          }
        }
        timer = setInterval(countDown, 1000)
      },

      resetPasswd() {
        console.log("here" + this.passwdAgain)
        if (this.email == "") {
          this.info(2, "邮箱不能为空")
          return
        }
        if (this.password == "") {
          this.info(2, "密码不能为空")
          return
        }

        if (this.passwdAgain == "") {
          this.info(2, "请再次确认密码")
          return
        }
        if (this.verify_code == "") {
          this.info(2, "验证码不能为空")
          return
        }
      },

      info(type, content){
        if(type === 0){   //normal info
          this.$message(content)
        }else if(type === 1){
          this.$message({
            message: content,
            type : 'success'
          })
        }else if(type === 2){
          this.$message({
            message: content,
            type: 'warning'
          });
        }else if(type === 3){
          this.$message.error(content);
        }
      }
    }
  }
</script>

<style scoped>
  .label{
    background-color: #ffffff;
    width: 350px;
    height: 430px;
    border-radius: 5px;
  }
  .input{
    width: 270px;
    height: 40px;
    margin-bottom: 20px;
  }
</style>
