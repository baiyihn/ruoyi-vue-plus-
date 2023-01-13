package com.zhi.blog.service;

import com.zhi.blog.domain.vo.WebsiteConfigVO;

/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/11 20:20
 */
public interface IWebsiteConfigService {

    /**
     * 前端获取网站配置
     *
     * @return {@link WebsiteConfigVO} 网站配置
     */
    WebsiteConfigVO getWebsiteConfig();


    /**
     * 后台获取网站配置
     *
     * @return {@link WebsiteConfigVO} 网站配置
     */
    WebsiteConfigVO getAdminWebsiteConfig();


    /**
     * 保存或更新网站配置
     *
     * @param websiteConfigVO 网站配置
     */
    void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO);


}
