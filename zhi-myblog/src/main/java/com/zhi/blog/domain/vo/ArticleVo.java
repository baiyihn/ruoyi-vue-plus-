package com.zhi.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.zhi.blog.domain.Category;
import com.zhi.blog.domain.Tag;
import com.zhi.common.annotation.ExcelDictFormat;
import com.zhi.common.convert.ExcelDictConvert;
import com.zhi.common.core.domain.entity.SysUser;
import lombok.Data;

import java.util.List;


/**
 * 文章列表视图对象 blog_article
 *
 * @author ftz
 * @date 2023-01-07
 */
@Data
@ExcelIgnoreUnannotated
public class ArticleVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 作者
     */
    @ExcelProperty(value = "作者")
    private Long userId;

    /**
     * 作者名
     */
    private String username;

    /**
     * 文章分类
     */
    @ExcelProperty(value = "文章分类")
    private Long categoryId;


    /**
     * 文章标签名称
     */
    private List<String> tagNameList;


    /**
     * 文章分类名称
     */
    @ExcelProperty(value = "文章分类名称")
    private String categoryName;


    /**
     * 文章缩略图
     */
    @ExcelProperty(value = "文章缩略图")
    private String articleCover;

    /**
     * 标题
     */
    @ExcelProperty(value = "标题")
    private String articleTitle;

    /**
     * 内容
     */
    @ExcelProperty(value = "内容")
    private String articleContent;

    /**
     * 文章类型 1原创 2转载 3翻译
     */
    @ExcelProperty(value = "文章类型 1原创 2转载 3翻译", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "article_type")
    private Integer type;

    /**
     * 原文链接
     */
    @ExcelProperty(value = "原文链接")
    private String originalUrl;

    /**
     * 是否置顶 0否 1是
     */
    @ExcelProperty(value = "是否置顶 0否 1是", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private Integer isTop;

    /**
     * 是否删除 0否 1是
     */
    @ExcelProperty(value = "是否删除 0否 1是", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private Integer isDelete;

    /**
     * 状态值 1公开 2私密 3评论可见
     */
    @ExcelProperty(value = "状态值 1公开 2私密 3评论可见", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "article_status")
    private Integer status;

    /**
     * 点赞数
     */
    @ExcelProperty(value = "点赞数")
    private Long likeCount;

    /**
     * 浏览量
     */
    @ExcelProperty(value = "浏览量")
    private Long viewsCount;


}
