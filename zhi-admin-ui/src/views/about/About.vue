<template>
  <el-card class="main-card">
    <div class="title" style="  font-size: 16px;font-weight: bold;">{{ this.$route.meta.title }}</div>


    <mavon-editor
      ref="md"
      @imgAdd="uploadImg"
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
import {getAbout, updateAbout} from "@/api/about/about";
export default {
  created() {
    this.getAbout();
  },
  data: function() {
    return {
      form:{
        aboutContent: ""
      },

    };
  },
  methods: {
    getAbout() {
      getAbout().then(response =>{
        this.aboutContent = response.data;
      })

    },
    uploadImg(pos, file) {
      var formdata = new FormData();
      if (file.size / 1024 < this.config.UPLOAD_SIZE) {
        formdata.append("file", file);
        this.axios
          .post("/dev-api/blogInfo/admin/articles/images", formdata)
          .then(({ data }) => {
            this.$refs.md.$img2Url(pos, data.data);
          });
      } else {
        // 压缩到200KB,这里的200就是要压缩的大小,可自定义
        imageConversion
          .compressAccurately(file, this.config.UPLOAD_SIZE)
          .then(res => {
            formdata.append(
              "file",
              new window.File([res], file.name, { type: file.type })
            );
            this.axios
              .post("/api/blogInfo/admin/articles/images", formdata)
              .then(({ data }) => {
                this.$refs.md.$img2Url(pos, data.data);
              });
          });
      }
    },
    updateAbout() {
      updateAbout(this.form).then(( response ) => {
          if (response.code == 200) {
            this.$modal.msgSuccess(response.msg);
          } else {
            this.$modal.msgSuccess(response.msg);
          }
        });
    }
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
