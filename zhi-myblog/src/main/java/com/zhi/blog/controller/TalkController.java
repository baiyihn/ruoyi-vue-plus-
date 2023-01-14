package com.zhi.blog.controller;

import java.util.List;
import java.util.Arrays;

import cn.dev33.satoken.annotation.SaIgnore;
import com.zhi.blog.domain.vo.PageResult;
import com.zhi.blog.dto.TalkDTO;
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
import com.zhi.blog.domain.vo.TalkVo;
import com.zhi.blog.domain.bo.TalkBo;
import com.zhi.blog.service.ITalkService;
import com.zhi.common.core.page.TableDataInfo;

/**
 * 说说管理
 *
 * @author ftz
 * @date 2023-01-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/talk/talk")
public class TalkController extends BaseController {

    private final ITalkService iTalkService;


    /**
     * 查看博客首页说说
     *
     * @return {@link R<String>}
     */
    @ApiOperation(value = "查看首页说说")
    @SaIgnore
    @GetMapping("/home/talks")
    public R<List<String>> listHomeTalks() {
        return R.ok(iTalkService.listHomeTalks());
    }




    /**
     * 博客前端查看说说列表
     *
     * @return {@link R<TalkDTO>}
     */
    @SaIgnore
    @GetMapping("/talks")
    public R<PageResult<TalkDTO>> listTalks() {
        return R.ok(iTalkService.listTalks());
    }


    /**
     * 查询说说管理列表
     */
    @SaCheckPermission("talk:talk:list")
    @GetMapping("/list")
    public TableDataInfo<TalkVo> list(TalkBo bo, PageQuery pageQuery) {
        return iTalkService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出说说管理列表
     */
    @SaCheckPermission("talk:talk:export")
    @Log(title = "说说管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TalkBo bo, HttpServletResponse response) {
        List<TalkVo> list = iTalkService.queryList(bo);
        ExcelUtil.exportExcel(list, "说说管理", TalkVo.class, response);
    }

    /**
     * 获取说说管理详细信息
     *
     * @param id 主键
     */
//    @SaCheckPermission("talk:talk:query")
    @SaIgnore
    @GetMapping("/{id}")
    public R<TalkVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iTalkService.queryById(id));
    }

    /**
     * 新增说说管理
     */
    @SaCheckPermission("talk:talk:add")
    @Log(title = "说说管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TalkBo bo) {
        return toAjax(iTalkService.insertByBo(bo));
    }

    /**
     * 修改说说管理
     */
    @SaCheckPermission("talk:talk:edit")
    @Log(title = "说说管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TalkBo bo) {
        return toAjax(iTalkService.updateByBo(bo));
    }

    /**
     * 删除说说管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("talk:talk:remove")
    @Log(title = "说说管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iTalkService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
