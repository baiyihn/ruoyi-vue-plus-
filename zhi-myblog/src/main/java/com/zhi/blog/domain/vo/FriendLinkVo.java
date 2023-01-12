package com.zhi.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.zhi.common.annotation.ExcelDictFormat;
import com.zhi.common.convert.ExcelDictConvert;
import lombok.Data;


/**
 * 友链管理视图对象 blog_friend_link
 *
 * @author ftz
 * @date 2023-01-12
 */
@Data
@ExcelIgnoreUnannotated
public class FriendLinkVo {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 链接名
     */
    @ExcelProperty(value = "链接名")
    private String linkName;

    /**
     * 链接头像
     */
    @ExcelProperty(value = "链接头像")
    private String linkAvatar;

    /**
     * 链接地址
     */
    @ExcelProperty(value = "链接地址")
    private String linkAddress;

    /**
     * 链接介绍
     */
    @ExcelProperty(value = "链接介绍")
    private String linkIntro;


}
