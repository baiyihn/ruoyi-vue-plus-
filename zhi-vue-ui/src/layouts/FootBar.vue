<template>

  <div class="footer-container" >
    <div class="footer">
    <div id="jsi-flying-fish-container" class="fish-cartoon">
<!--    <div class="footer-body">-->
<!--      <div class="link"></div>-->
<!--      <div class="footer-custom">-->
<!--        ©2022 By-->
<!--        ftz-->
<!--      </div>-->
<!--      <a-->
<!--          class="footer-copyright"-->
<!--          href="https://beian.miit.gov.cn/#/Integrated/index"-->
<!--          target="_blank"-->
<!--      >-->
<!--       ftz-->
<!--      </a>-->
<!--    </div>-->
    </div>
    </div>


  </div>
</template>
<script setup lang="ts">
// 鱼儿跳跃
import "@/assets/js/fish.js";
</script>

<style scoped lang="less">

.footer-body {
  margin-bottom: 0;
  height: 100px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: #fff;
  // background: rgba(66, 185, 133, 0.6);
  background: #036cd5;
}
.footer-copyright {
  margin-top: 8px;
  margin-bottom: 5px;
  font-size: 14px;
  text-decoration: none;
  color: #fff;
}
.footer-custom {
  font-size: 14px;
}
.fish-cartoon {
  background-color: #f3f6f7;
  position: relative;
  width: 100%;

}

</style>
