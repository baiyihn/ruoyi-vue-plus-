package com.zhi.blog.service;

import com.zhi.blog.domain.Message;
import com.zhi.blog.domain.vo.MessageVo;
import com.zhi.blog.domain.bo.MessageBo;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 留言管理Service接口
 *
 * @author ftz
 * @date 2023-01-11
 */
public interface IMessageService {

    /**
     * 查询留言管理
     */
    MessageVo queryById(Long id);

    /**
     * 查询留言管理列表
     */
    TableDataInfo<MessageVo> queryPageList(MessageBo bo, PageQuery pageQuery);

    /**
     * 查询留言管理列表
     */
    List<MessageVo> queryList(MessageBo bo);

    /**
     * 新增留言管理
     */
    Boolean insertByBo(MessageBo bo);

    /**
     * 修改留言管理
     */
    Boolean updateByBo(MessageBo bo);

    /**
     * 校验并批量删除留言管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 审核留言
     */
    Boolean auditMessage(Long id, Boolean review);
}
