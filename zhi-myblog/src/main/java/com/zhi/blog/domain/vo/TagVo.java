package com.zhi.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.zhi.common.annotation.ExcelDictFormat;
import com.zhi.common.convert.ExcelDictConvert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 标签管理视图对象 blog_tag
 *
 * @author ftz
 * @date 2023-01-08
 */
@Data
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {

    private static final long serialVersionUID = 1L;

    /**
     *标签id
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 标签名
     */
    @ExcelProperty(value = "标签名")
    private String tagName;


    /**
     * 文章数量
     */
    private Integer articleCount;


}
