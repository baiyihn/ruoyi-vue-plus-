<template>
  <el-card class="main-card">
    <div class="title" style="  font-size: 16px;font-weight: bold;">{{ this.$route.meta.title }}</div>

    <mavon-editor
      ref="md"
      @imgAdd="handleEditorImgAdd"
      @imgDel="handleEditorImgDel"
      v-model="form.aboutContent"
      style="height:calc(100vh - 250px);margin-top:2.25rem"
    />

    <el-button
      type="danger"
      size="medium"
      class="edit-btn"
      @click="updateAbout"
    >
      修改
    </el-button>
  </el-card>
</template>

<script>
import * as imageConversion from "image-conversion";
import {getAbout, updateAbout, uploadOssImage} from "@/api/about/about";
import {getToken} from "@/utils/auth";

export default {
  created() {
    this.getAbout();
  },
  data: function() {
    return {
      form:{
        aboutContent: ""
      },
      imgFile:[],

    };
  },
  methods: {
    getAbout() {
      getAbout().then(response =>{
        this.aboutContent = response.msg;
      })

    },
    // 上传成功回调
    // uploadImg(pos, file) {
    //   // 第一步.将图片上传到服务器.
    //   var formdata = new FormData();
    //   formdata.append('image', file);
    //   this.img_file[pos] = file;
    //
    //   this.axios({
    //     url: '/dev-api/system/oss/upload',
    //     method: 'post',
    //     data: formdata,
    //     headers: { 'Content-Type': 'multipart/form-data' ,'Authorization': getToken()},
    //   }).then((res) => {
    //     let _res = res.data;
    //     // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
    //     this.$refs.md.$img2Url(pos, _res.url);
    //   })
    // },
    handleEditorImgAdd (pos,file) {
      let formdata = new FormData()
      formdata.append('file', file)
      this.imgFile[pos] = file
      uploadOssImage(formdata).then(res => {
        if (res.code === 200) {
          this.$modal.msgSuccess("上传成功");
          let url = res.data.url
          let name = file.name
          if (name.includes('-')) {
            name = name.replace(/-/g, '')
          }
          let content = this.form.aboutContent
          // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)  这里是必须要有的
          this.$refs.md.$img2Url(pos, res.data.url);
        } else {
          this.$modal.msgError(res.data.msg);
        }
      })
    },
    handleEditorImgDel (pos) {
      delete this.imgFile[pos]
    },



    updateAbout() {
      updateAbout(this.form).then(( response ) => {
          if (response.code == 200) {
            this.$modal.msgSuccess(response.msg);
          } else {
            this.$modal.msgSuccess(response.msg);
          }
        });
    },
    // 上传前loading加载
    handleBeforeUpload(file) {
      let isImg = false;
      if (this.fileType.length) {
        let fileExtension = "";
        if (file.name.lastIndexOf(".") > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
        }
        isImg = this.fileType.some((type) => {
          if (file.type.indexOf(type) > -1) return true;
          if (fileExtension && fileExtension.indexOf(type) > -1) return true;
          return false;
        });
      } else {
        isImg = file.type.indexOf("image") > -1;
      }

      if (!isImg) {
        this.$modal.msgError(`文件格式不正确, 请上传${this.fileType.join("/")}图片格式文件!`);
        return false;
      }
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize;
        if (!isLt) {
          this.$modal.msgError(`上传头像图片大小不能超过 ${this.fileSize} MB!`);
          return false;
        }
      }
      this.$modal.loading("正在上传图片，请稍候...");
      this.number++;
    },

  // 根据下标替换字符
    replaceStr (str, index, char)  {
      const strAry = str.split('');
      strAry[index] = char;
      return strAry.join('');
    },

  }
};
</script>

<style scoped>
.edit-btn {
  float: right;
  margin: 1rem 0;
}

.main-card {
  min-height: calc(100vh - 126px);
}
</style>
