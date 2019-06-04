<template>
  <div>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <merchant-head-nav-bar></merchant-head-nav-bar>
    <div style="font-size: 13px; color: black; position: absolute; top: 80px; left: 50px">>&nbsp&nbsp基本信息</div>
    <div style="position: absolute; width: 900px; left: 50%; margin-left: -450px; height: 450px;top: 80px; background-color: white">
      <el-menu default-active="1" mode="horizontal" style="padding-left: 20px" @select="handleSelect">
        <el-menu-item index="1">店铺信息</el-menu-item>
        <el-menu-item index="2">资质认证</el-menu-item>
        <el-menu-item index="3">账号信息</el-menu-item>
      </el-menu>
      <div v-show="base_info_show" style="position: absolute; left: 0px; width: 100%; height: 500px; background-color:white; top: 66px;">
        <div>
          <span style="font-size: 13px; font-weight: bold;position: absolute; left: 40px; top: 20px;">门店头像</span>
          <img :src="modelMerchant.image" style="width: 60px;position: absolute; left: 120px; top: 20px;"/>
          <el-button @click="cropperShow = true" type="text" style="position: absolute; right: 30px; top: 8px;">修改</el-button>
          <div style="position: absolute; left: 40px; width: 830px; height: 0.5px; background-color: rgba(223,223,223,0.8);top: 100px;"></div>
        </div>
        <div>
          <span style="font-size: 13px; font-weight: bold;position: absolute; left: 40px; top: 120px;">门店名称</span>
          <span style="font-size: 13px; position: absolute; left: 120px; top: 122px;">{{modelMerchant.name}}</span>
          <el-button @click="modify_name.dialogVisible=true" type="text" style="position: absolute; right: 30px; top: 108px;">修改</el-button>
          <div style="position: absolute; left: 40px; width: 830px; height: 0.5px; background-color: rgba(223,223,223,0.8);top: 160px;"></div>
        </div>
        <div>
          <span style="font-size: 13px; font-weight: bold;position: absolute; left: 40px; top: 180px;">联系电话</span>
          <span style="font-size: 13px; position: absolute; left: 120px; top: 182px;">{{modelMerchant.phone}}</span>
          <el-button @click="modify_phone.dialogVisible=true" type="text" style="position: absolute; right: 30px; top: 168px;">修改</el-button>
          <div style="position: absolute; left: 40px; width: 830px; height: 0.5px; background-color: rgba(223,223,223,0.8);top: 220px;"></div>
        </div>
        <div>
          <span style="font-size: 13px; font-weight: bold;position: absolute; left: 40px; top: 240px;">店铺地址</span>
          <span style="font-size: 13px; position: absolute; left: 120px; top: 242px;">{{modelMerchant.address.province}}/{{modelMerchant.address.city}}/{{modelMerchant.address.part}}</span>
          <el-button @click="modify_address.dialogVisible=true" type="text" style="position: absolute; right: 30px; top: 228px;">修改</el-button>
          <div style="position: absolute; left: 40px; width: 830px; height: 0.5px; background-color: rgba(223,223,223,0.8);top: 220px;"></div>
        </div>
        <div>
          <span style="font-size: 13px; font-weight: bold;position: absolute; left: 40px; top: 300px;">门店简介</span>
          <span style="text-align: left;font-size: 13px; position: absolute; left: 120px; top: 302px; width: 700px;  overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">{{modelMerchant.briefIntroduction}}</span>
          <el-button @click="modify_introduction.dialogVisible=true" type="text" style="position: absolute; right: 30px; top: 288px;">修改</el-button>
          <div style="position: absolute; left: 40px; width: 830px; height: 0.5px; background-color: rgba(223,223,223,0.8);top: 280px;"></div>
        </div>
        <div>
          <span style="font-size: 13px; font-weight: bold;position: absolute; left: 40px; top: 360px;">门店公告</span>
          <p>{{modelMerchant.bulletin}}</p>
          <el-button @click="modify_bulletin.dialogVisible = true" type="text" style="position: absolute; right: 30px; top: 348px;">修改</el-button>
          </div>
      </div>
      <div v-show="certificate_show" style="position: absolute; left: 0px; width: 100%; height: 400px; background-color:white; top: 66px;">
        <div style="position: absolute; top: 150px;left: 400px">已认证</div>
      </div>
      <div v-show="account_show" style="position: absolute; left: 0px; width: 100%; height: 400px; background-color:white; top: 66px;">
        <div>
          <span style="position: absolute; left: 80px; top: 20px;">标识码：&nbsp{{modelMerchant.serial}}</span>
          </div>
        <el-button @click="modify_password.dialogVisible = true" style="position: absolute; top: 8px; left: 300px;font-size: 16px" type="text">修改密码<i class="el-icon-edit-outline"></i></el-button>
      </div>
    </div>
    <image-cropper title="修改图片" :dialog-visible="cropperShow" @setDialogDisVisible="setDialogDisVisible" style="position: absolute; bottom: 50px; left: 10px" @getImage="getSingleImage"></image-cropper>
    <el-dialog title="修改店铺名称" :visible.sync="modify_name.dialogVisible" width="360px">
      <el-input style="width: 200px;" v-model="modify_name.newName"></el-input>
      <el-button @click="modifyName" type="primary">修改</el-button>
    </el-dialog>
    <el-dialog title="修改联系电话" :visible.sync="modify_phone.dialogVisible" width="360px">
      <el-input style="width: 200px;" v-model="modify_phone.newPhone"></el-input>
      <el-button @click="modifyPhone" type="primary">修改</el-button>
    </el-dialog>
    <el-dialog title="修改简介" :visible.sync="modify_introduction.dialogVisible" width="360px">
      <el-input type="textarea" :rows="2" style="width: 300px;float: top;margin-top: -30px" v-model="modify_introduction.context"></el-input>
      <el-button size="primary" style="float: top; margin-top: 10px;margin-left: 220px" @click="modifyIntroduction" type="primary">修改</el-button>
    </el-dialog>
    <el-dialog title="修改公告板" :visible.sync="modify_bulletin.dialogVisible" width="360px">
      <el-input type="textarea" :rows="4" style="width: 300px;float: top;margin-top: -30px" v-model="modify_bulletin.context"></el-input>
      <el-button @click="modifyBulletin" type="primary" style="float: top; margin-top: 10px;margin-left: 220px">修改</el-button>
    </el-dialog>

    <el-dialog title="修改地址" :visible.sync="modify_address.dialogVisible" width="500px">
      <div style="height: 60px"></div>
      <el-cascader autocomplete="off"  style="width: 300px;position:absolute; top: 80px;left: 50px"
                   :options="addressOptions"
                   v-model="modify_address.address">
      </el-cascader>
      <el-button @click="modifyAddress" type="primary" style="position:absolute; top: 80px;left: 150px;margin-left: 220px">修改</el-button>
    </el-dialog>

    <el-dialog title="修改密码" :visible.sync="modify_password.dialogVisible" width="300px">
      <el-form :model="modify_password">
        <el-form-item>
          <el-input v-model="modify_password.oldPassword" style="width: 200px" placeholder="输入旧密码" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="modify_password.newPassword" placeholder="输入新密码" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="modifyPassword" type="primary" style="width: 200px">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
  import MerchantHeadNavBar from '../../components/merchant/MerchantHeadNavBar'
  import logo_image from '../../assets/headImage2.jpg'
  import ImageCropper from '../../components/common/ImageCropper'
  import { provinceAndCityData, regionData, provinceAndCityDataPlus, regionDataPlus, CodeToText, TextToCode }
    from 'element-china-area-data'
    export default {
      components: {MerchantHeadNavBar,ImageCropper},
      name: "merchant-base-info-page",
      data(){
        return{
          addressOptions:regionDataPlus,
          cropperShow:false,
          base_info_show:true,
          certificate_show:false,
          account_show:false,
          modelMerchant:{
          },
          modify_name:{
            newName:"",
            dialogVisible:false
          },
          modify_phone:{
            newPhone:"",
            dialogVisible:false
          },
          modify_introduction:{
            context:"",
            dialogVisible:false
          },
          modify_bulletin:{
             context:"",
            dialogVisible:false
          },
          modify_password:{
            newPassword:"",
            oldPassword:"",
            dialogVisible:false
          },
          modify_address:{
            dialogVisible:false,
            address:[]
          }
        }
      },
      created(){
        this.getModelMerchant()
      },
      methods:{
        modifyAddress(){
          this.modify_address.dialogVisible = false
          if(this.modify_address.address.length!=0){
            var array = this.modify_address.address
            this.modelMerchant.address.province_code = array[0]
            this.modelMerchant.address.city_code = array[1]
            this.modelMerchant.address.part_code = array[2]
            this.modelMerchant.address.province = CodeToText[array[0]]
            this.modelMerchant.address.city = CodeToText[array[1]]
            this.modelMerchant.address.part = CodeToText[array[2]]
            this.updateBaseInfo()
          }
        },

        modifyPassword(){
          var oldPasswd = this.modify_password.oldPassword
          var newPasswd = this.modify_password.newPassword
          if(oldPasswd == ''){
            this.info(2,'请填入旧密码')
            return
          }
          if (newPasswd == ''){
            this.info(2,'请填入新密码')
            return
          }
          if (oldPasswd != this.modelMerchant.password){
            this.info(2,"旧密码错误")
            return
          }
          this.modelMerchant.password = newPasswd
          this.updateMerchant()
          this.modify_password.dialogVisible = false
          this.info(1,'密码修改成功')
        },
        modifyName(){
          this.modelMerchant.name = this.modify_name.newName
          this.updateBaseInfo()
          this.modify_name.dialogVisible=false
        },

        modifyPhone(){
          this.modelMerchant.phone = this.modify_phone.newPhone
          this.updateBaseInfo()
          this.modify_phone.dialogVisible=false
        },
        modifyIntroduction(){
          this.modelMerchant.briefIntroduction = this.modify_introduction.context
          this.updateBaseInfo()
          this.modify_introduction.dialogVisible=false
        },
        modifyBulletin(){
          this.modelMerchant.bulletin = this.modify_bulletin.context
          this.updateBaseInfo()
          this.modify_bulletin.dialogVisible=false
        },

        addImage(){
          this.cropperShow = true
          this.$emit("setDialogVisible",true)
        },
        setDialogDisVisible(val){
          this.cropperShow = false
        },
        getSingleImage(val){
          var id = localStorage.getItem("merchantID")
          this.$http.post('http://localhost:8080/end/merchant/get/?mid='+id).then(result=>{
            console.log(JSON.stringify(result.data))
            this.modelMerchant = result.data
            this.modelMerchant.image = val
            this.updateBaseInfo()
          })
        },

        updateBaseInfo(){
          this.$http.post('http://localhost:8080/end/merchant/update/baseInfo',this.modelMerchant).then(result=>{
            console.log(result.data)
          })
          this.info(1,'修改待审批')
        },
        updateMerchant(){
          this.$http.post('http://localhost:8080/end/merchant/update/merchant',this.modelMerchant).then(result=>{
            console.log(result.data)
          })
        },

        getModelMerchant(){
          var id = localStorage.getItem("merchantID")
          this.$http.post('http://localhost:8080/end/merchant/get/?mid='+id).then(result=>{
            this.modelMerchant = result.data
          })
        },

        handleSelect(key){
          if (key == 1){
            this.base_info_show = true
            this.certificate_show = false
            this.account_show = false
          }else if (key == 2){
            this.base_info_show = false
            this.certificate_show = true
            this.account_show = false
          }else{
            this.base_info_show = false
            this.certificate_show = false
            this.account_show = true
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
        }
      }
    }
</script>

<style scoped>
p{
  font-size: 13px; position: absolute;
  left: 110px; top: 342px; width: 700px;
  text-align: justify;
  line-height: 20px;
  max-height: 80px;
  overflow: hidden;
}
p:after {
  content:"...";
  position:absolute;
  bottom:0;
  right:0;
  padding: 0 8px;
  background-color: #fff;
}
</style>
