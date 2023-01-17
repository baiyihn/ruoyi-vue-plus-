package com.zhi.web.controller.blogSearch;

import cn.dev33.satoken.annotation.SaIgnore;
import com.zhi.blog.dto.ArticleSearchDTO;
import com.zhi.blog.dto.vo.ConditionVO;
import com.zhi.common.core.domain.R;
import com.zhi.web.controller.strategy.context.SearchStrategyContext;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/17 18:55
 */

@RestController
public class ArticleSearch {

    @Resource
    private SearchStrategyContext searchStrategyContext;


    /**
     * 搜索文章
     *
     * @param condition 条件
     * @return {@link R} 文章列表
     */
    @SaIgnore
    @ApiOperation(value = "搜索文章")
    @GetMapping("/articles/search")
    public R<List<ArticleSearchDTO>> listArticlesBySearch(ConditionVO condition) {
        return R.ok( searchStrategyContext.executeSearchStrategy(condition.getKeywords()));
    }

}
