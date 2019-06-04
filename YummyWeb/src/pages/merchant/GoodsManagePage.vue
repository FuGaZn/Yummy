<template>
  <div>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <merchant-head-nav-bar></merchant-head-nav-bar>
    <div style="position: absolute; left: 0; top: 65px;width: 100%; height: 60px; background-color: white">
      <el-menu default-active="1" mode="horizontal" style="position: absolute; top: 0px;width: 600px;left: 80px" @select="handleSelect">
        <el-menu-item index="1">全部
          <el-badge :value="newOrders" :hidden="newOrders == 0" style="margin-left: 3px; margin-top: -3px"></el-badge>
        </el-menu-item>
        <el-menu-item index="2">单品<el-badge :value="cancelOrders" :hidden="cancelOrders == 0" style="margin-left: 3px; margin-top: -3px"></el-badge>
        </el-menu-item>,
        <el-menu-item index="3">套餐<el-badge :value="cancelOrders" :hidden="cancelOrders == 0" style="margin-left: 3px; margin-top: -3px"></el-badge>
        </el-menu-item>
        <el-menu-item index="4">优惠券<el-badge :value="cancelOrders" :hidden="cancelOrders == 0" style="margin-left: 3px; margin-top: -3px"></el-badge>
        </el-menu-item>
      </el-menu>
      <div style="position: absolute; right: 100px;top: 10px;">
        <el-button @click="modifyCostDialogShow=true" type="text">修改配送费、包装费</el-button>
      </div>
    </div>
    <div style="position: absolute; left: 2%; top: 140px; width: 98%; height: 400px;">
      <div v-show="showGoods" v-for="item in arrayShow" :key="item">
        <el-popover
          placement="bottom"
          title="备注"
          width="200"
          trigger="hover" v-bind:content="item.details">
          <div slot="reference" class="good_div">
            <div style="position: relative">
              <img :src="item.image" style="position: absolute; right: 15px; top: 15px;width: 105px">
              <span class="font_family"
                    style="text-align:left;position: absolute; left: 20px; top: 14px;font-size: 15px;width: 130px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;"
              >{{item.name}}</span>
              <span style="font-size: 13px; color: #ff5658;position: absolute; left: 20px; top: 40px;">￥&nbsp<span style="font-weight: bold">{{item.price}}</span>&nbsp元</span>
              <span style="position: absolute; left: 20px; top: 100px; font-size: 16px">{{item.real_number}}&nbsp/&nbsp{{item.max_number}}</span>
              <el-button type="info" style="position: absolute; right: 15px; top: 130px; font-size: 12px" size="mini" @click="modifyGood(item.id,item.type)">编辑</el-button>
              <el-button-group style="position: absolute; left: 20px; top: 130px;">
                <el-button @click="topUpNumber(item.id, item.type)" type="success" v-show="item.bought_number!=0" style="font-size: 12px" size="mini">置满</el-button>
                <el-button @click="clearNumber(item.id,item.type)" type="info" v-show="item.bought_number != item.max_number" style="font-size: 12px;" size="mini">沽清</el-button>
              </el-button-group>
            </div>
          </div>
        </el-popover>


      </div>
      <div v-show="!showGoods" v-for="item in coupons" :key="item">
        <div style="margin: 5px;position: relative;float: left;width: 350px;height: 80px;border: 1px solid rgb(255, 9, 9);background-color: rgba(255, 86, 88, 0.9);margin: 10px">
          <div style="position: absolute; width: 350px;left: 50%; margin-left: -175px; top: 15px;" v-if="item.satisfied==0">
            <span style="color: white;font-size: 18px">【无门槛红包】&nbsp￥<span>{{item.discount}}</span>&nbsp元</span>
          </div>
          <div style="position: absolute; width: 350px;left: 50%; margin-left: -175px; top: 15px;" v-if="item.satisfied!=0">
            <span style="color: white;font-size: 18px">【满减红包】&nbsp满&nbsp{{item.satisfied}}&nbsp减&nbsp{{item.discount}}&nbsp元</span>
          </div>
          <div style="position: absolute; width: 350px;left: 50%; margin-left: -175px; top: 45px;">
            <span style="color: white;font-size: 13px;">{{item.start}} &nbsp - &nbsp {{item.end}}</span>
          </div>
          <el-button @click="openDeleteDialog(item.cid)" type="text" style="position: absolute; right: 5px;top: -8px;color: white;font-size: 14px"><i class="el-icon-error"></i></el-button>
        </div>
      </div>
    </div>
    <add-btn-group style="position: fixed; bottom: 50px; right: 50px"></add-btn-group>
    <el-dialog :visible.sync="dialogVisible" width="360px">
      <span>确定要删除该优惠卷吗</span>
      <el-button @click="deleteCoupon(couponInd)" type="primary" style="margin-left: 20px">删除</el-button>
    </el-dialog>
    <el-dialog :visible.sync="modifyCostDialogShow" width="400px">
      <div style="height: 100px"></div>
        <span style="position: absolute; left: 50px;top: 60px;">配送费</span>
      <el-input size="small" style="width: 200px;position: absolute;left: 110px;top: 55px;" v-model="modelMerchant.conveyCost"></el-input>
      <span style="position: absolute; left: 50px;top: 110px;">包装费</span>
      <el-input size="small" style="width: 200px;position: absolute;left: 110px;top: 105px;" v-model="modelMerchant.packageCost"></el-input>
      <el-button @click="modifyCost" type="primary" size="small" style="width: 100px">确定</el-button>
    </el-dialog>
  </div>
