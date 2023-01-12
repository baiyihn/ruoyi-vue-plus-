package com.zhi.blog.mapper;

import com.zhi.blog.domain.BlogPage;
import com.zhi.blog.domain.vo.BlogPageVo;
import com.zhi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 页面管理Mapper接口
 *
 * @author ftz
 * @date 2023-01-12
 */
public interface BlogPageMapper extends BaseMapperPlus<BlogPageMapper, BlogPage, BlogPageVo> {


    /**
     * 查询页面列表
     */
    @Select("select p.id ,p.page_name,p.page_label,o.url as pageCover from blog_page p left join sys_oss o on p.page_cover = o.oss_id")
    List<BlogPage>  BlogPageList();

}
