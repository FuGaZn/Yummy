<template>
  <div>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <merchant-head-nav-bar></merchant-head-nav-bar>
    <div style="font-size: 13px; color: black; position: absolute; top: 85px; left: 200px">>&nbsp&nbsp{{nowOperate}}</div>
    <div style="width: 150px; position: absolute; top: 140px;left: 20px; color: grey;height: 200px">
      <el-button @click="addSingle" style="position: absolute; left: 20px;" type="text" class="btn">添加单品</el-button>
      <el-button @click="addPackage" style="position: absolute; left: 10px;top: 70px;" type="text" class="btn">添加套餐</el-button>
      <el-button @click="addCoupon" style="position: absolute; left: 10px; top: 140px;" type="text" class="btn">
        添加优惠劵
      </el-button>
    </div>
    <div v-show="pageShow[0]" style="position: absolute; width: 1000px; left: 200px; height: 420px; top: 120px;background-color: white">
      <div @click="addImage" class="add_image_div" v-show="addImageDivShow">
        <i class="add_image_div_plus el-icon-plus"></i>
        <span style="margin-top: 100px;position: absolute; top:75px; left: 126px;font-size: 13px;color:rgba(128,128,128,0.7)">添加图片</span>
      </div>
      <img style="width: 300px;position: absolute; left: 40px;top: 40px;" :src="single.image">
      <el-tooltip class="item" effect="dark" content="删除图片" placement="top-start">
      <el-button @click="deleteImage" type="text" style="position: absolute; left: 350px; top: 295px; font-size: 18px"><i class="el-icon-delete"></i></el-button>
      </el-tooltip>
      <span style="position: absolute; left: 440px; top: 40px;">名称</span>
      <input v-model="single.name" class="_input" style="position: absolute; left: 520px; top: 40px; width: 315px"/>
      <span style="position: absolute; left: 440px; top: 90px;">价格</span>
      <input type="text" onkeyup="this.value=this.value.toString().match(/^\d+(?:\.\d{0,2})?/)" v-model="single.price" class="_input"
             style="position: absolute; left: 520px; top: 90px; width: 315px"/>
      <span style="position: absolute; left: 440px; top:140px;">总数</span>
      <input type="text" onkeyup="this.value=this.value.toString().match(/^\d+(?:\.\d{0,2})?/)" v-model="single.max_number" class="_input"
             style="position: absolute; left: 520px; top: 140px; width: 315px"/>
      <span style="position: absolute; left: 440px; top: 190px;">备注</span>
      <el-input style="position: absolute; left: 520px; top: 190px; width: 320px;"
        type="textarea"
        :rows="5"
        placeholder="请输入内容"
        v-model="single.details">
      </el-input>
      <el-button @click="updateGood" type="primary" size="mini" style="font-size: 14px; position: absolute; left: 880px; top: 340px;">确定</el-button>
    </div>

    <div v-show="pageShow[1]" style="position: absolute; width: 1000px; left: 200px; height: 700px; top: 120px;background-color: white">
      <div @click="addImage" class="add_image_div" v-show="addImageDivShow">
        <i class="add_image_div_plus el-icon-plus"></i>
        <span style="margin-top: 100px;position: absolute; top:75px; left: 126px;font-size: 13px;color:rgba(128,128,128,0.7)">添加图片</span>
      </div>
      <img style="width: 300px;position: absolute; left: 40px;top: 40px;" :src="newPackage.image">
      <el-tooltip class="item" effect="dark" content="删除图片" placement="top-start">
        <el-button @click="deleteImage" type="text" style="position: absolute; left: 350px; top: 295px; font-size: 18px"><i class="el-icon-delete"></i></el-button>
      </el-tooltip>
      <span style="position: absolute; left: 440px; top: 40px;">名称</span>
      <input v-model="newPackage.name" class="_input" style="position: absolute; left: 520px; top: 40px; width: 315px"/>
      <span style="position: absolute; left: 440px; top:90px;">总数</span>
      <input type="text" onkeyup="this.value=this.value.toString().match(/^\d+(?:\.\d{0,2})?/)" v-model="newPackage.max_number" class="_input"
             style="position: absolute; left: 520px; top:90px; width: 315px"/>
      <span style="position: absolute; left: 440px; top: 140px;">备注</span>
      <el-input style="position: absolute; left: 520px; top: 140px; width: 320px;"
                type="textarea"
                :rows="6"
                placeholder="请输入内容"
                v-model="newPackage.details">
      </el-input>
      <div style="position: absolute; left: 0px; width: 100%;top: 340px;">
        <span style="font-size: 16px;font-weight: bold;position: absolute; left: 40px;top: 0;">商品列表</span>
        <el-table show-summary max-height="300" :data="newPackage.goods" size="small" style="position: absolute; top: 0px;width: 760px;left: 120px">
          <el-table-column label="名称" width="300" prop="name"></el-table-column>
          <el-table-column label="单价" width="150" align="center" prop="price"></el-table-column>
          <el-table-column label="数量" align="center" width="160">
            <template slot-scope="scope">
              <el-input-number size="mini" :min="0" v-model="scope.row.number" @change="counterChange(scope.$index, scope.row.number)"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="小计" width="150" align="center" prop="total"></el-table-column>
        </el-table>
      </div>
      <div style="position: fixed; width: 100%; height: 50px; bottom: 0; left: 0; background-color: rgba(253,253,253,0.5); border: 1px solid rgb(178,178,178)">
        <el-autocomplete @select="addGood" :fetch-suggestions="querySearchAsync" v-model="state" placeholder="查找商品" style="width: 300px;position: absolute; top: 10px;left: 300px" size="small">
        </el-autocomplete>
        <div style="position: absolute; right: 330px;font-size: 24px;top: 10px;">总价&nbsp￥</div>
        <input v-model="newPackage.real_price" class="real_price_input" onkeyup="this.value=this.value.toString().match(/^\d+(?:\.\d{0,2})?/)"/>
        <el-button @click="updatePackage" type="success" style="position: absolute; right: 100px;top: 8px; font-size: 15px; width: 100px" size="small">确定</el-button>
      </div>
    </div>

    <div v-show="pageShow[2]" style="position: absolute; width: 1000px; left: 200px; height: 400px; top: 120px;background-color: white">
      <div class="couponShowDiv">
        <div  v-for="item in coupons" :key="item">
          <div style="position: relative;float: top;width: 350px;height: 80px;border: 1px solid rgb(255, 9, 9);background-color: rgba(255, 86, 88, 0.9);margin: 10px">
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
      <div style="position: absolute; left: 450px; top: 20px; width: 520px;height: 360px;border:1px solid rgba(233,233,233,1);border-radius: 3px">
        <span style="font-size: 16px;font-weight: bold;color: #656565;position: absolute; left: 20px;top: 10px;">添加优惠券</span>
        <el-form ref="newCoupon" :model="newCoupon" label-width="100px" style="position: absolute; left: 10px; top: 60px;">
          <el-form-item label="优惠类型">
            <el-select style="margin-left: 22px;width: 300px" v-model="newCoupon.type" placeholder="选择类型" size="small">
              <el-option label="无门槛红包" value='0'></el-option>
              <el-option label="满减红包" value='1'></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="上限金额" v-show="newCoupon.type == '1'" >
            <el-input onkeyup="this.value=this.value.toString().match(/^\d+(?:\.\d{0,2})?/)" style="width: 300px;margin-left: 22px" :min="0" size="small" v-model="newCoupon.satisfied"></el-input>
          </el-form-item>
          <el-form-item label="优惠金额">
            <el-input onkeyup="this.value=this.value.toString().match(/^\d+(?:\.\d{0,2})?/)" size="small" style="width: 300px;margin-left: 22px" v-model="newCoupon.discount"></el-input>
          </el-form-item>
          <el-form-item label="起止日期">
            <el-date-picker size="small" style="width: 145px;margin-left: 22px;font-size: 12px"
                            v-model="newCoupon.start" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                            >
            </el-date-picker>
            -
            <el-date-picker size="small" style="width: 145px;font-size: 12px"
                            v-model="newCoupon.end" format="yyyy-MM-dd" value-format="yyyy-MM-dd">
            </el-date-picker>
          </el-form-item>
          <el-button @click="updateCoupon" type="primary" size="mini" style="font-size: 14px;margin-left: 360px">提交</el-button>
        </el-form>
      </div>
    </div>
    <image-cropper title="添加图片" :dialog-visible="cropperShow" @setDialogDisVisible="setDialogDisVisible" style="position: absolute; bottom: 50px; left: 10px" @getImage="getSingleImage"></image-cropper>
    <el-dialog :visible.sync="dialogVisible" width="360px">
      <span>确定要删除该优惠卷吗</span>
      <el-button @click="deleteCoupon(couponInd)" type="primary" style="margin-left: 20px">删除</el-button>
    </el-dialog>
  </div>
