<template>
  <div>
    <div style="width: 100%; position: absolute; left: 0; top: 0; height: 65px;" :style="backgroundColor">
      <img src="../../assets/logo.png" @click="goMarket" style="width: 170px; margin-top: 5px; position: absolute; left: 30px;cursor: pointer">
      <el-button-group style="position: absolute; left: 240px;">
        <el-button type="text" class="btn" @click="goMarket">首页</el-button>
        <el-button type="text" class="btn" @click="myOrder">我的订单</el-button>
      </el-button-group>
      <el-dropdown style="position: absolute; right:40px" @command="handleCommand">
        <el-button type="text" class="btn2" style="font-size: 14px">{{username}}<i class="el-icon-arrow-down el-icon--right"></i></el-button>
        <el-dropdown-menu slot="dropdown" >
          <el-dropdown-item command="personal_center">个人中心</el-dropdown-item>
          <el-dropdown-item command="my_address">我的地址</el-dropdown-item>
          <el-dropdown-item command="log_out">退出登陆</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
  export default {
    name: "main-nav-bar",
    props:{
      backgroundColor:{
        type:"",
        default:"background-color:#1E89E0"
      }
    },
    data(){
      return{
        username:""
      }
    },
    mounted(){
      this.username = localStorage.getItem("username")
    },
    methods:{
      personalCenter(){
        this.$router.push({path:'/main-page'})
      },
      myOrder(){
        this.$router.push({path: '/order'});
      },
      myData(){
        this.$router.push({path: '/data'});
      },
      goMarket(){
        this.$router.push({path: '/market'})
      },
      handleCommand(command){
        if (command == "personal_center"){
          this.personalCenter()
        }else if(command == "my_address"){
          this.myData()
        }else{
          this.$router.push("/login")
        }
      }
    }
  }
</script>

<style scoped>
.btn{
  font-size: 16px;
  color:white;
  width: 100px;
  height: 65px;
  border-radius: 0;
}
.btn2{
  width: 200px;
  color: white;height: 65px;

}
  .btn:hover{
    background-color: #1074E0;
  }
  .btn:focus{
    background-color: #1074E0;
  }
</style>
