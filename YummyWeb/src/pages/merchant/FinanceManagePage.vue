<template>
  <div>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <merchant-head-nav-bar></merchant-head-nav-bar>
    <div style="position: absolute; width: 1100px; height: 100px; background-color: white;
      left: 50%; margin-left: -550px; top: 80px; border: 1px solid rgba(164,164,164,0.5); border-radius: 3px">
      <span style="position: absolute; top: 28px; width: 450px; left: 60px;text-align: left">账户余额:&nbsp&nbsp
        <span style="color: #ff474e;font-weight: bold">￥
          <span style="font-size: 40px; font-weight: bold">{{balance}}</span>
        </span>
      </span>
      <span style="position: absolute; top: 28px; width: 600px; left: 450px;text-align: left">今日营收:&nbsp&nbsp
        <span style="color: #ff822f;font-weight: bold">￥
          <span style="font-size: 40px; font-weight: bold">{{todayEarning}}</span>
        </span>
      </span>
      <el-button size="mini" type="success" style="font-size: 14px;width: 80px;position: absolute; right: 60px;top: 34px;">提现</el-button>
    </div>
    <div style="position: absolute; width: 1100px; height: 350px; background-color: white; left: 50%;
       margin-left: -550px; top: 200px;border: 1px solid rgba(164,164,164,0.5); border-radius: 3px">
      <span style="position: absolute; left: 50px; top: 20px; font-weight: bold">余额流水</span>
      <div style="position: absolute; left: 250px; top: 20px;">
        <el-radio v-model="showType" label="0">全部</el-radio>
        <el-radio v-model="showType" label="1">收入</el-radio>
        <el-radio v-model="showType" label="2">支出</el-radio>
      </div>
      <div>
        <el-date-picker size="small" style="position: absolute; right: 50px; top: 16px;"
                        v-model="showDate"
                        type="daterange"
                        range-separator="-"
                        start-placeholder="start"
                        end-placeholder="end">
        </el-date-picker>
      </div>
      <el-table stripe border :data="accountRecords" size="small" style="width: 1000px;position: absolute; left: 50px; top: 70px;" max-height="250">
        <el-table-column label="创建时间" prop="date" align="center" width="200"></el-table-column>
        <el-table-column label="类型" prop="type" align="center" width="150"></el-table-column>
        <el-table-column label="金额" prop="money" align="center" width="150"></el-table-column>
        <el-table-column label="余额" prop="balance" align="center" width="150"></el-table-column>
        <el-table-column label="备注" prop="tips"></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
  import MerchantHeadNavBar from '../../components/merchant/MerchantHeadNavBar'
  import ElRadio from "element-ui/packages/radio/src/radio";

  export default {
    name: "finance-manage-page",
    components:{
      ElRadio,
      MerchantHeadNavBar},
    data(){
      return{
        balance:1024.25,
        todayEarning:888.66,
        showType:'0',
        showDate:{

        },
        merchant:{},
        accountRecords:[
          {
            date:'2019-03-01',
            type:'支出',
            money:'-1000',
            balance:'1024',
            tips:'提现至银行卡'
          },
          {
            date:'2019-03-08',
            type:'收入',
            money:'+821',
            balance:'1845',
            tips:'昨日营收'
          },
          {
            date:'2019-03-08',
            type:'支出',
            money:'-1000',
            balance:'845',
            tips:'提现至银行卡'
          },
          {
            date:'2019-03-09',
            type:'支出',
            money:'+1063',
            balance:'1908',
            tips:'昨日营收'
          },
          {
            date:'2019-03-10',
            type:'收入',
            money:'+666',
            balance:'2574',
            tips:'昨日营收'
          },
          {
            date:'2019-03-11',
            type:'支出',
            money:'-1000',
            balance:'1574',
            tips:'提现至银行卡'
          },
        ]
      }
    },
    created(){
      var id = localStorage.getItem("merchantID")
      this.$http.post('http://localhost:8080/end/merchant/get/?mid='+id).then(result=>{
        this.merchant = result.data
        this.todayEarning = this.merchant.todayEarning
        this.balance = this.merchant.balance
      })
    },
    methods:{

    }
  }
</script>

<style scoped>

</style>
