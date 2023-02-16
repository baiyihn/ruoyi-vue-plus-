package com.zhi.blog.service.impl;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhi.blog.blogutils.CategoryOrTag;
import com.zhi.blog.domain.Category;
import com.zhi.blog.domain.Tag;
import com.zhi.blog.domain.vo.PageResult;
import com.zhi.blog.dto.*;
import com.zhi.blog.dto.vo.ConditionVO;
import com.zhi.blog.mapper.CategoryMapper;
import com.zhi.blog.mapper.TagMapper;
import com.zhi.blog.service.ITagService;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhi.common.exception.base.BaseException;
import com.zhi.common.helper.LoginHelper;
import com.zhi.common.utils.BeanCopyUtils;
import com.zhi.common.utils.StringUtils;
import com.zhi.common.utils.blog.PageUtils;
import com.zhi.common.utils.redis.RedisUtils;
import com.zhi.system.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.zhi.blog.domain.bo.ArticleBo;
import com.zhi.blog.domain.vo.ArticleVo;
import com.zhi.blog.domain.Article;
import com.zhi.blog.mapper.ArticleMapper;
import com.zhi.blog.service.IArticleService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.zhi.common.constant.blog.CommonConst.*;
import static com.zhi.common.constant.blog.RedisPrefixConst.*;
import static com.zhi.common.enums.blog.ArticleStatusEnum.PUBLIC;

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

    @Resource
    private final CategoryMapper categoryMapper;

    @Resource
    private final TagMapper tagMapper;

    private final CategoryOrTag categoryOrTag;

    @Resource
    private final RedisService redisService;

    @Resource
    private final HttpSession session;

    @Resource
    private final ITagService tagService;







    /**
     * 文章点赞
     * @param articleId 文章id
     */
    @Override
    public void saveArticleLike(Integer articleId,Long userid) {
        // 判断是否点赞
        String articleLikeKey = ARTICLE_USER_LIKE + userid;
        if (RedisUtils.hasKey(articleLikeKey)) {
            // 点过赞则删除文章id
            RedisUtils.deleteObject(articleLikeKey);
            // 文章点赞量-1
            RedisUtils.decrAtomicValue(ARTICLE_LIKE_COUNT);
        } else {
            // 未点赞则增加文章id
            RedisUtils.setCacheObject(articleLikeKey, articleId);
            // 文章点赞量+1
            RedisUtils.incrAtomicValue(ARTICLE_LIKE_COUNT);
        }
    }



    /**
     * 查看文章归档
     */
    @Override
    public PageResult<ArchiveDTO> listArchives() {
        Page<Article> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        // 获取分页数据
//        Page<Article> articlePage = baseMapper.selectPage(page, new LambdaQueryWrapper<Article>()
            List<Article> articlePage = baseMapper.selectList(new LambdaQueryWrapper<Article>()
            .select(Article::getId, Article::getArticleTitle, Article::getCreateTime)
            .orderByDesc(Article::getCreateTime)
            .eq(Article::getIsDelete, FALSE)
            .eq(Article::getStatus, PUBLIC.getStatus()));
        List<ArchiveDTO> archiveDTOList = BeanCopyUtils.copyList(articlePage, ArchiveDTO.class);
        return new PageResult<>(archiveDTOList, (int) articlePage.size());
    }


    /**
     * 查看博客前端首页文章
     *
     */
    @Override
    public List<ArticleHomeDTO> listArticles() {
        return baseMapper.listArticles(PageUtils.getLimitCurrent(), PageUtils.getSize());
    }



    /**
     * 博客前端根据id查看文章
     *
     * @param articleId 文章id
     * @return {@link ArticleDTO} 文章信息
     */
    @Override
    public ArticleDTO getArticleById(Integer articleId) {
        // 查询推荐文章
        CompletableFuture<List<ArticleRecommendDTO>> recommendArticleList = CompletableFuture
            .supplyAsync(
                () -> {
                    List<ArticleRecommendDTO> articleRecommendDTOS = baseMapper.listRecommendArticles(articleId);
                    articleRecommendDTOS.forEach(item ->{
                        item.setArticleCover(baseMapper.ImgUrl(Long.parseLong(item.getArticleCover())));
                    });
                    return articleRecommendDTOS;
                }
            );
        // 查询最新文章
        CompletableFuture<List<ArticleRecommendDTO>> newestArticleList = CompletableFuture
            .supplyAsync(() -> {
                List<Article> articleList = baseMapper.selectList(new LambdaQueryWrapper<Article>()
                    .select(Article::getId, Article::getArticleTitle, Article::getArticleCover, Article::getCreateTime)
                    .eq(Article::getIsDelete, FALSE)
                    .eq(Article::getStatus, PUBLIC.getStatus())
                    .orderByDesc(Article::getId)
                    .last("limit 5"));
                // 将前台图片转换为url
                articleList.stream().map(i -> {
                    i.setArticleCover(baseMapper.ImgUrl(Long.parseLong(i.getArticleCover())));
                    return i;
                }).collect(Collectors.toList());
                return BeanCopyUtils.copyList(articleList, ArticleRecommendDTO.class);
            });
        // 查询id对应文章
        ArticleDTO article = baseMapper.getArticleById(articleId);
        if (Objects.isNull(article)) {
            throw new BaseException("文章不存在");
        }
        // 更新文章浏览量
        updateArticleViewsCount(articleId);
        // 查询上一篇下一篇文章
        Article lastArticle = baseMapper.selectOne(new LambdaQueryWrapper<Article>()
            .select(Article::getId, Article::getArticleTitle, Article::getArticleCover)
            .eq(Article::getIsDelete, FALSE)
            .eq(Article::getStatus, PUBLIC.getStatus())
            .lt(Article::getId, articleId)
            .orderByDesc(Article::getId)
            .last("limit 1"));
        Article nextArticle = baseMapper.selectOne(new LambdaQueryWrapper<Article>()
            .select(Article::getId, Article::getArticleTitle, Article::getArticleCover)
            .eq(Article::getIsDelete, FALSE)
            .eq(Article::getStatus, PUBLIC.getStatus())
            .gt(Article::getId, articleId)
            .orderByAsc(Article::getId)
            .last("limit 1"));
        // 将图片转换为url
        if (Objects.nonNull(lastArticle)){
            lastArticle.setArticleCover(baseMapper.ImgUrl(Long.parseLong(lastArticle.getArticleCover())));
        }
        if (Objects.nonNull(nextArticle)){
            nextArticle.setArticleCover(baseMapper.ImgUrl(Long.parseLong(nextArticle.getArticleCover())));
        }

        article.setLastArticle(BeanCopyUtils.copyObject(lastArticle, ArticlePaginationDTO.class));
        article.setNextArticle(BeanCopyUtils.copyObject(nextArticle, ArticlePaginationDTO.class));
        // 封装点赞量和浏览量
        Double score = redisService.zScore(ARTICLE_VIEWS_COUNT, articleId);
        if (Objects.nonNull(score)) {
            article.setViewsCount(score.intValue());
        }

        if (Objects.nonNull(RedisUtils.getCacheObject(ARTICLE_LIKE_COUNT))){
            article.setLikeCount(RedisUtils.getCacheObject(ARTICLE_LIKE_COUNT));
        } else {
            //  初始文章点赞量
            RedisUtils.setCacheObject(ARTICLE_LIKE_COUNT,0);
        }
        // 封装文章信息
        try {
            article.setRecommendArticleList(recommendArticleList.get());
            article.setNewestArticleList(newestArticleList.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }





    @Override
    public ArticlePreviewListDTO listArticlesByCondition(ConditionVO condition) {
        // 查询文章
        List<ArticlePreviewDTO> articlePreviewDTOList = baseMapper.listArticlesByCondition(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        //将文章图片转化为url
        articlePreviewDTOList.forEach(item ->{
            item.setArticleCover(baseMapper.ImgUrl(Long.parseLong(item.getArticleCover())));
        });
        // 搜索条件对应名(标签或分类名)
        String name;
        if (Objects.nonNull(condition.getCategoryId())) {
            name = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                    .select(Category::getCategoryName)
                    .eq(Category::getId, condition.getCategoryId()))
                .getCategoryName();
        } else {
            name = tagService.getOne(new LambdaQueryWrapper<Tag>()
                    .select(Tag::getTagName)
                    .eq(Tag::getId, condition.getTagId()))
                    .getTagName();
        }
        return ArticlePreviewListDTO.builder()
            .articlePreviewDTOList(articlePreviewDTOList)
            .name(name)
            .build();
    }




    /**
     * 更新文章浏览量
     *
     * @param articleId 文章id
     */
    public void updateArticleViewsCount(Integer articleId) {
        // 判断是否第一次访问，增加浏览量
        Set<Integer> articleSet = (Set<Integer>) Optional.ofNullable(session.getAttribute(ARTICLE_SET)).orElse(new HashSet<>());
        if (!articleSet.contains(articleId)) {
            articleSet.add(articleId);
            session.setAttribute(ARTICLE_SET, articleSet);
            // 浏览量+1
            redisService.zIncr(ARTICLE_VIEWS_COUNT, articleId, 1D);
        }
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
     * 后台查询文章列表列表
     */
    @Override
    public TableDataInfo<ArticleVo> queryPageList(ArticleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Article> lqw = buildQueryWrapper(bo);
        Page<ArticleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        for(ArticleVo articleVo :result.getRecords()){
            articleVo.setArticleCover(baseMapper.ImgUrl(Long.parseLong(articleVo.getArticleCover())));
            articleVo.setCategoryName(baseMapper.selectCategoryNameById(articleVo.getCategoryId()));
            articleVo.setUsername(baseMapper.getUsernameById(articleVo.getUserId()));
            articleVo.setTagNameList(baseMapper.queryArticleTags(articleVo.getId()));
        }
        return TableDataInfo.build(result);
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
        bo.setUserId(LoginHelper.getUserId());
       // 没有传值默认分配
        if (StringUtils.isEmpty(bo.getCategoryName())){
            bo.setCategoryName(DEFAULT);
        }
        //设置文章分类
        categoryOrTag.AddCateOrTag(bo);
        bo.setCategoryId(categoryMapper.selectIdByName(bo.getCategoryName()));
        Article add = BeanUtil.toBean(bo, Article.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        //给文章默认标签
        if (bo.getTagNameList().isEmpty()){
            bo.setTagNameList(Arrays.asList(DEFAULT));
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
