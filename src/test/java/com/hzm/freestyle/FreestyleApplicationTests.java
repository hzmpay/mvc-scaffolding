package com.hzm.freestyle;

import com.hzm.freestyle.dto.User;
import com.hzm.freestyle.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FreestyleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FreestyleApplicationTests {

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

