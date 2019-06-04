<template>
  <div>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <div style="position: absolute; left: 50%;top: 80px; width: 1100px;height: 500px;background-color: white;margin-left: -550px">
      <el-table :data="merchants">
        <el-table-column width="140px" align="center">
          <template slot-scope="scope">
            <img style="width: 60px" :src="approvalMerchants[scope.$index].image"/>
          </template>
        </el-table-column>
        <el-table-column label="标识码" prop="serial" width="100px"></el-table-column>
        <el-table-column label="店铺名称" width="150px">
          <template slot-scope="scope">
            {{scope.row.name}}
            <span style="color: red">({{approvalMerchants[scope.$index].name}})</span>
          </template>
        </el-table-column>
        <el-table-column label="店铺类型" width="150px">
          <template slot-scope="scope">
            {{merchantTypes[scope.row.type].label}}
            <span style="color: red">({{merchantTypes[approvalMerchants[scope.$index].type].label}})</span>
          </template>
        </el-table-column>
        <el-table-column label="地址" width="360px">
          <template slot-scope="scope">
            {{scope.row.address.province}}/{{scope.row.address.city}}/{{scope.row.address.part}}
            <span style="color: red">({{approvalMerchants[scope.$index].address.province}}/{{approvalMerchants[scope.$index].address.city}}/{{approvalMerchants[scope.$index].address.part}})</span>
          </template>
        </el-table-column>
        <el-table-column width="120px">
          <template slot-scope="scope">
            <el-button-group style="width: 120px" v-show="show">
              <el-button size="mini" type="success" @click="approve(approvalMerchants[scope.$index].amid)">批准</el-button>
              <el-button size="mini" type="danger" @click="refuse(approvalMerchants[scope.$index].amid)">拒绝</el-button>
            </el-button-group>
          </template>
        </el-table-column>
        <el-table-column>
        </el-table-column>
      </el-table>
      <div>
        <el-select size="small" style="position: absolute; right: 50px;top: 8px;" v-model="type" placeholder="未批准申请" @change="handleSelect">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </div>
    </div>
    <manager-head-nav-bar></manager-head-nav-bar>
  </div>
</template>

<script>
  import ManagerHeadNavBar from '../../components/manager/ManagerHeadNavBar'
  export default {
    name: "merchant-approve-page",
    components:{ManagerHeadNavBar},
    data(){
      return{
        show:true,
        type:'',
        options:[
          {
            label:'未批准申请',
            value:'0'
          },
          {
            label:'已驳回申请',
            value:'1'
          },
          {
            label:'已批准申请',
            value:'2'
          },
          {
            label:'全部申请',
            value:'3'
          }
        ],
        merchantTypes:[
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
        approvalMerchants:[{
          amid:0,
          name:"",
          image:"",
          address:"",
          type:0,
        }],
        merchants:[]
      }

    },
    created(){
      this.getAllApprovalMerchants('0')
    },
    methods:{
      handleSelect(){
        if(this.type == '0'){
          this.show= true
        }else{
          this.show=false
        }
        this.getAllApprovalMerchants(this.type)
      },

      approve(amid){
        var path = "http://localhost:8080/end/manager/approve?amid="+amid
        this.$http.post(path).then(result=>{
          this.getAllApprovalMerchants('0')
        })
      },
      refuse(amid){
        var path = "http://localhost:8080/end/manager/refuse?amid="+amid
        this.$http.post(path).then(result=>{
          this.getAllApprovalMerchants('0')
        })
      },
      getAllApprovalMerchants(type){
        if(type =='0'){
          this.approvalMerchants = []
          this.merchants = []
          var path = "http://localhost:8080/end/manager/get/unhandle"
          this.$http.post(path).then(result=>{
            this.approvalMerchants = result.data
            for (var i=0;i<this.approvalMerchants.length;i++){
              var path2 = "http://localhost:8080/end/merchant/get?mid="+this.approvalMerchants[i].mid
              this.$http.post(path2).then(result=>{
                this.merchants.push(result.data)
              })

            }
          })
        }else if(type == '1'){
          this.approvalMerchants = []
          this.merchants = []
          var path = "http://localhost:8080/end/manager/get/refused"
          this.$http.post(path).then(result=>{
            this.approvalMerchants = result.data
            for (var i=0;i<this.approvalMerchants.length;i++){
              var path2 = "http://localhost:8080/end/merchant/get?mid="+this.approvalMerchants[i].mid
              this.$http.post(path2).then(result=>{
                this.merchants.push(result.data)
              })
            }
          })
        }else if(type == '3'){
          this.approvalMerchants = []
          this.merchants = []
          var path = "http://localhost:8080/end/manager/get/merchants"
          this.$http.post(path).then(result=>{
            this.approvalMerchants = result.data
            for (var i=0;i<this.approvalMerchants.length;i++){
              var path2 = "http://localhost:8080/end/merchant/get?mid="+this.approvalMerchants[i].mid
              this.$http.post(path2).then(result=>{
                this.merchants.push(result.data)
              })
            }
          })
        }else if(type=='2'){
          this.approvalMerchants = []
          this.merchants = []
          var path = "http://localhost:8080/end/manager/get/approved"
          this.$http.post(path).then(result=>{
            this.approvalMerchants = result.data
            for (var i=0;i<this.approvalMerchants.length;i++){
              var path2 = "http://localhost:8080/end/merchant/get?mid="+this.approvalMerchants[i].mid
              this.$http.post(path2).then(result=>{
                this.merchants.push(result.data)
              })
            }
          })
        }
      },
      getMerchant(mid){
        var path = "http://localhost:8080/end/merchant/get?mid="+mid
        this.$http.post(path).then(result=>{
          return result.data
        })
      }
    }
  }
</script>

<style scoped>

</style>
