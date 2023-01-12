package com.zhi.blog.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.zhi.blog.domain.vo.BlogHomeInfoVo;
import com.zhi.blog.service.IBlogInfoService;
import com.zhi.common.core.domain.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/12 15:43
 */
@RestController
@RequestMapping("/blogInfo")
public class BlogInfoController {


    @Resource
    private IBlogInfoService blogInfoService;

    /**
     * 查看博客信息
     *
     */
    @SaIgnore
    @ApiOperation(value = "查看博客信息")
    @GetMapping()
    public R<BlogHomeInfoVo> getBlogHomeInfo() {
        return R.ok(blogInfoService.getBlogHomeInfo());
    }



    /**
     * 上传访客信息
     *
     * @return {@link R}
     */
    @SaIgnore
    @PostMapping("/report")
    public R<?> report() {
        blogInfoService.report();
        return R.ok();
    }




}
