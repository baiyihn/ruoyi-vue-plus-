<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="评论类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="1.文章 2.友链 3.说说" clearable>
          <el-option
            v-for="dict in dict.type.commen_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="评论状态" prop="state">
        <el-select v-model="queryParams.state" placeholder="请选择评论状态" clearable>
          <el-option
            v-for="dict in dict.type.comment_status"
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
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['comment:comment:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['comment:comment:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['comment:comment:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="commentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="评论用户" align="center" prop="nickname" />

      <el-table-column label="评论内容" align="center" prop="commentContent"  >
        <template slot-scope="scope">
          <div class="talk-content" v-html="scope.row.commentContent" />
        </template>

      </el-table-column>>

      <el-table-column label="回复用户" align="center" prop="replyUserName" >
        <template slot-scope="scope">
          <span>{{scope.row.replyUserName== null ? '无' :scope.row.replyUserName}}</span>
        </template>
      </el-table-column>>
<!--      <el-table-column label="父评论id" align="center" prop="parentId" />-->
      <el-table-column label="来源" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.commen_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="评论文章" align="center" prop="articleTitle" />
      <el-table-column label="评论ip" align="center" prop="ipAddress" />
      <el-table-column label="真实地址" align="center" prop="ipSource" />
      <el-table-column label="评论状态" align="center" prop="state">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.comment_status" :value="scope.row.state"/>
        </template>
      </el-table-column>

      <el-table-column v-if="this.websiteConfigForm.isCommentReview == 1" label="审核" align="center" class-name="small-padding fixed-width">

        <template slot-scope="scope">
          <el-button v-if="scope.row.state != 1 ?true:false" type="success" size="small" @click="audit(scope.row)">通过</el-button>
          <el-button v-if="scope.row.state==3?false:true" type="danger" size="small" @click="Unaudit(scope.row)">驳回</el-button>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['comment:comment:edit']"-->
<!--          >修改</el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['comment:comment:remove']"-->
<!--          >删除</el-button>-->
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

    <!-- 添加或修改评论管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="评论用户Id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入评论用户Id" />
        </el-form-item>
        <el-form-item label="评论内容" prop="commentContent">
          <el-input v-model="form.commentContent" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="回复用户id" prop="replyUserId">
          <el-input v-model="form.replyUserId" placeholder="请输入回复用户id" />
        </el-form-item>
        <el-form-item label="父评论id" prop="parentId">
          <el-input v-model="form.parentId" placeholder="请输入父评论id" />
        </el-form-item>
        <el-form-item label="评论类型 1.文章 2.友链 3.说说" prop="type">
          <el-select v-model="form.type" placeholder="请选择评论类型 1.文章 2.友链 3.说说">
            <el-option
              v-for="dict in dict.type.commen_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否删除  0否 1是" prop="isDelete">
          <el-input v-model="form.isDelete" placeholder="请输入是否删除  0否 1是" />
        </el-form-item>
        <el-form-item label="评论主题id" prop="topicId">
          <el-input v-model="form.topicId" placeholder="请输入评论主题id" />
        </el-form-item>
        <el-form-item label="评论ip" prop="ipAddress">
          <el-input v-model="form.ipAddress" placeholder="请输入评论ip" />
        </el-form-item>
        <el-form-item label="真实地址" prop="ipSource">
          <el-input v-model="form.ipSource" placeholder="请输入真实地址" />
        </el-form-item>
        <el-form-item label="评论状态" prop="state">
          <el-select v-model="form.state" placeholder="请选择评论状态">
            <el-option
              v-for="dict in dict.type.comment_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
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
import {auditComment, listComment, getComment, delComment, addComment, updateComment } from "@/api/comment/comment";
import {getWebsiteConfig} from "@/api/website/website";

export default {
  name: "Comment",
  dicts: ['commen_type', 'comment_status'],
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
      // 评论管理表格数据
      commentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: undefined,
        commentContent: undefined,
        replyUserId: undefined,
        parentId: undefined,
        type: undefined,
        isDelete: undefined,
        topicId: undefined,
        ipAddress: undefined,
        ipSource: undefined,
        state: undefined,
      },
      //审核参数
      auditParams:{
        id:null,
        status:null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "主键不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "评论用户Id不能为空", trigger: "blur" }
        ],
        commentContent: [
          { required: true, message: "评论内容不能为空", trigger: "blur" }
        ],
        replyUserId: [
          { required: true, message: "回复用户id不能为空", trigger: "blur" }
        ],
        parentId: [
          { required: true, message: "父评论id不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "评论类型 1.文章 2.友链 3.说说不能为空", trigger: "change" }
        ],
        isDelete: [
          { required: true, message: "是否删除  0否 1是不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "评论时间不能为空", trigger: "blur" }
        ],
        topicId: [
          { required: true, message: "评论主题id不能为空", trigger: "blur" }
        ],
        ipAddress: [
          { required: true, message: "评论ip不能为空", trigger: "blur" }
        ],
        ipSource: [
          { required: true, message: "真实地址不能为空", trigger: "blur" }
        ],
        state: [
          { required: true, message: "评论状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getWebsiteConfig();

  },
  methods: {
    /** 查询评论管理列表 */
    getList() {
      this.loading = true;
      listComment(this.queryParams).then(response => {
        this.commentList = response.rows;
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
        userId: undefined,
        commentContent: undefined,
        replyUserId: undefined,
        parentId: undefined,
        type: undefined,
        isDelete: undefined,
        createTime: undefined,
        updateTime: undefined,
        topicId: undefined,
        ipAddress: undefined,
        ipSource: undefined,
        state: undefined,
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
      this.title = "添加评论管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getComment(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改评论管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateComment(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addComment(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除评论管理编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delComment(ids);
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
      this.download('comment/comment/export', {
        ...this.queryParams
      }, `comment_${new Date().getTime()}.xlsx`)
    },
    /**   审核逻辑  */
    audit(row){
      const id = row.id
      this.$modal.confirm('确定通过审核？').then(() => {
        this.loading = true;
        this.auditParams.id = id;
        this.auditParams.status = true;
        return auditComment(this.auditParams);
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
      this.$modal.confirm('确定驳回评论？').then(() => {
        this.loading = true;
        this.auditParams.id = id;
        this.auditParams.status = false;
        return auditComment(this.auditParams);
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
