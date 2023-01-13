package com.zhi.blog.service.impl;
import cn.hutool.core.bean.BeanUtil;
import com.zhi.blog.blogutils.CategoryOrTag;
import com.zhi.blog.domain.Tag;
import com.zhi.blog.dto.ArticleHomeDTO;
import com.zhi.blog.mapper.CategoryMapper;
import com.zhi.blog.mapper.TagMapper;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhi.common.utils.BeanCopyUtils;
import com.zhi.common.utils.StringUtils;
import com.zhi.common.utils.blog.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.zhi.blog.domain.bo.ArticleBo;
import com.zhi.blog.domain.vo.ArticleVo;
import com.zhi.blog.domain.Article;
import com.zhi.blog.mapper.ArticleMapper;
import com.zhi.blog.service.IArticleService;

import java.util.*;

/**
 * 文章列表Service业务层处理
 *
 * @author ftz
 * @date 2023-01-07
 */
@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements IArticleService {

    private final ArticleMapper baseMapper;

    private final CategoryMapper categoryMapper;

    private final TagMapper tagMapper;

    private final CategoryOrTag categoryOrTag;


    @Override
    public List<ArticleHomeDTO> listArticles() {
        return baseMapper.listArticles(PageUtils.getLimitCurrent(), PageUtils.getSize());
    }


    /**
     * 查询文章列表
     */
    @Override
    public ArticleVo queryById(Long id){
        Article article = baseMapper.queryArticleById(id);
        article.setTagNameList(baseMapper.queryArticleTags(article.getId()));
        return BeanCopyUtils.copy(article,ArticleVo.class);

    }

    /**
     * 查询文章列表列表
     */
    @Override
    public TableDataInfo<ArticleVo> queryPageList(ArticleBo bo, PageQuery pageQuery) {
        //   文章对应的全部标签
        List<Article> articles = baseMapper.queryArticle();
        for(Article article:articles){
            article.setTagNameList(baseMapper.queryArticleTags(article.getId()));
        }
        return TableDataInfo.build(BeanCopyUtils.copyList(articles, ArticleVo.class));
//        LambdaQueryWrapper<Article> lqw = buildQueryWrapper(bo);
//        Page<ArticleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
    }

    /**
     * 查询文章列表列表
     */
    @Override
    public List<ArticleVo> queryList(ArticleBo bo) {
        LambdaQueryWrapper<Article> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Article> buildQueryWrapper(ArticleBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Article> lqw = Wrappers.lambdaQuery();
//        lqw.eq(bo.getUserId() != null, Article::getUserId, bo.getUserId());
        lqw.eq(bo.getCategoryId() != null, Article::getCategoryId, bo.getCategoryId());
        lqw.eq(StringUtils.isNotBlank(bo.getArticleCover()), Article::getArticleCover, bo.getArticleCover());
        lqw.eq(StringUtils.isNotBlank(bo.getArticleTitle()), Article::getArticleTitle, bo.getArticleTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getArticleContent()), Article::getArticleContent, bo.getArticleContent());
        lqw.eq(bo.getType() != null, Article::getType, bo.getType());
        lqw.eq(StringUtils.isNotBlank(bo.getOriginalUrl()), Article::getOriginalUrl, bo.getOriginalUrl());
        lqw.eq(bo.getIsDelete() != null, Article::getIsDelete, bo.getIsDelete());
        lqw.eq(bo.getStatus() != null, Article::getStatus, bo.getStatus());

        return lqw;
    }

    /**
     * 新增文章列表
     */
    @Override
    public Boolean insertByBo(ArticleBo bo) {
        bo.setUserId(baseMapper.getUserIdByName(bo.getCreateBy()));
        //设置文章分类
        categoryOrTag.AddCateOrTag(bo);
        bo.setCategoryId(categoryMapper.selectIdByName(bo.getCategoryName()));
        Article add = BeanUtil.toBean(bo, Article.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        for(String s:bo.getTagNameList()){
            // 如果是新增的标签则先将标签新增
            if (baseMapper.queryTagIdByName(s)==null){
                Tag tag = Tag.builder().tagName(s).build();
                tagMapper.insert(tag);
            }
            //判断文章是否已经有这个标签
            List<String> tags = baseMapper.queryArticleTags(bo.getId());
            if (tags.contains(s)){
                continue;
            }
            baseMapper.insertById(bo.getId(),baseMapper.queryTagIdByName(s));
        }
        return flag;
    }

    /**
     * 修改文章列表
     */
    @Override
    public Boolean updateByBo(ArticleBo bo) {
        categoryOrTag.AddCateOrTag(bo);
        bo.setCategoryId(categoryMapper.selectIdByName( bo.getCategoryName()));
        Article update = BeanUtil.toBean(bo, Article.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Article entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除文章列表
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
            //将文章标签表对应的数据删除
            for (Long id :ids){
                baseMapper.delectTagsById(id);
            }
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
