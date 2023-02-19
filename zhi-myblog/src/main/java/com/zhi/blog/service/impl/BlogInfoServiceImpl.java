package com.zhi.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zhi.blog.domain.Article;
import com.zhi.blog.domain.bo.BlogPageBo;
import com.zhi.blog.domain.vo.BlogHomeInfoVo;
import com.zhi.blog.domain.vo.BlogPageVo;
import com.zhi.blog.domain.vo.WebsiteConfigVO;
import com.zhi.blog.dto.vo.BlogInfoVO;
import com.zhi.blog.mapper.ArticleMapper;
import com.zhi.blog.mapper.CategoryMapper;
import com.zhi.blog.mapper.TagMapper;
import com.zhi.blog.service.IBlogInfoService;
import com.zhi.blog.service.IBlogPageService;
import com.zhi.blog.service.IWebsiteConfigService;
import com.zhi.common.utils.blog.IpUtils;
import com.zhi.common.utils.redis.RedisUtils;
import com.zhi.system.service.RedisService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.zhi.common.constant.blog.CommonConst.*;
import static com.zhi.common.constant.blog.RedisPrefixConst.*;
import static com.zhi.common.enums.blog.ArticleStatusEnum.PUBLIC;

/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/12 15:47
 */
@Service
public class BlogInfoServiceImpl implements IBlogInfoService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private IBlogPageService blogPageService;

    @Resource
    private IWebsiteConfigService websiteConfigService;

    @Resource
    private RedisService redisService;

    @Resource
    private HttpServletRequest request;

    @Override
    public BlogHomeInfoVo getBlogHomeInfo() {
        // 查询文章数量
        Long articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
            .eq(Article::getStatus, PUBLIC.getStatus())
            .eq(Article::getIsDelete, FALSE));
        // 查询分类数量
        Long categoryCount = categoryMapper.selectCount(null);
        // 查询标签数量
        Long tagCount = tagMapper.selectCount(null);
        // 查询访问量
        Object count = RedisUtils.getCacheObject(BLOG_VIEWS_COUNT);
        String viewsCount = Optional.ofNullable(count).orElse(0).toString();
        // 查询网站配置
        WebsiteConfigVO websiteConfig = websiteConfigService.getWebsiteConfig();
        // 查询页面图片
        List<BlogPageVo> pageVOList = blogPageService.queryList(new BlogPageBo());
        // 封装数据
        return BlogHomeInfoVo.builder()
            .articleCount(articleCount)
            .categoryCount(categoryCount)
            .tagCount(tagCount)
            .viewsCount(viewsCount)
            .websiteConfig(websiteConfig)
            .pageList(pageVOList)
            .build();

    }



    @Override
    public void report() {
        // 获取ip
        String ipAddress = IpUtils.getIpAddress(request);
        // 获取访问设备
        UserAgent userAgent = IpUtils.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        // 判断是否访问
        if (!redisService.sIsMember(UNIQUE_VISITOR, md5)) {
            // 统计游客地域分布
            String ipSource = IpUtils.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                ipSource = ipSource.substring(0, 2)
                    .replaceAll(PROVINCE, "")
                    .replaceAll(CITY, "");
                redisService.hIncr(VISITOR_AREA, ipSource, 1L);
            } else {
                redisService.hIncr(VISITOR_AREA, UNKNOWN, 1L);
            }
            // 访问量+1
            redisService.incr(BLOG_VIEWS_COUNT, 1);
            // 保存唯一标识
            redisService.sAdd(UNIQUE_VISITOR, md5);
        }
    }

    @Override
    public String getAbout() {
        Object value = redisService.get(ABOUT);
        return Objects.nonNull(value) ? value.toString() : "";
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAbout(BlogInfoVO blogInfoVO) {
        redisService.set(ABOUT, blogInfoVO.getAboutContent());
    }




}
