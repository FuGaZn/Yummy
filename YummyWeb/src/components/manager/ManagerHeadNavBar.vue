<template>
  <div>
    <div style="width: 100%; position: absolute; left: 0; top: 0; height: 65px; background-color:#1E89E0">
      <img src="../../assets/logo.png" @click="toApprove" style="width: 170px; margin-top: 5px; position: absolute; left: 30px;cursor: pointer">
      <el-button-group style="position: absolute; left: 240px;">
        <el-button type="text" class="btn" @click="toApprove">审批</el-button>
        <el-button type="text" class="btn" @click="toStatistics">数据</el-button>
      </el-button-group>
      <el-dropdown style="position: absolute; right:40px" @command="handleCommand">
        <el-button type="text" class="btn2" style="">{{merchant_name}}<i class="el-icon-arrow-down el-icon--right"></i></el-button>
        <el-dropdown-menu slot="dropdown" >
          <el-dropdown-item command="log_out">退出登陆</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
  export default {
    name: "manager-head-nav-bar",
    data(){
      return{
        merchant_name:""
      }
    },
    created(){
      this.merchant_name = localStorage.getItem("managerName")
    },
    methods:{
      handleCommand(command){
        if (command == 'log_out') {
          this.$router.push({path: '/merchant/login'})
        }
      },
      toApprove(){
        this.$router.push({path: '/manager/approve'})
      },
      toStatistics(){
        this.$router.push({path: '/manager/statistics'})
      },

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
