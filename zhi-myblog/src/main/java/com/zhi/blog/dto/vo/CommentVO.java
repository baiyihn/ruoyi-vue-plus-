package com.zhi.blog.dto.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 评论
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "评论")
public class CommentVO {

    /**
     * 评论用户id
     */
    private Long id;

    /**
     * 回复用户id
     */
    private Long replyUserId;

    /**
     * 评论主题id
     */
    private Long topicId;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    @ApiModelProperty(name = "commentContent", value = "评论内容", required = true, dataType = "String")
    private String commentContent;

    /**
     * 父评论id
     */
    @ApiModelProperty(name = "parentId", value = "评论父id", dataType = "Integer")
    private Long parentId;

    /**
     * 类型
     */
    @NotNull(message = "评论类型不能为空")
    @ApiModelProperty(name = "type", value = "评论类型", dataType = "Integer")
    private Integer type;

}
