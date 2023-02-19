package com.zhi.blog.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.zhi.blog.domain.vo.BlogHomeInfoVo;
import com.zhi.blog.dto.vo.BlogInfoVO;
import com.zhi.blog.service.IBlogInfoService;
import com.zhi.common.core.controller.BaseController;
import com.zhi.common.core.domain.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/12 15:43
 */
@RestController
@RequestMapping("/blogInfo")
public class BlogInfoController extends BaseController {


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


    /**
     * 查看关于我的信息
     */
    @SaIgnore
    @GetMapping("/about")
    public R<String> getAbout() {
        return R.ok("操作成功",blogInfoService.getAbout());
    }


    /**
     * 修改关于我的信息
     */
    @ApiOperation(value = "修改关于我信息")
    @PutMapping("/admin/about")
    public R<?> updateAbout( @RequestBody BlogInfoVO blogInfoVO) {
        blogInfoService.updateAbout(blogInfoVO);
        return R.ok();
    }




}
