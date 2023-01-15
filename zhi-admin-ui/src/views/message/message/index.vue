<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="昵称" prop="nickname">
        <el-input
          v-model="queryParams.nickname"
          placeholder="请输入昵称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="留言内容" prop="messageContent">
        <el-input
          v-model="queryParams.messageContent"
          placeholder="请输入留言内容"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户ip" prop="ipAddress">
        <el-input
          v-model="queryParams.ipAddress"
          placeholder="请输入用户ip"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户地址" prop="ipSource">
        <el-input
          v-model="queryParams.ipSource"
          placeholder="请输入用户地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="弹幕速度" prop="time">
        <el-input
          v-model="queryParams.time"
          placeholder="请输入弹幕速度"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否审核" prop="isReview">
        <el-select v-model="queryParams.isReview" placeholder="请选择是否审核" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['message:message:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['message:message:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['message:message:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['message:message:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="messageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" v-if="true"/>
      <el-table-column label="昵称" align="center" prop="nickname" />
      <el-table-column label="头像" align="center" prop="avatar" width="100">
          <template slot-scope="scope">
              <image-preview :src="scope.row.avatar" :width="50" :height="50"/>
          </template>
      </el-table-column>
      <el-table-column label="留言内容" align="center" prop="messageContent" />
      <el-table-column label="用户ip" align="center" prop="ipAddress" />
      <el-table-column label="用户地址" align="center" prop="ipSource" />
      <el-table-column label="弹幕速度" align="center" prop="time" />
      <el-table-column label="是否审核" align="center" prop="isReview">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isReview"/>
        </template>
      </el-table-column>
      <el-table-column v-if="this.websiteConfigForm.isCommentReview == 1" label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.isReview=='Y'?false:true" type="success" size="small" @click="audit(scope.row)">通过</el-button>
          <el-button v-if="scope.row.isReview=='Y'?true:false" type="danger" size="small" @click="Unaudit(scope.row)">驳回</el-button>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['message:message:edit']"-->
<!--          >修改</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改留言管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="头像">
          <image-upload v-model="form.avatar"/>
        </el-form-item>
        <el-form-item label="留言内容" prop="messageContent">
          <el-input v-model="form.messageContent" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="用户ip" prop="ipAddress">
          <el-input v-model="form.ipAddress" placeholder="请输入用户ip" />
        </el-form-item>
        <el-form-item label="用户地址" prop="ipSource">
          <el-input v-model="form.ipSource" placeholder="请输入用户地址" />
        </el-form-item>
        <el-form-item label="弹幕速度" prop="time">
          <el-input v-model="form.time" placeholder="请输入弹幕速度" />
        </el-form-item>
        <el-form-item label="是否审核">
          <el-radio-group v-model="form.isReview">
            <el-radio
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
:label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listMessage, getMessage, delMessage, addMessage, updateMessage, auditMessage} from "@/api/message/message";
import {auditComment} from "@/api/comment/comment";
import {getWebsiteConfig} from "@/api/website/website";

export default {
  name: "Message",
  dicts: ['sys_yes_no'],
  data() {
    return {
      //网站配置信息
      websiteConfigForm:{},
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 留言管理表格数据
      messageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        nickname: undefined,
        avatar: undefined,
        messageContent: undefined,
        ipAddress: undefined,
        ipSource: undefined,
        time: undefined,
        isReview: undefined,
      },
      //审核参数
      auditParams:{
        id:null,
        review:null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "主键id不能为空", trigger: "blur" }
        ],
        nickname: [
          { required: true, message: "昵称不能为空", trigger: "blur" }
        ],
        avatar: [
          { required: true, message: "头像不能为空", trigger: "blur" }
        ],
        messageContent: [
          { required: true, message: "留言内容不能为空", trigger: "blur" }
        ],
        time: [
          { required: true, message: "弹幕速度不能为空", trigger: "blur" }
        ],
        isReview: [
          { required: true, message: "是否审核不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "发布时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this. getWebsiteConfig();
  },
  methods: {
    /** 查询留言管理列表 */
    getList() {
      this.loading = true;
      listMessage(this.queryParams).then(response => {
        this.messageList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        nickname: undefined,
        avatar: undefined,
        messageContent: undefined,
        ipAddress: undefined,
        ipSource: undefined,
        time: undefined,
        isReview: "0",
        createTime: undefined,
        updateTime: undefined,
        createBy: undefined,
        updateBy: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加留言管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getMessage(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改留言管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateMessage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addMessage(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除留言管理编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delMessage(ids);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('message/message/export', {
        ...this.queryParams
      }, `message_${new Date().getTime()}.xlsx`)
    },
    /**   审核逻辑  */
    audit(row){
      const id = row.id
      this.$modal.confirm('确定通过审核？').then(() => {
        this.loading = true;
        this.auditParams.id = id;
        this.auditParams.review = true;
        return auditMessage(this.auditParams);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("审核成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    Unaudit(row){
      const id = row.id
      this.$modal.confirm('确定驳回留言？').then(() => {
        this.loading = true;
        this.auditParams.id = id;
        this.auditParams.review = false;
        return auditMessage(this.auditParams);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("驳回成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    //获取网站配置信息
    getWebsiteConfig() {
      getWebsiteConfig().then(response=>{
        this.websiteConfigForm = response.data;
      })
    },


  }
};
</script>
