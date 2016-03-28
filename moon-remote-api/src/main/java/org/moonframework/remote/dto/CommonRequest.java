package org.moonframework.remote.dto;

import java.io.Serializable;

/**
 * 接口参数
 * Created by lcj on 2015/9/7.
 */
public class CommonRequest<T extends Param> implements Serializable {

    private static final long serialVersionUID = -1208494747362535596L;

    /**
     * 基础参数
     */
    private Base base;

    /**
     * 业务参数
     */
    private T param;

    public static <T extends Param> CommonRequest<T> of(T t) {
        return new CommonRequest<>(t);
    }

    public CommonRequest() {
    }

    public CommonRequest(T param) {
        this.param = param;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }
}
