package com.zhi.system.service;

import cn.dev33.satoken.secure.BCrypt;
import com.zhi.common.constant.CacheConstants;
import com.zhi.common.constant.Constants;
import com.zhi.common.constant.UserConstants;
import com.zhi.common.core.domain.entity.SysUser;
import com.zhi.common.core.domain.model.RegisterBody;
import com.zhi.common.core.service.LogininforService;
import com.zhi.common.enums.UserType;
import com.zhi.common.exception.base.BaseException;
import com.zhi.common.exception.user.CaptchaException;
import com.zhi.common.exception.user.CaptchaExpireException;
import com.zhi.common.exception.user.UserException;
import com.zhi.common.utils.MessageUtils;
import com.zhi.common.utils.ServletUtils;
import com.zhi.common.utils.StringUtils;
import com.zhi.common.utils.redis.RedisUtils;
import com.zhi.system.domain.vo.UserVO;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.zhi.common.constant.Constants.AVATAR;
import static com.zhi.common.constant.Constants.TYPE;
import static com.zhi.common.constant.blog.RedisPrefixConst.USER_CODE_KEY;


/**
 * 注册校验方法
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service
public class SysRegisterService {

    private final ISysUserService userService;
    private final ISysConfigService configService;
    private final LogininforService asyncService;

    @Autowired
    private RedisService redisService;





    /**
     * 注册
     */
    public void register(RegisterBody registerBody) {
        HttpServletRequest request = ServletUtils.getRequest();
        String username = registerBody.getUsername();
        String password = registerBody.getPassword();
        // 校验用户类型是否存在
        String userType = UserType.getUserType(registerBody.getUserType()).getUserType();

        boolean captchaEnabled = configService.selectCaptchaEnabled();
        // 验证码开关
        if (captchaEnabled) {
            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid(), request);
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setNickName(username);
        sysUser.setPassword(BCrypt.hashpw(password));
        sysUser.setUserType(userType);

        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(sysUser))) {
            throw new UserException("user.register.save.error", username);
        }
        boolean regFlag = userService.registerUser(sysUser);
        if (!regFlag) {
            throw new UserException("user.register.error");
        }
        asyncService.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success"), request);
    }


    /**
     * 博客前台用户注册
     */
    public  void blogregister(UserVO user){
        HttpServletRequest request = ServletUtils.getRequest();
        // 校验账号是否合法
        if (checkUser(user)) {
            throw new BaseException("用户名已被注册！");
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserName(user.getUsername());
        sysUser.setNickName(user.getUsername());
        sysUser.setPassword(BCrypt.hashpw(user.getPassword()));
        sysUser.setUserType(TYPE);
        sysUser.setAvatar(AVATAR);
        sysUser.setEmail(user.getEmail());
        boolean regFlag = userService.registerUser(sysUser);
        if (!regFlag) {
            throw new UserException("user.register.error");
        }
        asyncService.recordLogininfor(user.getUsername(), Constants.REGISTER, MessageUtils.message("user.register.success"), request);

    }


    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid, HttpServletRequest request) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.defaultString(uuid, "");
        String captcha = RedisUtils.getCacheObject(verifyKey);
        RedisUtils.deleteObject(verifyKey);
        if (captcha == null) {
            asyncService.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.jcaptcha.expire"), request);
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            asyncService.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.jcaptcha.error"), request);
            throw new CaptchaException();
        }
    }


    /**
     * 校验用户数据是否合法
     *
     * @param user 用户数据
     * @return 结果
     */
    private Boolean checkUser(UserVO user) {
        //验证码检验
        if (!user.getCode().equals(redisService.get(USER_CODE_KEY + user.getEmail()))) {
            throw new BaseException("验证码错误！");
        }
        //查询用户名是否存在
        SysUser sysUser = userService.selectUserByUserName(user.getUsername());
        return Objects.nonNull(sysUser);
    }




}
