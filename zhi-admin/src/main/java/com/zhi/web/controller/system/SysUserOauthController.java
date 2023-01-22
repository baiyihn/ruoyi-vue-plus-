package com.zhi.web.controller.system;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.xkcoding.justauth.AuthRequestFactory;
import com.zhi.common.core.domain.R;
import com.zhi.common.core.domain.entity.SysUser;
import com.zhi.common.core.domain.model.BlogLoginUser;
import com.zhi.common.utils.BeanCopyUtils;
import com.zhi.common.utils.StringUtils;
import com.zhi.system.service.SysLoginService;
import com.zhi.system.service.SysRegisterService;
import io.undertow.servlet.spec.HttpServletResponseImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 第三方登录
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/20 15:05
 */
@SaIgnore
@Slf4j
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysUserOauthController {


    private final AuthRequestFactory factory;

    private final SysRegisterService registerService;


    private final SysLoginService loginService;


    /**
     * 登录类型
     */
    @GetMapping
    public Map<String, String> loginType() {
        List<String> oauthList = factory.oauthList();
        return oauthList.stream().collect(Collectors
            .toMap(oauth -> oauth.toLowerCase() + "登录",
                oauth -> "http://127.0.0.1:8080/oauth/login/" + oauth.toLowerCase()));
    }
    /**
     * 返回给前端回调地址
     *
     * @param response  response
     * @throws IOException
     */

    @GetMapping("/login/{type}")
    public R login(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(type);
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        return R.ok("请求成功",authorizeUrl);
    }

    /**
     * 登录成功后的回调
     *
     * @return 登录成功后的信息
     */
    @SaIgnore
    @RequestMapping("/{type}/callback")
    public void login(@PathVariable String type, AuthCallback callback , HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException {
        AuthRequest authRequest = factory.get(type);
        AuthResponse authResponse = authRequest.login(callback);
        String s = JSONUtil.toJsonStr(authResponse.getData());
        SysUser sysUser = JSON.parseObject(s,SysUser.class);
        BlogLoginUser blogLoginUser = registerService.OauthRegister(sysUser);
        httpServletResponse.sendRedirect("http://localhost:8081/oauth/login/gitee?userid="+blogLoginUser.getId());

    }








}
