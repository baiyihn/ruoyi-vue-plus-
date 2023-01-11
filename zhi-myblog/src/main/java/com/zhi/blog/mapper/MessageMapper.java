package com.zhi.blog.mapper;

import com.zhi.blog.domain.Message;
import com.zhi.blog.domain.vo.MessageVo;
import com.zhi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 留言管理Mapper接口
 *
 * @author ftz
 * @date 2023-01-11
 */
public interface MessageMapper extends BaseMapperPlus<MessageMapper, Message, MessageVo> {

    /**
     * 根据id修改留言状态
     */
    @Update("update blog_message set is_review = #{review} where id = #{id}")
    int UpdateMessageReviewById(@Param("id") Long id,@Param("review") String review);



}
