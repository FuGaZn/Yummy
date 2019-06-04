<template>
    <div>
      <el-dialog :title="title" :visible.sync="dialogVisible" width="600px;" :setDialogVisible="setDialogVisible" :before-close="cancel">
        <div style="height: 350px">
          <div class="show-info">
            <div class="test">
              <vue-cropper
                ref="cropper2"
                :img="cropper.img"
                :outputSize="cropper.size"
                :outputType="cropper.outputType"
                :info="cropper.info"
                :canScale="cropper.canScale"
                :autoCrop="cropper.autoCrop"
                :autoCropWidth="cropper.autoCropWidth"
                :autoCropHeight="cropper.autoCropHeight"
                :fixed="cropper.fixed"
                :fixedNumber="cropper.fixedNumber"
                @realTime="realTime"
              ></vue-cropper>
            </div>
            <div style="position: absolute; left: 450px; top:100px; width: 100px; height: 100px">
              <div class="show-preview" :style="{'width': previews.w + 'px', 'height': previews.h + 'px',  'overflow': 'hidden', 'margin': '5px'}">
                <div :style="previews.div">
                  <img :src="previews.url" :style="previews.img">
                </div>
              </div>
            </div>
            <label class="btn" for="upload2">选择照片</label>
            <input type="file" id="upload2" style="position:absolute; clip:rect(0 0 0 0);" accept="image/png, image/jpeg, image/gif, image/jpg" @change="uploadImg($event, 2)">
            <button @click="saveHeadImg('base64')" class="btn">确定</button>
            <button class="btn" @click="cancel">取消</button>
          </div>
        </div>
      </el-dialog>
    </div>
</template>

<script>
  import vueCropper from 'vue-cropper'
    export default {
        name: "image-cropper",
      components:{path:vueCropper},
      props:['image','dialogVisible','title'],
      data(){
          return{
            previews: {},
            option: {
              img: '',
              size: 1,
              full: false,
              outputType: 'png',
              canMove: true,
              fixedBox: false,
              original: false,
              canMoveBox: true
            },
            cropper: {
              img: 'http://localhost:8080/static/img/userDefault.d70d303.png',
              info: true,
              size: 1,
              outputType: 'jpeg',
              canScale: true,
              autoCrop: true,
              // 只有自动截图开启 宽度高度才生效
              autoCropWidth: 150,
              autoCropHeight: 125,
              fixed: true,
              fixedNumber: [1, 1]
            },
          }
      },
      methods:{
        setDialogVisible(val){
          this.dialogVisible = true
        },
        realTime (data) {
          this.previews = data
        },
        cancel () {
          this.image = ""
          this.dialogVisible = false
          this.$emit('setDialogDisVisible',false)
          this.$emit('getImage',"")
        },
        saveHeadImg (type) {
          this.$refs.cropper2.getCropData((data) => {
            this.image = data
            this.$emit('getImage',this.image)
          })
          this.dialogVisible = false
          this.$emit('setDialogDisVisible',false)
        },
        uploadImg (e, num) {
          var file = e.target.files[0]
          if (!/\.(gif|jpg|jpeg|png|bmp|GIF|JPG|PNG)$/.test(e.target.value)) {
            alert('图片类型必须是.gif,jpeg,jpg,png,bmp中的一种')
            return false
          }
          var reader = new FileReader()
          reader.onload = (e) => {
            let data
            if (typeof e.target.result === 'object') {
              // 把Array Buffer转化为blob 如果是base64不需要
              data = window.URL.createObjectURL(new Blob([e.target.result]))
            } else {
              data = e.target.result
            }
            if (num === 1) {
              this.option.img = data
            } else if (num === 2) {
              this.cropper.img = data
            }
          }
          reader.readAsArrayBuffer(file)
        },
      }
    }
</script>

<style scoped>
  .show-info {
    margin-bottom: 50px;
  }
  .btn {
    display: inline-block;
    line-height: 1;
    white-space: nowrap;
    cursor: pointer;
    background: #fff;
    border: 1px solid #c0ccda;
    color: #1f2d3d;
    text-align: center;
    box-sizing: border-box;
    outline: none;
    margin:20px 10px 0px 0px;
    padding: 9px 15px;
    font-size: 14px;
    border-radius: 4px;
    color: #fff;
    background-color: #50bfff;
    border-color: #50bfff;
    transition: all .2s ease;
    text-decoration: none;
    user-select: none;
  }

  .test {
    height: 300px;
    width: 400px;
  }
  .model img {
    display: block;
    margin: auto;
    max-width: 80%;
    user-select: none;
    background-position: 0px 0px, 10px 10px;
    background-size: 20px 20px;
    background-image: linear-gradient(45deg, #eee 25%, transparent 25%, transparent 75%, #eee 75%, #eee 100%),linear-gradient(45deg, #eee 25%, white 25%, white 75%, #eee 75%, #eee 100%);
  }

  @keyframes slide {
    0%  {
      background-position: 0 0;
    }
    100% {
      background-position: -100% 0;
    }
  }

</style>
