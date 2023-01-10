package com.zhi.blog.mapper;

import com.zhi.blog.domain.Article;
import com.zhi.blog.domain.Tag;
import com.zhi.blog.domain.vo.ArticleVo;
import com.zhi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 文章列表Mapper接口
 *
 * @author ftz
 * @date 2023-01-07
 */
public interface ArticleMapper extends BaseMapperPlus<ArticleMapper, Article, ArticleVo> {


    /**
     * 查询全部文章列表
     * @return
     */
    @Select("  select a.id,a.user_id,a.category_id,o.url as article_cover,a.article_title,a.article_content,a.type,a.original_url,a.is_top,a.is_delete,a.status,a.create_time,a.update_time,a.like_count,a.views_count,a.create_by,a.update_by,c.category_name as categoryName,u.user_name as username\n" +
        "        from blog_article a\n" +
        "        left join blog_category c on a.category_id = c.id\n" +
        "        left join sys_user u       on a.user_id = u.user_id\n" +
        "        left join sys_oss o on a.article_cover = o.oss_id")
    List<Article> queryArticle();


    /**
     * 根据id查询文章列表
     * @param id
     * @return
     */
    @Select("  select a.id,a.user_id,a.category_id,a.article_cover,a.article_title,a.article_content,a.type,a.original_url,a.is_top,a.is_delete,a.status,a.create_time,a.update_time,a.like_count,a.views_count,a.create_by,a.update_by,c.category_name,u.user_name\n" +
        "        from blog_article a\n" +
        "                 left join blog_category c on a.category_id = c.id\n" +
        "                 left join sys_user u       on a.user_id = u.user_id\n" +
        "        where a.id = #{id}")
    Article queryArticleById(Long id);


    /**
     * 根据id查询文章标签
     * @param id
     * @return
     */
    @Select("SELECT t.tag_name\n" +
        "from\n" +
        "blog_article_tag at \n" +
        "left JOIN  blog_tag t  on at.tag_id = t.id\n" +
        "where at.article_id = #{id}")
    List<String> queryArticleTags(Long id);


    /**
     * 根据id插入标签数据
     */
    @Insert("INSERT INTO blog_article_tag(article_id,tag_id) VALUES(#{aid},#{tid})")
    int insertById(@Param("aid") Long aid , @Param("tid") Long tid);

    /**
     * 根据名称标签查询标签id
     */
    @Select("select id from blog_tag b where b.tag_name = #{name} ")
    Long queryTagIdByName(String name);


    /**
     * 根据文章id删除对应标签
     */
    @Delete("delete from blog_article_tag where article_id = #{id} ")
    int delectTagsById(Long id);


    /**
     * 根据用户名获取当前登录者id
     */
    @Select("select u.user_id from sys_user u where user_name = #{username} ")
    Long getUserIdByName(String username);











}
