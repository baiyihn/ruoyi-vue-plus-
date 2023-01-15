package com.zhi.web.controller.system;

import cn.dev33.satoken.annotation.SaIgnore;
import com.alibaba.fastjson.JSON;
import com.zhi.common.annotation.AccessLimit;
import com.zhi.common.core.controller.BaseController;
import com.zhi.common.core.domain.R;
import com.zhi.common.core.domain.model.RegisterBody;
import com.zhi.common.exception.base.BaseException;
import com.zhi.system.domain.vo.UserVO;
import com.zhi.system.dto.EmailDTO;
import com.zhi.system.service.ISysConfigService;
import com.zhi.system.service.RedisService;
import com.zhi.system.service.SysRegisterService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.zhi.common.constant.blog.MQPrefixConst.EMAIL_EXCHANGE;
import static com.zhi.common.constant.blog.RedisPrefixConst.CODE_EXPIRE_TIME;
import static com.zhi.common.constant.blog.RedisPrefixConst.USER_CODE_KEY;
import static com.zhi.common.utils.blog.CommonUtils.checkEmail;
import static com.zhi.common.utils.blog.CommonUtils.getRandomCode;

/**
 * 注册验证
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
public class SysRegisterController extends BaseController {

    private final SysRegisterService registerService;
    private final ISysConfigService configService;

    @Resource
    private RabbitTemplate rabbitTemplate;


    @Resource
    private RedisService redisService;

    /**
     * 用户注册
     */
    @SaIgnore
    @PostMapping("/register")
    public R<Void> register(@Validated @RequestBody RegisterBody user) {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return R.fail("当前系统没有开启注册功能！");
        }
        registerService.register(user);
        return R.ok();
    }


    /**
     * 前台用户注册邮箱验证码检测
     */
    @SaIgnore
    @AccessLimit(seconds = 60, maxCount = 1)
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String")
    @GetMapping("/users/code")
    public R<?> sendCode(String email) {
        // 校验邮箱是否合法
        if (!checkEmail(email)) {
            throw new BaseException("请输入正确邮箱");
        }
        // 生成六位随机验证码发送
        String code = getRandomCode();
        // 发送验证码
        EmailDTO emailDTO = EmailDTO.builder()
            .email(email)
            .subject("验证码")
            .content("您的验证码为 " + code + " 有效期15分钟，请不要告诉他人哦！")
            .build();
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        // 将验证码存入redis，设置过期时间为15分钟
        redisService.set(USER_CODE_KEY + email, code, CODE_EXPIRE_TIME);
        return R.ok();
    }


    /**
     * 前台用户注册
     *
     * @param user 用户信息
     * @return {@link R<>}
     */
    @SaIgnore
    @ApiOperation(value = "用户注册")
    @PostMapping("/blog/register")
    public R<?> register(@Valid @RequestBody UserVO user) {
        registerService.blogregister(user);
        return R.ok();
    }


}
