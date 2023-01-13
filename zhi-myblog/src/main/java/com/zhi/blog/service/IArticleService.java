package com.zhi.blog.service;

import com.zhi.blog.domain.Article;
import com.zhi.blog.domain.vo.ArticleVo;
import com.zhi.blog.domain.bo.ArticleBo;
import com.zhi.blog.dto.ArticleHomeDTO;
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
     * 查询首页文章
     *
     * @return 文章列表
     */
    List<ArticleHomeDTO> listArticles();


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
