<template>
    <v-app id="app">


        <!--主题切换-->
        <div class="theme-switch" @click="check">
            <v-img v-if="!isSon" width="50" height="50" src="https://s1.ax1x.com/2023/01/16/pSlxa4J.png"
                   style="font-size: 40px;"/>
            <v-img v-if="isSon" width="50" height="50" src="https://s1.ax1x.com/2023/01/16/pSljjoQ.png"
                   style="font-size: 40px;"/>
        </div>

        <transition name="slide-up">
            <ToggleDay v-show="showAnimation"></ToggleDay>
        </transition>

        <!-- 导航栏 -->
        <TopNavBar></TopNavBar>
        <!-- 侧边导航栏 -->
        <SideNavBar></SideNavBar>
        <!-- 内容 -->
        <v-content>
            <router-view :key="$route.fullPath"/>
        </v-content>
        <!-- 页脚 -->
        <Footer></Footer>
        <!-- 返回顶部 -->
        <BackTop></BackTop>
        <!-- 搜索模态框 -->
        <searchModel></searchModel>
        <!-- 登录模态框 -->
        <LoginModel></LoginModel>
        <!-- 注册模态框 -->
        <RegisterModel></RegisterModel>
        <!-- 忘记密码模态框 -->
        <ForgetModel></ForgetModel>
        <!-- 绑定邮箱模态框 -->
        <EmailModel></EmailModel>
        <!-- 音乐播放器 -->
        <Player v-if="blogInfo.websiteConfig.isMusicPlayer == 1 && !isMobile"/>
        <!-- 聊天室 -->
        <ChatRoom v-if="blogInfo.websiteConfig.isChatRoom == 1"></ChatRoom>
    </v-app>
</template>
<script>

import TopNavBar from "./components/layout/TopNavBar";
import SideNavBar from "./components/layout/SideNavBar";
import Footer from "./components/layout/Footer";
import BackTop from "./components/BackTop";
import searchModel from "./components/model/SearchModel";
import LoginModel from "./components/model/LoginModel";
import RegisterModel from "./components/model/RegisterModel";
import ForgetModel from "./components/model/ForgetModel";
import EmailModel from "./components/model/EmailModel";
import Player from "./components/zw-player/player.vue";
import ChatRoom from "./components/ChatRoom";
import ToggleDay from "./components/base/toggle_day/ToggleDay.vue";

export default {

    created() {
        // 获取博客信息
        this.getBlogInfo();
        // 上传访客信息
        this.axios.post("/api/blogInfo/report");
    },
    data: function () {
        return {
            isSon: true,
            showAnimation: false,
            timer: null
        };
    },

    components: {
        TopNavBar,
        Player,
        SideNavBar,
        Footer,
        BackTop,
        searchModel,
        LoginModel,
        RegisterModel,
        ForgetModel,
        EmailModel,
        ChatRoom,
        ToggleDay
    },
    methods: {
        getBlogInfo() {
            this.axios.get("/api/blogInfo").then(({data}) => {
                this.$store.commit("checkBlogInfo", data.data);
            });
        },
        check() {
            clearTimeout(this.timer)
            this.showAnimation = true
            this.timer = null
            this.isSon = !this.isSon;
            new Promise((resolve) => {
                setTimeout(() => {
                    resolve()
                }, 300)
            }).then(() => {
                this.$vuetify.theme.dark = !this.$vuetify.theme.dark
            })
            this.timer = setTimeout(() => {
                this.showAnimation = false
            }, 2500)
        }
    },
    computed: {
        blogInfo() {
            return this.$store.state.blogInfo;
        },
        isMobile() {
            const flag = navigator.userAgent.match(
                /(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i
            );
            return flag;
        }
    }
};
</script>

<style scoped>
.theme-switch {
    position: fixed;
    right: 24px;
    bottom: 54px;
    z-index: 999;
}
</style>
