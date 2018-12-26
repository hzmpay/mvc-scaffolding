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
    private String userId;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户真实姓名
     */
    private String realName;
    /**
     * 当前门店ID
     */
    private String storeId;
    /**
     * 商户ID
     */
    private String companyId;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 用户类型：0=普通用户，1=VIP会员，2=银卡会员，3=金卡会员，4=钻石会员
     */
    private Integer type;
    /**
     * 用户状态：0=正常，1=异常锁定
     */
    private Integer status;
    /**
     * 当前TOKEN
     */
    private String accessToken;
    /**
     * 注册时间
     */
    private Date crtTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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
                ", phone='" + phone + '\'' +
                ", realName='" + realName + '\'' +
                ", storeId='" + storeId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", type=" + type +
                ", status=" + status +
                ", accessToken='" + accessToken + '\'' +
                ", crtTime=" + crtTime +
                '}';
    }
}
