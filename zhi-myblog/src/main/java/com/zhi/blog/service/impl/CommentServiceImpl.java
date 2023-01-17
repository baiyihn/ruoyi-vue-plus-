package com.zhi.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhi.blog.domain.vo.PageResult;
import com.zhi.blog.domain.vo.WebsiteConfigVO;
import com.zhi.blog.dto.CommentDTO;
import com.zhi.blog.dto.ReplyCountDTO;
import com.zhi.blog.dto.ReplyDTO;
import com.zhi.blog.dto.vo.CommentVO;
import com.zhi.blog.service.IBlogInfoService;
import com.zhi.blog.service.IWebsiteConfigService;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhi.common.utils.StringUtils;
import com.zhi.common.utils.blog.HTMLUtils;
import com.zhi.common.utils.blog.PageUtils;
import com.zhi.system.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.zhi.blog.domain.bo.CommentBo;
import com.zhi.blog.domain.vo.CommentVo;
import com.zhi.blog.domain.Comment;
import com.zhi.blog.mapper.CommentMapper;
import com.zhi.blog.service.ICommentService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.zhi.common.constant.blog.CommonConst.*;
import static com.zhi.common.constant.blog.RedisPrefixConst.COMMENT_LIKE_COUNT;

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

    @Resource
    private RedisService redisService;


    @Resource
    private IWebsiteConfigService websiteConfigService;



    /**
     * 查询博客前台评论
     * @param commentVO 评论信息
     */
    @Override
    public PageResult<CommentDTO> listComments(CommentVO commentVO) {
        // 查询评论量
        Long commentCount = baseMapper.selectCount(new LambdaQueryWrapper<Comment>()
            .eq(Objects.nonNull(commentVO.getTopicId()), Comment::getTopicId, commentVO.getTopicId())
            .eq(Comment::getType, commentVO.getType())
            .isNull(Comment::getParentId)
            .eq(Comment::getState, STATE));
        if (commentCount == 0) {
            return new PageResult<>();
        }
        // 分页查询评论数据
        List<CommentDTO> commentDTOList = baseMapper.listComments(PageUtils.getLimitCurrent(), PageUtils.getSize(), commentVO);
        if (CollectionUtils.isEmpty(commentDTOList)) {
            return new PageResult<>();
        }
        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = redisService.hGetAll(COMMENT_LIKE_COUNT);
        // 提取评论id集合
        List<Long> commentIdList = commentDTOList.stream()
            .map((CommentDTO::getId))
            .collect(Collectors.toList());
        // 根据评论id集合查询回复数据
        List<ReplyDTO> replyDTOList = baseMapper.listReplies(commentIdList);
        // 封装回复点赞量
        replyDTOList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getId().toString())));
        // 根据评论id分组回复数据
        Map<Long, List<ReplyDTO>> replyMap = replyDTOList.stream()
            .collect(Collectors.groupingBy(ReplyDTO::getParentId));
        // 根据评论id查询回复量
        Map<Long, Integer> replyCountMap = baseMapper.listReplyCountByCommentId(commentIdList)
            .stream().collect(Collectors.toMap(ReplyCountDTO::getCommentId, ReplyCountDTO::getReplyCount));
        // 封装评论数据
        commentDTOList.forEach(item -> {
            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
            item.setReplyDTOList(replyMap.get(item.getId()));
            item.setReplyCount(replyCountMap.get(item.getId()));
        });
        return new PageResult<>(commentDTOList, Integer.parseInt(String.valueOf(commentCount)));
    }


    /**
     * 博客前台添加评论
     * @param commentVO 评论对象
     */
    @Override
    public void saveComment(CommentVO commentVO) {
        // 判断是否需要审核
        WebsiteConfigVO websiteConfig = websiteConfigService.getWebsiteConfig();
        Integer isReview = websiteConfig.getIsCommentReview();
        // 过滤标签
        commentVO.setCommentContent(HTMLUtils.deleteTag(commentVO.getCommentContent()));
        Comment comment = Comment.builder()
            .userId(commentVO.getId())
            .replyUserId(commentVO.getReplyUserId())
            .topicId(commentVO.getTopicId())
            .commentContent(commentVO.getCommentContent())
            .parentId(commentVO.getParentId())
            .type(commentVO.getType())
            .state(isReview == TRUE ? 2 : 1)
            .build();
            baseMapper.insert(comment);
        // 判断是否开启邮箱通知,通知用户
//        if (websiteConfig.getIsEmailNotice().equals(TRUE)) {
//            CompletableFuture.runAsync(() -> notice(comment));
//        }
    }


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
        for(CommentVo commentVo :result.getRecords()){
            commentVo.setNickname(baseMapper.getNickNameById(commentVo.getUserId()));
            commentVo.setArticleTitle(baseMapper.getArticleTitleByTopic(commentVo.getTopicId()));
            commentVo.setReplyUserName(baseMapper.getNickNameById(commentVo.getReplyUserId()));
        }
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



    /**
     * 审核评论
     */
    @Override
    public Boolean auditComment(Long id, Boolean status) {

        return baseMapper.UpdateCommentStatusById(id,status?1:3)>0;

    }


}
