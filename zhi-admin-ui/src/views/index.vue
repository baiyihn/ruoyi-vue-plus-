<template>
  <div
    class="tagBall"
    ref="tagBall"
    @mouseover.self.stop="mouseOver"
    @mouseleave.self.stop="mouseLeave"
  >
    <p v-for="(item, index) in 50" :key="index" class="tag">{{ item }}</p>
  </div>
</template>

<script>
export default {
  name: "",
  components: {},
  data() {
    return {
      tagEle: "",
      paper: "",
      RADIUS: 300,
      fallLength: 500,
      tags: [],
      angleX: Math.PI / 500,
      angleY: Math.PI / 500,
      CX: "",
      CY: "",
      EX: "",
      EY: "",
      timing: null,
    };
  },
  props: {
  },
  computed: {
    maxum() {
      return this.wordList.sort()[0].value;
    },
  },
  watch: {},
  created() {},
  mounted() {
    this.tagEle = this.$refs.tagBall.children;
    this.paper = this.$refs.tagBall;
    this.CX = this.paper.offsetWidth / 2;
    this.CY = this.paper.offsetHeight / 2;
    this.EX =
      this.paper.offsetLeft +
      document.body.scrollLeft +
      document.documentElement.scrollLeft;
    this.EY =
      this.paper.offsetTop +
      document.body.scrollTop +
      document.documentElement.scrollTop;
    this.innit();
    this.animate();
  },
  methods: {
    innit() {
      for (var i = 0; i < this.tagEle.length; i++) {
        var a, b;
        var k = (2 * (i + 1) - 1) / this.tagEle.length - 1;
        var a = Math.acos(k);
        var b = a * Math.sqrt(this.tagEle.length * Math.PI);
        var x = this.RADIUS * Math.sin(a) * Math.cos(b);
        var y = this.RADIUS * Math.sin(a) * Math.sin(b);
        var z = this.RADIUS * Math.cos(a);
        // var t = this.tag({ ele: this.tagEle[i], x, y, z });
        this.tagEle[i].style.color =
          "rgb(" +
          parseInt(Math.random() * 255) +
          "," +
          parseInt(Math.random() * 255) +
          "," +
          parseInt(Math.random() * 255) +
          ")";
        this.tags.push({ ele: this.tagEle[i], x, y, z });
      }
    },
    tag({ ele, x, y, z }) {
      var scale = this.fallLength / (this.fallLength - z);
      var alpha = (z + this.RADIUS) / (2 * this.RADIUS);
      ele.style.fontSize = 15 * scale + "px";
      ele.style.opacity = alpha + 0.5;
      ele.style.filter = "alpha(opacity = " + (alpha + 0.5) * 100 + ")";
      ele.style.zIndex = parseInt(scale * 100);
      ele.style.left = x + this.CX - ele.offsetWidth / 2 + "px";
      ele.style.top = y + this.CY - ele.offsetHeight / 2 + "px";
    },
    animate() {
      this.timing = setInterval(() => {
        this.rotateX();
        this.rotateY();
        this.tags.forEach((item) => {
          this.tag(item);
        });
      }, 20);
    },
    rotateX() {
      var cos = Math.cos(this.angleX);
      var sin = Math.sin(this.angleX);
      this.tags.forEach((item) => {
        var y1 = item.y * cos - item.z * sin;
        var z1 = item.z * cos + item.y * sin;
        item.y = y1;
        item.z = z1;
      });
    },
    rotateY() {
      var cos = Math.cos(this.angleY);
      var sin = Math.sin(this.angleY);
      this.tags.forEach((item) => {
        var x1 = item.x * cos - item.z * sin;
        var z1 = item.z * cos + item.x * sin;
        item.x = x1;
        item.z = z1;
      });
    },
    mouseOver(event) {
      var x = event.clientX - this.EX - this.CX;
      var y = event.clientY - this.EY - this.CY;
      console.log(x, y);
      this.angleY = x * 0.0001;
      this.angleX = y * 0.0001;
      if (!this.timing) this.animate();
    },
    mouseLeave(event) {
      var x = event.clientX - this.EX - this.CX;
      var y = event.clientY - this.EY - this.CY;
      this.angleY = x * 0.0001;
      this.angleX = y * 0.0001;
    },
    cheMouseOver() {
      clearInterval(this.timing);
      this.timing = null;
    },
  },
};
</script>
<style lang='scss' scoped>
.tagBall {
  width: 300px;
  height: 300px;
  margin: 50px auto;
  position: relative;
  padding-top: 500px;
}
.tag {
  display: block;
  position: absolute;
  left: 0px;
  top: 0px;
  color: #000;
  text-decoration: none;
  font-size: 15px;
  font-family: "微软雅黑";
  font-weight: bold;
}
</style>
