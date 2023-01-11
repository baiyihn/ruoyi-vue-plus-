package com.zhi.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.zhi.blog.domain.WebsiteConfig;
import com.zhi.blog.domain.vo.WebsiteConfigVO;
import com.zhi.blog.mapper.WebsiteConfigMapper;
import com.zhi.blog.service.IWebsiteConfigService;
import com.zhi.common.utils.redis.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

import static com.zhi.blog.constant.CommonConst.DEFAULT_CONFIG_ID;
import static com.zhi.blog.constant.RedisPrefixConst.WEBSITE_CONFIG;


/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/11 20:22
 */
@Service
public class WebsiteConfigServiceImpl implements IWebsiteConfigService {

    @Resource
    private WebsiteConfigMapper websiteConfigMapper;


    @Override
    public WebsiteConfigVO getWebsiteConfig() {
        WebsiteConfigVO websiteConfigVO;
        //获取缓存数据
        Object websiteConfig = RedisUtils.getCacheObject(WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)){
            websiteConfigVO = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVO.class);
        }else {
            //从数据库中加载
            String config = websiteConfigMapper.selectById(DEFAULT_CONFIG_ID).getConfig();
            websiteConfigVO = JSON.parseObject(config, WebsiteConfigVO.class);
            RedisUtils.setCacheObject(WEBSITE_CONFIG, config);
        }
        return websiteConfigVO;
    }


    @Override
    public void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO) {
        // 修改网站配置
        WebsiteConfig websiteConfig = WebsiteConfig.builder()
            .id(1)
            .config(JSON.toJSONString(websiteConfigVO))
            .build();
        websiteConfigMapper.updateById(websiteConfig);
        // 删除缓存
        RedisUtils.deleteObject(WEBSITE_CONFIG);
    }
}
