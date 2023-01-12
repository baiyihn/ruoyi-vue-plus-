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
import com.zhi.blog.domain.vo.BlogPageVo;
import com.zhi.blog.domain.bo.BlogPageBo;
import com.zhi.blog.service.IBlogPageService;
import com.zhi.common.core.page.TableDataInfo;

/**
 * 页面管理
 *
 * @author ftz
 * @date 2023-01-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/page/page")
public class BlogPageController extends BaseController {

    private final IBlogPageService iBlogPageService;

    /**
     * 查询页面管理列表
     */
    @SaCheckPermission("page:page:list")
    @GetMapping("/list")
    public TableDataInfo<BlogPageVo> list(BlogPageBo bo, PageQuery pageQuery) {
        return iBlogPageService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出页面管理列表
     */
    @SaCheckPermission("page:page:export")
    @Log(title = "页面管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(BlogPageBo bo, HttpServletResponse response) {
        List<BlogPageVo> list = iBlogPageService.queryList(bo);
        ExcelUtil.exportExcel(list, "页面管理", BlogPageVo.class, response);
    }

    /**
     * 获取页面管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("page:page:query")
    @GetMapping("/{id}")
    public R<BlogPageVo> getInfo(@NotNull(message = "主键不能为空")
                                 @PathVariable Long id) {
        return R.ok(iBlogPageService.queryById(id));
    }

    /**
     * 新增页面管理
     */
    @SaCheckPermission("page:page:add")
    @Log(title = "页面管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody BlogPageBo bo) {
        return toAjax(iBlogPageService.insertByBo(bo));
    }

    /**
     * 修改页面管理
     */
    @SaCheckPermission("page:page:edit")
    @Log(title = "页面管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BlogPageBo bo) {
        return toAjax(iBlogPageService.updateByBo(bo));
    }

    /**
     * 删除页面管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("page:page:remove")
    @Log(title = "页面管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iBlogPageService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
