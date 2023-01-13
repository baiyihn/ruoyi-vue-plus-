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
import static com.zhi.common.constant.blog.CommonConst.DEFAULT_CONFIG_ID;
import static com.zhi.common.constant.blog.RedisPrefixConst.WEBSITE_CONFIG;


/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/11 20:22
 */
@Service
public class WebsiteConfigServiceImpl implements IWebsiteConfigService {

    @Resource
    private WebsiteConfigMapper websiteConfigMapper;


    /**
     * 前台获取
     * @return
     */

    @Override
    public WebsiteConfigVO getWebsiteConfig() {
        WebsiteConfigVO websiteConfigVO;
        //获取缓存数据
        Object websiteConfig = RedisUtils.getCacheObject(WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)){
            websiteConfigVO = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVO.class);
            //将图片改为url地址
            websiteConfigVO.setWebsiteAvatar(websiteConfigMapper.websiteAvatar(Long.parseLong(websiteConfigVO.getWebsiteAvatar())));
            websiteConfigVO.setAlipayQRCode(websiteConfigMapper.alipayQRCode(Long.parseLong(websiteConfigVO.getAlipayQRCode())));
            websiteConfigVO.setTouristAvatar(websiteConfigMapper.touristAvatar(Long.parseLong(websiteConfigVO.getTouristAvatar())));
            websiteConfigVO.setUserAvatar( websiteConfigMapper.userAvatar(Long.parseLong(websiteConfigVO.getUserAvatar())));
            websiteConfigVO.setWeiXinQRCode(websiteConfigMapper.weiXinQRCode(Long.parseLong(websiteConfigVO.getWeiXinQRCode())));
        }else {
            //从数据库中加载
            String config = websiteConfigMapper.selectById(DEFAULT_CONFIG_ID).getConfig();
            websiteConfigVO = JSON.parseObject(config, WebsiteConfigVO.class);
            //将图片改为url地址
            websiteConfigVO.setWebsiteAvatar(websiteConfigMapper.websiteAvatar(Long.parseLong(websiteConfigVO.getWebsiteAvatar())));
            websiteConfigVO.setAlipayQRCode(websiteConfigMapper.alipayQRCode(Long.parseLong(websiteConfigVO.getAlipayQRCode())));
            websiteConfigVO.setTouristAvatar(websiteConfigMapper.touristAvatar(Long.parseLong(websiteConfigVO.getTouristAvatar())));
            websiteConfigVO.setUserAvatar( websiteConfigMapper.userAvatar(Long.parseLong(websiteConfigVO.getUserAvatar())));
            websiteConfigVO.setWeiXinQRCode(websiteConfigMapper.weiXinQRCode(Long.parseLong(websiteConfigVO.getWeiXinQRCode())));
            RedisUtils.setCacheObject(WEBSITE_CONFIG, config);
        }
        return websiteConfigVO;
    }

    /**
     * 后台获取
     * @return
     */
    @Override
    public WebsiteConfigVO getAdminWebsiteConfig() {
        WebsiteConfigVO websiteConfigVO;
        String config = websiteConfigMapper.selectById(DEFAULT_CONFIG_ID).getConfig();
        websiteConfigVO = JSON.parseObject(config, WebsiteConfigVO.class);
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
