package com.zhi.blog.domain.bo;

import com.zhi.common.core.domain.BaseEntity;
import com.zhi.common.core.validate.AddGroup;
import com.zhi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 页面管理业务对象 blog_page
 *
 * @author ftz
 * @date 2023-01-12
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class BlogPageBo extends BaseEntity {

    /**
     * 页面id
     */
    @NotNull(message = "页面id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 页面名
     */
    @NotBlank(message = "页面名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pageName;

    /**
     * 页面标签
     */
    @NotBlank(message = "页面标签不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pageLabel;

    /**
     * 页面封面
     */
    @NotBlank(message = "页面封面不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pageCover;


}
