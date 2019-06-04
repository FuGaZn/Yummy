<template>
  <div>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <head-nav-bar></head-nav-bar>
    <left-nav-bar :personal-center="personalCenter" :my-account="myAccount" :statistics="toStatistic" :my-data="myData" :my-orders="myOrder"></left-nav-bar>
    <div style="font-size: 13px; color: black; position: absolute; top: 85px; left: 200px">>&nbsp&nbsp{{nowOperate}}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<span v-show="unPayed" style="color: red">订单未在规定时间内支付，已取消</span></div>
    <el-button-group style="position: absolute; right: 70px; top: 80px;">
      <el-button @click="payDialogVisible = true" :disabled="payDisabled" type="danger" size="small">
        <span v-show="!(minutes == 0 && seconds == 0)">{{minutes}}:{{seconds}}&nbsp</span>立即支付
      </el-button>
      <el-button @click="cancelDialogVisible = true" :disabled="cancelDisabled" type="info" size="small">取消订单</el-button>
      <el-button @click="backDialogVisible = true" :disabled="backDisabled" type="danger" size="small">申请退款</el-button>
      <el-button @click="finishOrder" :disabled="finishDisabled" type="success" size="small">完成订单</el-button>
    </el-button-group>
    <div style="width: 1000px; height:400px; background-color: white; position: absolute; top: 120px; left: 200px">
      <el-steps :active="order.situation" simple style="height: 40px; margin-top: 0px;float: top">
        <el-step title="订单已提交" ></el-step>
        <el-step title="商家准备中" ></el-step>
        <el-step title="配送中" ></el-step>
        <el-step title="已送达" ></el-step>
      </el-steps>
      <div style="float:top; margin-left: 25px;margin-top: 20px">
        <div style="position:relative;width: 950px; height: 100px;background-color: #F5F7FA; border-radius: 5px;border:1px solid #cbd8e7">
          <img @click="goMerchant()" :src="merchant.image" style="cursor: pointer;width: 50px; position: absolute; left: 30px; top: 24px;">
          <span style="font-size:18px;  color: #535353; position: absolute; top: 24px; left: 100px;">{{merchant.name}}</span>
          <span style="font-size:13px;  color: rgba(101,101,101,0.7); position: absolute; top: 54px; left: 100px;">商家电话&nbsp{{merchant.phone}}</span>
          <span style="font-size:13px;  color: rgba(101,101,101,0.7); position: absolute; top: 54px; left: 400px;">订单号：{{order.serial}}</span>
        </div>
      </div>
      <div style="float: top; margin-top: 20px">
        <el-table :data="order.goodItems" style="width: 950px; margin-left: 25px; margin-right: 25px;">
          <el-table-column label="菜品" prop="name"></el-table-column>
          <el-table-column label="单价" prop="price" align="center"></el-table-column>
          <el-table-column label="数量" prop="number" align="center"></el-table-column>
          <el-table-column label="总价" prop="total" align="center"></el-table-column>
        </el-table>
      </div>
      <div style="float: top; padding-top:20px;position: relative;background-color: white; width: 1000px; height: 200px">
        <span style="position: absolute; left: 40px; top: 30px;">餐盒</span>
        <span style="position: absolute; right: 140px; top: 30px;">{{order.packageCost}}</span>
        <span style="position: absolute; left: 40px; top: 70px;">配送费</span>
        <span style="position: absolute; right: 140px; top: 70px;">{{order.conveyCost}}</span>
        <div v-show="order.coupons.length>0">
          <span style="position: absolute; left: 40px; top: 110px;">优惠金额</span>
          <span style="position: absolute; right: 140px; top: 110px;color: red">-{{order.coupons[0].discount}}</span>
        </div>
        <div style="width: 950px; height: 1px; position: absolute; top: 160px; left:25px; background-color: rgba(87,87,87,0.2);"></div>
        <div style="position: absolute; top: 180px; right: 100px">实际支付：<span style="font-size: 33px;font-weight: bold; color: #F74342">{{order.total}}</span></div>
        <div style="height: 50px;width: 1000px; position: absolute; top: 220px;background-color: white"></div>
      </div>
    </div>
    <shopping-cart></shopping-cart>
    <el-dialog :visible.sync="cancelDialogVisible" width="400px">
      <el-input placeholder="取消原因" v-model="order.backInfo" style="width: 240px" type="textarea" :rows="3"></el-input>
      <el-button @click="cancelOrder" type="primary" style="margin-left: 20px">确定</el-button>
    </el-dialog>
    <el-dialog :visible.sync="backDialogVisible" width="400px">
      <el-input placeholder="退款原因" v-model="order.backInfo" style="width: 240px" type="textarea" :rows="3"></el-input>
      <el-button @click="backOrder" type="primary" style="margin-left: 20px">确定</el-button>
    </el-dialog>
    <el-dialog :visible.sync="payDialogVisible" width="300px">
      <el-button @click="pay" type="primary" style="">确认支付</el-button>
    </el-dialog>
  </div>
</template>

