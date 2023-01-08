<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="文章类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="1原创 2转载 3翻译" clearable>
          <el-option
            v-for="dict in dict.type.article_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="状态值" prop="status">
        <el-select v-model="queryParams.status" placeholder="1公开 2私密 3评论可见" clearable>
          <el-option
            v-for="dict in dict.type.article_status"
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
          v-hasPermi="['article:article:add']"
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
          v-hasPermi="['article:article:edit']"
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
          v-hasPermi="['article:article:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['article:article:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="articleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="文章分类" align="center" prop="category.categoryName" />
      <el-table-column label="文章缩略图" align="center" prop="articleCover" width="300">
        <template slot-scope="scope">
          <image-preview :src="scope.row.articleCover" :width="180" :height="100"/>
        </template>
      </el-table-column>
      <el-table-column label="标题" align="center" prop="articleTitle" />
      <el-table-column label="文章类型 " align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.article_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="是否置顶" align="center" prop="isTop">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isTop"/>
        </template>
      </el-table-column>

      <el-table-column label="状态值" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.article_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="点赞数" align="center" prop="likeCount" />
      <el-table-column label="浏览量" align="center" prop="viewsCount" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['article:article:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['article:article:remove']"
          >删除</el-button>
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

    <!-- 添加或修改文章列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="文章分类" prop="categoryId">
          <el-input v-model="form.categoryName" placeholder="请输入文章分类" />
        </el-form-item>
        <el-form-item label="文章缩略图">
          <image-upload v-model="form.articleCover"/>
        </el-form-item>
        <el-form-item label="标题" prop="articleTitle">
          <el-input v-model="form.articleTitle" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="文章类型">
          <el-radio-group v-model="form.type">
            <el-radio
              v-for="dict in dict.type.article_type"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否置顶 0否 1是">
          <el-radio-group v-model="form.isTop">
            <el-radio
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态值">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.article_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="点赞数" prop="likeCount">
          <el-input v-model="form.likeCount" placeholder="请输入点赞数" />
        </el-form-item>
        <el-form-item label="浏览量" prop="viewsCount">
          <el-input v-model="form.viewsCount" placeholder="请输入浏览量" />
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
import { listArticle, getArticle, delArticle, addArticle, updateArticle } from "@/api/article/article";

export default {
  name: "Article",
  dicts: ['article_status', 'article_type', 'sys_yes_no'],
  data() {
    return {
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
      // 文章列表表格数据
      articleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: undefined,
        categoryId: undefined,
        articleCover: undefined,
        articleTitle: undefined,
        articleContent: undefined,
        type: undefined,
        originalUrl: undefined,
        isTop: undefined,
        isDelete: undefined,
        status: undefined,
        likeCount: undefined,
        viewsCount: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "作者不能为空", trigger: "blur" }
        ],
        categoryId: [
          { required: true, message: "文章分类不能为空", trigger: "blur" }
        ],
        articleCover: [
          { required: true, message: "文章缩略图不能为空", trigger: "blur" }
        ],
        articleTitle: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
        articleContent: [
          { required: true, message: "内容不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "文章类型 1原创 2转载 3翻译不能为空", trigger: "blur" }
        ],
        originalUrl: [
          { required: true, message: "原文链接不能为空", trigger: "blur" }
        ],
        isTop: [
          { required: true, message: "是否置顶 0否 1是不能为空", trigger: "blur" }
        ],
        isDelete: [
          { required: true, message: "是否删除 0否 1是不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态值 1公开 2私密 3评论可见不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "发表时间不能为空", trigger: "blur" }
        ],
        likeCount: [
          { required: true, message: "点赞数不能为空", trigger: "blur" }
        ],
        viewsCount: [
          { required: true, message: "浏览量不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询文章列表列表 */
    getList() {
      this.loading = true;
      listArticle(this.queryParams).then(response => {
        this.articleList = response.rows;
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
        categoryId: undefined,
        articleCover: undefined,
        articleTitle: undefined,
        articleContent: undefined,
        type: 0,
        originalUrl: undefined,
        isTop: 0,
        isDelete: 0,
        status: 0,
        createTime: undefined,
        updateTime: undefined,
        likeCount: undefined,
        viewsCount: undefined,
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
      this.title = "添加文章列表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getArticle(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改文章列表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateArticle(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addArticle(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除文章列表编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delArticle(ids);
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
      this.download('article/article/export', {
        ...this.queryParams
      }, `article_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
