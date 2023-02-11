//package com.zhi.web.controller.strategy.impl;
//import cn.easyes.core.conditions.LambdaEsQueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.zhi.blog.domain.es.EsArticle;
//import com.zhi.blog.dto.ArticleSearchDTO;
//import com.zhi.blog.easyesMapper.EsArticleMapper;
//import com.zhi.web.controller.strategy.SearchStrategy;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//import static com.zhi.common.constant.blog.CommonConst.*;
//
///**
// * es搜索策略实现
// *
// * @author yezhiqiu
// * @date 2021/07/27
// */
//@Log4j2
//@Service("esSearchStrategyImpl")
//public class EsSearchStrategyImpl implements SearchStrategy {
//
//
//    @Resource
//    private  EsArticleMapper esArticleMapper;
//
//
//    @Override
//    public List<ArticleSearchDTO> searchArticle(String keywords) {
//        if (StringUtils.isBlank(keywords)) {
//            return new ArrayList<>();
//        }
//        // 搜索文章
//        LambdaEsQueryWrapper<EsArticle> wrapper = new LambdaEsQueryWrapper<>();
//        wrapper
//            .eq(EsArticle::getStatus,ISTRUE)
//            .eq(EsArticle::getType,ISTRUE)
//            .and(i -> i
//                .like(EsArticle::getArticle_title, keywords)
//                .or()
//                .like(EsArticle::getArticle_content, keywords)
//            );
//
//
//        List<EsArticle> articleList = esArticleMapper.selectList(wrapper);
//
//
//        // 高亮处理
//        return articleList.stream().map(item -> {
//            // 获取关键词第一次出现的位置
//            String articleContent = item.getArticle_content();
//            int index = item.getArticle_content().indexOf(keywords);
//            if (index != -1) {
//                // 获取关键词前面的文字
//                int preIndex = index > 25 ? index - 25 : 0;
//                String preText = item.getArticle_content().substring(preIndex, index);
//                // 获取关键词到后面的文字
//                int last = index + keywords.length();
//                int postLength = item.getArticle_content().length() - last;
//                int postIndex = postLength > 175 ? last + 175 : last + postLength;
//                String postText = item.getArticle_content().substring(index, postIndex);
//                // 文章内容高亮
//                articleContent = (preText + postText).replaceAll(keywords, PRE_TAG + keywords + POST_TAG);
//            }
//            // 文章标题高亮
//            String articleTitle = item.getArticle_title().replaceAll(keywords, PRE_TAG + keywords + POST_TAG);
//            return ArticleSearchDTO.builder()
//                .id(item.getId())
//                .articleTitle(articleTitle)
//                .articleContent(articleContent)
//                .build();
//        }).collect(Collectors.toList());
//
//    }
//
//}
