package com.zhi.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * 页面管理视图对象 blog_page
 *
 * @author ftz
 * @date 2023-01-12
 */
@Data
@ExcelIgnoreUnannotated
public class BlogPageVo {

    private static final long serialVersionUID = 1L;

    /**
     * 页面id
     */
    @ExcelProperty(value = "页面id")
    private Long id;

    /**
     * 页面名
     */
    @ExcelProperty(value = "页面名")
    private String pageName;

    /**
     * 页面标签
     */
    @ExcelProperty(value = "页面标签")
    private String pageLabel;

    /**
     * 页面封面
     */
    @ExcelProperty(value = "页面封面")
    private String pageCover;


}
