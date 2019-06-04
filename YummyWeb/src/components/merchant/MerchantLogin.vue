<template>
  <div class="label">
    <h1 style="margin-top: 35px">Yummy!<span style="margin-left:10px;font-size: 20px">(Merchants)</span></h1>
    <div>
      <el-input placeholder="merchant serial" class="input" v-model="serial"></el-input>
      <el-input type="password" placeholder="Password" v-model="password" class="input" ></el-input>
      <el-button class="input" type="primary" @click="login">Login</el-button>
      <div style="font-size: 14px; float: left;margin-left: 43px">
        <el-button type="text" @click="resetPasswd">I forget my password</el-button>
        <el-button type="text" @click="toRegister">I haven't registered</el-button>
        <el-button @click="toMemberLogin" style="position: absolute; bottom: 7px; right: 20px" type="text">会员登陆<i style="margin-left: 3px;" class="iconfont">&#xe6c9</i></el-button>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "login",
    props:{
      toRegister:{
        type:Function,
        default: function() {}
      },
      resetPasswd:{
        type:Function,
        default: function () {}
      }
    },
    data() {
      return {
        password:"",
        serial:""

      }
    },
    methods:{
      toMemberLogin(){
        var path = '/'
        this.$router.push({path:path})
      },

      login(){
        if(this.email == ""){
          this.info(2,"ID不能为空")
          return
        }
        if(this.password == ""){
          this.info(2, "密码不能为空")
          return
        }
        this.$http.post('http://localhost:8080/end/merchant/login?serial='+this.serial+"&&password="+this.password).then(result=>{
          localStorage.setItem("merchantID",result.data)
          this.$http.post('http://localhost:8080/end/merchant/get?mid='+result.data).then(result=>{
            var merchant = result.data
            console.log(merchant.name)
            localStorage.setItem("merchantName",merchant.name)
          })
          this.$router.push("/merchant/order-manage")
          window.location.reload()
        })

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
    margin-top: 20px;
  }
</style>