<script>
  import HeadNavBar from '../../components/member/HeadNavBar'
  import LeftNavBar from '../../components/member/LeftNavBar'
  import image from '../../assets/headImage.png'
  import ShoppingCart from '../../components/member/ShoppingCart'
  export default {
    name: "order-details-page",
    components:{HeadNavBar, LeftNavBar,ShoppingCart},
    data(){
      return{
        nowOperate:'订单详情',
        order:{},
        merchant:{},
        payDisabled:false,
        cancelDisabled:false,
        backDisabled:false,
        finishDisabled:false,
        cancelDialogVisible:false,
        backDialogVisible:false,
        minutes:0,
        seconds:0,
        unPayed:false,
        payDialogVisible:false
      }
    },
    mounted(){
      this.init()
    },
    methods:{
      init(){
        this.$http.post('http://localhost:8080/end/user/get/order?oid='+localStorage.getItem("orderID")).then(result=>{
          this.order = result.data
          if(this.order.situation>=3)
            this.cancelDisabled = true
          if(this.order.situation!=0) {
            this.payDisabled = true
            this.minutes = 0
            this.seconds = 0
          }if(this.order.situation == 0){
            this.backDisabled = true
            this.finishDisabled = true
            this.waitPay()
          }
          this.$http.post('http://localhost:8080/end/merchant/get?mid='+this.order.mid).then(result=>{
            this.merchant = result.data
          })
        })
      },
      pay(){
        this.payDialogVisible = false
        var path = 'http://localhost:8080/end/user/get/order?oid='+localStorage.getItem("orderID")
        this.$http.post(path).then(result=>{
          var order = result.data
          order.situation = 1
          var path2 = 'http://localhost:8080/end/user/get?uid='+localStorage.getItem("userID")
          this.$http.post(path2).then(result=>{
            var user = result.data
            this.payDialogShow = false
            if(user.balance > this.order.total){
              user.balance -= this.order.total
              user.score += this.order.total
              var path2 = 'http://localhost:8080/end/user/update/user'
              this.$http.post(path2,user).then(result=>{
                var path3 = 'http://localhost:8080/end/user/update/order'
                this.$http.post(path3,order).then(result=>{
                  this.init()
                })
              })
            }else{
              this.info(2,'余额不足，请充值')
            }
          })
        })

      },
      timer () {
        var _this = this
        var timer = null
        var t = this.minutes * 60 + this.seconds
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
          _this.seconds = s
          _this.minutes = m
          if(parseInt(_this.minutes) <= 0 && _this.seconds <=0) {
            _this.payDisabled = true
            _this.cancelUnpayedOrder()
          }
        }
        timer = setInterval(countDown, 1000)
      },
      waitPay(){
        var order_hour = parseInt(this.order.time.substring(11,13))
        var order_minutes = parseInt(this.order.time.substring(14,16))
        var order_seconds = parseInt(this.order.time.substring(17,19))

        var date = new Date()
        var now_hour = date.getHours()
        if(now_hour<order_hour||now_hour-order_hour>1) {
          this.cancelUnpayedOrder()
          return
        }
        var now_minutes = date.getMinutes()
        var now_seconds = date.getSeconds()-5
        if(now_seconds < order_seconds){
          this.seconds = now_seconds-order_seconds + 60
          now_minutes-=1
        }else{
          this.seconds = now_seconds-order_seconds
        }
        if(now_minutes<order_minutes){
          this.minutes = now_minutes-order_minutes+60
          now_hour -= 1
        }else{
          this.minutes = now_minutes-order_minutes
        }
        if(now_hour<order_hour){
          this.cancelUnpayedOrder()
          return
        }
        if(this.minutes>2){
          this.cancelUnpayedOrder()
          return
        }
        if(this.seconds!=0) {
          this.seconds = 60 - this.seconds
          this.minutes++
        }
        this.minutes = 2 - this.minutes
        this.timer()
      },
      cancelUnpayedOrder(){
        this.seconds = 0
        this.minutes = 0
        this.payDisabled = true
        this.unPayed = true
        this.cancelDisabled = true
        this.$http.post('http://localhost:8080/end/user/get/order?oid='+localStorage.getItem("orderID")).then(result=> {
          var order = result.data
          order.backInfo = '未在规定时间内支付'
          order.beforeSituation = order.situation
          order.situation = 5
          this.$http.post('http://localhost:8080/end/user/update/order', order).then(result => {
          })
        })
      },

      cancelOrder(){
        this.cancelDialogVisible = false
        this.$http.post('http://localhost:8080/end/user/get/order?oid='+localStorage.getItem("orderID")).then(result=>{
          var order = result.data
          order.backInfo = this.order.backInfo
          order.beforeSituation = order.situation
          order.situation = 5
          this.$http.post('http://localhost:8080/end/user/update/order',order).then(result=>{
            this.info(1,'提交取消请求')
            this.$router.push("/order")
          })
        })
      },

      backOrder(){
        this.backDialogVisible = false
        this.$http.post('http://localhost:8080/end/user/get/order?oid='+localStorage.getItem("orderID")).then(result=>{
          var order = result.data
          order.backInfo = this.order.backInfo
          order.beforeSituation = order.situation
          order.situation = 6
          this.$http.post('http://localhost:8080/end/user/update/order',order).then(result=>{
            this.info(1,'提交退款请求')
            this.$router.push("/order")
          })
        })
      },

      finishOrder(){
        this.$http.post('http://localhost:8080/end/user/order/finish?oid='+localStorage.getItem("orderID")).then(result=>{
          this.info(1,'完成交易')
          this.$router.push('/order')
        })
      },
      goMerchant(){
        localStorage.setItem("merchantID",this.merchant.mid)
        this.$router.push('/shop/'+this.merchant.serial)
      },
      personalCenter(){
        this.$router.push({path:'/main-page'})
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
      myOrder(){
        console.log("hter")
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
