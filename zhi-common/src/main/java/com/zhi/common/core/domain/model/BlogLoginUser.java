package com.zhi.common.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ftz-lover
 * @version 1.0
 * @date 2023/1/15 13:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogLoginUser implements Serializable {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户邮件
     */
    private String email;

    /**
     * 用户ip
     */
    private String ipAddress;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;






}
