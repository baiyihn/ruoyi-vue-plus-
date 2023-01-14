package com.zhi.blog.service;

import com.zhi.blog.domain.Article;
import com.zhi.blog.domain.vo.ArticleVo;
import com.zhi.blog.domain.bo.ArticleBo;
import com.zhi.blog.domain.vo.PageResult;
import com.zhi.blog.dto.ArchiveDTO;
import com.zhi.blog.dto.ArticleDTO;
import com.zhi.blog.dto.ArticleHomeDTO;
import com.zhi.blog.dto.ArticlePreviewListDTO;
import com.zhi.blog.dto.vo.ConditionVO;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 文章列表Service接口
 *
 * @author ftz
 * @date 2023-01-07
 */
public interface IArticleService {


    /**
     * 查询文章归档
     *
     * @return 文章归档
     */
    PageResult<ArchiveDTO> listArchives();


    /**
     * 查看博客前端首页文章
     *
     */
    List<ArticleHomeDTO> listArticles();


    /**
     * 博客前端根据id查看文章
     *
     * @param articleId 文章id
     * @return {@link ArticleDTO} 文章信息
     */
    ArticleDTO getArticleById(Integer articleId);

    /**
     * 根据条件查询文章列表
     *
     * @param condition 条件
     * @return 文章列表
     */
    ArticlePreviewListDTO listArticlesByCondition(ConditionVO condition);



    /**
     * 查询文章列表
     */
    ArticleVo queryById(Long id);

    /**
     * 查询文章列表列表
     */
    TableDataInfo<ArticleVo> queryPageList(ArticleBo bo, PageQuery pageQuery);

    /**
     * 查询文章列表列表
     */
    List<ArticleVo> queryList(ArticleBo bo);

    /**
     * 新增文章列表
     */
    Boolean insertByBo(ArticleBo bo);

    /**
     * 修改文章列表
     */
    Boolean updateByBo(ArticleBo bo);

    /**
     * 校验并批量删除文章列表信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
