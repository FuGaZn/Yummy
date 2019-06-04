<template>
  <div>
    <div style="position:fixed; left: 0;
    top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <merchant-head-nav-bar></merchant-head-nav-bar>
    <div style="position: absolute; left: 0; top: 65px;width: 100%; height: 60px; background-color: white">
      <el-menu default-active="1" mode="horizontal" style="position: absolute; top: 0px;width: 600px;left: 80px" @select="handleSelect">
        <el-menu-item index="1">新订单
          <el-badge :value="newOrderList.length" :hidden="newOrderList.length == 0" style="margin-left: 3px; margin-top: -3px"></el-badge>
        </el-menu-item>
        <el-menu-item index="2">取消订单<el-badge :value="cancelOrderList.length" :hidden="cancelOrderList.length == 0" style="margin-left: 3px; margin-top: -3px"></el-badge>
        </el-menu-item>
        <el-menu-item index="3">配送中<el-badge :value="conveyOrderList.length" :hidden="conveyOrderList.length == 0" style="margin-left: 3px; margin-top: -3px"></el-badge>
        </el-menu-item>
        <el-menu-item index="4">售后退款<el-badge :value="backOrderList.length" :hidden="backOrderList.length == 0" style="margin-left: 3px; margin-top: -3px"></el-badge>
        </el-menu-item>
        <el-menu-item index="5">历史订单</el-menu-item>
      </el-menu>
    </div>
    <div>
      <div v-show="newOrderShow" style="position: absolute; left: 50%; width: 1200px; margin-left: -600px; top: 140px; height: 500px; background-color: white">
        <div style="position: fixed; left: 50%; width: 1200px; margin-left: -600px; top: 140px; height: 500px; background-color: white"></div>
        <el-table :data="newOrderList" style="width: 1200px; padding-left: 25px; padding-right:25px;">
          <el-table-column type="index" :index="new_indexMethod">
          </el-table-column>
          <el-table-column label="时间" prop="time" width="140">
          </el-table-column>
          <el-table-column label="姓名" prop="address.name" width="100"></el-table-column>
          <el-table-column label="手机号码" prop="address.phone" width="120"></el-table-column>
          <el-table-column label="地址" width="530">
            <template slot-scope="scope">
              <div>
                <div>{{scope.row.address.province}}/{{scope.row.address.city}}/{{scope.row.address.part}}</div>
                <div style="font-size: 12px; margin-top: 3px">{{scope.row.address.details}}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="状态"width="100">
            <template slot-scope="scope">
              <span>{{order_situation_options[scope.row.situation-1].label}}</span>
            </template>
          </el-table-column>
          <el-table-column label="查看详情" type="expand" width="100">
            <template slot-scope="scope">
              <div>
                <goods-list :has-coupon="scope.row.coupons.length != 0" :coupon_discount="scope.row.coupons[0].discount" :coupon_satisfied="scope.row.coupons[0].satisfied" :goods="scope.row.goodItems" :total="scope.row.total"></goods-list>
                <div style="position: absolute; left: 600px; top: 20px;">
                  <span style="position: absolute; top:0px;font-weight: bold;font-size: 16px;width: 100px">备注：</span>
                  <p style="position: absolute; top: 20px; width: 500px; font-size: 12px;line-height: 20px;white-space: normal; max-height: 160px;overflow: hidden">{{scope.row.details}}</p>
                </div>
                <div style="position: absolute; right: 60px; top: 280px;">
                  <el-button-group>
                    <el-button :disabled="scope.row.acceptDisabled" @click="acceptOrder(scope.$index,scope.row.oid)" type="primary" size="small">接单</el-button>
                    <el-button :disabled="scope.row.conveyDisabled" @click="conveyOrder(scope.$index,scope.row.oid)" type="success" size="small">配送</el-button>
                  </el-button-group>
                </div>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-show="cancelOrderShow" style="position: absolute; left: 50%; width: 1200px; margin-left: -600px; top: 140px; height: 500px; background-color: white">
        <div style="position: fixed; left: 50%; width: 1200px; margin-left: -600px; top: 140px; height: 500px; background-color: white"></div>
        <el-table :data="cancelOrderList" style="width: 1200px; padding-left: 25px; padding-right:25px;">
          <el-table-column type="index" :index="cancel_indexMethod">
          </el-table-column>
          <el-table-column label="时间" prop="time" width="140">
          </el-table-column>
          <el-table-column label="姓名" prop="address.name" width="100"></el-table-column>
          <el-table-column label="地址" width="530">
            <template slot-scope="scope">
              <div>
                <div>{{scope.row.address.province}}/{{scope.row.address.city}}/{{scope.row.address.part}}</div>
                <div style="font-size: 12px; margin-top: 3px">{{scope.row.address.details}}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="状态" width="100">
            <template slot-scope="scope">
              <span>{{order_situation_options[scope.row.situation-1].label}}</span>
            </template>
          </el-table-column>
          <el-table-column label="手机号码" prop="address.phone" width="120"></el-table-column>
          <el-table-column label="查看详情" type="expand" width="100">
            <template slot-scope="scope">
              <div>
                <goods-list :has-coupon="scope.row.coupons.length != 0" :coupon_discount="scope.row.coupons[0].discount"  :coupon_satisfied="scope.row.coupons[0].satisfied" :goods="scope.row.goodItems" :total="scope.row.total"></goods-list>
                <div style="position: absolute; left: 600px; top: 20px;">
                  <span style="position: absolute; top:0px;font-weight: bold;font-size: 16px;width: 100px">备注：</span>
                  <p style="position: absolute; top: 20px; width: 500px; font-size: 12px;line-height: 20px;white-space: normal; max-height: 160px;overflow: hidden">{{scope.row.details}}</p>
                </div>
                <div >
                  <span style="color: red;position: absolute; top:220px;font-weight: bold;font-size: 16px;width: 100px;left: 600px">取消原因</span>
                  <p style="left: 600px;position: absolute; top: 236px; width: 500px; font-size: 12px;line-height: 20px;white-space: normal;max-height: 60px;overflow: hidden;color: red">{{scope.row.backInfo}}</p>
                </div>
                <div style="position: absolute; right: 60px; top: 330px;">
                  <el-button-group>
                    <el-button @click="refuseCancelOrder(scope.$index, scope.row.oid)" type="danger" size="small">拒绝</el-button>
                    <el-button @click="acceptCancelOrder(scope.$index, scope.row.oid)" type="success" size="small">接受</el-button>
                  </el-button-group>
                </div>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-show="conveyOrderShow" style="position: absolute; left: 50%; width: 1200px; margin-left: -600px; top: 140px; height: 500px; background-color: white">
        <div style="position: fixed; left: 50%; width: 1200px; margin-left: -600px; top: 140px; height: 500px; background-color: white"></div>
        <el-table :data="conveyOrderList" style="width: 1200px; padding-left: 25px; padding-right:25px;">
          <el-table-column type="index" :index="convey_indexMethod">
          </el-table-column>
          <el-table-column label="时间" prop="time" width="140">
          </el-table-column>
          <el-table-column label="姓名" prop="address.name" width="100"></el-table-column>
          <el-table-column label="手机号码" prop="address.phone" width="120"></el-table-column>
          <el-table-column label="地址" width="630">
            <template slot-scope="scope">
              <div>
                <div>{{scope.row.address.province}}/{{scope.row.address.city}}/{{scope.row.address.part}}</div>
                <div style="font-size: 12px; margin-top: 3px">{{scope.row.address.details}}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="查看详情" type="expand" width="100">
            <template slot-scope="scope">
              <div>
                <goods-list :has-coupon="scope.row.coupons.length != 0" :coupon_discount="scope.row.coupons[0].discount" :coupon_satisfied="scope.row.coupons[0].satisfied" :goods="scope.row.goodItems" :total="scope.row.total"></goods-list>
                <div style="position: absolute; left: 600px; top: 20px;">
                  <span style="position: absolute; top:0px;font-weight: bold;font-size: 16px;width: 100px">备注：</span>
                  <p style="position: absolute; top: 20px; width: 500px; font-size: 12px;line-height: 20px;white-space: normal; max-height: 160px;overflow: hidden">{{scope.row.details}}</p>
                </div>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-show="backOrderShow" style="position: absolute; left: 50%; width: 1200px; margin-left: -600px; top: 140px; height: 500px; background-color: white">
        <div style="position: fixed; left: 50%; width: 1200px; margin-left: -600px; top: 140px; height: 500px; background-color: white"></div>
        <el-table :data="backOrderList" style="width: 1200px; padding-left: 25px; padding-right:25px;">
          <el-table-column type="index" :index="back_indexMethod">
          </el-table-column>
          <el-table-column label="时间" prop="time" width="140">
          </el-table-column>
          <el-table-column label="姓名" prop="address.name" width="100"></el-table-column>
          <el-table-column label="手机号码" prop="address.phone" width="120"></el-table-column>
          <el-table-column label="地址" width="630">
            <template slot-scope="scope">
              <div>
                <div>{{scope.row.address.province}}/{{scope.row.address.city}}/{{scope.row.address.part}}</div>
                <div style="font-size: 12px; margin-top: 3px">{{scope.row.address.details}}</div>
              </div>
            </template>
          </el-table-column>
           <el-table-column label="查看详情" type="expand" width="100">
            <template slot-scope="scope">
              <div>
                <goods-list :has-coupon="scope.row.coupons.length!=0" :coupon_discount="scope.row.coupons[0].discount" :coupon_satisfied="scope.row.coupons[0].satisfied" :goods="scope.row.goodItems" :total="scope.row.total"></goods-list>
                <div style="position: absolute; left: 600px; top: 20px;">
                  <span style="position: absolute; top:0px;font-weight: bold;font-size: 16px;width: 100px">备注：</span>
                  <p style="position: absolute; top: 20px; width: 500px; font-size: 12px;line-height: 20px;white-space: normal; max-height: 160px;overflow: hidden">{{scope.row.details}}</p>
                </div>
                <div>
                  <span style="color: red;position: absolute; top:220px;font-weight: bold;font-size: 16px;width: 100px;left: 600px">退款原因</span>
                  <p style="left: 600px;position: absolute; top: 236px; width: 500px; font-size: 12px;line-height: 20px;white-space: normal;max-height: 60px;overflow: hidden;color: red">{{scope.row.backInfo}}</p>
                </div>
                <div style="position: absolute; right: 60px; top: 330px;">
                  <el-button-group>
                    <el-button type="danger" size="small">拒绝</el-button>
                    <el-button type="success" size="small">接受</el-button>
                  </el-button-group>
                </div>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-show="allOrderShow" style="position: absolute; left: 50%; width: 1200px; margin-left: -600px; top: 140px; height: 500px; background-color: white">
        <div style="position: fixed; left: 50%; width: 1200px; margin-left: -600px; top: 140px; height: 500px; background-color: white"></div>
        <div style="position: absolute; height: 50px;width: 1200px;">
          <div style="position: absolute; left: 60px; top: 20px;">
            <span style="position: absolute;left: 0;width: 40px">日期</span>
            <el-radio style="position: absolute;left: 80px; top: 4px;" v-model="order_date_radio" label="1">今日</el-radio>
            <el-radio  style="position: absolute;left: 160px; top: 4px;"v-model="order_date_radio" label="2">本月</el-radio>
            <el-date-picker size="small" style="width: 300px; position: absolute; left: 240px;top: -5px;"
              v-model="order_date_picker"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker>
            <span style="position: absolute; left: 800px;width: 100px">状态筛选</span>
            <el-select @change="handleSituationChange" style="position: absolute; left: 920px;width: 140px;top: -5px;" v-model="order_situation_selected" placeholder="选择状态" size="small">
              <el-option
                v-for="item in order_situation_options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </div>
        </div>
        <el-table :data="allOrderList" style="width: 1200px; padding-right: 25px; padding-left: 25px; ;margin-top: 70px">
          <el-table-column type="index">
          </el-table-column>
          <el-table-column label="时间" prop="time" width="140">
          </el-table-column>
          <el-table-column label="姓名" prop="address.name" width="100"></el-table-column>
          <el-table-column label="手机号码" prop="address.phone" width="120"></el-table-column>
          <el-table-column label="地址" width="530">
            <template slot-scope="scope">
              <div>
                <div>{{scope.row.address.province}}/{{scope.row.address.city}}/{{scope.row.address.part}}</div>
                <div style="font-size: 12px; margin-top: 3px">{{scope.row.address.details}}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="状态" width="100">
            <template slot-scope="scope">
              <span>{{order_situation_options[scope.row.situation-1].label}}</span>
            </template>
          </el-table-column>
          <el-table-column label="查看详情" type="expand" width="100">
            <template slot-scope="scope">
              <div>
                <goods-list :has-coupon="scope.row.coupons.length != 0" :coupon_discount="scope.row.coupons[0].discount" :coupon_satisfied="scope.row.coupons[0].satisfied" :goods="scope.row.goodItems" :total="scope.row.total" ></goods-list>
                <div style="position: absolute; left: 600px; top: 20px;">
                  <span style="position: absolute; top:0px;font-weight: bold;font-size: 16px;width: 100px">备注：</span>
                  <p style="position: absolute; top: 20px; width: 500px; font-size: 12px;line-height: 20px;white-space: normal; max-height: 160px;overflow: hidden">{{scope.row.details}}</p>
                </div>
                <div v-if="scope.row.situation == 5">
                  <span style="color: red;position: absolute; top:220px;font-weight: bold;font-size: 16px;width: 100px;left: 600px">取消原因</span>
                  <p style="left: 600px;position: absolute; top: 236px; width: 500px; font-size: 12px;line-height: 20px;white-space: normal;max-height: 60px;overflow: hidden;color: red">{{scope.row.backInfo}}</p>
                </div>
                <div v-if="scope.row.situation== 6">
                  <span style="color: red;position: absolute; top:220px;font-weight: bold;font-size: 16px;width: 100px;left: 600px">退款原因</span>
                  <p style="left: 600px;position: absolute; top: 236px; width: 500px; font-size: 12px;line-height: 20px;white-space: normal;max-height: 60px;overflow: hidden;color: red">{{scope.row.backInfo}}</p>
                </div>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>

    </div>
  </div>
