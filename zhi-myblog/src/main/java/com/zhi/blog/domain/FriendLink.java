package com.zhi.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.zhi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 友链管理对象 blog_friend_link
 *
 * @author ftz
 * @date 2023-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_friend_link")
public class FriendLink extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 链接名
     */
    private String linkName;
    /**
     * 链接头像
     */
    private String linkAvatar;
    /**
     * 链接地址
     */
    private String linkAddress;
    /**
     * 链接介绍
     */
    private String linkIntro;

}
