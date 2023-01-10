package com.zhi.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.zhi.common.core.domain.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 标签管理对象 blog_tag
 *
 * @author ftz
 * @date 2023-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_tag")
@Builder
public class Tag extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 标签ID
     */
    @TableId(value = "id" ,type = IdType.AUTO)
    private Long id;
    /**
     * 标签名
     */
    private String tagName;

}
