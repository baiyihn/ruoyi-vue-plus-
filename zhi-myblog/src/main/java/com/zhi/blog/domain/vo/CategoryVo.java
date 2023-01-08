package com.zhi.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.zhi.common.annotation.ExcelDictFormat;
import com.zhi.common.convert.ExcelDictConvert;
import lombok.Data;


/**
 * 分类管理视图对象 blog_category
 *
 * @author ftz
 * @date 2023-01-07
 */
@Data
@ExcelIgnoreUnannotated
public class CategoryVo {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 分类名
     */
    @ExcelProperty(value = "分类名")
    private String categoryName;


}
