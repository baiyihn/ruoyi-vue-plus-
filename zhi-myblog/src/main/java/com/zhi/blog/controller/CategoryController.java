package com.zhi.blog.controller;

import java.util.List;
import java.util.Arrays;

import cn.dev33.satoken.annotation.SaIgnore;
import com.zhi.blog.domain.vo.PageResult;
import com.zhi.blog.dto.CategoryDTO;
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
import com.zhi.blog.domain.vo.CategoryVo;
import com.zhi.blog.domain.bo.CategoryBo;
import com.zhi.blog.service.ICategoryService;
import com.zhi.common.core.page.TableDataInfo;

import static com.zhi.common.constant.blog.CommonConst.DEFAULTCATORTAGID;

/**
 * 分类管理
 *
 * @author ftz
 * @date 2023-01-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/category/category")
public class CategoryController extends BaseController {

    private final ICategoryService iCategoryService;

    /**
     * 查看前台分类列表
     *
     * @return {@link R<CategoryDTO>} 分类列表
     */
    @SaIgnore
    @ApiOperation(value = "查看分类列表")
    @GetMapping("/categories")
    public R<PageResult<CategoryDTO>> listCategories() {
        return R.ok(iCategoryService.listCategories());
    }


    /**
     * 查询分类管理列表
     */
    @SaCheckPermission("category:category:list")
    @GetMapping("/list")
    public TableDataInfo<CategoryVo> list(CategoryBo bo, PageQuery pageQuery) {
        return iCategoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出分类管理列表
     */
    @SaCheckPermission("category:category:export")
    @Log(title = "分类管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CategoryBo bo, HttpServletResponse response) {
        List<CategoryVo> list = iCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "分类管理", CategoryVo.class, response);
    }

    /**
     * 获取分类管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("category:category:query")
    @GetMapping("/{id}")
    public R<CategoryVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iCategoryService.queryById(id));
    }

    /**
     * 新增分类管理
     */
    @SaCheckPermission("category:category:add")
    @Log(title = "分类管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CategoryBo bo) {
        return toAjax(iCategoryService.insertByBo(bo));
    }

    /**
     * 修改分类管理
     */
    @SaCheckPermission("category:category:edit")
    @Log(title = "分类管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CategoryBo bo) {
        return toAjax(iCategoryService.updateByBo(bo));
    }

    /**
     * 删除分类管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("category:category:remove")
    @Log(title = "分类管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        if (Arrays.asList(ids).contains(DEFAULTCATORTAGID)){
            return R.fail("不允许删除默认分类");
        }
        return toAjax(iCategoryService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
