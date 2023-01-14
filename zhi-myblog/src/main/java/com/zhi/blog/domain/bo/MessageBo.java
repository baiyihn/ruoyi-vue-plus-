package com.zhi.blog.domain.bo;

import com.zhi.common.core.domain.BaseEntity;
import com.zhi.common.core.validate.AddGroup;
import com.zhi.common.core.validate.EditGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;


/**
 * 留言管理业务对象 blog_message
 *
 * @author ftz
 * @date 2023-01-11
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nickname;

    /**
     * 头像
     */
    @NotBlank(message = "头像不能为空", groups = { AddGroup.class, EditGroup.class })
    private String avatar;

    /**
     * 留言内容
     */
    @NotBlank(message = "留言内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String messageContent;

    /**
     * 用户ip
     */
    private String ipAddress;

    /**
     * 用户地址
     */
    private String ipSource;

    /**
     * 弹幕速度
     */
    @NotNull(message = "弹幕速度不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer time;

    /**
     * 是否审核
     */
    @NotBlank(message = "是否审核不能为空", groups = { AddGroup.class, EditGroup.class })
    private String isReview;


}
