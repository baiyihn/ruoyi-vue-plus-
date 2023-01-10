package com.zhi.blog.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.zhi.common.core.domain.BaseEntity;
import com.zhi.common.core.domain.entity.SysUser;
import com.zhi.system.domain.SysOss;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * 文章列表对象 blog_article
 *
 * @author ftz
 * @date 2023-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_article")
public class Article extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *ID
     */
    @TableId(value = "id" ,type = IdType.AUTO)
    private Long id;

    /**
     * 作者
     */
    private Long userId;

    /**
     * 作者名
     */
    @TableField(exist = false)
    private String username;


    /**
     * 文章分类
     */
    private Long categoryId;


    /**
     * 文章分类名称
     */
    @TableField(exist = false)
    private String categoryName;


    /**
     * 文章标签
     */
    @TableField(exist = false)
    private List<String> tagNameList;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 缩略图地址
     */
    @TableField(exist = false)
    private SysOss sysOss;

    /**
     * 标题
     */
    private String articleTitle;
    /**
     * 内容
     */
    private String articleContent;
    /**
     * 文章类型 1原创 2转载 3翻译
     */
    private Integer type;
    /**
     * 原文链接
     */
    private String originalUrl;
    /**
     * 是否置顶 0否 1是
     */
    private Integer isTop;
    /**
     * 是否删除 0否 1是
     */
    private Integer isDelete;
    /**
     * 状态值 1公开 2私密 3评论可见
     */
    private Integer status;
    /**
     * 点赞数
     */
    private Long likeCount;
    /**
     * 浏览量
     */
    private Long viewsCount;

}
