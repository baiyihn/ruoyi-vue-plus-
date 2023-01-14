package com.zhi.blog.mapper;

import com.zhi.blog.domain.Talk;
import com.zhi.blog.domain.vo.TalkVo;
import com.zhi.blog.dto.TalkDTO;
import com.zhi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 说说管理Mapper接口
 *
 * @author ftz
 * @date 2023-01-13
 */
public interface TalkMapper extends BaseMapperPlus<TalkMapper, Talk, TalkVo> {

    /**
     * 获取说说列表
     *
     * @param current 页码
     * @param size    大小
     * @return {@link List<TalkDTO>}
     */
    List<TalkDTO> listTalks(@Param("current") Long current, @Param("size") Long size);




    /**
     * 查看后台说说
     *
     */
    @Select("select \n" +
        "\tt.id,\n" +
        "\tt.content,\n" +
        "\tt.images,\n" +
        "\tt.create_time,\n" +
        "\tu.nick_name,\n" +
        "\tu.avatar ,\n" +
        "\tt.create_by\n" +
        "\tfrom \n" +
        "\tblog_talk t \n" +
        "\tLEFT JOIN sys_user u\n" +
        "\tON t.user_id = u.user_id")
    List<TalkVo> listBackTalks();


    /**
     * 根据id查询后台说说
     */
    @Select("select \n" +
        "\tt.id,\n" +
        "\tt.content,\n" +
        "\tt.images,\n" +
        "\tt.create_time,\n" +
        "\tu.nick_name,\n" +
        "\tu.avatar ,\n" +
        "\tt.create_by\n" +
        "\tfrom \n" +
        "\tblog_talk t \n" +
        "\tLEFT JOIN sys_user u\n" +
        "\tON t.user_id = u.user_id\n" +
        "\twhere t.id = #{id}\n")
    TalkVo BackTalks(Long id);


    /**
     * 将图片id转换为oss中的url
     */
    @Select("select o.url from sys_oss o where o.oss_id = #{url}")
    String imageUrl(@Param("url") Long url);

    /**
     * 根据用户名获取当前登录者id
     */
    @Select("select u.user_id from sys_user u where user_name = #{username} ")
    Long getUserIdByName(String username);







}
