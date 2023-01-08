package com.zhi.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.zhi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 分类管理对象 blog_category
 *
 * @author ftz
 * @date 2023-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_category")
public class Category extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 分类名
     */
    private String categoryName;

}
