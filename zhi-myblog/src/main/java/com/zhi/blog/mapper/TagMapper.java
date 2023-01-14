package com.zhi.blog.mapper;

import com.zhi.blog.domain.Tag;
import com.zhi.blog.domain.vo.TagVo;
import com.zhi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 标签管理Mapper接口
 *
 * @author ftz
 * @date 2023-01-08
 */
public interface TagMapper extends BaseMapperPlus<TagMapper, Tag, TagVo> {


    /**
     * 标签集合
     * @return
     */
    List<TagVo> tagList();

    /**
     * 根据id判断是否有文章与该标签id相对应
     */
    @Select("select count(*) from blog_article_tag where tag_id = #{id}")
    int ArticleTag(Long id);




}
