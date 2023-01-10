package com.zhi.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zhi.common.core.domain.R;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.zhi.blog.domain.bo.TagBo;
import com.zhi.blog.domain.vo.TagVo;
import com.zhi.blog.domain.Tag;
import com.zhi.blog.mapper.TagMapper;
import com.zhi.blog.service.ITagService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 标签管理Service业务层处理
 *
 * @author ftz
 * @date 2023-01-08
 */
@RequiredArgsConstructor
@Service
public class TagServiceImpl implements ITagService {

    private final TagMapper baseMapper;

    /**
     * 查询标签管理
     */
    @Override
    public TagVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询标签管理列表
     */
    @Override
    public TableDataInfo<TagVo> queryPageList(TagBo bo, PageQuery pageQuery) {
//        LambdaQueryWrapper<Tag> lqw = buildQueryWrapper(bo);
//        Page<TagVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<TagVo> tagVos = baseMapper.tagList();
        return TableDataInfo.build(tagVos);
    }

    /**
     * 查询标签管理列表
     */
    @Override
    public List<TagVo> queryList(TagBo bo) {
        LambdaQueryWrapper<Tag> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Tag> buildQueryWrapper(TagBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Tag> lqw = Wrappers.lambdaQuery();
        lqw.like(bo.getTagName() != null, Tag::getTagName, bo.getTagName());
        return lqw;
    }

    /**
     * 新增标签管理
     */
    @Override
    public Boolean insertByBo(TagBo bo) {
        Tag add = BeanUtil.toBean(bo, Tag.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改标签管理
     */
    @Override
    public Boolean updateByBo(TagBo bo) {
        Tag update = BeanUtil.toBean(bo, Tag.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Tag entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除标签管理
     */
    @Override
    public R deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            // 判断是否有文章目前是这个标签
            for(Long id:ids){
                int i = baseMapper.ArticleTag(id);
                if (i!=0){
                    return R.fail("已有文章对应此标签，无法删除");
                }
            }
        }
        baseMapper.deleteBatchIds(ids);
        return  R.ok();
    }
}
