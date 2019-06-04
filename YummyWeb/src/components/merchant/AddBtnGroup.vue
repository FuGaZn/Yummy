<template>
  <div>
    <el-button @click="click" class="btn" type="text" ><i class="el-icon-circle-plus" ></i>
    </el-button>
  </div>
</template>

<script>
  export default {
    name: "add-btn-group",
    methods:{
      click(){
        this.$router.push({path: '/merchant/add'})
      }
    }
  }
</script>

<style scoped>
.btn{
  color: #ff822f; font-size: 50px;font-weight: bold;
}
  .btn:hover{
    color: #ff5809;
  }
  .btn:active{
    color: #ff3f0e;
  }
</style>
