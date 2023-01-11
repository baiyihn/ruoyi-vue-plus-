package com.zhi.blog.controller;

import java.util.List;
import java.util.Arrays;

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
import com.zhi.blog.domain.vo.CommentVo;
import com.zhi.blog.domain.bo.CommentBo;
import com.zhi.blog.service.ICommentService;
import com.zhi.common.core.page.TableDataInfo;

/**
 * 评论管理
 *
 * @author ftz
 * @date 2023-01-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/comment/comment")
public class CommentController extends BaseController {

    private final ICommentService iCommentService;

    /**
     * 查询评论管理列表
     */
    @SaCheckPermission("comment:comment:list")
    @GetMapping("/list")
    public TableDataInfo<CommentVo> list(CommentBo bo, PageQuery pageQuery) {
        return iCommentService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出评论管理列表
     */
    @SaCheckPermission("comment:comment:export")
    @Log(title = "评论管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CommentBo bo, HttpServletResponse response) {
        List<CommentVo> list = iCommentService.queryList(bo);
        ExcelUtil.exportExcel(list, "评论管理", CommentVo.class, response);
    }

    /**
     * 获取评论管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("comment:comment:query")
    @GetMapping("/{id}")
    public R<CommentVo> getInfo(@NotNull(message = "主键不能为空")
                                @PathVariable Long id) {
        return R.ok(iCommentService.queryById(id));
    }

    /**
     * 新增评论管理
     */
    @SaCheckPermission("comment:comment:add")
    @Log(title = "评论管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CommentBo bo) {
        return toAjax(iCommentService.insertByBo(bo));
    }

    /**
     * 修改评论管理
     */
    @SaCheckPermission("comment:comment:edit")
    @Log(title = "评论管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CommentBo bo) {
        return toAjax(iCommentService.updateByBo(bo));
    }

    /**
     * 删除评论管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("comment:comment:remove")
    @Log(title = "评论管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iCommentService.deleteWithValidByIds(Arrays.asList(ids), true));
    }


    /**
     * 审核评论
     */
    @GetMapping("/audit")
    public R<Void> audit(Long id, Boolean status){
        return toAjax(iCommentService.auditComment(id,status));

    }


}
