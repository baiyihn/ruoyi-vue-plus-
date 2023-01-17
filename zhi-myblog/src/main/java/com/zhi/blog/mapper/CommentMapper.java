package com.zhi.blog.mapper;

import com.zhi.blog.domain.Comment;
import com.zhi.blog.domain.vo.CommentVo;
import com.zhi.blog.dto.CommentCountDTO;
import com.zhi.blog.dto.CommentDTO;
import com.zhi.blog.dto.ReplyCountDTO;
import com.zhi.blog.dto.ReplyDTO;
import com.zhi.blog.dto.vo.CommentVO;
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
     * 查看评论
     *
     * @param current   当前页码
     * @param size      大小
     * @param commentVO 评论信息
     * @return 评论集合
     */

    List<CommentDTO> listComments(@Param("current") Long current, @Param("size") Long size, @Param("commentVO") CommentVO commentVO);

    /**
     * 查看评论id集合下的回复
     *
     * @param commentIdList 评论id集合
     * @return 回复集合
     */
    List<ReplyDTO> listReplies(@Param("commentIdList") List<Long> commentIdList);

    /**
     * 根据评论id查询回复总量
     *
     * @param commentIdList 评论id集合
     * @return 回复数量
     */
    List<ReplyCountDTO> listReplyCountByCommentId(@Param("commentIdList") List<Long> commentIdList);


    /**
     * 根据id修改评论状态
     */
    @Update("update blog_comment set state = #{status} where id = #{id} ")
    int UpdateCommentStatusById(@Param("id") Long id , @Param("status") int status);


    /**
     * 根据评论主题id获取评论量
     *
     * @param topicIdList 说说id列表
     * @return {@link List<CommentCountDTO>}说说评论量
     */
    List<CommentCountDTO> listCommentCountByTopicIds(@Param("topicIdList") List<Integer> topicIdList);


    /**
     * 根据id查询用户昵称
     */
    @Select("select u.nick_name from sys_user u  where u.user_id = #{id}")
    String getNickNameById(Long id);

    /**
     * 根据评论主题获取文章标题
     */
    @Select("select a.article_title as articleTitle from blog_article a where a.id = #{id}")
    String getArticleTitleByTopic(Long id);

}
