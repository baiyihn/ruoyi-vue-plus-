package com.zhi.blog.domain.bo;

import com.zhi.common.core.domain.BaseEntity;
import com.zhi.common.core.validate.AddGroup;
import com.zhi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 标签管理业务对象 blog_tag
 *
 * @author ftz
 * @date 2023-01-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class TagBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 标签名
     */
    @NotBlank(message = "标签名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tagName;


}