</template>

<script>
  import ImageCropper from '../../components/common/ImageCropper'
  import MerchantHeadNavBar from '../../components/merchant/MerchantHeadNavBar'
  import HeadImage from '../../assets/headImage2.jpg'
  export default {
    name: "add-info-page",
    components:{MerchantHeadNavBar,ImageCropper},
    data(){
      return {
        dialogVisible:false,
        couponInd:0,
        nowOperate: '单品',
        pageShow: [true, false, false],
        single: {
          gid: 0,
          mid: "",
          image: "",
          name: "",
          price: 0,
          details: "",
          max_number: 0,
        },

        selectGoods: [
        ],
        timeout: null,
        state: '',

        newPackage: {
          pid:0,
          mid:"",
          image: "",
          name: "",
          details: "",
          total: "",
          max_number: "",
          setting_price:0,
          real_price:0,
          goods: []
        },
        type: 0,
        cropperShow: false,
        addImageDivShow: true,
        coupons: [],
        newCoupon:  {
          cid:0,
          type: "",
          discount: 9,
          satisfied: 0,
          start: '2019-02-24',
          end: '2019-02-24',
        },
        modelMerchant:{},
      }
    },
    created(){
      this.getModelMerchant()
    },
    methods:{
      openDeleteDialog(i){
        this.couponInd = i;
        this.dialogVisible = true
      },
      counterChange(index, number){
        var _this = this
        this.$nextTick(function () {
          if (number == 0){
            _this.newPackage.goods.splice(index, 1)
          }else{
            _this.newPackage.goods[index].total =  _this.newPackage.goods[index].price * _this.newPackage.goods[index].number
          }
          _this.summary()
        })
      },

      summary(){
        var total = 0
        for (var i=0;i<this.newPackage.goods.length;i++){
          total+=this.newPackage.goods[i].number * this.newPackage.goods[i].price
        }
        this.newPackage.setting_price = total
        this.newPackage.real_price = total
      },

      updateGood(){
        this.single.mid = localStorage.getItem("merchantID")
        this.modelMerchant.goods.push(this.single);
        this.updateModelMerchant()
        this.info(1,'添加成功')
      },

      updatePackage(){
        this.newPackage.mid = localStorage.getItem("merchantID")
        this.modelMerchant.packages.push(this.newPackage)
        this.$http.post('http://localhost:8080/end/merchant/add/package',this.modelMerchant).then(result=>{
          this.getModelMerchant()
          this.info(1,'添加成功')
        })
      },

      deleteCoupon(cid){
        this.$http.post('http://localhost:8080/end/merchant/delete/coupon?cid='+cid).then(result=> {
          this.info(1,'删除成功')
          this.getModelMerchant()
          this.dialogVisible = false;
        })
      },
      updateCoupon(){
        var coupon = {
          mid:localStorage.getItem("merchantID"),
          discount:this.newCoupon.discount,
          satisfied:this.newCoupon.satisfied,
          start:this.newCoupon.start,
          end:this.newCoupon.end
        }
        if (this.newCoupon.type == 0){
          coupon.satisfied = 0
        }
        this.modelMerchant.coupons.push(coupon)
        this.updateModelMerchant()
        this.info(1,'添加成功')
      },

      getModelMerchant(){
        var id = localStorage.getItem("merchantID")
        this.$http.post('http://localhost:8080/end/merchant/get/?mid='+id).then(result=>{
          this.modelMerchant = result.data
          this.coupons = []
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
        })
      },

      updateModelMerchant(){
        this.$http.post('http://localhost:8080/end/merchant/update/merchant',this.modelMerchant).then(result=>{
          this.getModelMerchant()
        })
      },

      initShow(){
        for (var i=0;i<this.pageShow.length;i++)
          this.pageShow[i]=false
      },
      addSingle(){
        this.type = 0
        this.nowOperate = '单品'
        this.initShow()
        this.pageShow[0]=true
      },
      addPackage(){
        this.type = 1
        this.nowOperate= '套餐'
        this.initShow()
        this.pageShow[1]=true
      },
      addCoupon(){
        this.nowOperate='优惠券'
        this.initShow()
        this.pageShow[2]=true
      },

      deleteImage(){
        if(this.type == 0)
          this.single.image = ""
        else  this.newPackage.image = ""
        this.addImageDivShow = true
        this.cropperShow = false
      },
      addImage(){
        this.cropperShow = true
        this.addImageDivShow =false
        this.$emit("setDialogVisible",true)
      },
      setDialogDisVisible(val){
        this.cropperShow = false
      },
      getSingleImage(val){
        if(this.type == 0) {
          this.single.image = val
        }else{
          this.newPackage.image = val
        }
        if (val == "")
          this.deleteImage()
      },

      querySearchAsync(queryString, cb) {
        this.selectGoods = []
        for (var i=0;i<this.modelMerchant.goods.length;i++){
          var item = this.modelMerchant.goods[i]
          this.selectGoods.push({
            value:item.name+" 1份 ￥"+item.price+'元',
            index:i,
            gid:item.gid
          })
        }

        var selectGoods = this.selectGoods;
        var results = selectGoods;

        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          cb(results);
        }, 3000 * Math.random());
      },
      createStateFilter(queryString) {
        return (state) => {
          return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
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

      addGood(item){
        var item1 = this.modelMerchant.goods[item.index]
        var good_item = {
          gid:item1.gid,
          mid:localStorage.getItem("merchantID"),
          name:item1.name,
          price:item1.price,
          number:1,
          total:item1.price,
          details:item1.details,
          image:item1.image
        }
        this.newPackage.goods.push(good_item)
        this.newPackage.setting_price += good_item.price;
        this.newPackage.real_price += good_item.price
      }
    }
  }
