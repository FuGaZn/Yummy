<template>
  <div>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <head-nav-bar></head-nav-bar>
    <left-nav-bar :personal-center="personalCenter" :my-account="myAccount" :statistics="toStatistic" :my-data="myData" :my-orders="myOrder"></left-nav-bar>
    <div style="font-size: 13px; color: black; position: absolute; top: 85px; left: 200px">>&nbsp&nbsp{{nowOperate}}</div>
    <div style="width: 1000px; height: 500px; background-color: white; position: absolute; top: 120px; left: 200px">
      <div>
        <span style="position: absolute; left: 50px; top: 30px;">基本信息</span>
        <span style="position: absolute; left: 70px; top: 70px; font-size: 14px">昵称：{{user.name}}</span>
        <el-button @click="modify_name.dialogVisible = true" style="position: absolute; left: 50px; top: 57px; " type="text"><i style="font-size: 18px" class="el-icon-edit"></i></el-button>

        <span style="position: absolute; left: 370px; top: 70px; font-size: 14px">邮箱：{{user.email}}</span>

        <el-button @click="modify_phone.dialogVisible = true" style="position: absolute; left: 650px; top: 57px; " type="text"><i style="font-size: 18px" class="el-icon-edit"></i></el-button>
        <span style="position: absolute; left: 670px; top: 70px; font-size: 14px">手机号码：{{user.phone}}</span>

        <el-button @click="modify_password.dialogVisible = true" type="text" style="position: absolute; left: 50px; top: 100px;"><i style="font-size: 18px" class="el-icon-edit"></i>密码修改</el-button>
      </div>
      <div>
        <span style="position: absolute; left: 50px; top: 160px">地址管理</span>
        <div style="position: absolute; left: 30px; top: 200px;width: 1000px">
          <div v-for="item in user.addresses" :key="item">
            <div class="address">
              <span style="position: absolute; left: 20px; top: 10px;font-size: 14px">{{item.name}}&nbsp&nbsp{{gender}}</span>
              <span style="position: absolute; left: 20px; top: 40px;font-size: 12px">{{item.phone}}</span>
              <p class="address_p">{{item.province}}/{{item.city}}/{{item.part}}&nbsp&nbsp{{item.details}}</p>
              <el-button-group class="address_btn_group" style="position: absolute; right: 20px;">
                <el-button @click="modifyAddress(item.aid)" class="address_btn" type="text" style="margin-right: 10px;font-size: 12px">修改</el-button>
                <el-button @click="deleteAddress(item.aid)" class="address_btn" type="text" style="font-size: 12px">删除</el-button>
              </el-button-group>
            </div>
          </div>
          <div class="addAddressBtn" @click="new_address_dialogShow = true">
            <el-button class="addAddressBtn_plus" type="text"><i style="font-size: 18px; font-weight: bold" class="el-icon-plus"></i>添加新地址</el-button>
          </div>
        </div>
      </div>
    </div>

    <el-dialog title="修改昵称" :visible.sync="modify_name.dialogVisible" width="360px">
      <el-input style="width: 200px;" v-model="modify_name.newName"></el-input>
      <el-button @click="modifyName" type="primary">修改</el-button>
    </el-dialog>
    <el-dialog title="修改手机号码" :visible.sync="modify_phone.dialogVisible" width="360px">
      <el-input style="width: 200px;" v-model="modify_phone.newPhone"></el-input>
      <el-button @click="modifyPhone" type="primary">修改</el-button>
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
    <el-dialog title="新地址" :visible.sync="new_address_dialogShow" width="630px">
      <el-form :model="new_address" ref="new_address" :rules="rules">
        <el-form-item label="姓名" label-width="120px" required prop="name">
          <el-input v-model="new_address.name" style="width: 400px;margin-left: -20px"></el-input>
        </el-form-item>
        <el-form-item label-width="120px" label="性别">
          <div>
            <el-radio style="margin-left: -280px" v-model="new_address.gender" label="0">先生</el-radio>
            <el-radio v-model="new_address.gender" label="1">女士</el-radio>
          </div>
        </el-form-item>
        <el-form-item label="位置" label-width="120px" required>
          <el-cascader autocomplete="off"  style="margin-left: -20px;width: 400px"
                       :options="addressOptions"
                       v-model="address_array"
                       @change="handleLocationChange">
          </el-cascader>
        </el-form-item>
        <el-form-item label-width="120px" label="详细地址" required prop="details">
          <el-input v-model="new_address.details" style="width: 400px;margin-left: -20px"></el-input>
        </el-form-item>
        <el-form-item label="手机号" label-width="120px" required prop="phone">
          <el-input v-model="new_address.phone" style="width: 400px;margin-left: -20px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="submitForm('new_address')" type="primary" style="width: 200px;margin-left: 300px">保存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="修改地址" :visible.sync="modify_address_dialogShow" width="630px">
      <el-form :model="modify_address" ref="modify_address" :rules="rules">
        <el-form-item label="姓名" label-width="120px" required prop="name">
          <el-input v-model="modify_address.name" style="width: 400px;margin-left: -20px"></el-input>
        </el-form-item>
        <el-form-item label-width="120px" label="性别">
          <div>
            <el-radio style="margin-left: -280px" v-model="modify_address.gender" label="0">先生</el-radio>
            <el-radio v-model="modify_address.gender" label="1">女士</el-radio>
          </div>
        </el-form-item>
        <el-form-item label="位置" label-width="120px" required>
          <el-cascader autocomplete="off"  style="margin-left: -20px;width: 400px"
                       :options="addressOptions"
                       v-model="address_array"
                       @change="handleLocationChange">
          </el-cascader>
        </el-form-item>
        <el-form-item label-width="120px" label="详细地址" required prop="details">
          <el-input v-model="modify_address.details" style="width: 400px;margin-left: -20px"></el-input>
        </el-form-item>
        <el-form-item label="手机号" label-width="120px" required prop="phone">
          <el-input v-model="modify_address.phone" style="width: 400px;margin-left: -20px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="submitModifyAddressForm" type="primary" style="width: 200px;margin-left: 300px">保存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <shopping-cart></shopping-cart>

  </div>
