package com.hzm.freestyle;

import com.hzm.freestyle.dto.User;
import com.hzm.freestyle.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FreestyleApplicationTests {
    
    @Resource
    private UserService userService;

    @Test
    public void contextLoads() {

        String userId = "U00FB219D222391B4";
        User user = userService.getUserByUserId(userId);
        System.out.println(user);
    }

}

