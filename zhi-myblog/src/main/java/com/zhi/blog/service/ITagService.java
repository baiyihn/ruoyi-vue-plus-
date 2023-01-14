package com.zhi.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhi.blog.domain.Tag;
import com.zhi.blog.domain.vo.PageResult;
import com.zhi.blog.domain.vo.TagVo;
import com.zhi.blog.domain.bo.TagBo;
import com.zhi.blog.dto.TagDTO;
import com.zhi.common.core.domain.R;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 标签管理Service接口
 *
 * @author ftz
 * @date 2023-01-08
 */
public interface ITagService extends IService<Tag>  {

    /**
     * 查询前台标签列表
     *
     * @return 标签列表
     */
    PageResult<TagDTO> listTags();

    /**
     * 查询标签管理
     */
    TagVo queryById(Long id);

    /**
     * 查询标签管理列表
     */
    TableDataInfo<TagVo> queryPageList(TagBo bo, PageQuery pageQuery);

    /**
     * 查询标签管理列表
     */
    List<TagVo> queryList(TagBo bo);

    /**
     * 新增标签管理
     */
    Boolean insertByBo(TagBo bo);

    /**
     * 修改标签管理
     */
    Boolean updateByBo(TagBo bo);

    /**
     * 校验并批量删除标签管理信息
     */
    R deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
