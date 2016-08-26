package org.moonframework.remote.dto;

/**
 * 通用提示消息枚举
 * Created by lcj on 2015/9/16.
 */
public enum CommonCode {
    SUCCESS(100, "操作成功"),
    FAILURE(101, "操作失败"),
    ERROR_UNKNOW(102, "未知错误"),
    IP_BLACKLIST(103, "IP黑名单"),
    WITHOUT_PERMISSION(104, "角色未授权"),
    ERROR_TIMESTAMP(105, "请求超时"),
    ERROR_SIGNUSED(106, "重复提交"),
    SERVICE_BUSY(107, "服务器繁忙"),
    PARAM_INVALID(108, "无效参数"),

    UNKNOWN_ACCOUNT(201, "未知账户"),
    INCORRECT_CREDENTIALS(202, "密码错误"),
    DISABLED_ACCOUNT(203, "账号未激活"),
    LOCKED_ACCOUNT(204, "账号已锁定"),
    AUTHENTICATION_EXCEPTION(205, "认证失败"),
    KARMA_NOTENOUGH(206,"可用积分不够,请刷新再尝试"),
    CODES_NOTENOUGH(207,"剩余可购买次数不够，请刷新再尝试");

    private int code;
    private String message;

    CommonCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return Integer.valueOf(this.code);
    }

    public String getMessage() {
        return this.message;
    }
}