package com.zhi.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.zhi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 页面管理对象 blog_page
 *
 * @author ftz
 * @date 2023-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_page")
public class BlogPage extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 页面id
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 页面名
     */
    private String pageName;
    /**
     * 页面标签
     */
    private String pageLabel;
    /**
     * 页面封面
     */
    private String pageCover;

}
