package com.zhi.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.zhi.common.core.domain.BaseEntity;
import lombok.*;


/**
 * 分类管理对象 blog_category
 *
 * @author ftz
 * @date 2023-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("blog_category")
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *id
     */
    @TableId(value = "id" ,type = IdType.AUTO)
    private Long id;
    /**
     * 分类名
     */
    private String categoryName;

}
