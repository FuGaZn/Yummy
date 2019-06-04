<template>
  <div>
    <div style="width: 1200px;">
      <el-table :data="goods" style="font-size: 12px;float: top;width: 500px" max-height="300">
        <el-table-column label="商品" prop="name"></el-table-column>
        <el-table-column label="数量" prop="number" align="center"></el-table-column>
        <el-table-column label="小计" prop="total" align="center"></el-table-column>
      </el-table>
      <div  style="float: top; width: 500px; height: 80px; margin-top: 5px; position: relative;">
        <div v-show="hasCoupon">
          <span style="position: absolute; left: 10px; top: 10px; font-size: 12px">优惠</span>
          <span style="position: absolute; left: 245px; top: 10px; font-size: 12px; color: red">满&nbsp&nbsp{{coupon_satisfied}}&nbsp&nbsp减&nbsp&nbsp{{coupon_discount}}</span>
        </div>
        <span style="position: absolute; left: 10px; top: 46px">总计</span>
        <span style="position: absolute; right: 50px; top: 42px;color: red; font-size: 14px"><span style="font-size: 25px;font-weight: bold">{{total}}</span>&nbsp&nbsp元</span>
      </div>
    </div>

  </div>
</template>

<script>
  export default {
    name: "goods-list",
    props:['goods','total','coupon_discount','coupon_satisfied','hasCoupon']

  }
</script>

<style scoped>

</style>
