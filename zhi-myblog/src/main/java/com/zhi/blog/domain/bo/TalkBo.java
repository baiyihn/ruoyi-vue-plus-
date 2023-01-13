package com.zhi.blog.domain.bo;

import com.zhi.common.core.domain.BaseEntity;
import com.zhi.common.core.validate.AddGroup;
import com.zhi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 说说管理业务对象 blog_talk
 *
 * @author ftz
 * @date 2023-01-13
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class TalkBo extends BaseEntity {

    /**
     * 说说id
     */
    @NotNull(message = "说说id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 说说内容
     */
    @NotBlank(message = "说说内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 图片
     */
    private String images;

    /**
     * 是否置顶
     */
    private Integer isTop;


}
