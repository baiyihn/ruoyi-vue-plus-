<template>
  <div>
    <div class="banner" :style="cover">
      <h1 class="banner-title">个人中心</h1>
    </div>
    <v-card class="blog-container">
      <div>
        <span class="info-title">基本信息</span>
      </div>
      <v-row class="info-wrapper">
        <v-col md="3" cols="12">
          <button id="pick-avatar" @click="uploadAvatar">
            <v-avatar size="140">
              <img :src="this.$store.state.avatar" />
            </v-avatar>
          </button>
<!--          <avatar-cropper-->
<!--            @uploaded="uploadAvatar"-->
<!--            trigger="#pick-avatar"-->
<!--            upload-url="/api/users/avatar"-->
<!--          />-->
        </v-col>
        <v-col md="7" cols="12">
          <v-text-field
            v-model="userInfo.nickname"
            label="昵称"
            placeholder="请输入您的昵称"
          />
          <v-text-field
            v-model="userInfo.webSite"
            class="mt-7"
            label="个人网站"
            placeholder="http://你的网址"
          />
          <v-text-field
            v-model="userInfo.intro"
            class="mt-7"
            label="简介"
            placeholder="介绍下自己吧"
          />
          <div v-if="loginType != 0" class="mt-7 binding">
            <v-text-field
              disabled
              v-model="email"
              label="邮箱号"
              placeholder="请绑定邮箱"
            />
            <v-btn v-if="email" text small @click="openEmailModel">
              修改绑定
            </v-btn>
            <v-btn v-else text small @click="openEmailModel">
              绑定邮箱
            </v-btn>
          </div>
          <v-btn @click="updataUserInfo" outlined class="mt-5">修改</v-btn>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
// import AvatarCropper from "vue-avatar-cropper";
export default {
  // components: { AvatarCropper },
  data: function() {
    return {
      userInfo: {
        nickname: this.$store.state.nickname,
        intro: this.$store.state.intro,
        webSite: this.$store.state.webSite,
        loginType: this.$store.state.loginType,
        id : this.$store.state.userId

      }
    };
  },
  methods: {
    updataUserInfo() {
        console.log(this.$store.state.userId+"456465");
        console.log(this.$store.state.nickname);

        this.axios.put("/api/system/user/users/info", this.userInfo).then(({ data }) => {
        if (data.code == 200) {
          this.$store.commit("updateUserInfo", this.userInfo);
          this.$toast({ type: "success", message: "修改成功" });
        } else {
          this.$toast({ type: "error", message: data.message });
        }
      });
    },
    uploadAvatar() {
        this.$toast({ type: "success", message: "🚆✈暂未开放头像修改功能哦❤" });
      // if (data.flag) {
      //   this.$store.commit("updateAvatar", data.data);
      //   this.$toast({ type: "success", message: "上传成功" });
      // } else {
      //   this.$toast({ type: "error", message: data.message });
      // }
    },
    openEmailModel() {
      this.$store.state.emailFlag = true;
    }
  },
  computed: {
    email() {
      return this.$store.state.email;
    },
    loginType() {
      return this.$store.state.loginType;
    },
    cover() {
      var cover = "";
      this.$store.state.blogInfo.pageList.forEach(item => {
        if (item.pageLabel == "user") {
          cover = item.pageCover;
        }
      });
      return "background: url(" + cover + ") center center / cover no-repeat";
    }
  }
};
</script>

<style scoped>
.info-title {
  font-size: 1.25rem;
  font-weight: bold;
}
.info-wrapper {
  margin-top: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
}
#pick-avatar {
  outline: none;
}
.binding {
  display: flex;
  align-items: center;
}
</style>
