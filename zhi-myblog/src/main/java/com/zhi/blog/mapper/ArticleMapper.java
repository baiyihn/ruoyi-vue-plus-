package com.zhi.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhi.blog.domain.Article;
import com.zhi.blog.domain.Tag;
import com.zhi.blog.domain.vo.ArticleVo;
import com.zhi.blog.dto.ArticleDTO;
import com.zhi.blog.dto.ArticleHomeDTO;
import com.zhi.blog.dto.ArticlePreviewDTO;
import com.zhi.blog.dto.ArticleRecommendDTO;
import com.zhi.blog.dto.vo.ConditionVO;
import com.zhi.common.annotation.DataColumn;
import com.zhi.common.annotation.DataPermission;
import com.zhi.common.core.domain.entity.SysUser;
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

    @Override
    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id"),
        @DataColumn(key = "userName", value = "user_id")
    })
    <P extends IPage<Article>> P selectPage(P page, @Param(Constants.WRAPPER) Wrapper<Article> queryWrapper);


    /**
     * 查询首页文章
     *
     * @param current 页码
     * @param size    大小
     * @return 文章列表
     */
    List<ArticleHomeDTO> listArticles(@Param("current") Long current, @Param("size") Long size);


    /**
     * 查看文章的推荐文章
     *
     * @param articleId 文章id
     * @return 文章列表
     */
    @Select(" SELECT\n" +
        "          id,\n" +
        "          article_title,\n" +
        "          article_cover,\n" +
        "          create_time\n" +
        "        FROM\n" +
        "         (\n" +
        "          SELECT DISTINCT article_id FROM\n" +
        "                 ( \n" +
        "\t\t\t\t\t\t\t\t SELECT tag_id FROM blog_article_tag WHERE article_id = #{articleId}) t \n" +
        "\t\t\t\t\t\t\t\t JOIN blog_article_tag t1 ON t.tag_id = t1.tag_id    \n" +
        "\t\t\t\t         WHERE\n" +
        "                 article_id != #{articleId}\t\t\t\t\t\t\t\t \n" +
        "         ) t2\n" +
        "           JOIN blog_article a ON t2.article_id = a.id\n" +
        "           WHERE a.is_delete = 0\n" +
        "           ORDER BY\n" +
        "           is_top DESC,id DESC\n" +
        "           LIMIT 6")
    List<ArticleRecommendDTO> listRecommendArticles(@Param("articleId") Integer articleId);


    /**
     * 根据id查询文章
     *
     * @param articleId 文章id
     * @return 文章信息
     */
    ArticleDTO getArticleById(@Param("articleId") Integer articleId);


    /**
     * 根据条件查询文章
     *
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return 文章列表
     */
    List<ArticlePreviewDTO> listArticlesByCondition(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVO condition);


    /**
     * 查询全部文章列表
     *
     * @return
     */
    @Select("  select a.id,a.user_id,a.category_id,o.url as article_cover,a.article_title,a.article_content,a.type,a.original_url,a.is_top,a.is_delete,a.status," +
        "a.create_time,a.update_time,a.like_count,a.views_count,a.create_by,a.update_by,c.category_name as categoryName,u.user_name as username\n" +
        "        from blog_article a\n" +
        "        left join blog_category c on a.category_id = c.id\n" +
        "        left join sys_user u       on a.user_id = u.user_id\n" +
        "        left join sys_oss o on a.article_cover = o.oss_id")
    List<Article> queryArticle();


    /**
     * 根据id查询文章列表
     *
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
     *
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
    int insertById(@Param("aid") Long aid, @Param("tid") Long tid);

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


    /**
     * 将文章图片转换为url
     */
    @Select("select o.url from sys_oss o where o.oss_id = #{url}")
    String ImgUrl(Long url);

    /**
     * 根据分类id查询分类名称
     */
    @Select("select c.category_name from blog_category c where c.id = #{id} ")
    String selectCategoryNameById(Long id);

    /**
     * 根据id获取当前用户名
     */
    @Select("select u.user_name from sys_user u where u.user_id = #{id}")
    String getUsernameById(Long id);


}
