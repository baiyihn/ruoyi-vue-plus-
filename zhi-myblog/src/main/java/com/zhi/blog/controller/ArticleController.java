package com.zhi.blog.controller;

import java.util.List;
import java.util.Arrays;

import cn.dev33.satoken.annotation.SaIgnore;
import com.zhi.blog.domain.vo.PageResult;
import com.zhi.blog.dto.*;
import com.zhi.blog.dto.vo.ConditionVO;
import com.zhi.blog.dto.vo.LikeVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.zhi.common.annotation.RepeatSubmit;
import com.zhi.common.annotation.Log;
import com.zhi.common.core.controller.BaseController;
import com.zhi.common.core.domain.PageQuery;
import com.zhi.common.core.domain.R;
import com.zhi.common.core.validate.AddGroup;
import com.zhi.common.core.validate.EditGroup;
import com.zhi.common.enums.BusinessType;
import com.zhi.common.utils.poi.ExcelUtil;
import com.zhi.blog.domain.vo.ArticleVo;
import com.zhi.blog.domain.bo.ArticleBo;
import com.zhi.blog.service.IArticleService;
import com.zhi.common.core.page.TableDataInfo;

/**
 * 文章列表
 *
 * @author ftz
 * @date 2023-01-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/article/article")
public class ArticleController extends BaseController {

    private final IArticleService iArticleService;



    /**
     * 点赞文章
     *
     * @return {@link R<>}
     */
    @SaIgnore
    @ApiOperation(value = "点赞文章")
    @PostMapping("/articles/like")
    public R<?> saveArticleLike(@RequestBody LikeVO likeVO) {
        iArticleService.saveArticleLike(likeVO.getArticleId(),likeVO.getUserid());
        return R.ok();
    }


    /**
     * 查看文章归档
     *
     * @return {@link R<ArchiveDTO>} 文章归档列表
     */
    @SaIgnore
    @ApiOperation(value = "查看文章归档")
    @GetMapping("/articles/archives")
    public R<PageResult<ArchiveDTO>> listArchives() {
        return R.ok(iArticleService.listArchives());
    }


    /**
     * 查看博客前端首页文章
     *
     */
    @SaIgnore
    @ApiOperation(value = "查看首页文章")
    @GetMapping("/articles")
    public R<List<ArticleHomeDTO>> listArticles() {
        return R.ok(iArticleService.listArticles());
    }




    /**
     * 博客前端根据id查看文章
     *
     * @param articleId 文章id
     * @return {@link R<ArticleDTO>} 文章信息
     */
    @SaIgnore
    @ApiOperation(value = "根据id查看文章")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @GetMapping("/articles/{articleId}")
    public R<ArticleDTO> getArticleById(@PathVariable("articleId") Integer articleId) {
        return R.ok(iArticleService.getArticleById(articleId));
    }


    /**
     * 根据条件查询文章
     *
     * @param condition 条件
     * @return {@link R<ArticlePreviewListDTO>} 文章列表
     */
    @SaIgnore
    @ApiOperation(value = "根据条件查询文章")
    @GetMapping("/articles/condition")
    public R<ArticlePreviewListDTO> listArticlesByCondition(ConditionVO condition) {
        return R.ok(iArticleService.listArticlesByCondition(condition));
    }


    /**
     * 查询文章列表列表
     */
    @GetMapping("/list")
    public TableDataInfo<ArticleVo> list(ArticleBo bo, PageQuery pageQuery) {
        return iArticleService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出文章列表列表
     */
    @SaCheckPermission("article:article:export")
    @Log(title = "文章列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ArticleBo bo, HttpServletResponse response) {
        List<ArticleVo> list = iArticleService.queryList(bo);
        ExcelUtil.exportExcel(list, "文章列表", ArticleVo.class, response);
    }

    /**
     * 获取文章列表详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("article:article:query")
    @GetMapping("/{id}")
    public R<ArticleVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iArticleService.queryById(id));
    }

    /**
     * 新增文章列表
     */
    @SaCheckPermission("article:article:add")
    @Log(title = "文章列表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ArticleBo bo) {
        return toAjax(iArticleService.insertByBo(bo));
    }

    /**
     * 修改文章列表
     */
    @SaCheckPermission("article:article:edit")
    @Log(title = "文章列表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ArticleBo bo) {
        return toAjax(iArticleService.updateByBo(bo));
    }

    /**
     * 删除文章列表
     *
     * @param ids 主键串
     */
    @SaCheckPermission("article:article:remove")
    @Log(title = "文章列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iArticleService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
