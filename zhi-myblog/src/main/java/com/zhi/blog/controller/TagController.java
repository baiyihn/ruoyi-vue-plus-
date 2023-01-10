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
import com.zhi.blog.domain.vo.TagVo;
import com.zhi.blog.domain.bo.TagBo;
import com.zhi.blog.service.ITagService;
import com.zhi.common.core.page.TableDataInfo;

/**
 * 标签管理
 *
 * @author ftz
 * @date 2023-01-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/tag/tag")
public class TagController extends BaseController {

    private final ITagService iTagService;

    /**
     * 查询标签管理列表
     */
    @SaCheckPermission("tag:tag:list")
    @GetMapping("/list")
    public TableDataInfo<TagVo> list(TagBo bo, PageQuery pageQuery) {
        return iTagService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出标签管理列表
     */
    @SaCheckPermission("tag:tag:export")
    @Log(title = "标签管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TagBo bo, HttpServletResponse response) {
        List<TagVo> list = iTagService.queryList(bo);
        ExcelUtil.exportExcel(list, "标签管理", TagVo.class, response);
    }

    /**
     * 获取标签管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("tag:tag:query")
    @GetMapping("/{id}")
    public R<TagVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iTagService.queryById(id));
    }

    /**
     * 新增标签管理
     */
    @SaCheckPermission("tag:tag:add")
    @Log(title = "标签管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TagBo bo) {
        return toAjax(iTagService.insertByBo(bo));
    }

    /**
     * 修改标签管理
     */
    @SaCheckPermission("tag:tag:edit")
    @Log(title = "标签管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TagBo bo) {
        return toAjax(iTagService.updateByBo(bo));
    }

    /**
     * 删除标签管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("tag:tag:remove")
    @Log(title = "标签管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return iTagService.deleteWithValidByIds(Arrays.asList(ids), true);
    }
}
