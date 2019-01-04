package com.hzm.freestyle.controller;

import com.alibaba.fastjson.JSONObject;
import com.hzm.freestyle.dto.User;
import com.hzm.freestyle.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/demo")
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @Resource
    private UserService userService;

    @RequestMapping("/demo")
    public String demo() {
        return "demo";
    }

    @RequestMapping("/user")
    public User user() {
        LOGGER.info("正在查询用户");
        Integer userId = 1;
        User user = userService.getUserByUserId(userId);
        System.out.println(user);
        String userJo = JSONObject.toJSONString(user);
        LOGGER.info("查询结果 : {}", userJo);
        return user;
    }

    @RequestMapping("/getUser")
    public List<User> getUser() {
        return userService.getUser();
    }
}
