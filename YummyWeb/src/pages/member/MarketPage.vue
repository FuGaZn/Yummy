<template>
  <div>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <head-nav-bar></head-nav-bar>
    <div style="position: absolute; left: 50px;top: 75px;">
      <span style="color: black;font-size: 12px;">当前位置:&nbsp{{nowAddress.province}}/{{nowAddress.city}}/{{nowAddress.part}}&nbsp{{nowAddress.details}}</span>
      <el-button @click="selectAddressDialogVisible=true" type="text" style="font-size: 12px;">[切换地址]</el-button>
    </div>
    <div style="position: absolute; right: 50px; top: 80px;">
      <el-input style="width: 300px;border-radius: 0" size="small" placeholder="搜索商家、美食">
        <el-button slot="append" icon="el-icon-search"></el-button>
      </el-input>
    </div>
    <div style="position: absolute;width: 94%;left: 2%; top: 120px;height: 100px; background-color: white">
      <span style="position: absolute;left: 20px; top: 20px;font-size: 14px;font-weight: bold">商家分类</span>
      <div style="position: absolute; left: 100px; top: 10px;width: 90%">
        <div  v-for="item in merchantTypes" :key="item" style="width: 100%">
          <div class="tag" @click="searchTypeMerchants(item)">{{item.label}}</div>
        </div>
      </div>
      <div>
        <span style="position: absolute;left: 20px; bottom: 20px;font-size: 14px;font-weight: bold">当前位置</span>
        <span style="position: absolute;left: 100px; bottom: 20px;font-size: 14px;color: red">{{nowType.label}}</span>
      </div>
    </div>
    <div style="position: absolute;width: 94%;left: 2%; top: 230px;height: 400px; background-color: white;border: 1px solid rgb(216,216,216);">
      <div v-for="item in merchants" :key="item">
        <el-popover
          placement="right-start"
          width="250"
          trigger="hover">
          <div @click="goMerchantPage(item.serial)"  style="width: 250px;position: relative;cursor: pointer">
            <p style="font-size: 14px;font-weight: bold;width: 230px;color: #454545; margin-left: 10px;
                margin-top: 0px;line-height: 22px">
              {{item.name}}
            </p>
            <div style="font-size: 12px;margin-top: -5px;margin-left: 10px">{{merchantTypes[item.type+1].label}}</div>
            <div style="font-size: 12px;width: 220px;margin-left: 15px;height: 20px; margin-top: 10px;
                background-color: rgba(233,233,233,0.5);text-align: center">
              配送费：￥{{item.conveyCost}}</div>
            <p class="p1">
              {{item.bulletin}}
            </p>
          </div>
          <div @click="goMerchantPage(item.serial)" class="merchant" style="position: relative;cursor: pointer" slot="reference">
            <img :src="item.image" style="width: 110px;position: absolute;top: 20px;left: 20px">
            <span style="font-size: 16px;color: black;font-weight: bold; position:absolute; left: 140px; top: 22px; width: 140px; text-align: left;
                overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">{{item.name}}
            </span>
            <div class="tag2" style="position: absolute; left: 140px;top: 50px; font-size: 13px">
              {{merchantTypes[item.type+1].label}}
            </div>
            <span style="position: absolute;left: 144px;top: 90px;font-size: 12px">配送费：￥{{item.conveyCost}}</span>
          </div>
        </el-popover>

      </div>
    </div>
    <shopping-cart></shopping-cart>
    <el-dialog :visible.sync="dialogVisible" width="360px">
      <span>请先去添加地址</span>
      <el-button @click="goDataManage" type="primary" style="margin-left: 20px">确定</el-button>
    </el-dialog>
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
  </div>
</template>

<script>
  import HeadNavBar from '../../components/member/HeadNavBar'
  import ShoppingCart from '../../components/member/ShoppingCart'
  import HeadImage from '../../assets/headImage2.jpg'
  export default {
    name: "market-page",
    components:{HeadNavBar,ShoppingCart},
    data(){
      return{
        selectAddressDialogVisible:false,
        dialogVisible:false,
        user:{},
        nowAddress:{},
        nowType:{
          value:-1,
          label:'全部'
        },
        merchantTypes:[
          {
            value:-1,
            label:'全部'
          },
          {
            value:0,
            label:'果蔬生鲜'
          },
          {
            value:1,
            label:'甜点饮品'
          },
          {
            value:2,
            label:'特色菜系'
          },
          {
            value:3,
            label:'快餐便当'
          },
          {
            value:4,
            label:'小吃夜宵'
          },

        ],
        merchants:[
        ]
      }
    },
    created(){
      this.getMerchantsByNowAddress()
      this.getUserModel()
    },
    methods:{
      changeAddress(aid){
        for(var i=0;i<this.user.addresses.length;i++){
          if(this.user.addresses[i].aid == aid){
            this.nowAddress = this.user.addresses[i]
            localStorage.setItem("addressID",this.nowAddress.aid)
            localStorage.setItem("addressCode",this.nowAddress.part_code)
          }
        }
        this.selectAddressDialogVisible = false
        this.getMerchantsByNowAddress()
      },
      goDataManage(){
        this.dialogVisible = false;
        this.$router.push("/data")
      },
      getMerchantsByNowAddress(){
        if (localStorage.getItem("addressCode") == undefined){
          this.dialogVisible = true;
        }
        var path = "http://localhost:8080/end/user/get/merchants/address?address_code="+localStorage.getItem("addressCode")
        this.$http.post(path).then(result=>{
          this.merchants = result.data
        })
      },
      getUserModel(){
        var path = "http://localhost:8080/end/user/get?uid="+localStorage.getItem("userID")
        this.$http.post(path).then(result=>{
          var user = result.data
          this.user = user
          this.nowAddress = this.user.addresses[0]
        })
      },
      goMerchantPage(id){
        var path = '/shop/'+id
        localStorage.setItem("merchantSerial",id)
        this.$router.push(path)
      },
      searchTypeMerchants(item){
        if (localStorage.getItem("addressCode") == undefined){
          this.dialogVisible = true;
        }
        this.nowType = item
        if(item.value == -1){
          this.getMerchantsByNowAddress()
        }else{
          var path = "http://localhost:8080/end/user/get/merchants/type?type="+item.value+"&&address_code="+localStorage.getItem("addressCode")
          this.$http.post(path).then(result=>{
            this.merchants = result.data
          })
        }
      }
    }
  }
</script>

<style scoped>
  .tag{
    float: left; font-size: 13px;padding: 5px;background-color: white; border-radius: 3px;cursor: pointer;
    padding-left: 10px;padding-right: 10px;
    margin: 5px;
  }
  .tag:hover{
    background-color: rgba(233,233,233,0.5);
  }
  .tag2{
    float: left; font-size: 12px; border-radius: 3px;
    padding:2px 5px 2px 5px;
    margin: 5px;border: 1px solid rgb(216,216,216);
    color: rgb(120, 120, 120);
  }
  .merchant{
    float: left;
    width: 292px;
    height: 150px;
    background-color: white;
    border-bottom: 1px solid rgb(216,216,216)
  }
  .merchant:hover{
    background-color: rgba(233,233,233,0.5);
  }
  .p1{
    font-size: 12px;width: 226px;line-height: 20px; background-color: white;margin-left: 12px;
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
