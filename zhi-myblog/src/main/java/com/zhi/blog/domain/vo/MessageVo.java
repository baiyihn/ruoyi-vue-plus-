package com.zhi.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.zhi.common.annotation.ExcelDictFormat;
import com.zhi.common.convert.ExcelDictConvert;
import lombok.Data;


/**
 * 留言管理视图对象 blog_message
 *
 * @author ftz
 * @date 2023-01-11
 */
@Data
@ExcelIgnoreUnannotated
public class MessageVo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 昵称
     */
    @ExcelProperty(value = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @ExcelProperty(value = "头像")
    private String avatar;

    /**
     * 留言内容
     */
    @ExcelProperty(value = "留言内容")
    private String messageContent;

    /**
     * 用户ip
     */
    @ExcelProperty(value = "用户ip")
    private String ipAddress;

    /**
     * 用户地址
     */
    @ExcelProperty(value = "用户地址")
    private String ipSource;

    /**
     * 弹幕速度
     */
    @ExcelProperty(value = "弹幕速度")
    private Integer time;

    /**
     * 是否审核
     */
    @ExcelProperty(value = "是否审核", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private String isReview;


}
