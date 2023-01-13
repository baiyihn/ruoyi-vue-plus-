package com.zhi.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.zhi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 说说管理对象 blog_talk
 *
 * @author ftz
 * @date 2023-01-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_talk")
public class Talk extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 说说id
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 说说内容
     */
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
