package com.zhi.blog.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/16 22:59
 * 用户点赞前端封装信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeVO {

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 用户id
     */
    private Long userid;
}
