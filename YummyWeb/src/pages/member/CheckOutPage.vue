<template>
  <div>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <head-nav-bar></head-nav-bar>
    <div style="font-size: 13px; color: black; position: absolute; top: 70px; left: 40px">>&nbsp&nbsp{{nowOperate}}</div>
    <div style="position: absolute; left: 40px; top: 100px; width: 480px;background-color: white">
      <div style="float: top; position: relative; width: 480px">
        <h1 style="font-size: 20px; position: absolute; left: 30px; top: 0px;">订单详情</h1>
        <el-button @click="returnShop" style="position: absolute; right: 30px; top: 5px;" type="text"><i class="el-icon-arrow-left"></i>返回商家 </el-button>
        <div style="width: 480px; height: 1px; background-color:  rgba(233,233,233,0.7); position: absolute; left: 0px; top: 50px;"></div>
      </div>
      <el-table :data="user.cart.cartItems" style="float: top;padding-left: 24px; margin-top: 60px" max-height="300">
        <el-table-column width="205" label="商品">
          <template slot-scope="scope">
            <p style="width: 195px">{{scope.row.name}}</p>
          </template>
        </el-table-column>
        <el-table-column width="120" label="分数">
          <template slot-scope="scope">
            <el-input-number size="mini" style="width: 90px;height: 30px" v-model="scope.row.number" @change="counterChange(scope.$index, scope.row.number)"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column prop="total" width="100" align="center" style="font-size: 12px" label="小计"></el-table-column>
      </el-table>
      <div style="float:top;width: 480px; height: 140px; margin-top: 30px; background-color: white">
        <div style="position: relative;">
          <span style="position: absolute; left: 34px; top: 0px; font-size: 14px">配送费</span>
          <span style="position: absolute; right: 74px; top: 0px; font-size: 14px">{{order.conveyCost}}</span>
          <span style="position: absolute; left: 34px; top: 40px; font-size: 14px">餐具</span>
          <span style="position: absolute; right: 74px; top: 40px; font-size: 14px">{{order.packageCost}}</span>
          <span style="position: absolute; right: 74px; top: 80px; font-size: 14px;color: red; font-size: 20px">￥
            <span style="font-weight: bold; font-size: 40px">{{order.total}}</span>
          </span>
        </div>
      </div>
      <div style="float: top; width: 480px; height: 100px; background-color: rgba(233,233,233,0.5)"></div>
    </div>
    <div style="position: absolute; left: 535px; top: 100px; width: 700px;background-color: white; height: 500px">
      <div>
        <h1 style="font-size: 20px; position: absolute; left: 30px; top: 0px;">收货地址</h1>
        <div class="address" @click="selectAddressDialogVisible=true">
          <i style="font-size: 40px; color: rgb(164,164,164); position: absolute; left: 20px; top: 20px;" class="el-icon-location-outline"></i>
          <div style="background-color: rgb(164,164,164); width: 1px; height: 40px; position: absolute; left: 76px; top: 20px;"></div>
          <div>
            <span style="font-size: 13px; position: absolute; left: 100px; top: 18px;">{{order.address.name}}&nbsp&nbsp
              <span v-if="order.address.gender == 0">先生</span><span v-if="order.address.gender == 1">女士</span>
              &nbsp&nbsp{{order.address.phone}}</span>
            <span style="font-size: 12px; position: absolute; left: 100px; top: 42px;">{{order.address.province}}/{{order.address.city}}/{{order.address.part}}&nbsp&nbsp{{order.address.details}}</span>
          </div>
        </div>
        <h1 style="font-size: 20px; position: absolute; left: 30px; top: 170px;">选择优惠</h1>
        <div style="position: absolute; left: 25px; top: 230px;">
          <div v-if="hasCoupon" class="coupon">
            <span style="font-size: 20px; color: white; position: absolute; top: 23px;alignment: center;width: 300px; left: 0">满&nbsp&nbsp{{coupon.satisfied}}&nbsp&nbsp减&nbsp&nbsp{{coupon.discount}}&nbsp&nbsp元</span>
            <el-button @click="reduceCoupon" type="text" class="reduceCoupon"><i class="el-icon-close"></i></el-button>
          </div>
          <div v-if="!hasCoupon" @click="selectCouponDialogVisible = true" class="selectDiscount">
            <el-button  class="selectDiscount_plus" type="text"><i style="font-size: 18px; font-weight: bold" class="el-icon-plus"></i>添加优惠卷</el-button>
          </div>
        </div>
        <h1 style="font-size: 20px; position: absolute; left: 30px; top: 330px;">订单备注</h1>
        <div style="position: absolute; left: 0px; top: 370px;width: 100%;height: 200px;background-color: white;">
          <el-input v-model="order.details" type="textarea" style="position: absolute; width: 600px;left: 30px;top: 20px;" :rows="4"></el-input>
        </div>
      </div>
    </div>
    <div style="position: fixed; width: 100%; height: 50px; bottom: 0; left: 0; background-color: rgba(253,253,253,0.5); border: 1px solid rgb(178,178,178)">
      <span style="position: absolute; right: 300px; top: 10px; font-size: 14px">应付金额：<span style="color: red; font-size: 16px">￥<span style="font-size: 30px;font-weight: bold">{{realTotal}}</span></span></span>
      <el-button @click="payDialogShow = true" type="success" style="position: absolute; right: 100px;top: 8px; font-size: 15px; width: 100px" size="small">提交</el-button>
    </div>
    <el-dialog title="切换地址" :visible.sync="selectAddressDialogVisible" width="500px">
      <div style="margin-top: -30px">
        <div v-for="item in user.addresses" :key="item" class="address_div" @click="changeAddress(item.aid)">
          <div  style="position: relative;font-size: 12px">
            <div style="position: absolute; left: 30px;top: 7px">
              <span>{{item.name}}</span>
              <span v-if="item.gender==0">先生</span>
              <span v-if="item.gender==1">女士</span>
              &nbsp&nbsp&nbsp&nbsp&nbsp
              <span>{{item.phone}}</span>
              &nbsp&nbsp&nbsp&nbsp&nbsp
              <span>{{item.province}}/{{item.city}}/{{item.part}}</span>
            </div>
            <span style="position: absolute; left: 30px;top: 33px;">{{item.details}}</span>
          </div>
        </div>
        <el-button @click="goDataManage" class="el-icon-circle-plus" type="text">添加新地址</el-button>
      </div>
    </el-dialog>
    <el-dialog title="选择优惠" :visible.sync="selectCouponDialogVisible" width="430px">
      <div style="margin-top: -30px;height: 300px;overflow: scroll;overflow-x: hidden">
        <div v-for="item in showCoupons" :key="item">
          <div style="position: relative;float: top;width: 350px;height: 80px;margin: 10px">
            <div @click="addCoupon(item)" style="cursor: pointer;position: absolute;width: 350px;height: 80px;background-color: rgba(255, 86, 88, 0.9);">
              <div style="position: absolute; width: 350px;left: 50%; margin-left: -175px; top: 15px;" v-if="item.satisfied==0">
                <span style="color: white;font-size: 18px">【无门槛红包】&nbsp￥<span>{{item.discount}}</span>&nbsp元</span>
              </div>
              <div style="position: absolute; width: 350px;left: 50%; margin-left: -175px; top: 15px;" v-if="item.satisfied!=0">
                <span style="color: white;font-size: 18px">【满减红包】&nbsp满&nbsp{{item.satisfied}}&nbsp减&nbsp{{item.discount}}&nbsp元</span>
              </div>
              <div style="position: absolute; width: 350px;left: 50%; margin-left: -175px; top: 45px;">
                <span style="color: white;font-size: 13px;">{{item.start}} &nbsp - &nbsp {{item.end}}</span>
              </div>
            </div>
            <div v-show="!item.show" style="position: absolute;width: 350px;height: 80px;top: 0;left: 0;background-color: rgba(255,255,255,0.7)">
              <div style="width: 60px;height: 60px;border: 1px solid black; border-radius: 50%;position: absolute;left: 145px;top: 10px;">
                <span style="position: absolute; top: 20px;left: 6px">不可用</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
    <el-dialog title="支付" :visible.sync="payDialogShow" width="360px">
      <el-button @click="payAfter" type="info" style="margin-right: 10px">稍后支付</el-button>
      <el-button @click="pay" type="primary" style="margin-left: 10px">立即支付</el-button>
    </el-dialog>
  </div>
