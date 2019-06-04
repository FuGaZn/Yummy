<template>
  <div>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <head-nav-bar></head-nav-bar>
    <left-nav-bar :personal-center="personalCenter" :my-account="myAccount" :my-data="myData" :statistics="toStatistic" :my-orders="myOrder"></left-nav-bar>
    <div style="font-size: 13px; color: black; position: absolute; top: 85px; left: 200px">>&nbsp&nbsp{{nowOperate}}</div>
    <div>
      <div style="width: 1000px; height:400px; background-color: white; position: absolute; top: 120px; left: 200px"></div>
      <div style="width: 1000px; background-color: white; position: absolute; top: 120px; left: 200px;">
        <div style="float:top;">

        </div>
        <div style="float: top">
          <el-table :data="orders.slice((currentPage-1)*tablePageSize,currentPage*tablePageSize)" style="width: 1000px; padding-left: 10px;padding-right: 10px;">
            <el-table-column prop="time" sortable label="下单时间" width="100px"></el-table-column>
            <el-table-column label="订单内容" width="80">
              <template slot-scope="scope">
                <img style="width: 70px; margin-bottom: 10px;margin-top: 10px" :src="merchants[scope.$index].image" />
              </template>
            </el-table-column>
            <el-table-column width="410">
              <template slot-scope="scope">
                <div>
                    <span v-for="item in scope.row.goodItems" :key="item">
                  {{item.name}}&nbsp{{item.number}}份&nbsp{{item.total}}元&nbsp/
                </span>
                </div>
                <div>编号：{{scope.row.serial}}</div>
              </template>
            </el-table-column>
            <el-table-column sortable label="支付金额（元）" prop="total" width="200" align="center"></el-table-column>
            <el-table-column label="状态" width="100" align="center">
              <template slot-scope="scope">
                {{order_situation_options[scope.row.situation].label}}
              </template>
            </el-table-column>
            <el-table-column label="操作" width="100" align="center">
              <template slot-scope="scope">
                <el-button type="text" @click="showOrderDetails(scope.$index,scope.row.serial)">查看详情</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div style="text-align: center;margin-top: 30px;float: top;">
          <el-pagination
            background
            layout="total, prev, pager, next"
            :total="orders.length"
            @current-change="current_change">
          </el-pagination>
        </div>
      </div>
    </div>
    <shopping-cart></shopping-cart>
  </div>
</template>

<script>
  import HeadNavBar from '../../components/member/HeadNavBar'
  import LeftNavBar from '../../components/member/LeftNavBar'
  import image from '../../assets/headImage.png'
  import ShoppingCart from '../../components/member/ShoppingCart'
  export default {
    name: "order-manage-page",
    components:{HeadNavBar,LeftNavBar,ShoppingCart},
    data(){
      return{
        nowOperate:'我的订单',
        currentPage:1,
        tablePageSize:8,
        order_situation_options:[
          {
            label:'未支付',
            value:0
          },
          {
            label: "待接单",
            value:1
          },
          {
            label: '准备中',
            value: 2
          },
          {
            label: '配送中',
            value:3
          },
          {
            label: "已完成",
            value:4
          },
          {
            label: '取消订单',
            value:5
          },
          {
            label: '申请赔款',
            value: 6
          }
        ],
        orders:[

        ],
        merchants:[]
      }
    },
    created(){
      this.$http.post('http://localhost:8080/end/user/get/orders?uid='+localStorage.getItem("userID")).then(result=>{
        this.orders = result.data
        for(var i=0;i<this.orders.length;i++){
          this.$http.post('http://localhost:8080/end/merchant/get?mid='+this.orders[i].mid).then(result=>{
            this.merchants.push(result.data)
          })
        }
      })
    },
    methods:{
      showOrderDetails(index, serial){
        console.log(this.orders[index].oid)
        var path = '/order/'+serial
        this.$router.push({path:path})
      },
      current_change(current){
        this.currentPage = current
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
