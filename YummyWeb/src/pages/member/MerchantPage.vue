<template>
  <div>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <head-nav-bar ></head-nav-bar>
    <div style="position: absolute; left: 0;top: 65px; width: 100%;height: 146px;background-color: white">
      <img :src="merchant.image" style="width: 90px;position: absolute;left: 80px;top: 28px;"/>
      <span style="position: absolute; left: 200px; top: 30px; font-size: 20px">{{merchant.name}}</span>
      <span style="position: absolute; left: 200px; top: 65px; font-size: 14px;">
        {{merchant.address.province}}/{{merchant.address.city}}/{{merchant.address.part}}
        &nbsp&nbsp&nbsp&nbsp<span style="color:#FC463F;font-size: 16px">{{merchant.phone}}</span>
      </span>
      <span style="position: absolute; left: 200px; top: 95px; font-size: 12px;">{{merchant.briefIntroduction}}</span>
      <div>
        <span style="position: absolute; right: 200px; top: 33px;">包装费</span>
        <span style="font-size: 25px;font-weight: bold;top: 70px;right: 208px;position: absolute;color: #FF9C00;">￥{{merchant.packageCost}}</span>
      </div>
      <div>
        <span style="position: absolute; right: 350px; top: 33px;">配送费</span>
        <span style="font-size: 25px;font-weight: bold;top: 70px;right: 358px;position: absolute; color:#FC463F">￥{{merchant.conveyCost}}</span>
      </div>
    </div>
    <div style="width: 300px;height: 200px;position: absolute;left: 920px;top: 230px;background-color: white">
      <div style="position: absolute;left: 0;right: 0;height: 35px;width: 100%;background-color: #1E89E0;color: white;padding-top: 10px">商家公告</div>
      <p style="position: absolute;left: 0px;top: 25px;;font-size: 13px;width: 270px;text-align: justify;background-color: white;padding: 15px;line-height: 20px;color: #3c3c3c">
        {{merchant.bulletin}}</p>
    </div>
    <div style="width: 900px;position: absolute;left: 10px;top: 270px;height: 300px;">
      <el-button @click="couponDialogVisible = true"  type="danger" style="position: absolute; top: -45px;left: 760px;">领取优惠券</el-button>
      <div>
        <div class="good_div" v-for="item in merchant.goods" :key="item" style="position: relative;">
          <img :src="item.image" style="position: absolute;left: 0;top: 0;width: 110px"/>
          <span style="font-size: 16px;position: absolute;left: 130px;top: 10px;">{{item.name}}</span>
          <span style="font-size: 12px;position:absolute;left: 130px;top: 40px;">{{item.details}}</span>
          <span style="font-size: 15px;font-weight: bold;position: absolute;left: 130px;bottom: 10px;color: red">￥&nbsp{{item.price}}</span>
          <el-button @click="addGood(item)" type="primary"  style="width: 100px;position: absolute;right: 10px;bottom: 10px;" round size="small">加入购物车</el-button>
          <div v-show="item.bought_number>=item.max_number" style="position: absolute;left: 0;top: 0;width: 100%;height: 100%; background-color: rgba(254,254,254,0.7)">
            <div style="width: 60px;height: 60px;border: 1px solid black; border-radius: 50%;position: absolute;left: 185px;top: 25px;">
              <span style="position: absolute; top: 20px;left: 6px">已售罄</span>
            </div>
          </div>
        </div>
        <div class="good_div" v-for="item in merchant.packages" :key="item" style="position: relative;">
          <img :src="item.image" style="position: absolute;left: 0;top: 0;width: 110px"/>
          <span style="font-size: 16px;position: absolute;left: 130px;top: 10px;">{{item.name}}</span>
          <span style="font-size: 12px;position:absolute;left: 130px;top: 40px;">{{item.details}}</span>
          <span style="font-size: 15px;font-weight: bold;position: absolute;left: 130px;bottom: 10px;color: red">￥&nbsp{{item.real_price}}</span>
          <el-button @click="addPackage(item)" type="primary"  style="width: 100px;position: absolute;right: 10px;bottom: 10px;" round size="small">加入购物车</el-button>
          <div v-show="item.bought_number>=item.max_number" style="position: absolute;left: 0;top: 0;width: 100%;height: 100%; background-color: rgba(254,254,254,0.7)">
            <div style="width: 60px;height: 60px;border: 1px solid black; border-radius: 50%;position: absolute;left: 185px;top: 25px;">
              <span style="position: absolute; top: 20px;left: 6px">已售罄</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <shopping-cart :in-good="good"></shopping-cart>
    <el-dialog title="优惠券" :visible.sync="couponDialogVisible" width="430px">
      <div style="margin-top: -30px;height: 300px;overflow: scroll;overflow-x: hidden">
        <div v-for="item in merchant.coupons" :key="item">
          <div style="position: relative;float: top;width: 350px;height: 80px;margin: 10px">
            <div style="position: absolute;width: 350px;height: 80px;background-color: rgba(255, 86, 88, 0.9);">
              <div style="position: absolute; width: 350px;left: 50%; margin-left: -210px; top: 15px;" v-if="item.satisfied==0">
                <span style="color: white;font-size: 18px">【无门槛红包】&nbsp￥<span>{{item.discount}}</span>&nbsp元</span>
              </div>
              <div style="position: absolute; width: 350px;left: 50%; margin-left: -210px; top: 15px;" v-if="item.satisfied!=0">
                <span style="color: white;font-size: 18px">【满减红包】&nbsp满&nbsp{{item.satisfied}}&nbsp减&nbsp{{item.discount}}&nbsp元</span>
              </div>
              <div style="position: absolute; width: 350px;left: 50%; margin-left: -210px; top: 45px;">
                <span style="color: white;font-size: 13px;">{{item.start}} &nbsp - &nbsp {{item.end}}</span>
              </div>
              <div @click="getCoupon(item)" style="cursor: pointer;position: absolute;right: 0;top: 0;height: 80px;width: 60px;background-color: rgb(255,60,61);color: white;font-size: 18px">
                <div style="margin-top: 10px">
                  <div>领</div>
                 <div>取</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import HeadNavBar from '../../components/member/HeadNavBar'
  import ShoppingCart from '../../components/member/ShoppingCart'
  export default {
    name: "merchant-page",
    components:{HeadNavBar,ShoppingCart},
    data(){
      return{
        couponDialogVisible:false,
        navBarBackColor:"background-color:#1E89E0",
        merchant:{},
        goodItems:[],
        good:{},
        user:{}
      }
    },
    created(){
      this.getMerchant()
    },
    methods:{
      addGood(good){
        var item = {
          type:0,
          pid:0,
          gid:good.gid,
          mid:this.merchant.mid,
          name:good.name,
          price:good.price,
          number:1,
          total:good.price,
          details:good.details,
          image:good.image
        }
        for(var i=0;i<this.merchant.goods.length;i++){
          if(this.merchant.goods[i].gid == good.gid){
            this.merchant.goods[i]._number++;
          }
        }
        this.good = item
        this.goodItems.push(item)
      },

      getCoupon(item){
        this.$http.post('http://localhost:8080/end/user/get?uid='+localStorage.getItem("userID")).then(result=>{
          var user = result.data
          if(user.coupons == null)
            user.coupons = []
          item.cid = 0
          item.uid = user.uid
          item.disabled = false
          user.coupons.push(item)
          this.$http.post('http://localhost:8080/end/user/update/user',user).then(result=>{
            this.couponDialogVisible = false
          })
        })
      },
      addPackage(pack){
        var item = {
          type:1,
          pid:pack.pid,
          gid:0,
          mid:this.merchant.mid,
          name:pack.name,
          price:pack.real_price,
          number:1,
          total:pack.real_price,
          details:pack.details,
          image:pack.image
        }
        this.good = item
        this.goodItems.push(item)
      },

      getMerchant(){
        var serial = localStorage.getItem("merchantSerial")
        this.$http.post('http://localhost:8080/end/merchant/getBySerial?serial='+serial).then(result=> {
          this.merchant = result.data
          localStorage.setItem("merchantID",this.merchant.mid)
        })
      }
    }

  }
</script>

<style scoped>
  .good_div{
    float: left;
    width: 430px;
    margin: 8px;
    height: 110px;
    background-color: white;
  }

</style>
