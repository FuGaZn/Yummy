<template>
  <div style="width: 150px; position: absolute; top: 140px;left: 20px; color: grey;height: 200px">
    <el-button @click="personalCenter" style="position: absolute; left: 20px;" type="text" class="btn"><i class="el-icon-ali-fangzi icon"></i>个人中心</el-button>
    <el-button @click="myOrders" style="position: absolute; left: 10px;top: 70px;" type="text" class="btn"><i class="el-icon-ali-zhangdan1 icon"></i>我的订单</el-button>
    <el-button @click="myAccount" style="position: absolute; left: 10px; top: 140px;" type="text" class="btn">
      <i class="el-icon-ali-qian icon"></i>我的账户
    </el-button>
    <el-button @click="myData" style="position: absolute; left: 10px; top: 210px;" type="text" class="btn">
      <i class="el-icon-ali-icon-user icon"></i>我的资料
    </el-button>
  </div>
</template>

<script>
  export default {
    name: "left-nav-bar",
    props:{
      personalCenter:{
        type:Function,
        default: function() {}
      },
      myOrders:{
        type:Function,
        default: function() {}
      },
      myAccount:{
        type:Function,
        default: function() {}
      },
      myData:{
        type:Function,
        default: function() {}
      },
      statistics:{
        type:Function,
        default:function () {

        }
      }
    }
  }
</script>

<style scoped>
  .btn{
    font-size: 20px;
    font-weight: bold;
    color: #646464;
  }
  .btn:hover{
    color: #1E89E0;
  }
  .btn:focus{
    color: #1E89E0;
  }
  .icon{
    font-size: 20px;
    margin-right: 5px
  }

</style>
