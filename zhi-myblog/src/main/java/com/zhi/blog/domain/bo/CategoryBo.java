package com.zhi.blog.domain.bo;

import com.zhi.common.core.domain.BaseEntity;
import com.zhi.common.core.validate.AddGroup;
import com.zhi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 分类管理业务对象 blog_category
 *
 * @author ftz
 * @date 2023-01-07
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 分类名
     */
    @NotBlank(message = "分类名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String categoryName;


}
