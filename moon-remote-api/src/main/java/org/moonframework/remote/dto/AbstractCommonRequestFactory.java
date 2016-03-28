package org.moonframework.remote.dto;

import java.io.Serializable;

/**
 * request构建工厂类
 */
public abstract class AbstractCommonRequestFactory {

    public <T extends Param> CommonRequest<T> getCommonRequest(T dto) {
        CommonRequest<T> commonRequest = new CommonRequest<T>();
        commonRequest.setParam(dto);
        commonRequest.setBase(getBase());
        return commonRequest;
    }

    public abstract Base getBase();

}
