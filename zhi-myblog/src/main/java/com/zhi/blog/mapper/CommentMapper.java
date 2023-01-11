package com.zhi.blog.mapper;

import com.zhi.blog.domain.Comment;
import com.zhi.blog.domain.vo.CommentVo;
import com.zhi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 评论管理Mapper接口
 *
 * @author ftz
 * @date 2023-01-10
 */
public interface CommentMapper extends BaseMapperPlus<CommentMapper, Comment, CommentVo> {


    /**
     * 根据id修改评论状态
     */
    @Update("update blog_comment set state = #{status} where id = #{id} ")
    int UpdateCommentStatusById(@Param("id") Long id , @Param("status") int status);

    /**
     * 查询全部评论
     */
    @Select("select c.*," +
        "u.nick_name," +
        "u1.nick_name as replyUserName ," +
        "a.article_title as articleTitle  " +
        "from blog_comment c " +
        "left join sys_user u on c.user_id = u.user_id " +
        "left join sys_user u1 on c.reply_user_id = u1.user_id " +
        "left join blog_article a on c.topic_id = a.id")
     List<CommentVo> queryCommentList();

}