</script>

<style scoped>
  .btn{
    font-size: 20px;
    font-weight: bold;
    color: #646464;
  }
  .btn:hover{
    color: #1E89E0;
  }
  .btn:focus{
    color: #1E89E0;
  }
  ._input{
    width: 200px;
    border-top-width: 0px;
    border-left-width: 0px;
    border-right-width: 0px;
    border-bottom: 1px solid black;
    line-height: 20px;
  }
  ._input:focus{
    border-bottom: 1px solid #1092f0;
    box-shadow: 0 2px 0 rgba(0,0,0,.02),0 2px 0 rgba(102,175,233,.1);
  }
  .add_image_div{
    width: 300px;position: absolute; left: 40px;top: 40px;height: 280px;
    background-color: rgba(233,233,233,0.5);
    border: 1px solid rgba(128,128,128,0.5);
    border-radius: 3px;
    cursor: pointer;
  }
  .add_image_div:hover{
    background-color: rgba(233,233,233,1);
  }
  .add_image_div_plus{
    position: absolute;
    top: 80px;
    left: 100px;
    font-size: 100px;
    font-weight: bold;
    color:rgba(128,128,128,0.4);
  }
  .couponShowDiv{
    position: absolute; left: 10px; width: 400px; height: 380px; top: 10px; overflow: scroll;
    overflow-x: hidden;
    overflow-y: auto;
  }
  .real_price_input{
    position: absolute; right: 220px; top: 14px;font-size: 23px;color: red;
    width: 100px;
    border-top-width: 0px;
    border-left-width: 0px;
    border-right-width: 0px;
    border-bottom: 1px solid black;
    line-height: 25px;
  }
  .real_price_input:focus{
    border-bottom: 1px solid #ff1911;

  }

</style>
