<template>
  <div>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <merchant-head-nav-bar></merchant-head-nav-bar>
    <div v-show="pageShow[0]" style="position: absolute; width: 1000px; left: 50%;margin-left: -500px; height: 420px; top: 100px;background-color: white">
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
      <span style="position: absolute; left: 440px; top:140px;">每日总数</span>
      <input type="text" onkeyup="this.value=this.value.toString().match(/^\d+(?:\.\d{0,2})?/)" v-model="single.max_number" class="_input"
             style="position: absolute; left: 520px; top: 140px; width: 315px"/>
      <span style="position: absolute; left: 440px; top: 190px;">备注</span>
      <el-input style="position: absolute; left: 520px; top: 190px; width: 320px;"
                type="textarea"
                :rows="6"
                placeholder="请输入内容"
                v-model="single.details">
      </el-input>
      <div style="font-size: 14px; position: absolute; left: 900px; top: 230px;">
        <el-tooltip class="item" effect="dark" content="删除" placement="right">
          <el-button @click="deleteGood" style="position: absolute; left: 20px;top: 0px;" circle type="danger"><i class="el-icon-close"></i></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="提交" placement="right">
          <el-button @click="modifyGood" style="position: absolute; left: 10px;top: 50px;" circle type="primary"><i class="el-icon-check"></i></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="返回" placement="right">
          <el-button @click="returnGoodsMangePage" style="position: absolute; left: 10px;top: 100px;" circle type="info"><i class="el-icon-back"></i></el-button>
        </el-tooltip>
      </div>
    </div>
    <div v-show="pageShow[1]" style="position: absolute; width: 1000px; left: 50%;margin-left: -500px;  height: 700px; top: 100px;background-color: white">
      <div @click="addImage" class="add_image_div" v-show="addImageDivShow">
        <i class="add_image_div_plus el-icon-plus"></i>
        <span style="margin-top: 100px;position: absolute; top:75px; left: 126px;font-size: 13px;color:rgba(128,128,128,0.7)">添加图片</span>
      </div>
      <img style="width: 300px;position: absolute; left: 40px;top: 40px;" :src="modifiedPackage.image">
      <el-tooltip class="item" effect="dark" content="删除图片" placement="top-start">
        <el-button @click="deleteImage" type="text" style="position: absolute; left: 350px; top: 295px; font-size: 18px"><i class="el-icon-delete"></i></el-button>
      </el-tooltip>
      <span style="position: absolute; left: 440px; top: 40px;">名称</span>
      <input v-model="modifiedPackage.name" class="_input" style="position: absolute; left: 520px; top: 40px; width: 315px"/>
      <span style="position: absolute; left: 440px; top:90px;">每日总数</span>
      <input type="text" onkeyup="this.value=this.value.toString().match(/^\d+(?:\.\d{0,2})?/)" v-model="modifiedPackage.max_number" class="_input"
             style="position: absolute; left: 520px; top:90px; width: 315px"/>
      <span style="position: absolute; left: 440px; top: 140px;">备注</span>
      <el-input style="position: absolute; left: 520px; top: 140px; width: 320px;"
                type="textarea"
                :rows="6"
                placeholder="请输入内容"
                v-model="modifiedPackage.details">
      </el-input>

      <div style="position: absolute; left: 0px; width: 100%;top: 340px;">
        <span style="font-size: 16px;font-weight: bold;position: absolute; left: 40px;top: 0;">商品列表</span>
        <el-table show-summary max-height="300" :data="modifiedPackage.goods" size="small" style="position: absolute; top: 0px;width: 760px;left: 120px">
          <el-table-column label="名称" width="300" prop="name"></el-table-column>
          <el-table-column label="单价" width="150" align="center" prop="price"></el-table-column>
          <el-table-column label="数量" align="center" width="160">
            <template slot-scope="scope">
              <el-input-number size="mini" @change="counterChange(scope.$index, scope.row.number)" :min="0" v-model="scope.row.number"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="小计" width="150" align="center" prop="total"></el-table-column>
        </el-table>
      </div>
      <div style="position: fixed; width: 100%; height: 50px; bottom: 0; left: 0; background-color: rgba(253,253,253,0.5); border: 1px solid rgb(178,178,178)">
        <el-autocomplete @select="addGood" :fetch-suggestions="querySearchAsync" v-model="state" placeholder="查找商品" style="width: 300px;position: absolute; top: 10px;left: 300px" size="small">
        </el-autocomplete>
        <div style="position: absolute; right: 330px;font-size: 24px;top: 10px;">总价&nbsp￥</div>
        <input v-model="modifiedPackage.real_price" class="real_price_input" onkeyup="this.value=this.value.toString().match(/^\d+(?:\.\d{0,2})?/)"/>
        <el-button-group style="position: absolute; right: 80px;top: 8px; font-size: 15px;">
          <el-button @click="modifyPackage" type="success" size="small" style=" font-size: 13px;">确定</el-button>
          <el-button @click="deletePackage" type="danger" size="small"  style=" font-size: 13px;">删除</el-button>
        </el-button-group>
      </div>
    </div>
    <image-cropper :dialog-visible="cropperShow" @setDialogDisVisible="setDialogDisVisible" style="position: absolute; bottom: 50px; left: 10px" @getImage="getSingleImage"></image-cropper>

  </div>
