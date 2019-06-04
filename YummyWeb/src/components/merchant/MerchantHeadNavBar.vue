<template>
  <div>
    <div style="width: 100%; position: absolute; left: 0; top: 0; height: 65px; background-color:#1E89E0">
      <img src="../../assets/logo.png" @click="toOrderManage" style="width: 170px; margin-top: 5px; position: absolute; left: 30px;cursor: pointer">
      <span style="position: absolute; left: 210px;top: 18px; color: white; font-size: 20px; cursor: pointer" @click="toMainPage">商家版</span>
      <el-button-group style="position: absolute; left: 300px;">
        <el-button type="text" class="btn" @click="toOrderManage">订单</el-button>
        <el-button type="text" class="btn" @click="toGoods">商品</el-button>
        <el-button type="text" class="btn" @click="toFinanceManage">财务</el-button>
      </el-button-group>
      <el-dropdown style="position: absolute; right:40px" @command="handleCommand">
        <el-button type="text" class="btn2" style="">{{merchant_name}}<i class="el-icon-arrow-down el-icon--right"></i></el-button>
        <el-dropdown-menu slot="dropdown" >
          <el-dropdown-item command="base_info">基本信息</el-dropdown-item>
          <el-dropdown-item command="log_out">退出登陆</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
  export default {
    name: "merchant-head-nav-bar",
    data(){
      return{
        merchant_name:"test"
      }
    },
    created(){
      this.merchant_name = localStorage.getItem("merchantName")
    },
    methods:{
      handleCommand(command){
        if (command == 'log_out'){
          this.$router.push({path: '/merchant/login'})
        }else if(command == 'base_info'){
          this.toBaseInfo()
        }
      },
      toBaseInfo(){
        this.$router.push({path: '/merchant/base-info'})
      },
      toOrderManage(){
        this.$router.push({path:'/merchant/order-manage'})
      },
      toMainPage(){
        this.$router.push({path: '/merchant/main-page'})
      },
      toFinanceManage(){
        this.$router.push({path:'/merchant/finance'})
      },
      toStatistics(){
        this.$router.push({path: '/merchant/statistics'})
      },
      toGoods(){
        this.$router.push({path:'/merchant/goods'})
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
    font-size: 14px;
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
