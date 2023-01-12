package com.zhi.blog.domain.bo;

import com.zhi.common.core.domain.BaseEntity;
import com.zhi.common.core.validate.AddGroup;
import com.zhi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 友链管理业务对象 blog_friend_link
 *
 * @author ftz
 * @date 2023-01-12
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class FriendLinkBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 链接名
     */
    @NotBlank(message = "链接名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String linkName;

    /**
     * 链接头像
     */
    @NotBlank(message = "链接头像不能为空", groups = { AddGroup.class, EditGroup.class })
    private String linkAvatar;

    /**
     * 链接地址
     */
    @NotBlank(message = "链接地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String linkAddress;

    /**
     * 链接介绍
     */
    @NotBlank(message = "链接介绍不能为空", groups = { AddGroup.class, EditGroup.class })
    private String linkIntro;


}
