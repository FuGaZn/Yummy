<template>
  <div style="position: fixed; height: 100%; background-color: rgba(233,233,233,1); top: 0px;right: 0;">
    <div style="float: left;width: 34px; background-color: rgba(0,00,0,0.7);height: 100%">
      <div @click="click" style="position: absolute; top: 170px; left: 0;width: 34px; height:100px; background-color: rgb(16,146,240);cursor: pointer">
        <i class="el-icon-ali-gouwuchekong" style="font-size: 20px; margin-top: 8px;color: white"></i>
        <div>
          <div style="color: white">购</div>
          <div style="color: white">物</div>
          <div style="color: white">车</div>
        </div>
      </div>
    </div>
    <div v-show="show" style="float: left;width: 300px; height: 100%;position: relative">
      <div style="position: absolute;left: 0; right: 0; height: 35px;background-color: white;width: 300px">
        <span style="position: absolute; left: 15px; top: 5px;">购物车</span>
      </div>
      <el-table v-show="hasGood" :data="cart.cartItems" style="position: absolute; left: 0; top: 40px;" max-height="400">
        <el-table-column width="123">
          <template slot-scope="scope">
            <p style="width: 115px">{{scope.row.name}}</p>
          </template>
        </el-table-column>
        <el-table-column width="120">
          <template slot-scope="scope">
            <el-input-number size="mini" style="width: 90px;height: 30px" v-model="scope.row.number" @change="counterChange(scope.$index, scope.row.number)"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column prop="total" width="40" style="font-size: 12px"></el-table-column>
      </el-table>
      <div style="position: absolute; left: 0; bottom: 0; background-color: white; width: 300px;height: 112px">
        <span style="font-size: 14px; width: 300px; position: absolute; left: 0; top: 20px;">共<span style="color: red; margin-left: 4px; margin-right: 4px;font-size: 16px">{{cart.totalNumber}}</span>份，总计<span style="color: red;margin-left: 4px; margin-right: 4px; font-size: 16px">{{cart.total}}</span>元</span>
        <el-button @click="checkOut" type="danger" style="width: 260px; position: absolute; top: 56px; left: 20px; font-size: 14px" size="small">结算</el-button>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "shopping-cart",
    props:['InGood'],
    data(){
      return{
        show:false,
        hasGood:true,
        merchant:{
          logo:0,
          name:0
        },
        cartItems:[],
        cart:{}
      }
    },
    created(){
      this.$http.post('http://localhost:8080/end/user/get/cart?uid='+localStorage.getItem("userID")).then(result=> {
        this.cart = result.data
        this.cart.uid = localStorage.getItem("userID")
      })
    },
    watch:{
      InGood:function (val) {
        if(this.cart.mid != localStorage.getItem("merchantID")){
          var cart = {
            cart_item_id:0,
            mid: localStorage.getItem("merchantID"),
            uid:localStorage.getItem("userID"),
            total:0,
            totalNumber:0,
            cartItems:[]
          }
          this.cart = cart
          this.$http.post('http://localhost:8080/end/user/get?uid='+localStorage.getItem("userID")).then(result=>{
            var user = result.data
            user.cart = cart
            this.cart = cart
            this.cart.cartItem = []
            this.cart.cartItems.push(val)
            this.sum()
            this.updateCart()
          })
        }else{
          this.cart.cartItems.push(val)
          this.sum()
          this.updateCart()
        }
      }
    },
    methods:{
      updateCart(){
        this.$http.post('http://localhost:8080/end/user/get?uid='+localStorage.getItem("userID")).then(result=>{
          var user = result.data
          user.cart = this.cart
          this.$http.post('http://localhost:8080/end/user/update/user',user)
        })
      },
      deleteItem(id){
        this.$http.post('http://localhost:8080/end/user/delete/cartItem?ciid='+id)
      },
      clearCart(){
        console.log(localStorage.getItem("merchantID"))
        this.$http.post('http://localhost:8080/end/user/clear/cart?uid='+localStorage.getItem("userID")+'&&mid='+localStorage.getItem("merchantID")).then(result=>{
          this.cart.cartItems.push(val)
          this.sum()
          this.updateCart()
        })
      },
      click(){
        this.show=!this.show
      },
      counterChange(index, number){
        var _this = this
        this.$nextTick(function () {
          if (number == 0){
            this.deleteItem(this.cart.cartItems[index].cart_item_id)
            _this.cart.cartItems.splice(index, 1)
          }else{
            this.updateCart()
          }
          _this.sum()
        })
      },
      sum(){
        var total = 0, totalNumber = 0
        for (var i=0;i<this.cart.cartItems.length;i++){
          totalNumber+=this.cart.cartItems[i].number
          total+=this.cart.cartItems[i].number * this.cart.cartItems[i].price
        }
        this.cart.totalNumber = totalNumber
        this.cart.total = total
      },
      checkOut(){
        this.$router.push({path: '/checkout'})
      }
    }
  }
</script>

<style scoped>
p{
  font-size: 12px;
  overflow: hidden;
  text-overflow: ellipsis;

  white-space: nowrap;
}
</style>
