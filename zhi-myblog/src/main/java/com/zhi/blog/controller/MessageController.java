package com.zhi.blog.controller;

import java.util.List;
import java.util.Arrays;

import cn.dev33.satoken.annotation.SaIgnore;
import com.zhi.blog.dto.MessageDTO;
import com.zhi.blog.dto.vo.MessageVO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
import com.zhi.blog.domain.vo.MessageVo;
import com.zhi.blog.domain.bo.MessageBo;
import com.zhi.blog.service.IMessageService;
import com.zhi.common.core.page.TableDataInfo;

/**
 * 留言管理
 *
 * @author ftz
 * @date 2023-01-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/message/message")
public class MessageController extends BaseController {

    private final IMessageService iMessageService;

    /**
     * 前台添加留言
     */
    @SaIgnore
    @ApiOperation(value = "添加留言")
    @PostMapping("/messages")
    public R<?> saveMessage(@Valid @RequestBody MessageVO messageVO) {
        iMessageService.saveMessage(messageVO);
        return R.ok();
    }

    /**
     * 查看前台留言列表
     *
     * @return {@link R<MessageDTO>} 留言列表
     */
    @SaIgnore
    @ApiOperation(value = "查看留言列表")
    @GetMapping("/messages")
    public R<List<MessageDTO>> listMessages() {
        return R.ok(iMessageService.listMessages());
    }

    /**
     * 查询留言管理列表
     */
    @SaCheckPermission("message:message:list")
    @GetMapping("/list")
    public TableDataInfo<MessageVo> list(MessageBo bo, PageQuery pageQuery) {
        return iMessageService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出留言管理列表
     */
    @SaCheckPermission("message:message:export")
    @Log(title = "留言管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MessageBo bo, HttpServletResponse response) {
        List<MessageVo> list = iMessageService.queryList(bo);
        ExcelUtil.exportExcel(list, "留言管理", MessageVo.class, response);
    }

    /**
     * 获取留言管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("message:message:query")
    @GetMapping("/{id}")
    public R<MessageVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iMessageService.queryById(id));
    }

    /**
     * 新增留言管理
     */
    @SaCheckPermission("message:message:add")
    @Log(title = "留言管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MessageBo bo) {
        return toAjax(iMessageService.insertByBo(bo));
    }

    /**
     * 修改留言管理
     */
    @SaCheckPermission("message:message:edit")
    @Log(title = "留言管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MessageBo bo) {
        return toAjax(iMessageService.updateByBo(bo));
    }

    /**
     * 删除留言管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("message:message:remove")
    @Log(title = "留言管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iMessageService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 审核留言
     */
    @GetMapping("/audit")
    public R<Void> audit(Long id,Boolean review){
        return toAjax(iMessageService.auditMessage(id,review));

    }
}
