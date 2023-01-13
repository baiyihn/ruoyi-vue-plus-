<template>
  <el-card class="main-card">
    <div class="title" style="  font-size: 16px;font-weight: bold;">{{ this.$route.meta.title }}</div>
    <div class="talk-container">
      <!-- 输入框 -->
      <Editor
        ref="editor"
        class="editor-wrapper"
        v-model="talk.content"
        placeholder="说点什么吧"
      />
      <!-- 操作菜单 -->
      <div class="operation-wrapper">
        <div class="left-wrapper">
          <!-- 表情 -->
          <el-popover placement="bottom-start" width="460" trigger="click">
            <span
              class="emoji-item"
              v-for="(value, key, index) of emojiList"
              :key="index"
              @click="addEmoji(key, value)"
            >
              <img
                :src="value"
                :title="key"
                class="emoji"
                width="24"
                height="24"
              />
            </span>
            <i
              class="iconfont el-icon-present"
              slot="reference"
            />
          </el-popover>

            <image-upload v-model="talk.images"/>


        </div>
        <div class="right-wrapper">
          <el-button
            type="primary"
            size="small"
            @click="saveOrUpdateTalk"
            :disabled="talk.content == ''"
          >
            发布
          </el-button>
        </div>
      </div>

    </div>
  </el-card>
</template>

<script>
import * as imageConversion from "image-conversion";
import EmojiList from "../../../assets/js/emoji";
import Editor from "../../../components/EditorTwo/Editor";
import {getTalk} from "@/api/talk/talk";
export default {
  components: {
    Editor
  },
  created() {
    if (this.$route.query.id) {
      getTalk(this.$route.query.id).then(response =>{
        this.talk = response.data;
        if (response.data.imgList){
          response.data.imgList.forEach(item =>{
            this.uploadList.push( {url: item} )
          })
        }
      })

    }
  },
  data: function() {
    return {
      emojiList: EmojiList,
      talk: {
        id: null,
        content: "",
        images: null
      },
      uploadList: []
    };
  },
  methods: {

    addEmoji(key, value) {
      this.$refs.editor.addText(
        "<img src= '" +
        value +
        "' width='24'height='24' alt=" +
        key +
        " style='margin: 0 1px;vertical-align: text-bottom'/>"
      );
    },
    upload(response) {
      this.uploadList.push({ url: response.data });
    },
    saveOrUpdateTalk() {
      if (this.talk.content.trim() == "") {
        this.$message.error("说说内容不能为空");
        return false;
      }
      // 转换图片
      if (this.uploadList.length > 0) {
        var imgList = [];
        this.uploadList.forEach(item => {
          imgList.push(item.url);
        });
        this.talk.images = JSON.stringify(imgList);
      }
      this.axios.post("/api/admin/talks", this.talk).then(({ data }) => {
        if (data.flag) {
          this.$refs.editor.clear();
          this.uploadList = [];
          this.$notify.success({
            title: "成功",
            message: data.message
          });
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
      });
    }
  },
  computed: {
  }
};
</script>

<style scoped>
.talk-container {
  margin-top: 40px;
}
.editor-wrapper {
  min-height: 150px;
}
.operation-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 16px;
}
.operation-btn {
  cursor: pointer;
  color: #838383;
  font-size: 20px;
  margin-right: 12px;
}
.talk-status {
  cursor: pointer;
  font-size: 12px;
  color: #999;
}
.emoji {
  user-select: none;
  margin: 0.25rem;
  display: inline-block;
  vertical-align: middle;
}
.emoji-item {
  cursor: pointer;
  display: inline-block;
}
.emoji-item:hover {
  transition: all 0.2s;
  border-radius: 0.25rem;
  background: #dddddd;
}
.left-wrapper {
  display: flex;
  width: 50%;
}
.talk-image-upload {
  margin-top: 8px;
}
</style>
