package com.zhi.blog.service;

import com.zhi.blog.domain.Category;
import com.zhi.blog.domain.vo.CategoryVo;
import com.zhi.blog.domain.bo.CategoryBo;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 分类管理Service接口
 *
 * @author ftz
 * @date 2023-01-07
 */
public interface ICategoryService {

    /**
     * 查询分类管理
     */
    CategoryVo queryById(Long id);

    /**
     * 查询分类管理列表
     */
    TableDataInfo<CategoryVo> queryPageList(CategoryBo bo, PageQuery pageQuery);

    /**
     * 查询分类管理列表
     */
    List<CategoryVo> queryList(CategoryBo bo);

    /**
     * 新增分类管理
     */
    Boolean insertByBo(CategoryBo bo);

    /**
     * 修改分类管理
     */
    Boolean updateByBo(CategoryBo bo);

    /**
     * 校验并批量删除分类管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
