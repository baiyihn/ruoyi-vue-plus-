package com.zhi.blog.service;

import com.zhi.blog.domain.Talk;
import com.zhi.blog.domain.vo.TalkVo;
import com.zhi.blog.domain.bo.TalkBo;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 说说管理Service接口
 *
 * @author ftz
 * @date 2023-01-13
 */
public interface ITalkService {

    /**
     * 查询说说管理
     */
    TalkVo queryById(Long id);

    /**
     * 查询说说管理列表
     */
    TableDataInfo<TalkVo> queryPageList(TalkBo bo, PageQuery pageQuery);

    /**
     * 查询说说管理列表
     */
    List<TalkVo> queryList(TalkBo bo);

    /**
     * 新增说说管理
     */
    Boolean insertByBo(TalkBo bo);

    /**
     * 修改说说管理
     */
    Boolean updateByBo(TalkBo bo);

    /**
     * 校验并批量删除说说管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
