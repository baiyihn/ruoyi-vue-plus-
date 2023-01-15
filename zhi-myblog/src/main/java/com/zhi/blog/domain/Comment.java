package com.zhi.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.zhi.common.core.domain.BaseEntity;
import lombok.*;


/**
 * 评论管理对象 blog_comment
 *
 * @author ftz
 * @date 2023-01-10
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName("blog_comment")
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id" ,type = IdType.AUTO)
    private Long id;
    /**
     * 评论用户Id
     */
    private Long userId;
    /**
     * 评论内容
     */
    private String commentContent;
    /**
     * 回复用户id
     */
    private Long replyUserId;
    /**
     * 父评论id
     */
    private Long parentId;
    /**
     * 评论类型 1.文章 2.友链 3.说说
     */
    private Integer type;
    /**
     * 是否删除  0否 1是
     */
    private Integer isDelete;
    /**
     * 评论主题id
     */
    private Integer topicId;
    /**
     * 评论ip
     */
    private String ipAddress;
    /**
     * 真实地址
     */
    private String ipSource;
    /**
     * 评论状态
     */
    private Integer state;

}
