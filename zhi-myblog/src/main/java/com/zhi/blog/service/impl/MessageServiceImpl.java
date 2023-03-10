package com.zhi.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zhi.blog.dto.MessageDTO;
import com.zhi.blog.dto.vo.MessageVO;
import com.zhi.blog.service.IWebsiteConfigService;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhi.common.utils.BeanCopyUtils;
import com.zhi.common.utils.StringUtils;
import com.zhi.common.utils.blog.IpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.zhi.blog.domain.bo.MessageBo;
import com.zhi.blog.domain.vo.MessageVo;
import com.zhi.blog.domain.Message;
import com.zhi.blog.mapper.MessageMapper;
import com.zhi.blog.service.IMessageService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Collection;

import static com.zhi.common.constant.blog.CommonConst.*;

/**
 * 留言管理Service业务层处理
 *
 * @author ftz
 * @date 2023-01-11
 */
@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements IMessageService {

    private final MessageMapper baseMapper;

    @Resource
    private IWebsiteConfigService websiteConfigService;

    @Resource
    private HttpServletRequest request;


    /**
     * 前台添加留言
     * @param messageVO
     */
    @Override
    public void saveMessage(MessageVO messageVO) {
        // 判断是否需要审核
        Integer isReview = websiteConfigService.getWebsiteConfig().getIsMessageReview();
        // 获取用户ip
        String ipAddress = IpUtils.getIpAddress(request);
        String ipSource = IpUtils.getIpSource(ipAddress);
        Message message = BeanCopyUtils.copyObject(messageVO, Message.class);
        message.setIpAddress(ipAddress);
        message.setIsReview(isReview == TRUE ? YES : NO );
        message.setIpSource(ipSource);
        baseMapper.insert(message);
    }

    /**
     * 查看前台留言弹幕
     * @return
     */
    @Override
    public List<MessageDTO> listMessages() {
        // 查询留言列表
        List<Message> messageList = baseMapper.selectList(new LambdaQueryWrapper<Message>()
            .select(Message::getId, Message::getNickname, Message::getAvatar, Message::getMessageContent, Message::getTime)
            .eq(Message::getIsReview,YES));
        return BeanCopyUtils.copyList(messageList, MessageDTO.class);
    }

    /**
     * 查询留言管理
     */
    @Override
    public MessageVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询留言管理列表
     */
    @Override
    public TableDataInfo<MessageVo> queryPageList(MessageBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Message> lqw = buildQueryWrapper(bo);
        Page<MessageVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询留言管理列表
     */
    @Override
    public List<MessageVo> queryList(MessageBo bo) {
        LambdaQueryWrapper<Message> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Message> buildQueryWrapper(MessageBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Message> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getNickname()), Message::getNickname, bo.getNickname());
        lqw.eq(StringUtils.isNotBlank(bo.getAvatar()), Message::getAvatar, bo.getAvatar());
        lqw.eq(StringUtils.isNotBlank(bo.getMessageContent()), Message::getMessageContent, bo.getMessageContent());
        lqw.eq(StringUtils.isNotBlank(bo.getIpAddress()), Message::getIpAddress, bo.getIpAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getIpSource()), Message::getIpSource, bo.getIpSource());
        lqw.eq(bo.getTime() != null, Message::getTime, bo.getTime());
        lqw.eq(StringUtils.isNotBlank(bo.getIsReview()), Message::getIsReview, bo.getIsReview());
        return lqw;
    }

    /**
     * 新增留言管理
     */
    @Override
    public Boolean insertByBo(MessageBo bo) {
        Message add = BeanUtil.toBean(bo, Message.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改留言管理
     */
    @Override
    public Boolean updateByBo(MessageBo bo) {
        Message update = BeanUtil.toBean(bo, Message.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Message entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除留言管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }


    /**
     * 审核留言
     * @param id
     * @param review
     * @return
     */
    @Override
    public Boolean auditMessage(Long id, Boolean review) {
        return baseMapper.UpdateMessageReviewById(id,review?"Y":"N")>0;
    }
}
