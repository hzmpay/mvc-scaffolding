package com.hzm.freestyle.controller;

import com.hzm.freestyle.dto.User;
import com.hzm.freestyle.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private UserService userService;

    @RequestMapping("/demo")
    public String demo() {
        return "demo";
    }

    @RequestMapping("user")
    public User getUser() {
        String userId = "U00FB219D222391B4";
        User user = userService.getUserByUserId(userId);
        System.out.println(user);
        return user;
    }
}
