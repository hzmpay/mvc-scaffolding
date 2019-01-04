package com.hzm.freestyle.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * asdasdasd
 *
 * @author Hezeming
 * @version 1.0
 * @date 2018-12-24
 */
public class User {

    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户真实姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 注册时间
     */
    private Date crtTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", crtTime=" + crtTime +
                '}';
    }
}
