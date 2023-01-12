package com.zhi.blog.service;

import com.zhi.blog.domain.vo.BlogPageVo;
import com.zhi.blog.domain.bo.BlogPageBo;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 页面管理Service接口
 *
 * @author ftz
 * @date 2023-01-12
 */
public interface IBlogPageService {

    /**
     * 查询页面管理
     */
    BlogPageVo queryById(Long id);

    /**
     * 查询页面管理列表
     */
    TableDataInfo<BlogPageVo> queryPageList(BlogPageBo bo, PageQuery pageQuery);

    /**
     * 查询页面管理列表
     */
    List<BlogPageVo> queryList(BlogPageBo bo);

    /**
     * 新增页面管理
     */
    Boolean insertByBo(BlogPageBo bo);

    /**
     * 修改页面管理
     */
    Boolean updateByBo(BlogPageBo bo);

    /**
     * 校验并批量删除页面管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
