<template>


  <LoadingAnimation v-if="isShowLoading"></LoadingAnimation>
  <div v-if="pinia.blogInfo" :class="pinia.themeName">

    <transition name="slide-up">
      <toggle-day v-show="showAnimation"></toggle-day>
    </transition>
    <!--回到顶部-->
    <a-back-top style="right: 24px;bottom: 72px;">
      <a-button shape="circle" size="large">
        <template #icon>
          <up-outlined/>
        </template>
      </a-button>
    </a-back-top>
    <!--主题切换-->
    <div class="theme-switch" @click="switchTheme()">
      <icon-font :type="state.themeClass ? 'icon-taiyang' : 'icon-yueliang'" style="font-size: 40px;"/>
    </div>


    <div>
      <!--顶部导航栏-->
      <top-bar></top-bar>
      <!--主体-->
      <router-view :key="route.fullPath"></router-view>
      <!--底部栏-->
      <foot-bar></foot-bar>
    </div>
    <!--登录注册弹窗-->
    <user-modal></user-modal>
    <!--搜索弹窗-->
    <search-modal></search-modal>
    <!--信息中心弹窗-->
    <message-modal></message-modal>
    <!--    聊天弹窗-->
    <chat-model></chat-model>
  </div>

</template>


<script setup lang="ts">

import {getBlogInfo} from "./api/blog";
import {uploadUserInfo} from "./api/user"
import TopBar from "./layouts/TopBar.vue";
import FootBar from "./layouts/FootBar.vue";
import UserModal from "./components/modal/UserModal.vue";
import SearchModal from "./components/modal/SearchModal.vue";
import MessageModal from "./components/modal/MessageModal.vue";
import ChatModel from "./components/modal/ChatModel.vue"
import {onMounted, reactive, ref} from "vue";
import {useRoute} from "vue-router";
import {useStore} from "./store/index";
import {createFromIconfontCN, UpOutlined} from "@ant-design/icons-vue";
import config from "./config/config";
import toggleDay from "./components/base/toggle_day/index.vue";
import LoadingAnimation from "./components/base/loading/index.vue";

const IconFont = createFromIconfontCN({
  scriptUrl: config.ICON_FONT_URL,
});
const route = ref(useRoute());


const pinia = useStore();
const state = reactive({
  themeClass: true
})
let showAnimation = ref(false)
let timer: any = null
const switchTheme = () => {
  showAnimation.value = true
  clearTimeout(timer)
  timer = null

  new Promise((resolve, reject) => {
    timer = setTimeout(() => {
      pinia.switchTheme(state.themeClass)
      resolve(true)
    }, 300)
  }).then(() => {
    state.themeClass = !state.themeClass
    pinia.switchTheme(state.themeClass)
  })


  timer = setTimeout(() => {
    showAnimation.value = false
  }, 2500);
}

const getInfo = async () => {
  const data = await getBlogInfo()
  pinia.saveBlogInfo(data);
}

const isShowLoading = ref(true)

onMounted(() => {
  //获取博客信息
  getInfo()
  //上传用户信息
  uploadUserInfo();
  setTimeout(() => {
    isShowLoading.value = false
  }, 1000)
});
</script>


<style scoped>
.theme-switch {
  position: fixed;
  right: 24px;
  bottom: 124px;
  z-index: 999;
}

.slide-up-enter-active,
.slide-up-leave-active {
  transition: all 1.5s;
}

.slide-up-enter-from {
  transform: translateY(-100%);
}

.slide-up-leave-to {
  transform: translateY(-100%);
}

</style>
