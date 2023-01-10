package com.zhi.blog.domain.Dto;

/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/9 16:10
 */

import lombok.Builder;
import lombok.Data;

/**
 * 封装返回给前端的文章标签
 * @author LEGION
 */
@Data
@Builder
public class TagListDto {

    private  Integer id;

    private String tag_name;


}
