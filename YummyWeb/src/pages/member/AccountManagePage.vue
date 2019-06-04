<template>
  <div>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <head-nav-bar></head-nav-bar>
    <left-nav-bar :personal-center="personalCenter" :my-account="myAccount" :statistics="toStatistic" :my-data="myData" :my-orders="myOrder"></left-nav-bar>
    <div style="font-size: 13px; color: black; position: absolute; top: 85px; left: 200px">>&nbsp&nbsp{{nowOperate}}</div>
    <div style="position: absolute; width: 1000px; height: 520px; top: 120px; left: 200px; background-color: white">
      <div>
        <h1 style="font-size: 20px; position: absolute; left: 30px; top: 0px">账户余额</h1>
        <div style="position: absolute; left: 30px;top: 60px; width: 940px; height: 80px; background-color: rgba(233,233,233,0.5);border: 1px solid rgba(178,178,178,0.5);border-radius: 3px">
          <span style="position: absolute; left: 30px; top: 28px; font-size: 14px">当前账户余额：&nbsp&nbsp<span style="color: red; font-weight: bold; font-size: 20px">{{balance}}</span>&nbsp&nbsp元</span>
        </div>
        <el-button @click="dialogVisible = true" type="primary" style="font-size: 14px; position: absolute; right: 50px; width: 80px; top: 10px;" size="small">充值</el-button>
        <h1 style="font-size: 20px; position: absolute; left: 30px; top: 150px;">充值记录</h1>

      </div>
      <el-table :data="records" style="position: absolute; top: 200px;width: 940px; left: 30px" max-height="300">
        <el-table-column label="充值时间" prop="time" sortable></el-table-column>
        <el-table-column label="充值金额" prop="chargeMoney" align="center" sortable></el-table-column>
        <el-table-column label="余额" prop="balance" align="center" sortable></el-table-column>
      </el-table>
    </div>
    <div style="position: absolute; width: 1000px; height: 100px; top: 600px; left: 200px;"></div>
    <shopping-cart></shopping-cart>
    <el-dialog title="充值" :visible.sync="dialogVisible" width="360px">
      <el-input onkeyup="this.value=this.value.toString().match(/^\d+(?:\.\d{0,2})?/)" style="width: 200px;" v-model="chargeMoney"></el-input>
      <el-button @click="charge" type="primary">确定</el-button>
    </el-dialog>
  </div>
</template>

<script>
  import HeadNavBar from '../../components/member/HeadNavBar'
  import LeftNavBar from '../../components/member/LeftNavBar'
  import ShoppingCart from '../../components/member/ShoppingCart'
  export default {
    name: "account-manage-page",
    components:{HeadNavBar,LeftNavBar,ShoppingCart},
    data() {
      return {
        nowOperate: '我的账户',
        balance: 10,
        chargeMoney:100,
        records: [],
        dialogVisible:false
      }
    },
    created(){
      var path = "http://localhost:8080/end/user/get?uid="+localStorage.getItem("userID")
      this.$http.post(path).then(result=>{
        var user = result.data
        this.balance = user.balance;
        this.records = user.chargeRecords;
      })
    },
    methods:{
      charge(){
        Date.prototype.format = function(fmt) {
          var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
          };
          if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
          }
          for(var k in o) {
            if(new RegExp("("+ k +")").test(fmt)){
              fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
          }
          return fmt;
        }
        var time = new Date().format("yyyy-MM-dd hh:mm:ss");
        var record = {
          crid:0,
          time:time,
          uid:localStorage.getItem("userID"),
          chargeMoney:parseFloat(this.chargeMoney),
          balance:parseFloat(this.chargeMoney)+this.balance
        }
        this.balance = record.balance;
        var path = "http://localhost:8080/end/user/get?uid="+localStorage.getItem("userID")
        this.$http.post(path).then(result=>{
          var user = result.data
          user.balance = this.balance
          user.chargeRecords.push(record)
          var path = "http://localhost:8080/end/user/update/user"
          this.$http.post(path,user).then(result=>{
            this.dialogVisible = false;
            this.records.push(record)
            this.info(2,'充值成功，充值金额 '+this.chargeMoney+' 元')
          })
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
      },
      personalCenter(){
        this.$router.push({path:'/main-page'})
      },
      myOrder(){
        this.$router.push({path: '/order'});
      },
      myAccount(){
        this.$router.push({path: '/account'});
      },
      myData(){
        this.$router.push({path: '/data'});
      },
      toStatistic(){
        this.$router.push({path: '/statistics'});
      }
    }
  }
</script>

<style scoped>

</style>
