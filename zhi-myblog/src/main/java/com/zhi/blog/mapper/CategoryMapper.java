package com.zhi.blog.mapper;

import com.zhi.blog.domain.Category;
import com.zhi.blog.domain.vo.CategoryVo;
import com.zhi.common.core.mapper.BaseMapperPlus;

/**
 * 分类管理Mapper接口
 *
 * @author ftz
 * @date 2023-01-07
 */
public interface CategoryMapper extends BaseMapperPlus<CategoryMapper, Category, CategoryVo> {

    /**
     * 根据id查询分类名称
     */
    String selectNameById(int id);


    /**
     * 根据名称查分类ID
     */
    Long selectIdByName(String name);



}
