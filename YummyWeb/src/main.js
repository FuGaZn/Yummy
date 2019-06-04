// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Axios from 'axios'
import VueAxios from 'vue-axios'
import VueCropper from 'vue-cropper'
import './assets/alicon/iconfont.css'
import Qs from 'qs'
import echarts from 'echarts'
import 'echarts/theme/macarons'
Vue.prototype.$echarts = echarts
//Vue.use(Marked)
Vue.config.productionTip = false
Vue.use(VueAxios, Axios)
Vue.use(ElementUI)
Vue.use(VueCropper)
Vue.use(Qs)
Vue.use(echarts)
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  render: h => h(App)
})
