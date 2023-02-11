package com.zhi.blog.domain.es;
import cn.easyes.annotation.IndexName;
import lombok.Data;

/**
 * 文章列表对象 blog_article
 *
 * @author ftz
 * @date 2023-01-07
 */

@Data
@IndexName("blog_article")
public class EsArticle  {


    /**
     *ID
     */
    private Long id;

    /**
     * 作者
     */
    private Long user_id;


    /**
     * 文章分类
     */
    private Long category_id;


    /**
     * 文章缩略图
     */
    private String article_cover;



    /**
     * 标题
     */
    private String article_title;

    /**
     * 内容
     */
    private String article_content;

    /**
     * 文章类型 1原创 2转载 3翻译
     */
    private Integer type;

    /**
     * 原文链接
     */
    private String original_url;

    /**
     * 是否置顶 0否 1是
     */
    private Integer is_top;

    /**
     * 是否删除 0否 1是
     */
    private Integer is_delete;

    /**
     * 状态值 1公开 2私密 3评论可见
     */
    private Integer status;

    /**
     * 点赞数
     */
    private Long like_count;

    /**
     * 浏览量
     */
    private Long views_count;

}
