<template>
  <div class="label">

    <el-form ref="merchant" status-icon :rules="rules" :model="merchant" label-width="120px" size="small" class="demo-ruleForm">
      <h1 style="padding-top: 24px; font-size: 30px; padding-bottom: 0px">Merchant Register</h1>
      <el-form-item label="店名" prop="name">
        <el-input autocomplete="off"  style="width: 400px; margin-left: -80px" v-model="merchant.name"></el-input>
      </el-form-item>
      <el-form-item label="品类" prop="type">
        <el-select style="width: 400px; margin-left: -80px" v-model="merchant.type" >
          <el-option autocomplete="off" v-for="item in merchantType" :key="item" :label="item.label" :value="item.value" placeholder=""></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-cascader autocomplete="off"  style="width: 400px; margin-left: -80px"
                     size="small"
                     :options="addressOptions"
                     v-model="merchant.address"
                     @change="handleLocationChange">
        </el-cascader>
      </el-form-item>
      <el-form-item  label="电话" prop="phone" required>
        <el-input autocomplete="off" style="width: 400px; margin-left: -80px" v-model="merchant.phone"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password" required>
        <el-input type="password" style="width: 400px; margin-left: -80px" v-model="merchant.password"/>
      </el-form-item>
      <el-form-item>
        <div style="margin-left: 160px">
          <el-button @click="toMerchantLogin">Cancel</el-button>
          <el-button type="primary" @click="submitForm('merchant')">Register</el-button>
        </div>
      </el-form-item>
    </el-form>
    <el-dialog :visible.sync="dialogVisible" width="360px">
      <span>注册成功，您的身份标识码为<span style="font-size: 16px; color: red">{{serial}}</span></span>
      <p style="font-size: 12px">这是您用于登陆和身份证明的唯一标识码，请妥善保管</p>
      <el-button @click="toBaseInfo" type="primary">我记住了</el-button>
    </el-dialog>
  </div>
</template>

<script>
  import { provinceAndCityData, regionData, provinceAndCityDataPlus, regionDataPlus, CodeToText, TextToCode }
    from 'element-china-area-data'
    export default {
        name: "merchant-register",
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
            addressOptions:regionDataPlus,
            merchant:{
              name:"",
              type:0,
              address:[],
              phone:"",
              password:"",
            },
            merchantType:[
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
            rules:{
              name:[
                {required: true, message:"Please input name",trigger: 'blur' }
              ],
              type:[
                {required: true, message:"Please input type",trigger: 'blur' }
              ],
              address:[
                {required: true, message:"Please input address",trigger: 'blur' }
              ],
              phone:[
                { validator: validatePhone, trigger: 'blur' }
              ]
            },
            serial:'1234567',
            dialogVisible:false
          }
      },
      methods:{
        handleLocationChange(){

        },
        toMerchantLogin(){
          this.$router.push({path:'/merchant/login'})
        },
        toBaseInfo(){
          this.$router.push({path: '/merchant/base-info'})
          this.info(0,'请完善店铺信息')
        },
        submitForm(formName) {
          var _this = this
          this.$refs[formName].validate((valid) => {
            if (valid) {
              var merc = {
                mid:1,
                name:_this.merchant.name,
                serial:'',
                password:this.merchant.password,
                image:'',
                bulletin:"",
                briefIntroduction:"",
                address:{
                  aid:0,
                  province_code:_this.merchant.address[0],
                  province:CodeToText[_this.merchant.address[0]],
                  city_code:_this.merchant.address[1],
                  city:CodeToText[_this.merchant.address[1]],
                  part_code:_this.merchant.address[2],
                  part:CodeToText[_this.merchant.address[2]]
                },
                phone: _this.merchant.phone,
                type:_this.merchant.type
              }
              this.$http.post('http://localhost:8080/end/merchant/register',merc).then(result=>{
                var response = result.data
                if (response == null || response == ""){
                  this.info(3,"注册失败")
                }else{
                  this.serial = response
                  localStorage.setItem("merchantSerial",this.serial)
                  localStorage.setItem("merchantName", this.merchant.name)
                  this.$http.post('http://localhost:8080/end/merchant/getBySerial?serial='+this.serial).then(result=>{
                    localStorage.setItem("merchantID",result.data.mid)
                  })
                  this.dialogVisible = true
                }
              })
            } else {
              console.log('error submit!!');
              return false;
            }
          });
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
  .label{
    background-color: #ffffff;
    width: 600px;
    height: 400px;
    border-radius: 5px;
  }
</style>
