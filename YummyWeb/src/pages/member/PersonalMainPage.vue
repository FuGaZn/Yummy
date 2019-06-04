<template>
  <div>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <head-nav-bar></head-nav-bar>
    <left-nav-bar :personal-center="personalCenter" :my-account="myAccount" :statistics="toStatistic" :my-data="myData" :my-orders="myOrder"></left-nav-bar>
    <div style="font-size: 13px; color: black; position: absolute; top: 85px; left: 200px">>&nbsp&nbsp{{nowOperate}}</div>
    <div>
      <div style="width: 1000px; height: 180px; background-color: white; position: absolute; top: 120px; left: 200px">
        <el-button @click="cropperShow = true" type="text" style="font-size: 15px; position: absolute; left: 60px; top: 60px;"><i class="el-icon-edit-outline"/>上传头像</el-button>
        <img @click="cropperShow = true" class="headImage" :src="user.image"/>
         <div style="position: absolute; left: 200px; top: 60px">
          <div style="position: absolute; top: -30px;left: -10px;width: 200px;text-align: left">{{user.name}}</div>
          <div style="position: absolute; left: -20px; top: 0;
          margin-left: 10px;background-color: #404040; font-size: 12px; width: 60px; height: 20px;color: white;border-radius: 5px;padding-top: 2px" >{{levelLabel}}</div>
        </div>
        <el-progress :percentage="percent" style="width: 170px;position: absolute; left: 160px; top: 90px;"></el-progress>
        <div style="font-size: 10px; position: absolute; left: 320px; top:95px">离下一等级还差{{user.nextLevelScore-user.score}}分</div>
        <div style="width: 600px; height: 120px; position: absolute; left: 400px;top: 30px;">
          <div style="height: 120px; width: 1px; background-color: rgba(120,120,120,0.3); position: absolute; right: 200px;"></div>
          <div @click="couponDialogVisible = true" style="width: 300px;height: 120px;position: absolute;left: 0;top: 0;cursor: pointer">
            <div style="position: absolute; left: 270px; top: 15px;width: 300px;text-align: left">优惠卷</div>
            <p style="font-size: 40px; color: #FC463F; font-weight: bold; position: absolute; left: 200px; top: 10px; width: 200px; alignment: center">{{user.coupons.length}}<span style="font-size: 16px;font-weight: normal">个</span></p>
          </div>
          <div style="height: 120px; width: 1px; background-color: rgba(120,120,120,0.3); position: absolute; right: 400px;"></div>
          <div style="position: absolute; left: 460px; top: 15px;">账户余额</div>
          <p style="font-size: 40px; color: #FF9C00; font-weight: bold; position: absolute; left: 400px; top: 10px; width: 200px; alignment: center">{{user.balance}}<span style="font-size: 16px;font-weight: normal">元</span></p>
        </div>
      </div>
      <div style="width: 1000px; height: 300px; background-color: white; position: absolute; top:320px; left: 200px;">
        <div>
          <span style="font-size: 16px; color: #353535; position: absolute; left: 30px; top: 14px;">最近订单</span>
          <div style="width: 950px; background-color: rgba(85,85,85,0.5); height: 1px; position: absolute; left: 25px; top: 44px;"></div>
          <el-button @click="myOrder" type="text" style="position:absolute; right: 30px; top: 10px; font-size: 12px">全部订单&nbsp></el-button>
        </div>
        <div style="width: 100%; background-color: white;">
        </div>
      </div>
    </div>
    <shopping-cart></shopping-cart>
    <el-dialog title="优惠券" :visible.sync="couponDialogVisible" width="430px">
      <div style="margin-top: -30px;height: 300px;overflow: scroll;overflow-x: hidden">
        <div v-for="item in user.coupons" :key="item">
          <div v-show="item.disabled == false" style="position: relative;float: top;width: 350px;height: 80px;margin: 10px">
            <div style="position: absolute;width: 350px;height: 80px;background-color: rgba(255, 86, 88, 0.9);">
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
          </div>
        </div>
      </div>
    </el-dialog>
    <image-cropper title="修改头像" :dialog-visible="cropperShow" @setDialogDisVisible="setDialogDisVisible" style="position: absolute; bottom: 50px; left: 10px" @getImage="getSingleImage"></image-cropper>
  </div>
</template>

<script>
  import HeadNavBar from '../../components/member/HeadNavBar'
  import LeftNavBar from '../../components/member/LeftNavBar'
  import ShoppingCart from '../../components/member/ShoppingCart'
  import ImageCropper from '../../components/common/ImageCropper'
  import image from '../../assets/headImage2.jpg'
  export default {
    components:{HeadNavBar,LeftNavBar,ShoppingCart,ImageCropper},
    name: "personal-info-page",
    data(){
      return{
        cropperShow:false,
        nowOperate:"个人中心",
        headImage:image,
        couponDialogVisible:false,
        user:{
          uid:"",
          name:"",
          image:"",
          email:"",
          password:"",
          balance:36.5,
          level:1,
          score:160,
          points:20,
          nextLevelScore:200,
          coupons:[{
          }]
        },
        levelLabel:"普通会员",
        percent:0,
        endUserModel:{}
      }
    },
    created(){
      this.user.name = localStorage.getItem("username");
      this.user.email = localStorage.getItem("userEmail");
      this.user.uid = localStorage.getItem("userID")
      var path = "http://localhost:8080/end/user/get?uid="+this.user.uid;
      this.$http.post(path).then(result=>{
        this.user = result.data
        if(this.user.level == 1){
          this.levelLabel="普通会员";
        }else if(this.user.level == 2)
          this.levelLabel='黄金会员';
        else if(this.user.level==3)
          this.levelLabel = '铂金会员';
        else if(this.user.level == 4)
          this.levelLabel = '钻石会员'
        this.percent = (this.user.score*100/this.user.nextLevelScore).toFixed(2);

      })
    },
    methods:{
      updateUserModel(){
        var path = "http://localhost:8080/end/user/update/user"
        this.$http.post(path,this.endUserModel).then(result=>{
        })
      },

      addImage(){
        this.cropperShow = true
        this.$emit("setDialogVisible",true)
      },
      setDialogDisVisible(val){
        this.cropperShow = false
      },
      getSingleImage(val){
        var path = "http://localhost:8080/end/user/get?uid="+localStorage.getItem("userID")
        this.$http.post(path).then(result=>{
          var user = result.data
          this.endUserModel = user
          this.endUserModel.image = val
          this.user = user;
          this.updateUserModel()
        })
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
.headImage{
  width: 120px; position: absolute; left: 40px; top: 30px;
}
  .headImage:hover{
    opacity:0.2;
    cursor: pointer;

  }
</style>