</template>

<script>
  import HeadNavBar from '../../components/member/HeadNavBar'
  import { provinceAndCityData, regionData, provinceAndCityDataPlus, regionDataPlus, CodeToText, TextToCode }
    from 'element-china-area-data'
  export default {
    name: "pay-page",
    components:{HeadNavBar},
    data(){
      return{
        payDialogShow:false,
        selectAddressDialogVisible:false,
        selectCouponDialogVisible:false,
        nowOperate:"核对订单",
        user:{},
        merchant:{},
        showCoupons:[],
        order:{
          oid:0,
          serial:'',
          situation:0,
          mid:localStorage.getItem("merchantID"),
          uid:localStorage.getItem("userID"),
          time:"",
          goodItems:[],
          coupons:[],
          conveyCost:0,
          packageCost:0,
          address:{},
          total:0,
          details:'',
        },
        realTotal:0,
        hasCoupon:false,
        coupon:{

        }
      }
    },
    created(){
      var path = "http://localhost:8080/end/user/get?uid="+localStorage.getItem("userID")
      this.$http.post(path).then(result=>{
        this.user = result.data
        var aid = localStorage.getItem("addressID")
        for(var i=0;i<this.user.addresses.length;i++){
          if(this.user.addresses[i].aid == aid){
            this.order.address = this.user.addresses[i]
          }
        }

        var path1 = "http://localhost:8080/end/merchant/get?mid="+localStorage.getItem("merchantID")
        this.$http.post(path1).then(result=>{
          this.merchant = result.data
          this.order.conveyCost = this.merchant.conveyCost
          this.order.packageCost = this.merchant.packageCost
          this.order.total = this.user.cart.total+this.order.conveyCost+this.order.packageCost
          this.realTotal = this.order.total

          for(var i=0;i<this.user.coupons.length;i++){
            var coupon = {
              cid:this.user.coupons[i].cid,
              mid:this.user.coupons[i].mid,
              discount:this.user.coupons[i].discount,
              satisfied:this.user.coupons[i].satisfied,
              start:this.user.coupons[i].start,
              end:this.user.coupons[i].end,
              show:true
            }
            if (this.user.coupons[i].disabled == true){
              coupon.show = false
            }
            if(this.user.coupons[i].mid!=localStorage.getItem("merchantID")){
              coupon.show = false
            }
            var date = this.getNowFormatDate();
            if(date<this.user.coupons[i].start || date>this.user.coupons[i].end){
              coupon.show = false
            }
            if(this.user.cart.total+this.order.conveyCost+this.order.packageCost<this.user.coupons[i].satisfied){
              coupon.show = false
            }
            console.log(coupon.show)
            this.showCoupons.push(coupon)
          }
        })
      })
    },
    methods:{
      payAfter(){
        var b = this.submitOrder()
        if(b = false)
          return
        this.order.situation = 0
        var path = 'http://localhost:8080/end/user/add/order/unpayed'
        this.$http.post(path,this.order).then(result=>{
          var id = result.data
          localStorage.setItem("orderID",id)
          this.info(1,'提交成功')
          var path2 = 'http://localhost:8080/end/user/get/order?oid='+id
          this.$http.post(path2).then(result=>{
            var serial = result.data.serial
            this.$router.push('/order/'+serial)
            window.location.reload()
          })
        })
        this.payDialogShow = false
      },
      pay(){
        var b = this.submitOrder()
        if(b = false)
          return
        this.order.situation = 1
        var path = 'http://localhost:8080/end/user/get?uid='+localStorage.getItem("userID")
        this.$http.post(path).then(result=>{
          var user = result.data
          this.payDialogShow = false
          if(this.order.coupons.length>0) {
            for (var i = 0; i < user.coupons.length; i++) {
              if (user.coupons[i].cid == this.order.coupons[0].cid){
                user.coupons[i].disabled = true
              }
            }
          }
          if(user.balance > this.order.total){
            user.balance -= this.order.total
            user.score += this.order.total
            var path2 = 'http://localhost:8080/end/user/update/user'
            this.$http.post(path2,user)
            this.updateOrder()
            this.updateMerchant()
          }else{
            this.info(2,'余额不足，请充值')
          }
        })
      },
      updateMerchant(){
        this.$http.post('http://localhost:8080/end/merchant/update/merchant',this.merchant).then(result=>{
          console.log(result.data)
        })
      },
      returnShop(){
        this.$router.push("/shop/"+localStorage.getItem("merchantSerial"))
      },
      submitOrder(){
        this.coupon.cid = 0
        this.order.coupons.push(this.coupon)
        this.order.total = this.realTotal
        for(var i=0;i<this.user.cart.cartItems.length;i++){
          var item = this.user.cart.cartItems[i]
          if(item.type==0) {
            for(var j=0;j<this.merchant.goods.length;j++){
              if(this.merchant.goods[j].gid == item.gid){
                if(this.merchant.goods[j].bought_number+item.number>this.merchant.goods[j].max_number){
                  this.info(3,'商品 '+this.merchant.goods[j].name+' 剩余数量不足，仅可购买'+this.merchant.goods[j].max_number-this.merchant.goods[i].bought_number+'份')
                  return false
                }else{
                  this.merchant.goods[j].bought_number+=item.number
                }
              }
            }
            var goodItem = {
              good_item_id:0,
              gid: item.gid,
              pid:0,
              mid: item.mid,
              name: item.name,
              price: item.price,
              number: item.number,
              total: item.total,
              details: item.details,
              image: item.image
            }
            this.order.goodItems.push(goodItem)
          }else{
            var pid = item.pid
            for(var j=0;j<this.merchant.packages.length;j++){
              if(pid == this.merchant.packages[j].pid){
                var pack = this.merchant.packages[j]
                if(pack.bought_number+item.number>this.max_number){
                  this.info(3,'套餐 '+pack.name+' 剩余数量不足，仅可购买'+pack.max_number-pack.bought_number+'份')
                  return false
                }else{
                  this.merchant.packages[j].bought_number+=item.number
                }
                for(var z=0;z<pack.goods.length;z++){
                  var good = pack.goods[i]
                  var goodItem1 = {
                    good_item_id:0,
                    gid: good.gid,
                    pid:pack.pid,
                    mid: good.mid,
                    name: good.name,
                    price: good.price,
                    number: good.number*item.number,
                    total: good.total*item.total,
                    details: good.details,
                    image: good.image
                  }
                  this.order.goodItems.push(goodItem1)
                }
              }
            }
          }
        }
        return true
      },

      updateOrder(){
        var path = 'http://localhost:8080/end/user/add/order'
        this.$http.post(path,this.order).then(result=>{
          var id = result.data
          localStorage.setItem("orderID",id)
          this.info(1,'提交成功')
            var path2 = 'http://localhost:8080/end/user/get/order?oid='+id
            this.$http.post(path2).then(result=>{
              var serial = result.data.serial
              this.$router.push('/order/'+serial)
              window.location.reload()
            })
        })
      },

      changeAddress(aid){
        this.selectAddressDialogVisible = false
        for(var i=0;i<this.user.addresses.length;i++){
          if(this.user.addresses[i].aid == aid){
            if(this.user.addresses[i].part_code == this.merchant.address.part_code) {
              this.order.address = this.user.addresses[i]
              localStorage.setItem("addressID", this.order.address.aid)
              localStorage.setItem("addressCode", this.order.address.part_code)
            }else{
              this.info(3,'该地址不在配送范围内')
            }
          }
        }
      },
      counterChange(index, number){
        var _this = this
        this.$nextTick(function () {
          if (number == 0){
            _this.user.cart.cartItems.splice(index, 1)
          }else{
            _this.user.cart.cartItems[index].total = _this.user.cart.cartItems[index].number * _this.user.cart.cartItems[index].price
          }
          _this.sum()
        })
      },
      sum(){
        var total = this.order.conveyCost+this.order.packageCost
        for (var i=0;i<this.user.cart.cartItems.length;i++){
          total+=this.user.cart.cartItems[i].number * this.user.cart.cartItems[i].price
        }
        this.order.total = total
        this.realTotal = total
        if(this.coupon.discount!=undefined){
          if(this.realTotal<this.coupon.satisfied){
            this.reduceCoupon()
          }else
            this.realTotal -= this.coupon.discount
        }
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
      reduceCoupon(){
        this.hasCoupon = false;
        this.realTotal = this.order.total;
      },
      addCoupon(item){
        this.hasCoupon = true;
        this.realTotal = this.order.total;
        this.realTotal -= item.discount;
        this.selectCouponDialogVisible = false
        this.coupon = item
      },
      goDataManage(){
        this.dialogVisible = false;
        this.$router.push("/data")
      },
      getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
          month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
          strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
      }
    }
  }
