<template>
  <div class="back">
    <div>
     <p>{{ showText }}</p>
    </div>
    <div class="stars" >
      <div class="star" v-for="(item, index) in starsCount" :key="index" ref="star"></div>
    </div>
  </div>
</template>

<script>

export default {

  mounted() {
    this.appear(
      " 当你看到这一行话的时候，那么首先恭喜你，博客运行成功。" +
      "从大一开始我便热爱上了自己的专业，在那个时候，看着很多大神炫酷的个人博客很是向往，" +
      "然而当时的我还是小白，在代码方面寸步难行，" +
      "经过了两年的埋头学习，如今我终于可以写出一些自己的东西，" +
      "这个博客蕴含的是我两年来的心血，作为一个大三的学生，自己懂得并不是很多，" +
      "代码上面不可避免的会出现很多错误和不合理的地方还请谅解！"
    );
    let starNodes =  this.$refs.star
    starNodes.forEach((item) => {
      let speed = 0.2 + Math.random();
      let thisDistance = this.distance + Math.random() * 300;
      item.style.transformOrigin = `0 0 ${thisDistance}px`;
      item.style.transform = `
        translate3d(0,0,-${thisDistance}px)
        rotateY(${Math.random() * 360}deg)
        rotateX(${Math.random() * -50}deg)
        scale(${speed},${speed})`;
    });

  },
  created() {
     this.starsRef = document.getElementsByClassName("stars")
  },


  data() {
    return {
      timer: null, //settimeout
      showText: "",
      starsRef:{},
      starsCount :800,
      distance:900,
    };
  },
  methods: {
    appear(content) {
      const _this = this;

      this.showText = "";
      clearTimeout(this.timer);

      var speed = 200; //设置定时的速度 越来越快
      var count = 1;

      function changeContent() {
        _this.showText = content.substring(0, count); //截取字符串
        count++;

        if (count != content.length + 1) {
          speed -= 1;
          if (speed < 5) speed = 5;

          _this.timer = setTimeout(changeContent, speed);
        }
      }
      changeContent();
    },
  },

};
</script>
<style lang="css" scoped>
.back {
  position: absolute;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  background: radial-gradient(
    200% 100% at bottom center,
    #f7f7b6,
    #e96f92,
    #1b2947
  );
  background: radial-gradient(
    200% 105% at top center,
    #1b2947 10%,
    #75517d 40%,
    #e96f92 65%,
    #f7f7b6
  );
  background-attachment: fixed;
  overflow: hidden;
}

@keyframes rotate {
  0% {
    transform: perspective(400px) rotateZ(20deg) rotateX(-40deg) rotateY(0);
  }
  100% {
    transform: perspective(400px) rotateZ(20deg) rotateX(-40deg)
    rotateY(-360deg);
  }
}
.stars {
  transform: perspective(500px);
  transform-style: preserve-3d;
  position: absolute;
  perspective-origin: 50% 100%;
  left: 45%;
  animation: rotate 90s infinite linear;
  bottom: 0;
}
.star {
  width: 2px;
  height: 2px;
  background: #f7f7b6;
  position: absolute;
  left: 0;
  top: 0;
  backface-visibility: hidden;
}

p{
  text-indent:2em;
  font-size: 40px;
  font-weight: 70;
  font-family: Georgia, serif;
  background-image:
    linear-gradient(90deg,
    #673ab7,
    #e91e63);
  background-clip: text;
  -webkit-background-clip: text;
  /* 这次的这句和以往不一样 */
  /* 以往是考虑低版本浏览器 */
  /* 这次就连最新版Chrome和Safari都需要 */
  /* 注意是background-clip: text;需要 */
  /* 如果是： content-box啊之类的就没这么苛刻的 */
  -webkit-text-fill-color: transparent;
  /* 这个属性的-webkit-前缀也比较特殊 */
  /* 寻常的-webkit-表示的是Chrome和Safari的私有属性 */
  /* 但是它却声明了所有的（除了那位浏览器） */
  background-image:
    linear-gradient(90deg,
    rgb(255, 167, 69),
    rgb(254, 134, 159),
    rgb(239, 122, 200),
    rgb(160, 131, 237),
    rgb(67, 174, 255),
    rgb(160, 131, 237),
    rgb(239, 122, 200),
    rgb(254, 134, 159),
    rgb(255, 167, 69));
  background-size: 200%;
  margin: 160px 30px;
}

p:hover{
  animation: streamer 2s linear infinite;
}

@keyframes streamer {
  0%{
    background-position: 0%;
  }
  100%{
    background-position: 200%;
  }
}
</style>
