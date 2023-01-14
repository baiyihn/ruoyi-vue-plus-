package com.zhi.blog.controller;

import java.util.List;
import java.util.Arrays;

import cn.dev33.satoken.annotation.SaIgnore;
import com.zhi.blog.dto.FriendLinkDTO;
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
import com.zhi.blog.domain.vo.FriendLinkVo;
import com.zhi.blog.domain.bo.FriendLinkBo;
import com.zhi.blog.service.IFriendLinkService;
import com.zhi.common.core.page.TableDataInfo;

/**
 * 友链管理
 *
 * @author ftz
 * @date 2023-01-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/friendLink/friendLink")
public class FriendLinkController extends BaseController {

    private final IFriendLinkService iFriendLinkService;

    /**
     * 查看前台友链列表
     *
     * @return {@link R<FriendLinkDTO>} 友链列表
     */
    @SaIgnore
    @ApiOperation(value = "查看友链列表")
    @GetMapping("/links")
    public R<List<FriendLinkDTO>> listFriendLinks() {
        return R.ok(iFriendLinkService.listFriendLinks());
    }

    /**
     * 查询友链管理列表
     */
    @SaCheckPermission("friendLink:friendLink:list")
    @GetMapping("/list")
    public TableDataInfo<FriendLinkVo> list(FriendLinkBo bo, PageQuery pageQuery) {
        return iFriendLinkService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出友链管理列表
     */
    @SaCheckPermission("friendLink:friendLink:export")
    @Log(title = "友链管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FriendLinkBo bo, HttpServletResponse response) {
        List<FriendLinkVo> list = iFriendLinkService.queryList(bo);
        ExcelUtil.exportExcel(list, "友链管理", FriendLinkVo.class, response);
    }

    /**
     * 获取友链管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("friendLink:friendLink:query")
    @GetMapping("/{id}")
    public R<FriendLinkVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iFriendLinkService.queryById(id));
    }

    /**
     * 新增友链管理
     */
    @SaCheckPermission("friendLink:friendLink:add")
    @Log(title = "友链管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FriendLinkBo bo) {
        return toAjax(iFriendLinkService.insertByBo(bo));
    }

    /**
     * 修改友链管理
     */
    @SaCheckPermission("friendLink:friendLink:edit")
    @Log(title = "友链管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FriendLinkBo bo) {
        return toAjax(iFriendLinkService.updateByBo(bo));
    }

    /**
     * 删除友链管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("friendLink:friendLink:remove")
    @Log(title = "友链管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iFriendLinkService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
