package com.zhi.blog.utils;

import com.zhi.blog.domain.Category;
import com.zhi.blog.domain.Tag;
import com.zhi.blog.domain.bo.ArticleBo;
import com.zhi.blog.mapper.ArticleMapper;
import com.zhi.blog.mapper.CategoryMapper;
import com.zhi.blog.mapper.TagMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/10 15:26
 */
@Component
public class CategoryOrTag {


    @Resource
    private  CategoryMapper categoryMapper;

    @Resource
    private  ArticleMapper articleMapper;

    @Resource
    private  TagMapper tagMapper;

    /**
     * 新增分类或者标签
     */
    public void  AddCateOrTag(ArticleBo bo){
        // 如果是新增的分类则先新增这个新分类
        String categoryName = bo.getCategoryName();
        if (categoryMapper.selectIdByName(categoryName)==null){
            Category category =Category.builder().categoryName(categoryName).build();
            categoryMapper.insert(category);
        }
        //首先将该文章的全部标签删除
        articleMapper.delectTagsById(bo.getId());
        //  递归标签 修改文章则走下面逻辑 新增文章单独处理
        if (bo.getId()!=null){
            for(String s:bo.getTagNameList()){
                // 如果是新增的标签则先将标签新增
                if (articleMapper.queryTagIdByName(s)==null){
                    Tag tag = Tag.builder().tagName(s).build();
                    tagMapper.insert(tag);
                }
                //判断文章是否已经有这个标签
                List<String> tags = articleMapper.queryArticleTags(bo.getId());
                if (tags.contains(s)){
                    continue;
                }
                articleMapper.insertById(bo.getId(),articleMapper.queryTagIdByName(s));
            }
        }

    }


}
