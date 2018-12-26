package com.hzm.freestyle.service;

import com.hzm.freestyle.dto.User;

/**
 * ?
 *
 * @author Hezeming
 * @version 1.0
 * @date 2018-12-25
 */
public interface UserService {

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId
     * @return
     */
    User getUserByUserId(String userId);
}
