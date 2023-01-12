package com.zhi.blog.controller;

import com.zhi.blog.domain.vo.WebsiteConfigVO;
import com.zhi.blog.service.IWebsiteConfigService;
import com.zhi.common.core.domain.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/11 20:17 网站配置信息
 */
@RestController
@RequestMapping("/website")
public class WebsiteConfigController {

    @Resource
    private IWebsiteConfigService websiteConfigService;

    /**
     * 获取网站配置
     */
    @GetMapping("/config")
    public R<WebsiteConfigVO> getWebsiteConfig() {
        return R.ok(websiteConfigService.getWebsiteConfig());
    }


    /**
     * 更新网站配置
     *
     * @param websiteConfigVO 网站配置信息
     * @return {@link R}
     */
    @ApiOperation(value = "更新网站配置")
    @PutMapping("/config")
    public R<?> updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVO websiteConfigVO) {
        websiteConfigService.updateWebsiteConfig(websiteConfigVO);
        return R.ok();
    }



}
