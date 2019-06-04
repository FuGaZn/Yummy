<template>
  <div class="label">
    <h1 style="margin-top: 35px">Yummy!</h1>
    <div>
      <el-input placeholder="Email Address" class="input" v-model="email"></el-input>
      <el-input type="password" placeholder="Password" v-model="password" class="input" ></el-input>
      <el-button class="input" type="primary" @click="login">Login</el-button>
      <div style="font-size: 14px; float: left;margin-left: 43px">
      <el-button type="text" @click="resetPasswd">I forget my password</el-button>
        <el-button type="text" @click="toRegister">I haven't registered</el-button>
        <el-button @click="toMerchantLogin" style="position: absolute; bottom: 7px; right: 20px" type="text">商家登陆<i style="margin-left: 3px;" class="iconfont">&#xe6c9</i></el-button>
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
          email:"",

        }
      },
      methods:{
        toMerchantLogin(){
          var path = '/merchant/login'
          this.$router.push({path: path})
        },
        login(){
          if(this.email == ""){
            this.info(2,"邮箱不能为空")
            return
          }
          if(this.password == ""){
            this.info(2, "密码不能为空")
            return
          }
          var path = 'http://localhost:8080/end/user/login?email='+this.email+"&&password="+this.password
          this.$http.post(path).then(result=>{
            let response = result.data;
            console.log(response)
            if(response == null || response == "") {
              var path2 = 'http://localhost:8080/end/manager/login?email='+this.email+"&&password="+this.password
              this.$http.post(path2).then(result=>{
                if (result.data == null || result.data == ""){
                  this.info(3, "登陆失败")
                }else{
                  localStorage.setItem("managerID",result.data)
                  localStorage.setItem("managerName",this.email)
                  this.$router.push('/manager/approve')
                }
              })
            } else{
              this.info(1, "登陆成功")
              localStorage.setItem("userEmail", this.email)
              localStorage.setItem("username",this.email)
              localStorage.setItem("userID",response)
              this.$router.push({path: '/main-page'})
            }
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
