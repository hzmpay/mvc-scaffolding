package com.hzm.freestyle;

import com.hzm.freestyle.dto.User;
import com.hzm.freestyle.service.UserService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.util.List;

public class FreestyleApplicationTests extends AbsSpringJunit4Context {

    @Resource
    private UserService userService;
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Test
    public void contextLoads() {

        Integer userId = 1;
        User user = userService.getUserByUserId(userId);
        System.out.println(user);
    }


    @Test
    public void transcationTest() {

        List<User> user = userService.getUser();
        System.out.println(user);
    }



}

