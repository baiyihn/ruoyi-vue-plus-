package com.zhi.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.zhi.common.annotation.ExcelDictFormat;
import com.zhi.common.convert.ExcelDictConvert;
import lombok.Data;


/**
 * 评论管理视图对象 blog_comment
 *
 * @author ftz
 * @date 2023-01-10
 */
@Data
@ExcelIgnoreUnannotated
public class CommentVo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 评论用户Id
     */
    @ExcelProperty(value = "评论用户Id")
    private Long userId;

    /**
     * 评论内容
     */
    @ExcelProperty(value = "评论内容")
    private String commentContent;

    /**
     * 回复用户id
     */
    @ExcelProperty(value = "回复用户id")
    private Long replyUserId;

    /**
     * 父评论id
     */
    @ExcelProperty(value = "父评论id")
    private Long parentId;

    /**
     * 评论类型 1.文章 2.友链 3.说说
     */
    @ExcelProperty(value = "评论类型 1.文章 2.友链 3.说说", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "commen_type")
    private Integer type;

    /**
     * 是否删除  0否 1是
     */
    @ExcelProperty(value = "是否删除  0否 1是")
    private Integer isDelete;

    /**
     * 评论主题id
     */
    @ExcelProperty(value = "评论主题id")
    private Long topicId;

    /**
     * 评论ip
     */
    @ExcelProperty(value = "评论ip")
    private String ipAddress;

    /**
     * 真实地址
     */
    @ExcelProperty(value = "真实地址")
    private String ipSource;

    /**
     * 评论状态
     */
    @ExcelProperty(value = "评论状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "comment_status")
    private Integer state;


}
