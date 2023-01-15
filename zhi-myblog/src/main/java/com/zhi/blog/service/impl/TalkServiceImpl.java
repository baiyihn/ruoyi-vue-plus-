package com.zhi.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zhi.blog.domain.vo.PageResult;
import com.zhi.blog.dto.CommentCountDTO;
import com.zhi.blog.dto.TalkDTO;
import com.zhi.blog.mapper.CommentMapper;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhi.common.utils.StringUtils;
import com.zhi.common.utils.blog.HTMLUtils;
import com.zhi.common.utils.blog.PageUtils;
import com.zhi.system.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.zhi.blog.domain.bo.TalkBo;
import com.zhi.blog.domain.vo.TalkVo;
import com.zhi.blog.domain.Talk;
import com.zhi.blog.mapper.TalkMapper;
import com.zhi.blog.service.ITalkService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.zhi.common.constant.blog.RedisPrefixConst.TALK_LIKE_COUNT;

/**
 * 说说管理Service业务层处理
 *
 * @author ftz
 * @date 2023-01-13
 */
@RequiredArgsConstructor
@Service
public class TalkServiceImpl implements ITalkService {

    private final TalkMapper baseMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private CommentMapper commentMapper;


    /**
     * 博客前端首页说说
     * @return
     */
    @Override
    public List<String> listHomeTalks() {
        // 查询最新10条说说
        return baseMapper.selectList(new LambdaQueryWrapper<Talk>()
                .orderByDesc(Talk::getIsTop)
                .orderByDesc(Talk::getId)
                .last("limit 10"))
            .stream()
            .map(item -> item.getContent().length() > 200 ? HTMLUtils.deleteTag(item.getContent().substring(0, 200)) : HTMLUtils.deleteTag(item.getContent()))
            .collect(Collectors.toList());
    }


    /**
     * 博客前端说说列表
     * @return
     */
    @Override
    public PageResult<TalkDTO> listTalks() {
        // 查询说说总量
        Long count = baseMapper.selectCount(new LambdaQueryWrapper<>());
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询说说
        List<TalkDTO> talkDTOList = baseMapper.listTalks(PageUtils.getLimitCurrent(), PageUtils.getSize());

        // 查询说说评论量
        List<Integer> topicIdList = talkDTOList.stream()
            .map(TalkDTO::getId)
            .collect(Collectors.toList());
        System.out.println(topicIdList.size()+"这是数量");
        Map<Integer, Integer> commentCountMap = commentMapper.listCommentCountByTopicIds(topicIdList)
            .stream()
            .collect(Collectors.toMap(CommentCountDTO::getId, CommentCountDTO::getCommentCount));


         //查询说说点赞量
        Map<String, Object> likeCountMap = redisService.hGetAll(TALK_LIKE_COUNT);
        talkDTOList.forEach(item -> {
            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
            item.setCommentCount(commentCountMap.get(item.getId()));
            // 转换图片格式
            if (Objects.nonNull(item.getImages())) {
                List<String> list = Arrays.asList(item.getImages().split(","));
                // 将图片转换为url路径
                List<String> urlList = new ArrayList<>();
                for (int i = 0;i<list.size();i++){
                    String url = baseMapper.imageUrl(Long.parseLong(list.get(i)));
                    urlList.add(url);
                    item.setImages(url);
                }
                item.setImgList(urlList);

            }
        });
        return new PageResult<>(talkDTOList, Integer.parseInt(String.valueOf(count)));
    }


    /**
     * 根据id查询说说管理
     */
    @Override
    public TalkVo queryById(Long id){
        TalkVo talkVo = baseMapper.BackTalks(id);
        if (Objects.nonNull(talkVo.getImages())){
            List<String> list = Arrays.asList(talkVo.getImages().split(","));
            // 将图片转换为url路径
            talkVo.setImgList(list);
        }
        return talkVo;
    }

    /**
     * 查询说说管理列表
     */
    @Override
    public TableDataInfo<TalkVo> queryPageList(TalkBo bo, PageQuery pageQuery) {
        Long count = baseMapper.selectCount(new LambdaQueryWrapper<>());
        if (count == 0) {
            return new TableDataInfo<>();
        }
        List<TalkVo> talkVos = baseMapper.listBackTalks();
        talkVos.forEach(item ->{
            if (Objects.nonNull(item.getImages())){
                List<String> list = Arrays.asList(item.getImages().split(","));
                // 将图片转换为url路径
                List<String> urlList = new ArrayList<>();
                for (int i = 0;i<list.size();i++){
                    String url = baseMapper.imageUrl(Long.parseLong(list.get(i)));
                    urlList.add(url);
                    item.setImages(url);
                }
                item.setImgList(urlList);

            }
        });
        return TableDataInfo.build(talkVos);
    }

    /**
     * 查询说说管理列表
     */
    @Override
    public List<TalkVo> queryList(TalkBo bo) {
        LambdaQueryWrapper<Talk> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Talk> buildQueryWrapper(TalkBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Talk> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, Talk::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), Talk::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getImages()), Talk::getImages, bo.getImages());
        lqw.eq(bo.getIsTop() != null, Talk::getIsTop, bo.getIsTop());
        return lqw;
    }

    /**
     * 新增说说管理
     */
    @Override
    public Boolean insertByBo(TalkBo bo) {
        bo.setUserId(baseMapper.getUserIdByName(bo.getCreateBy()));
        if (Objects.nonNull(bo.getImages())){
            List<String> list = Arrays.asList(bo.getImages().split(","));
            // 将图片转换为url路径
            bo.setImgList(list);
        }
        Talk add = BeanUtil.toBean(bo, Talk.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改说说管理
     */
    @Override
    public Boolean updateByBo(TalkBo bo) {
        bo.setUserId(baseMapper.getUserIdByName(bo.getCreateBy()));
        if (Objects.nonNull(bo.getImages())){
            List<String> list = Arrays.asList(bo.getImages().split(","));
            // 将图片转换为url路径
            bo.setImgList(list);
        }
        Talk update = BeanUtil.toBean(bo, Talk.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Talk entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除说说管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
