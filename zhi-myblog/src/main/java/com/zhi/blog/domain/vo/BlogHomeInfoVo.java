package com.zhi.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/12 15:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogHomeInfoVo {


    /**
     * 文章数量
     */
    private Long articleCount;

    /**
     * 分类数量
     */
    private Long categoryCount;

    /**
     * 标签数量
     */
    private Long tagCount;

    /**
     * 访问量
     */
    private String viewsCount;

    /**
     * 网站配置
     */
    private WebsiteConfigVO websiteConfig;

    /**
     * 页面列表
     */
    private List<BlogPageVo> pageList;


}
