package com.zhi.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhi.blog.domain.WebsiteConfig;
import org.apache.ibatis.annotations.Select;

/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/11 20:40
 */
public interface WebsiteConfigMapper extends BaseMapper<WebsiteConfig> {

    /**
     * 查询网站头像对应url
     */
    @Select("select o.url as websiteAvatar from sys_oss o where o.oss_id = #{websiteAvatar}")
    String websiteAvatar(Long websiteAvatar);

    /**
     * 查询用户头像url
     */
    @Select("select o.url as userAvatar from sys_oss o where o.oss_id = #{userAvatar}")
    String userAvatar(Long userAvatar);



    /**
     * 查询游客头像url
     */
    @Select("select o.url as touristAvatar from sys_oss o where o.oss_id = #{touristAvatar}")
    String touristAvatar(Long touristAvatar);


    /**
     * 微信收付款url
     */
    @Select("select o.url as weiXinQRCode from sys_oss o where o.oss_id = #{weiXinQRCode}")
    String weiXinQRCode(Long weiXinQRCode);



    /**
     * 支付宝收付款url
     */
    @Select("select o.url as alipayQRCode from sys_oss o where o.oss_id = #{alipayQRCode}")
    String alipayQRCode(Long alipayQRCode);


}