</template>

<script>
  import MerchantHeadNavBar from '../../components/merchant/MerchantHeadNavBar'
  import ElBadge from "element-ui/packages/badge/src/main"
  import GoodsList from '../../components/merchant/GoodsList'
    export default {
      components: {
        ElBadge,
        MerchantHeadNavBar,
        GoodsList
      },
      name: "order-manage-page",
      data() {
        return {
          show:true,
          newOrderShow:true,
          cancelOrderShow: false,
          conveyOrderShow: false,
          backOrderShow: false,
          allOrderShow:false,
          order_date_radio:'1',
          order_date_picker:'',
          order_situation_selected:'全部',
          order_situation_options:[
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
          newOrderList:[],
          cancelOrderList:[],
          conveyOrderList:[],
          backOrderList:[],
          allOrderList:[],
          orders:[]
        }
      },
      created(){
        this.getAllOrders()
      },
      methods: {
        handleSituationChange(situation){
    //      console.log(situation)
          this.allOrderList = []
          this.$http.post('http://localhost:8080/end/merchant/get/orders?mid='+localStorage.getItem("merchantID")).then(result=> {
            this.orders = result.data
            for(var z=0;z<this.orders.length;z++){
              var order = this.orders[z]
              if(order.situation == situation) {
                order.goodItems.push({
                  name:'配送',
                  price:this.orders[z].conveyCost,
                  number:1,
                  total:this.orders[z].conveyCost
                })
                order.goodItems.push({
                  name:'餐盒',
                  price:this.orders[z].packageCost,
                  number:1,
                  total:this.orders[z].conveyCost
                })
                this.allOrderList.push(order)
              }
            }
          })
        },
        acceptOrder(index,oid){
        //  this.newOrderShow
          var path = 'http://localhost:8080/end/merchant/update/situation?oid='+oid+'&&situation=2'
          this.$http.post(path).then(result=>{
            this.newOrderList[index].situation = 2
            this.newOrderList[index].conveyDisabled = false
            this.newOrderList[index].acceptDisabled=true
            }
          )
        },

        conveyOrder(index, oid){
          var path = 'http://localhost:8080/end/merchant/update/situation?oid='+oid+'&&situation=3'
          this.$http.post(path).then(result=>{
              this.getAllOrders()
            }
          )
        },

        refuseCancelOrder(index, oid){
          var path = 'http://localhost:8080/end/merchant/update/situation?oid='+oid+'&&situation=4'
          this.$http.post(path).then(result=>{
              this.getAllOrders()
            }
          )
        },
        acceptCancelOrder(index, oid){
          var path = 'http://localhost:8080/end/merchant/accept/cancel?oid='+oid
          this.$http.post(path).then(result=>{
              this.getAllOrders()
            }
          )
        },

        getAllOrders(){
          this.newOrderList = []
          this.conveyOrderList = []
          this.backOrderList = []
          this.allOrderList = []
          this.cancelOrderList = []
          this.$http.post('http://localhost:8080/end/merchant/get/orders?mid='+localStorage.getItem("merchantID")).then(result=> {
            this.orders = result.data
            for(var z=0;z<this.orders.length;z++){
              var order = this.orders[z]
              order.goodItems.push({
                name:'配送',
                price:this.orders[z].conveyCost,
                number:1,
                total:this.orders[z].conveyCost
              })
              order.goodItems.push({
                name:'餐盒',
                price:this.orders[z].packageCost,
                number:1,
                total:this.orders[z].conveyCost
              })
              if(this.orders[z].situation == 1){
                order.conveyDisabled = true
                order.acceptDisabled = false
                this.newOrderList.push(order)
              }else if(this.orders[z].situation == 2) {
                order.conveyDisabled = false
                order.acceptDisabled = true
                this.newOrderList.push(order)
              }else if(this.orders[z].situation == 5){
                this.cancelOrderList.push(order)
              }else if(this.orders[z].situation==3){
                this.conveyOrderList.push(order)
              }else if(this.orders[z].situation == 6) {
                this.backOrderList.push(order)
              }
              this.allOrderList.push(order)
            }
          })
        },

        handleSelect(key){
          this.initShow()
          if(key == 1)
            this.newOrderShow=true
          else if(key == 2)
            this.cancelOrderShow=true
          else if(key == 3)
            this.conveyOrderShow=true
          else if(key == 4)
            this.backOrderShow=true
          else if(key == 5)
            this.allOrderShow = true
        },
        initShow(){
          this.newOrderShow=false
          this.cancelOrderShow=false
          this.conveyOrderShow=false
          this.backOrderShow=false
          this.allOrderShow = false
        },
        new_indexMethod(index){
          return this.newOrderList.length-index;
        },
        cancel_indexMethod(index){
          return this.cancelOrderList.length-index;
        },
        back_indexMethod(index){
          return this.backOrderList.length-index;
        },
        convey_indexMethod(index){
          return this.conveyOrderList.length-index;
        }
      }
    }
</script>

<style scoped>

</style>
