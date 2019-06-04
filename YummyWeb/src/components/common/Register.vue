<template>
  <div class="label">
    <h1 style="margin-top: 35px">Yummy!</h1>
    <div>
      <el-input @blur="checkEmail" placeholder="Email Address" class="input" v-model="email"></el-input>
      <span v-show="isEmailErrorShow" style="font-size: 10px;color: red;position: absolute; left: 42px; top: 133px;">邮箱格式错误</span>
      <el-input type="password" placeholder="Password" v-model="password" class="input" ></el-input>
      <div style="margin-left: 40px">
        <el-input @blur="checkVerify"  style="width: 185px; float: left" placeholder="Verify Code" v-model="verify_code" class="input"></el-input>

        <el-button @click="sendVerifyCode" style="float: left; margin-left: 5px; width: 80px" type="primary" :loading="sendingVerify">{{sendingBtnContext}}</el-button>
      </div>
      <el-button class="input" type="primary" @click="register">Register</el-button>
      <div style="font-size: 14px; float: left;margin-left: 43px;margin-top: -20px">
        <el-button type="text" @click="toLogin">I already have an account</el-button>
      </div>
      <el-button @click="toMerchantLogin" style="position: absolute; bottom: 7px; right: 20px" type="text">商家登陆<i style="margin-left: 3px;" class="iconfont">&#xe6c9</i></el-button>
      <span v-show="isErrorShow" style="font-size: 10px;color: red;position: absolute; left: 42px; top: 255px;">验证码不正确</span>
    </div>
  </div>
</template>

<script>
  export default {
    name: "register",
    props:{
      toLogin:{
        type:Function,
        default: function() {}
      }
    },
    data() {
      return {
        password:"",
        email:"",
        verify_code:"",
        standard_verify_code:"123456",
        sendingVerify:false,
        isErrorShow:false,
        isEmailErrorShow:false,
        sendingBtnContext:"Send"
      }
    },
    methods:{
      trimStr(str){return str.replace(/(^\s*)|(\s*$)/g,"");},
      checkEmail() {
        this.isEmailErrorShow = false
        var pattern = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
        if(this.email != ""){
          if (!pattern.test(this.email)){
            this.isEmailErrorShow = true;
          }
        }

      },
      checkVerify(){
        if (this.verify_code == "" ) {
          this.isErrorShow = false;
          return
        }
        if(this.standard_verify_code != this.verify_code) {
          this.isErrorShow = true
          return
        }
        if(this.standard_verify_code == this.verify_code){
          this.isErrorShow = false
        }
      },
      toMerchantLogin(){
        var path = '/merchant/login'
        this.$router.push({path: path})
      },
      sendVerifyCode(){
        this.email = this.trimStr(this.email)
        if (this.email == null || this.email == ""){
          this.info(3,"请填入邮箱")
          return
        }
        var path = 'http://localhost:8080/end/user/send?email='+this.email
        this.$http.post(path).then(result=>{
          let response = result.data;
          this.standard_verify_code = response
        })
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

      register(){
        if(this.email == ""){
          this.info(2,"邮箱不能为空")
          return
        }
        if(this.password == ""){
          this.info(2, "密码不能为空")
          return
        }
        if(this.verify_code == ""){
          this.info(2, "验证码不能为空")
        }
        {
          var path = "http://localhost:8080/end/user/register?email="+this.email+"&&password="+this.password
          this.$http.post(path).then(result=>{
            let response = result.data;
            if(response == null || response == "") {
              this.info(3, "注册失败")
            }else {
              this.info(1, "注册成功，请继续完善个人信息")
              localStorage.setItem("userEmail", this.email)
              localStorage.setItem("username", this.email)
              localStorage.setItem("userID",response)
              this.$router.push({path: '/data'})
              window.location.reload('/data')
            }
          })
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
    height: 400px;
    border-radius: 5px;
  }
  .input{
    width: 270px;
    height: 40px;
    margin-bottom: 20px;
  }
</style>
