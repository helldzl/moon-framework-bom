package org.moonframework.remote.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 接口返回对象
 * Created by lcj on 2015/9/7.
 */
public class CommonResponse<T,E> implements Serializable{

    private static final long serialVersionUID = 28995106242382816L;
    /**
     * 编码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 错误信息
     * 特殊场景需要用到异常具体信息时候用到
     */
    private E error;

    public boolean isSuccess(){
        return CommonCode.SUCCESS.getCode().compareTo(code) == 0;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public E getError() {
        return error;
    }

    public void setError(E error) {
        this.error = error;
    }
}
