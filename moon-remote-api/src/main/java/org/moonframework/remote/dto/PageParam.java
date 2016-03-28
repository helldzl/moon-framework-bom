package org.moonframework.remote.dto;

import java.io.Serializable;

/**
 * 分页查询通用参数
 * Created by lcj on 2015/9/7.
 */
public class PageParam extends ServiceBeanNameParam implements Serializable {

    private static final long serialVersionUID = 5198964706752911489L;
    // 默认分页
    private static final int default_pageNumber = 1;

    // 默认分页大小
    private static final int default_pageSize = 20;

    // 最小分页大小
    private static final int min_pageSize = 1;

    // 最大分页大小
    private static final int max_pageSize = 1000;

    /**
     * 分页
     */
    private Integer pageNumber = default_pageNumber;

    /**
     * 分页大小
     */
    private Integer pageSize = default_pageSize;

    private boolean asc = true;

    public PageParam() {
    }

    public PageParam(String targetClassName) {
        super(targetClassName);
    }

    public PageParam(String targetClassName, Class<? extends Param> responseClass) {
        super(targetClassName, responseClass);
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {

        if (pageNumber == null || pageNumber == 0) {
            return;
        }

        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {

        if (pageSize == null || pageSize < min_pageSize || pageSize > max_pageSize) {
            return;
        }

        this.pageSize = pageSize;
    }

    public Integer from() {
        return (getPageNumber()-1)*getPageSize();
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }
}
