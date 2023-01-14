package com.zhi.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zhi.blog.domain.vo.PageResult;
import com.zhi.blog.dto.CategoryDTO;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhi.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.zhi.blog.domain.bo.CategoryBo;
import com.zhi.blog.domain.vo.CategoryVo;
import com.zhi.blog.domain.Category;
import com.zhi.blog.mapper.CategoryMapper;
import com.zhi.blog.service.ICategoryService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 分类管理Service业务层处理
 *
 * @author ftz
 * @date 2023-01-07
 */
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryMapper baseMapper;

    /**
     * 查看前台分类
     * @return
     */
    @Override
    public PageResult<CategoryDTO> listCategories() {
        return new PageResult<>(baseMapper.listCategoryDTO(), Integer.parseInt(String.valueOf(baseMapper.selectCount(null))));
    }

    /**
     * 查询分类管理
     */
    @Override
    public CategoryVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询分类管理列表
     */
    @Override
    public TableDataInfo<CategoryVo> queryPageList(CategoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Category> lqw = buildQueryWrapper(bo);
        Page<CategoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询分类管理列表
     */
    @Override
    public List<CategoryVo> queryList(CategoryBo bo) {
        LambdaQueryWrapper<Category> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Category> buildQueryWrapper(CategoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Category> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getCategoryName()), Category::getCategoryName, bo.getCategoryName());
        return lqw;
    }

    /**
     * 新增分类管理
     */
    @Override
    public Boolean insertByBo(CategoryBo bo) {
        Category add = BeanUtil.toBean(bo, Category.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改分类管理
     */
    @Override
    public Boolean updateByBo(CategoryBo bo) {
        Category update = BeanUtil.toBean(bo, Category.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Category entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除分类管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
