package com.zhi.blog.service;

import com.zhi.blog.domain.FriendLink;
import com.zhi.blog.domain.vo.FriendLinkVo;
import com.zhi.blog.domain.bo.FriendLinkBo;
import com.zhi.common.core.page.TableDataInfo;
import com.zhi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 友链管理Service接口
 *
 * @author ftz
 * @date 2023-01-12
 */
public interface IFriendLinkService {

    /**
     * 查询友链管理
     */
    FriendLinkVo queryById(Long id);

    /**
     * 查询友链管理列表
     */
    TableDataInfo<FriendLinkVo> queryPageList(FriendLinkBo bo, PageQuery pageQuery);

    /**
     * 查询友链管理列表
     */
    List<FriendLinkVo> queryList(FriendLinkBo bo);

    /**
     * 新增友链管理
     */
    Boolean insertByBo(FriendLinkBo bo);

    /**
     * 修改友链管理
     */
    Boolean updateByBo(FriendLinkBo bo);

    /**
     * 校验并批量删除友链管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
