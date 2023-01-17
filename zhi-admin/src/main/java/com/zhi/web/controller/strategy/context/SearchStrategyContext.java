package com.zhi.web.controller.strategy.context;

import com.zhi.blog.dto.ArticleSearchDTO;
import com.zhi.web.controller.strategy.SearchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.zhi.common.enums.blog.SearchModeEnum.getStrategy;

/**
 * 搜索策略上下文
 *
 * @author yezhiqiu
 * @date 2021/07/27
 */
@Service
public class SearchStrategyContext {
    /**
     * 搜索模式
     */
    @Value("${search.mode}")
    private String searchMode;

    @Resource
    private Map<String, SearchStrategy> searchStrategyMap;

    /**
     * 执行搜索策略
     *
     * @param keywords 关键字
     * @return {@link List<ArticleSearchDTO>} 搜索文章
     */
    public List<ArticleSearchDTO> executeSearchStrategy(String keywords) {
        System.out.println(getStrategy(searchMode));
        return searchStrategyMap.get(getStrategy(searchMode)).searchArticle(keywords);
    }

}
