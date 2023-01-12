package com.zhi.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhi.common.utils.BeanCopyUtils;
import com.zhi.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.zhi.blog.domain.bo.BlogPageBo;
import com.zhi.blog.domain.vo.BlogPageVo;
import com.zhi.blog.domain.BlogPage;
import com.zhi.blog.mapper.BlogPageMapper;
import com.zhi.blog.service.IBlogPageService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 页面管理Service业务层处理
 *
 * @author ftz
 * @date 2023-01-12
 */
@RequiredArgsConstructor
@Service
public class BlogPageServiceImpl implements IBlogPageService {

    private final BlogPageMapper baseMapper;

    /**
     * 查询页面管理
     */
    @Override
    public BlogPageVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询页面管理列表
     */
    @Override
    public TableDataInfo<BlogPageVo> queryPageList(BlogPageBo bo, PageQuery pageQuery) {
        List<BlogPage> blogPages = baseMapper.BlogPageList();
        return TableDataInfo.build(BeanCopyUtils.copyList(blogPages,BlogPageVo.class));
    }

    /**
     * 查询页面管理列表
     */
    @Override
    public List<BlogPageVo> queryList(BlogPageBo bo) {
        List<BlogPage> blogPages = baseMapper.BlogPageList();
        return BeanCopyUtils.copyList(blogPages,BlogPageVo.class);
    }

    private LambdaQueryWrapper<BlogPage> buildQueryWrapper(BlogPageBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BlogPage> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getPageName()), BlogPage::getPageName, bo.getPageName());
        lqw.eq(StringUtils.isNotBlank(bo.getPageLabel()), BlogPage::getPageLabel, bo.getPageLabel());
        lqw.eq(StringUtils.isNotBlank(bo.getPageCover()), BlogPage::getPageCover, bo.getPageCover());
        return lqw;
    }

    /**
     * 新增页面管理
     */
    @Override
    public Boolean insertByBo(BlogPageBo bo) {
        BlogPage add = BeanUtil.toBean(bo, BlogPage.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改页面管理
     */
    @Override
    public Boolean updateByBo(BlogPageBo bo) {
        BlogPage update = BeanUtil.toBean(bo, BlogPage.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(BlogPage entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除页面管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
