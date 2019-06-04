<template>
  <div>
    <div style="position:fixed; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(233,233,233,0.5);"></div>
    <manager-head-nav-bar></manager-head-nav-bar>
    <div style="position: absolute; left: 0; top: 65px;width: 100%; height: 60px; background-color: white">
      <el-menu @select="handleSelect" default-active="1" mode="horizontal" style="position: absolute; top: 0px;width: 600px;left: 150px">
        <el-menu-item index="1" style="width: 100px">餐厅</el-menu-item>
        <el-menu-item index="2" style="width: 100px">会员</el-menu-item>
        <el-menu-item index="3" style="width: 100px">平台</el-menu-item>
      </el-menu>
    </div>
    <div v-show="merchantShow" style="position: absolute; left:50%;margin-left: -600px; top: 140px;width: 1200px; height: 1000px; background-color: white">
      <div style="position: absolute;left: 60px;top: 20px;font-size: 18px;width: 200px;text-align: center">餐厅数量
        <span style="width: 200px;text-align:center;position: absolute; left: 0px;top: 40px;font-size: 40px;font-weight: bold;color: #ff474e">{{merchants_data.number}}</span>
      </div>
      <div style="position: absolute; top: 140px;width: 100%">
        <div style="width: 500px;height: 300px;position: absolute;left: 100px;top: 0px;" id="type_pie"></div>
        <div style="width: 500px;height: 300px;position: absolute;left: 620px;top: 0px;" id="type_zhu"></div>
        <div style="width: 900px;height: 500px;position: absolute;left: 100px;top: 320px;" id="map"></div>
      </div>
    </div>
    <div v-show="userShow" style="position: absolute; left:50%;margin-left: -600px; top: 140px;width: 1200px; height: 1000px; background-color: white">
      <div style="position: absolute;left: 60px;top: 20px;font-size: 18px;width: 200px;text-align: center">会员数量
        <span style="width: 200px;text-align:center;position: absolute; left: 0px;top: 40px;font-size: 40px;font-weight: bold;color: #ff474e">{{user_data.number}}</span>
      </div>
      <div style="position: absolute; top: 140px;width: 100%">
        <div style="width: 500px;height: 300px;position: absolute;left: 500px;top: -60px;" id="user_type_pie"></div>
        <div style="width: 900px;height: 500px;position: absolute;left: 200px;top: 260px;" id="user_map"></div>
      </div>
    </div>
    <div v-show="managerShow" style="position: absolute; left:50%;margin-left: -600px; top: 140px;width: 1200px; height: 600px;background-color: white">
      <div style="position: absolute;left: 60px;top: 20px;font-size: 18px;width: 200px;text-align: center">总收入
        <span style="width: 200px;text-align:center;position: absolute; left: 0px;top: 40px;font-size: 40px;font-weight: bold;color: #ff474e">{{manager_data.balance}}</span>
      </div>
      <div style="position: absolute;left: 300px;top: 20px;font-size: 18px;width: 200px;text-align: center">本月收入
        <span style="width: 200px;text-align:center;position: absolute; left: 0px;top: 40px;font-size: 40px;font-weight: bold;color: #ff9c00">{{manager_data.monthEarning}}</span>
      </div>
      <div style="position: absolute;left: 600px;top: 20px;font-size: 18px;width: 200px;text-align: center">今日收入
        <span style="width: 200px;text-align:center;position: absolute; left: 0px;top: 40px;font-size: 40px;font-weight: bold;color: #ff9c00">{{manager_data.todayEarning}}</span>
      </div>
      <div style="width: 600px;height: 400px;position: absolute;left: 100px;top: 160px;" id="line"></div>
    </div>
  </div>
</template>

