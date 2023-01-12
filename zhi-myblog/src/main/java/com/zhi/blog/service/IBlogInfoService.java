package com.zhi.blog.service;

import com.zhi.blog.domain.vo.BlogHomeInfoVo;

/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/12 15:46
 */
public interface IBlogInfoService {


    /**
     * 获取首页数据
     *
     * @return 博客首页信息
     */
    BlogHomeInfoVo getBlogHomeInfo();

    /**
     * 上传访客信息
     */
    void report();

}
