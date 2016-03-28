package org.moonframework.remote.dto;

import java.io.Serializable;

/**
 * 接口参数Base
 * Created by lcj on 2015/9/7.
 */
public class Base implements Serializable {

    private static final long serialVersionUID = 2786311503436078745L;
    /**
     * 操作人ID
     */
    private Long operId;

    /**
     * 当前登陆账户ID(一般情况下operId和accountId相同)
     */
    private Long accountId;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 客户端ip
     */
    private String ip;

    /**
     * 语言
     */
    private String language;

    /**
     * 客户端坐标信息
     */
    private String location;

    public Long getOperId() {
        return operId;
    }

    public void setOperId(Long operId) {
        this.operId = operId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
