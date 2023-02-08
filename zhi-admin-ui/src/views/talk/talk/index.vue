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
              v-for="(value, key, index) of newEmojiList"
              :key="index"
              @click="addEmoji(key, value)"
            >
              <div
                class="emoji"
                width="24"
                height="24">
                {{value}}
              </div>
            </span>
            <i
              class="iconfont el-icon-present"
              slot="reference"
            />
          </el-popover>
            <image-upload  style="padding-left: 50px" v-model="talk.images"/>
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
import EmojiList from "../../../assets/js/emoji";
import NewEmoji from "@/assets/js/newEmoji";
import Editor from "../../../components/EditorTwo/Editor";
import {addTalk, getTalk, updateTalk} from "@/api/talk/talk";
export default {
  components: {
    Editor
  },
  created() {
    this.finduser();
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
      //当前登录用户名称
      username:"",
      emojiList: EmojiList,
      newEmojiList:NewEmoji,
      talk: {
        id: null,
        content: "",
        images: null,
        create_by:""
      },
      uploadList: []
    };
  },
  methods: {

    addEmoji(key, value) {
      console.log(key+"6666666666"+value)
      this.$refs.editor.addText(
        value
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
      this.talk.createBy = this.username;
      //修改说说
      if (this.$route.query.id) {
        updateTalk(this.talk).then(response =>{
          console.log(888888888888888888888888)
          if (response.code == 200){
            this.$refs.editor.clear();
            this.uploadList = [];
            this.talk.images = null;
            this.$modal.msgSuccess("修改成功")
          }else {
            this.$modal.msgError("修改失败")
          }

        })
      }
        //新增说说
        addTalk(this.talk).then( response =>{
          console.log(9999999999999999999)
          if (response.code == 200){
            this.$refs.editor.clear();
            this.uploadList = [];
            this.$modal.msgSuccess("新增成功")
          }else {
            this.$modal.msgError("新增失败")
          }
        })

    },
    /**  获取cookie中的作者名称   */
    finduser(){
      const strCookie = document.cookie;
      const arrCookie=strCookie.split("; ");
      let username;
      for(let i=0; i<arrCookie.length; i++){
        const arr=arrCookie[i].split("=");
        if("username"==arr[0]){
          username=arr[1];
          break;
        }
      }
      this.username = username;
    },
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
