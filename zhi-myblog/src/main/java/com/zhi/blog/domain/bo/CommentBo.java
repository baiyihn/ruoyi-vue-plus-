package com.zhi.blog.domain.bo;

import com.zhi.common.core.domain.BaseEntity;
import com.zhi.common.core.validate.AddGroup;
import com.zhi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 评论管理业务对象 blog_comment
 *
 * @author ftz
 * @date 2023-01-10
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CommentBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 评论用户Id
     */
    @NotNull(message = "评论用户Id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String commentContent;

    /**
     * 回复用户id
     */
    @NotNull(message = "回复用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long replyUserId;

    /**
     * 父评论id
     */
    @NotNull(message = "父评论id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long parentId;

    /**
     * 评论类型 1.文章 2.友链 3.说说
     */
    @NotNull(message = "评论类型 1.文章 2.友链 3.说说不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer type;

    /**
     * 是否删除  0否 1是
     */
    @NotNull(message = "是否删除  0否 1是不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer isDelete;

    /**
     * 是否审核
     */
    @NotNull(message = "是否审核不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer isReview;

    /**
     * 评论主题id
     */
    @NotNull(message = "评论主题id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long topicId;

    /**
     * 评论ip
     */
    @NotBlank(message = "评论ip不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ipAddress;

    /**
     * 真实地址
     */
    @NotBlank(message = "真实地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ipSource;

    /**
     * 评论状态
     */
    @NotNull(message = "评论状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer state;


}