</script>

<style scoped>
.address{
  position: absolute;
  left: 30px;top: 70px;
  width: 640px; height:  80px;
  border: 1px solid rgba(16,142,234,1);
  cursor: pointer;
}
  .address:hover{
    background-color: rgba(233,233,233,0.5);
  }
.selectDiscount{
  float:left;
  position: relative;
  width: 300px;
  height: 80px;
  border: 1px solid rgba(105,105,105,0.5);
  background-color: rgba(233,233,233,0.5);
  margin:5px;
  cursor: pointer;
}
.selectDiscount:hover{
  border:1px solid  rgba(15, 137, 225, 1);
}
.selectDiscount_plus{
  color: rgba(105,105,105,0.7);font-size: 14px; position: absolute; left: 105px; top: 17px;
}
.selectDiscount:hover > .selectDiscount_plus{
  color: #0f89e1;
}
  .coupon{
    float:left;
    position: relative;
    width: 300px;
    height: 80px;
    background-color: rgba(255, 86, 88, 0.9);
    margin:5px;
  }
  .reduceCoupon{
    color: white;
    position: absolute;
    right: 5px;
    top: -10px;
  }
.address_div{
  float: top;
  height: 60px;
  width: 440px;
  border: 1px solid #1aaaf0;
  margin: 10px;
  cursor: pointer;
}
.address_div:hover{
  background-color: rgba(65, 166, 225, 0.2);
}
</style>
