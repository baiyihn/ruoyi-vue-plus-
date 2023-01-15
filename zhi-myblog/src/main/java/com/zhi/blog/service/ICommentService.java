package com.zhi.blog.service;


import com.zhi.blog.domain.vo.CommentVo;
import com.zhi.blog.domain.bo.CommentBo;
import com.zhi.blog.domain.vo.PageResult;
import com.zhi.blog.dto.CommentDTO;
import com.zhi.blog.dto.vo.CommentVO;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 评论管理Service接口
 *
 * @author ftz
 * @date 2023-01-10
 */
public interface ICommentService {


    /**
     * 查询博客前台评论
     * @param commentVO 评论信息
     */
    PageResult<CommentDTO> listComments(CommentVO commentVO);


    /**
     * 博客前台添加评论
     *
     * @param commentVO 评论对象
     */
    void saveComment(CommentVO commentVO);

    /**
     * 查询评论管理
     */
    CommentVo queryById(Long id);

    /**
     * 查询评论管理列表
     */
    TableDataInfo<CommentVo> queryPageList(CommentBo bo, PageQuery pageQuery);

    /**
     * 查询评论管理列表
     */
    List<CommentVo> queryList(CommentBo bo);

    /**
     * 新增评论管理
     */
    Boolean insertByBo(CommentBo bo);

    /**
     * 修改评论管理
     */
    Boolean updateByBo(CommentBo bo);

    /**
     * 校验并批量删除评论管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


    /**
     * 审核评论
     */
    Boolean auditComment(Long id,Boolean type);
}
