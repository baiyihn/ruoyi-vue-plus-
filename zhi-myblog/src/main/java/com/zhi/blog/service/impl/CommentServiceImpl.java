package com.zhi.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhi.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.zhi.blog.domain.bo.CommentBo;
import com.zhi.blog.domain.vo.CommentVo;
import com.zhi.blog.domain.Comment;
import com.zhi.blog.mapper.CommentMapper;
import com.zhi.blog.service.ICommentService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 评论管理Service业务层处理
 *
 * @author ftz
 * @date 2023-01-10
 */
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements ICommentService {

    private final CommentMapper baseMapper;

    /**
     * 查询评论管理
     */
    @Override
    public CommentVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询评论管理列表
     */
    @Override
    public TableDataInfo<CommentVo> queryPageList(CommentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Comment> lqw = buildQueryWrapper(bo);
        Page<CommentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询评论管理列表
     */
    @Override
    public List<CommentVo> queryList(CommentBo bo) {
        LambdaQueryWrapper<Comment> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Comment> buildQueryWrapper(CommentBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Comment> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, Comment::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getCommentContent()), Comment::getCommentContent, bo.getCommentContent());
        lqw.eq(bo.getReplyUserId() != null, Comment::getReplyUserId, bo.getReplyUserId());
        lqw.eq(bo.getParentId() != null, Comment::getParentId, bo.getParentId());
        lqw.eq(bo.getType() != null, Comment::getType, bo.getType());
        lqw.eq(bo.getIsDelete() != null, Comment::getIsDelete, bo.getIsDelete());
        lqw.eq(bo.getIsReview() != null, Comment::getIsReview, bo.getIsReview());
        lqw.eq(bo.getTopicId() != null, Comment::getTopicId, bo.getTopicId());
        lqw.eq(StringUtils.isNotBlank(bo.getIpAddress()), Comment::getIpAddress, bo.getIpAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getIpSource()), Comment::getIpSource, bo.getIpSource());
        lqw.eq(bo.getState() != null, Comment::getState, bo.getState());
        return lqw;
    }

    /**
     * 新增评论管理
     */
    @Override
    public Boolean insertByBo(CommentBo bo) {
        Comment add = BeanUtil.toBean(bo, Comment.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改评论管理
     */
    @Override
    public Boolean updateByBo(CommentBo bo) {
        Comment update = BeanUtil.toBean(bo, Comment.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Comment entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除评论管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