<script>
  import ManagerHeadNavBar from '../../components/manager/ManagerHeadNavBar'
  import 'echarts/map/js/china.js'
  import 'echarts/dist/extension/dataTool'
  require('../../assets/macarons')
  export default {
    name: "statistic-page",
    components:{ManagerHeadNavBar},
    data(){
      return{
        merchantShow:true,
        userShow:false,
        managerShow:false,
        manager_data:{
          balance:20810,
          monthEarning:1280,
          todayEarning:10,
          month_data:[{

          }]
        },
        user_data:{
          number:2314,
          types_pie:[
            {value:335, name:'Lv1 普通会员'},
            {value:310, name:'L2 黄金会员'},
            {value:234, name:'L3 铂金会员'},
            {value:135, name:'L4 钻石会员'},
          ],
          map:[
            {name: '北京',value: 100 },
            {name: '天津',value: 200 },
            {name: '上海',value: 1000 },
            {name: '广东',value: 2048 },
          ]
        },
        merchants_data:{
          number:100,
          types:[2900, 1200, 300, 200, 900, 300],
          types_mod:[0, 1700, 1400, 1200, 300, 0],
          types_pie:[
            {value:335, name:'果蔬生鲜'},
            {value:310, name:'甜点饮品'},
            {value:234, name:'特色菜系'},
            {value:135, name:'快餐便当'},
            {value:1548, name:'小吃夜宵'}
          ],
          map:[
            {name: '北京',value: 100 },
            {name: '天津',value: 200 },
            {name: '上海',value: 1000 },
            {name: '广东',value: 2048 },
          ]
        }
      }
    },
    mounted(){
      this.$http.post('http://localhost:8080/end/manager/get/merchant/number').then(result=>{
        this.merchants_data.number = result.data
        this.$http.post('http://localhost:8080/end/manager/statistic/merchant/type').then(result=>{
          var types = result.data
          console.log(result.data)
          var num = this.merchants_data.number
          this.merchants_data.types[0] =num
          this.merchants_data.types_mod[0] = 0
          for (var i=0;i<types.length;i++){
            this.merchants_data.types_pie[i].value = types[i]
            this.merchants_data.types_mod[i+1] = num - types[i]
            this.merchants_data.types[i+1] = types[i]
            num = num - types[i];
          }
          console.log(this.merchants_data.types)
          console.log(this.merchants_data.types_mod)
          this.drawLine()
          this.drawTypeZhu()
          this.drawTypePie()
          this.drawMap()
          this.drawUserTypePie()
          this.drawUserMap()
        })

      })
      this.$http.post('http://localhost:8080/end/manager/get/user/number').then(result=>{
        this.user_data.number = result.data
        this.$http.post('http://localhost:8080/end/manager/statistic/user/level').then(result=>{
          var arr = result.data
          for(var i=0;i<arr.length;i++){
            this.user_data.types_pie[i].value = arr[i]
          }
          this.drawLine()
          this.drawTypeZhu()
          this.drawTypePie()
          this.drawMap()
          this.drawUserTypePie()
          this.drawUserMap()
        })
      })
      this.$http.post('http://localhost:8080/end/manager/get?mid='+localStorage.getItem("managerID")).then(result=>{
        var manager= result.data
        this.manager_data.todayEarning = manager.todayEarning
        this.manager_data.monthEarning = manager.balance
        this.drawLine()
        this.drawTypeZhu()
        this.drawTypePie()
        this.drawMap()
        this.drawUserTypePie()
        this.drawUserMap()
      })


    },
    methods:{

      drawLine(){
        var _this = this
        let myChart = this.$echarts.init(document.getElementById("line"),'macarons')
        myChart.setOption(
            {
            title : {
              text: '近半年每月收入变化',
            },
            tooltip : {
              trigger: 'axis'
            },
            toolbox: {
              show : true,
              feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
              }
            },
            calculable : true,
            xAxis : [
              {
                type : 'category',
                boundaryGap : false,
                data : ['2018-09','2018-10','2018-11','2018-12','2019-01','2019-02']
              }
            ],
            yAxis : [
              {
                type : 'value',
                axisLabel : {
                  formatter: '{value}'
                }
              }
            ],
            series : [
              {
                name:'',
                type:'line',
                data:[3298, 3950, 3402, 3653, 2698, 3080],
                markPoint : {
                  data : [
                    {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
                  ]
                },
                markLine : {
                  data : [
                    {type : 'average', name : '平均值'}
                  ]
                }
              }
            ]
          }
      )
      },

      initShow(){
        this.merchantShow=false
        this.userShow=false
        this.managerShow=false
      },
      handleSelect(key){
        this.initShow()
        if(key == 1)
          this.merchantShow = true
        else if(key == 2)
          this.userShow=true
        else if(key == 3)
          this.managerShow=true
      },
      drawMap(){
        let myChart = this.$echarts.init(document.getElementById("map"),'macarons')
        function randomData() {
          return Math.round(Math.random()*1000);
        }

        myChart.setOption(
          {
            title: {
              text: '商家地域分布',
              left: 'left'
            },
            tooltip: {
              trigger: 'item'
            },
            visualMap: {
              min: 0,
              max: 2500,
              left: 'left',
              top: 'bottom',
              text: ['高','低'],           // 文本，默认为数值文本
              calculable: true
            },
            toolbox: {
              show: true,
              orient: 'vertical',
              left: 'right',
              top: 'center',
              feature: {
                dataView: {readOnly: false},
                restore: {},
                saveAsImage: {}
              }
            },
            series: [
              {
                name: 'iphone5',
                type: 'map',
                mapType: 'china',
                label: {
                  normal: {
                    show: true
                  },
                  emphasis: {
                    show: true
                  }
                },
                data:[
                  {name: '北京',value: randomData() },
                  {name: '天津',value: randomData() },
                  {name: '上海',value: randomData() },
                  {name: '重庆',value: randomData() },
                  {name: '河北',value: randomData() },
                  {name: '河南',value: randomData() },
                  {name: '云南',value: randomData() },
                  {name: '辽宁',value: randomData() },
                  {name: '黑龙江',value: randomData() },
                  {name: '湖南',value: randomData() },
                  {name: '安徽',value: randomData() },
                  {name: '山东',value: randomData() },
                  {name: '新疆',value: randomData() },
                  {name: '江苏',value: randomData() },
                  {name: '浙江',value: randomData() },
                  {name: '江西',value: randomData() },
                  {name: '湖北',value: randomData() },
                  {name: '广西',value: randomData() },
                  {name: '甘肃',value: randomData() },
                  {name: '山西',value: randomData() },
                  {name: '内蒙古',value: randomData() },
                  {name: '陕西',value: randomData() },
                  {name: '吉林',value: randomData() },
                  {name: '福建',value: randomData() },
                  {name: '贵州',value: randomData() },
                  {name: '广东',value: randomData() },
                  {name: '青海',value: randomData() },
                  {name: '西藏',value: randomData() },
                  {name: '四川',value: randomData() },
                  {name: '宁夏',value: randomData() },
                  {name: '海南',value: randomData() },
                  {name: '台湾',value: randomData() },
                  {name: '香港',value: randomData() },
                  {name: '澳门',value: randomData() }
                ]
              }
            ]
          }
        )
      },

      drawTypePie() {
        var _this = this
        let myChart = this.$echarts.init(document.getElementById('type_pie'),'macarons')
        myChart.setOption({
          title : {
            text: '餐厅类型分布',
            x:'left'
          },
          tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
          },
          toolbox: {
            show : true,
            feature : {
              mark : {show: true},
              dataView : {show: true, readOnly: false},
              magicType : {
                show: true,
                type: ['pie', 'funnel'],
                option: {
                  funnel: {
                    x: '25%',
                    width: '50%',
                    funnelAlign: 'left',
                    max: 1548
                  }
                }
              },
              restore : {show: true},
              saveAsImage : {show: true}
            }
          },
          calculable : true,
          series : [
            {
              name:'',
              type:'pie',
              radius : '55%',
              center: ['50%', '60%'],
              data:_this.merchants_data.types_pie
            }
          ]
        }
      )
      },
      drawTypeZhu(){
        var _this = this
        let myChart = this.$echarts.init(document.getElementById('type_zhu'),'macarons')
        myChart.setOption({
          title: {
            text: '餐厅类型信息',
          },
          tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
              type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function (params) {
              var tar = params[0];
              return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value;
            }
          },
          toolbox: {
            show : true,
            feature : {
              mark : {show: true},
              dataView : {show: true, readOnly: false},
              restore : {show: true},
              saveAsImage : {show: true}
            }
          },
          xAxis : [
            {
              type : 'category',
              splitLine: {show:false},
              data : ['总数','果蔬生鲜','甜点饮品','特色菜系','快餐便当','小吃夜宵']
            }
          ],
          yAxis : [
            {
              type : 'value'
            }
          ],
          series : [
            {
              name:'辅助',
              type:'bar',
              stack: '总量',
              itemStyle:{
                normal:{
                  barBorderColor:'rgba(0,0,0,0)',
                  color:'rgba(0,0,0,0)'
                },
                emphasis:{
                  barBorderColor:'rgba(0,0,0,0)',
                  color:'rgba(0,0,0,0)'
                }
              },
              data:this.merchants_data.types_mod
            },
            {
              name:'生活费',
              type:'bar',
              stack: '总量',
              itemStyle : { normal: {label : {show: true, position: 'inside'}}},
              data:this.merchants_data.types
            }
          ]
        })
      },
      drawUserTypePie() {
        let myChart = this.$echarts.init(document.getElementById('user_type_pie'),'macarons')
        myChart.setOption({
            title : {
              text: '会员等级',
              x:'left'
            },
            tooltip : {
              trigger: 'item',
              formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            toolbox: {
              show : true,
              feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {
                  show: true,
                  type: ['pie', 'funnel'],
                  option: {
                    funnel: {
                      x: '25%',
                      width: '50%',
                      funnelAlign: 'left',
                      max: 1548
                    }
                  }
                },
                restore : {show: true},
                saveAsImage : {show: true}
              }
            },
            calculable : true,
            series : [
              {
                name:'',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:this.user_data.types_pie
              }
            ]
          }
        )
      },
      drawUserMap(){
        function randomData() {
          return Math.round(Math.random()*1000);
        }
        let myChart = this.$echarts.init(document.getElementById("user_map"),'macarons')
        myChart.setOption(
          {
            title: {
              text: '会员地域分布',
              left: 'left'
            },
            tooltip: {
              trigger: 'item'
            },
            visualMap: {
              min: 0,
              max: 2500,
              left: 'left',
              top: 'bottom',
              text: ['高','低'],           // 文本，默认为数值文本
              calculable: true
            },
            toolbox: {
              show: true,
              orient: 'vertical',
              left: 'right',
              top: 'center',
              feature: {
                dataView: {readOnly: false},
                restore: {},
                saveAsImage: {}
              }
            },
            series: [
              {
                name: 'iphone5',
                type: 'map',
                mapType: 'china',
                label: {
                  normal: {
                    show: true
                  },
                  emphasis: {
                    show: true
                  }
                },
                data:[
                  {name: '北京',value: randomData() },
                  {name: '天津',value: randomData() },
                  {name: '上海',value: randomData() },
                  {name: '重庆',value: randomData() },
                  {name: '河北',value: randomData() },
                  {name: '河南',value: randomData() },
                  {name: '云南',value: randomData() },
                  {name: '辽宁',value: randomData() },
                  {name: '黑龙江',value: randomData() },
                  {name: '湖南',value: randomData() },
                  {name: '安徽',value: randomData() },
                  {name: '山东',value: randomData() },
                  {name: '新疆',value: randomData() },
                  {name: '江苏',value: randomData() },
                  {name: '浙江',value: randomData() },
                  {name: '江西',value: randomData() },
                  {name: '湖北',value: randomData() },
                  {name: '广西',value: randomData() },
                  {name: '甘肃',value: randomData() },
                  {name: '山西',value: randomData() },
                  {name: '内蒙古',value: randomData() },
                  {name: '陕西',value: randomData() },
                  {name: '吉林',value: randomData() },
                  {name: '福建',value: randomData() },
                  {name: '贵州',value: randomData() },
                  {name: '广东',value: randomData() },
                  {name: '青海',value: randomData() },
                  {name: '西藏',value: randomData() },
                  {name: '四川',value: randomData() },
                  {name: '宁夏',value: randomData() },
                  {name: '海南',value: randomData() },
                  {name: '台湾',value: randomData() },
                  {name: '香港',value: randomData() },
                  {name: '澳门',value: randomData() }
                ]
              }
            ]
          }
        )
      },
    }
  }
</script>

<style scoped>

</style>
