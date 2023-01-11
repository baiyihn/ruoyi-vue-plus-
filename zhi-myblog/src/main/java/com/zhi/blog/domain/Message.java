package com.zhi.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.zhi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 留言管理对象 blog_message
 *
 * @author ftz
 * @date 2023-01-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_message")
public class Message extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 留言内容
     */
    private String messageContent;
    /**
     * 用户ip
     */
    private String ipAddress;
    /**
     * 用户地址
     */
    private String ipSource;
    /**
     * 弹幕速度
     */
    private Integer time;
    /**
     * 是否审核
     */
    private String isReview;

}
