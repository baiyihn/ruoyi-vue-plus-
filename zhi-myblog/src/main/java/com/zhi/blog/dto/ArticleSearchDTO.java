package com.zhi.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;



/**
 * 搜索文章
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSearchDTO {

    /**
     * 文章id
     */
    @Id
    private Long id;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章内容
     */
    private String articleContent;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 文章状态
     */
    private Integer status;

}