</template>

<script>
  import MerchantHeadNavBar from '../../components/merchant/MerchantHeadNavBar'
  import AddBtnGroup from '../../components/merchant/AddBtnGroup'
  import image from '../../assets/headImage2.jpg'
  export default {
    name: "goods-manage-page",
    components:{MerchantHeadNavBar,AddBtnGroup},
    data(){
      return{
        modifyCostDialogShow:false,
        dialogVisible:false,
        couponInd:0,
        showGoods:true,
        modelMerchant:{},
        arrayShow:[

        ],
        allGoods:[

        ],
        singles:[
        ],
        packages:[

        ],
        coupons:[
        ],
      }
    },
    created(){
      var mid = localStorage.getItem("merchant_ID")
      this.getModelMerchant()
    },
    methods:{
      modifyCost(){
        this.modifyCostDialogShow = false
        this.info(1,'修改成功')
        this.updateModelMerchant()
      },
      topUpNumber(id, type){
        if(type == 0){
          this.$http.post('http://localhost:8080/end/merchant/get/?mid='+localStorage.getItem("merchantID")).then(result=>{
            var merchant = result.data
            // console.log(JSON.stringify(merchant))
            for(var i=0;i<merchant.goods.length;i++){
              if (merchant.goods[i].gid == id){
                merchant.goods[i].bought_number = 0
              }
            }
            this.$http.post('http://localhost:8080/end/merchant/update/merchant',merchant).then(result=>{
              this.getModelMerchant()
            })
          })
        }else{
          this.$http.post('http://localhost:8080/end/merchant/get/?mid='+localStorage.getItem("merchantID")).then(result=>{
            var merchant = result.data
            for(var i=0;i<merchant.packages.length;i++){
              if (merchant.packages[i].pid == id){
                merchant.packages[i].bought_number =0
                break
              }
            }
            this.$http.post('http://localhost:8080/end/merchant/update/merchant',merchant).then(result=>{
              this.getModelMerchant()
            })
          })
        }
      },
      clearNumber(id,type){
        if(type == 0){
          this.$http.post('http://localhost:8080/end/merchant/get/?mid='+localStorage.getItem("merchantID")).then(result=>{
            var merchant = result.data
           // console.log(JSON.stringify(merchant))
            for(var i=0;i<merchant.goods.length;i++){
              if (merchant.goods[i].gid == id){
                merchant.goods[i].bought_number = merchant.goods[i].max_number
                console.log(JSON.stringify(merchant.goods[i]))
              }
            }
            this.$http.post('http://localhost:8080/end/merchant/update/merchant',merchant).then(result=>{
              this.getModelMerchant()
            })
          })
        }else{
          this.$http.post('http://localhost:8080/end/merchant/get/?mid='+localStorage.getItem("merchantID")).then(result=>{
            var merchant = result.data
            for(var i=0;i<merchant.packages.length;i++){
              if (merchant.packages[i].pid == id){
                merchant.packages[i].bought_number = merchant.packages[i].max_number
                break
              }
            }
            this.$http.post('http://localhost:8080/end/merchant/update/merchant',merchant).then(result=>{
              this.getModelMerchant()
            })
          })
        }
      },

      updateModelMerchant(){
        this.$http.post('http://localhost:8080/end/merchant/update/merchant',this.modelMerchant).then(result=>{
          this.getModelMerchant()
        })
      },

      openDeleteDialog(i){
        this.couponInd = i;
        this.dialogVisible = true
      },

      deleteCoupon(cid){
        this.dialogVisible = false
        this.$http.post('http://localhost:8080/end/merchant/delete/coupon?cid='+cid).then(result=> {
          console.log(result.data)
          this.info(1,'删除成功')
          this.coupons = []
          this.getModelMerchant()
        })
      },

      getModelMerchant(){
        var id = localStorage.getItem("merchantID")
        this.$http.post('http://localhost:8080/end/merchant/get/?mid='+id).then(result=>{
          this.modelMerchant = result.data
          this.coupons = []
          this.singles = []
          this.packages = []
          this.allGoods = []
          var coupons = this.modelMerchant.coupons;
          for (var i=0;i<coupons.length;i++){
            var c = {
              cid:coupons[i].cid,
              type: 0,
              discount: coupons[i].discount,
              satisfied: coupons[i].satisfied,
              start: coupons[i].start,
              end: coupons[i].end
            }
            if(c.satisfied != 0)
              c.type = 1
            this.coupons.push(c)
          }
          var goods = this.modelMerchant.goods
          for(var i=0;i<goods.length;i++){
            var g = {
              id:goods[i].gid,
              type: 0,
              name: goods[i].name,
              image:goods[i].image ,
              max_number: goods[i].max_number,
              real_number: goods[i].max_number-goods[i].bought_number,
              price: goods[i].price,
              details:goods[i].details
            }
            this.singles.push(g)
            this.allGoods.push(g)
          }
          var packages = this.modelMerchant.packages
          for(var i=0;i<packages.length;i++){
            var p = {
              id:packages[i].pid,
              type: 1,
              name: packages[i].name,
              image:packages[i].image ,
              max_number: packages[i].max_number,
              real_number: packages[i].max_number-packages[i].bought_number,
              price: packages[i].real_price,
              details:packages[i].details
            }
            this.packages.push(p)
            this.allGoods.push(p)
          }
          this.arrayShow = this.allGoods
        })
      },
      modifyGood(id, type){
        if(type == 0){
          localStorage.setItem("modifiedID",id)
          localStorage.setItem("modifiedType","good")
        }else{
          localStorage.setItem("modifiedID",id)
          localStorage.setItem("modifiedType","package")
        }
        this.$router.push({path: '/merchant/goods/modify'})
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
      handleSelect(index){
        console.log("key "+index)
        this.showGoods = true;
        this.arrayShow = []
        if (index == '1'){
          this.arrayShow = this.allGoods;
        }else if(index == '2'){
          this.arrayShow = this.singles;
        }else if(index == '3'){
          this.arrayShow = this.packages;
        }else{
          this.showGoods = false;
        }
      }
    }
  }
</script>

<style scoped>
  .good_div{
    float: left; width: 280px;height: 170px;background-color: white;margin:10px;
    box-shadow: 1px 2px 4px rgba(233,233,233,1), 2px 6px 6px rgba(233,233,233,0.5);
    border:1px solid #fdfdfd;
  }
  .good_div:hover{
    border:1px solid #1aaaf0;
  }
  .font_family{
    font-family: "Microsoft YaHei";
  }
</style>
