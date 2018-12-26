package com.hzm.freestyle.service.impl;

import com.hzm.freestyle.dto.User;
import com.hzm.freestyle.mapper.UserMapper;
import com.hzm.freestyle.service.UserService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ?
 *
 * @author Hezeming
 * @version 1.0
 * @date 2018-12-25
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public User getUserByUserId(String userId) {
//        return userMapper.getUserByUserId(userId);
        return sqlSessionTemplate.selectOne("UserMapper.getUserByUserId", userId);
    }
}