</template>

<script>
  import ImageCropper from '../../components/common/ImageCropper'
  import MerchantHeadNavBar from '../../components/merchant/MerchantHeadNavBar'
  import HeadImage from '../../assets/headImage2.jpg'
  export default {
    name: "good-modify-page",
    components:{MerchantHeadNavBar,ImageCropper},
    data(){
      return{
        nowOperate:'单品',
        pageShow:[true,false],
        single:{
          gid: 0,
          mid: "",
          image: "",
          name: "",
          price: 0,
          details: "",
          max_number: 0,
          bought_number:0
        },

        selectGoods:[],
        timeout:null,
        state:'',

        modifiedPackage:{
        },
        type:0,
        cropperShow:false,
        addImageDivShow:false,
        merchant:{},
        modelMerchant:{}
      }
    },
    created(){
      var type = localStorage.getItem("modifiedType")
      var id = localStorage.getItem("modifiedID")
      if(type == 'good'){
        this.pageShow[0] = true
        this.pageShow[1] = false
        var mid = localStorage.getItem("merchantID")
        this.$http.post('http://localhost:8080/end/merchant/get/?mid=' + mid).then(result => {
          this.merchant = result.data
          var goods = this.merchant.goods
          for(var i=0;i<goods.length;i++){
            if(goods[i].gid == id){
              this.single = goods[i]
              if(this.single.image == '')
                this.addImageDivShow = true
            }
          }
        })
      }else if(type == 'package'){
        this.pageShow[0] = false
        this.pageShow[1] = true
        var mid = localStorage.getItem("merchantID")
        this.$http.post('http://localhost:8080/end/merchant/get/?mid=' + mid).then(result => {
          this.merchant = result.data
          var packages = this.merchant.packages
          for(var i=0;i<packages.length;i++){
            if(packages[i].pid == id){
              this.modifiedPackage = packages[i]
              if(this.modifiedPackage.image == '')
                this.addImageDivShow=true
            }
          }
        })
      }
    },

    methods:{
      summary(){
        var total = 0
        for (var i=0;i<this.modifiedPackage.goods.length;i++){
          total+=this.modifiedPackage.goods[i].number * this.modifiedPackage.goods[i].price
        }
        this.modifiedPackage.setting_price = total
        this.modifiedPackage.real_price = total
      },
      counterChange(index, number){
        var _this = this
        this.$nextTick(function () {
          if (number == 0){
            _this.modifiedPackage.goods.splice(index, 1)
          }else{
            _this.modifiedPackage.goods[index].total =  _this.modifiedPackage.goods[index].price * _this.modifiedPackage.goods[index].number
          }
          _this.summary()
        })
      },
      getModelMerchant() {
        var id = localStorage.getItem("merchantID")
        this.$http.post('http://localhost:8080/end/merchant/get/?mid=' + id).then(result => {
          this.merchant = result.data
          this.modelMerchant = result.data
        })
      },
      returnGoodsMangePage(){
        this.$router.push({path:'/merchant/goods'})
      },
      initShow(){
        for (var i=0;i<this.pageShow.length;i++)
          this.pageShow[i]=false
      },

      deleteImage(){
        if(this.type == 0)
          this.single.image = ""
        else  this.modifiedPackage.image = ""
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
          this.modifiedPackage.image = val
        }
        if (val == "")
          this.deleteImage()
      },

      querySearchAsync(queryString, cb) {
        this.selectGoods = []
        this.$http.post('http://localhost:8080/end/merchant/get/?mid=' + localStorage.getItem("merchantID")).then(result => {
          this.modelMerchant = result.data
          for (var i=0;i<this.modelMerchant.goods.length;i++){
            var item = this.modelMerchant.goods[i]
            this.selectGoods.push({
              value:item.name+" 1份 ￥"+item.price+'元',
              index:i,
              gid:item.gid
            })
          }
        })

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
      deleteGood(){
        var mid = localStorage.getItem("merchantID")
        this.$http.post('http://localhost:8080/end/merchant/get/?mid=' + mid).then(result => {
          var merchant = result.data
          var gid = localStorage.getItem("modifiedID")
          for(var i=0;i<merchant.goods.length;i++){
            if(merchant.goods[i].gid == gid){
              this.$http.post('http://localhost:8080/end/merchant/delete/good?gid='+gid).then(result=>{
                this.info(1,'删除成功')
                this.$router.push('/merchant/goods')
              })
            }
          }
        })
      },
      deletePackage(){
        var mid = localStorage.getItem("merchantID")
        this.$http.post('http://localhost:8080/end/merchant/get/?mid=' + mid).then(result => {
          var merchant = result.data
          var gid = localStorage.getItem("modifiedID")
          for(var i=0;i<merchant.packages.length;i++){
            if(merchant.packages[i].pid == gid){
              this.$http.post('http://localhost:8080/end/merchant/delete/package?pid='+gid).then(result=>{
                this.info(1,'删除成功')
                this.$router.push('/merchant/goods')
              })
            }
          }
        })
      },
      modifyGood(){
        var mid = localStorage.getItem("merchantID")
        this.$http.post('http://localhost:8080/end/merchant/get/?mid=' + mid).then(result => {
          var merchant = result.data
          var gid = localStorage.getItem("modifiedID")
          for(var i=0;i<merchant.goods.length;i++){
            if(merchant.goods[i].gid == gid){
              merchant.goods[i] = this.single
              this.$http.post('http://localhost:8080/end/merchant/update/merchant',merchant).then(result=>{
                this.info(1,'修改成功')
                this.$router.push('/merchant/goods')
              })
            }
          }
        })
      },
      modifyPackage(){
        var mid = localStorage.getItem("merchantID")
        this.$http.post('http://localhost:8080/end/merchant/get/?mid=' + mid).then(result => {
          var merchant = result.data
          var gid = localStorage.getItem("modifiedID")
          for(var i=0;i<merchant.packages.length;i++){
            if(merchant.packages[i].pid == gid){
              merchant.packages[i] = this.modifiedPackage
              this.$http.post('http://localhost:8080/end/merchant/update/merchant',merchant).then(result=>{
                this.info(1,'修改成功')
                this.$router.push("/merchant/goods")
              })
            }
          }
        })
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
      getModelMerchant(){
        var id = localStorage.getItem("merchantID")
        this.$http.post('http://localhost:8080/end/merchant/get/?mid='+id).then(result=>{
          this.modelMerchant = result.data
        })
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
        this.modifiedPackage.goods.push(good_item)
        this.modifiedPackage.setting_price += good_item.price;
        this.modifiedPackage.real_price += good_item.price
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
