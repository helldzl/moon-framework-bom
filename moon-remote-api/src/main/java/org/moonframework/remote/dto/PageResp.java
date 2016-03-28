package org.moonframework.remote.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询通用返回结果
 * Created by lcj on 2015/9/7.
 */
public class PageResp<T> implements Serializable {

    private static final long serialVersionUID = -2725284194224465585L;

    /**
     * 总数量
     */
    private Long total;

    /**
     * 查询结果
     */
    private List<T> rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