</template>

<script>
  import HeadNavBar from '../../components/member/HeadNavBar'
  import LeftNavBar from '../../components/member/LeftNavBar'
  import ShoppingCart from '../../components/member/ShoppingCart'
  import { provinceAndCityData, regionData, provinceAndCityDataPlus, regionDataPlus, CodeToText, TextToCode }
    from 'element-china-area-data'
  import qs from 'qs'
  export default {
    name: "data-manage-page",
    components:{HeadNavBar,LeftNavBar,ShoppingCart},

    data(){
      var validatePhone = (rule, value, callback) => {
        if (value.length==0){
          callback(new Error('Please input phone number'))
        }
        var b = false
        var reg = /^[1][3,4,5,7,8][0-9]{9}$/
        b = b | reg.test(value)
        var reg2 = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/
        b = b | reg2;
        if(b==false){
          callback(new Error('Phone number wrong'))
        }else
          callback()
      }
      return{
        rules:{
          name:[
            {required: true, message:"Please input name",trigger: 'blur' }
          ],
          details:[
            {required: true, message:"Please input address details",trigger: 'blur' }
          ],
          phone:[
            { validator: validatePhone, trigger: 'blur' }
          ]
        },
        address_array:[],
        addressOptions:regionDataPlus,
        new_address:{
          aid:"",
          uid:"",
          mid:"",
          province_code:"",
          province:"",
          city_code:"",
          city:"",
          part_code:"",
          part:"",
          details:"",
          name:"",
          phone:"",
          gender:'0'
        },
        new_address_dialogShow:false,
        modify_address_dialogShow:false,
        modify_address:{

        },
        modify_name:{
          newName:"",
          dialogVisible:false
        },
        modify_phone:{
          newPhone:"",
          dialogVisible:false
        },
        modify_password:{
          newPassword:"",
          oldPassword:"",
          dialogVisible:false
        },
        nowOperate:'我的资料',
        endUserModel:{

        },
        user:{
        },
        gender:'先生'
      }
    },
    created(){
      var path = "http://localhost:8080/end/user/get?uid="+localStorage.getItem("userID");
      this.$http.post(path).then(result=>{
        this.user = result.data
        if (this.user.gender == 1)
          this.gender='女士'
      })
    },
    methods:{
      handleLocationChange(){
        this.new_address.province_code = this.address_array[0];
        this.new_address.city_code = this.address_array[1];
        this.new_address.part_code = this.address_array[2];
        this.new_address.province = CodeToText[this.address_array[0]]
        this.new_address.city = CodeToText[this.address_array[1]]
        this.new_address.part = CodeToText[this.address_array[2]]
        console.log(this.address_array)
      },
      submitForm(formName) {
        var _this = this
        this.$refs[formName].validate((valid) => {
          if (valid) {
            _this.addAddress()
            _this.new_address_dialogShow = false
            return true
          }else return false
        })
      },
      modifyName(){
        var _this = this
        var path = "http://localhost:8080/end/user/get?uid="+localStorage.getItem("userID")
        this.$http.post(path).then(result=>{
          var user = result.data
          _this.endUserModel = user
          _this.endUserModel.name = _this.modify_name.newName
          _this.updateUserModel()
          _this.modify_name.dialogVisible = false;
          _this.user = _this.endUserModel
          _this.user.name = _this.modify_name.newName
        })
      },
      deleteAddress(aid){
        for(var i=0;i<this.user.addresses.length;i++){
          if(this.user.addresses[i].aid == aid){
            this.user.addresses.splice(i,1)
            var path = "http://localhost:8080/end/user/delete/address?aid="+aid;
            this.$http.post(path).then(result=>{
              console.log(result.data)
            })
          }
        }
      },
      modifyPhone(){
        var _this = this
        var path = "http://localhost:8080/end/user/get?uid="+localStorage.getItem("userID")
        this.$http.post(path).then(result=>{
          var user = result.data
          _this.endUserModel = user
          _this.endUserModel.phone = _this.modify_phone.newPhone
          _this.updateUserModel()
          _this.modify_phone.dialogVisible = false;
          _this.user = _this.endUserModel
          _this.user.phone = _this.modify_phone.newPhone

        })
      },
      modifyAddress(aid){
        for(var i=0;i<this.user.addresses.length;i++){
          if(this.user.addresses[i].aid == aid){
            this.modify_address = this.user.addresses[i];
            this.modify_address.gender = this.modify_address.gender+""
            break
          }
        }
        this.modify_address_dialogShow = true;
      },
      submitModifyAddressForm(){
        var _this = this
        this.$refs['modify_address'].validate((valid) => {
          if (valid) {
            _this.modifyAddressSure()
            _this.modify_address_dialogShow = false
            return true
          }else return false
        })
      },

      modifyAddressSure(){
        var path = "http://localhost:8080/end/user/get?uid="+localStorage.getItem("userID")
        this.$http.post(path).then(result=>{
          var user = result.data
          for(var i=0;i<user.addresses.length;i++){
            if(user.addresses[i].aid == this.modify_address.aid){
              user.addresses[i] = this.modify_address
              localStorage.setItem("addressCode",this.modify_address.part_code)
              localStorage.setItem("addressID",this.modify_address.aid)
            }
          }
          this.endUserModel = user;
          this.user = this.endUserModel
          this.updateUserModel()
        })
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
        if (oldPasswd != this.user.password){
          this.info(2,"旧密码错误")
          return
        }
        var _this = this
        var path = "http://localhost:8080/end/user/get?uid="+localStorage.getItem("userID")
        this.$http.post(path).then(result=>{
          var user = result.data
          _this.endUserModel = user
          _this.endUserModel.password = newPasswd
          _this.updateUserModel()
          _this.modify_password.dialogVisible = false
          _this.user = _this.endUserModel
          _this.user.password = _this.modify_password.newPassword
          this.info(1,'密码修改成功')
        })
      },

      updateUserModel(){
        var path = "http://localhost:8080/end/user/update/user"
        this.$http.post(path,this.endUserModel).then(result=>{
          console.log(result.data)
        })
      },

      getUserModel(){
        var path = "http://localhost:8080/end/user/get?uid="+localStorage.getItem("userID")
        this.$http.post(path).then(result=>{
          var user = result.data
          this.endUserModel = user
          this.user = user
        })
      },

      addAddress(){
        var path = "http://localhost:8080/end/user/get?uid="+localStorage.getItem("userID")
        this.$http.post(path).then(result=>{
          localStorage.setItem("addressCode",this.new_address.part_code)
          var user = result.data
          user.addresses.push(this.new_address)
          this.endUserModel = user;
          this.user = this.endUserModel
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
  .address{
    float:left;
    position: relative;
    width: 300px;
    height: 100px;
    border: 1px solid rgba(105,105,105,0.5);
    margin:5px
  }
.address:hover{
  border:1px solid  rgba(15, 137, 225, 1);
}
  .address_p{
    text-align: left;
    position: absolute; left: 20px;
    top: 54px;
    font-size: 12px;
    overflow: hidden;
    width: 260px;
    text-overflow: ellipsis;

    white-space: nowrap;
  }
  .address_btn{
    color: #b2b2b2;
  }
  .address_btn:hover{
    color: #0f89e1;
  }
  .address:hover > .address_btn_group > .address_btn{
    color: #0f89e1;
  }
  .addAddressBtn{
    float:left;
    position: relative;
    width: 300px;
    height: 100px;
    border: 1px solid rgba(105,105,105,0.5);
    background-color: rgba(233,233,233,0.5);
    margin:5px;
    cursor: pointer;
  }
  .addAddressBtn:hover{
    border:1px solid  rgba(15, 137, 225, 1);
  }
  .addAddressBtn_plus{
    color: rgba(105,105,105,0.7);font-size: 14px; position: absolute; left: 105px; top: 27px;
  }
  .addAddressBtn:hover > .addAddressBtn_plus{
    color: #0f89e1;
  }
</style>
