package org.moonframework.office.excel;

import org.moonframework.office.ResultEntity;

import java.util.List;

/**
 * @param <T>
 * @author quzile
 */
public class ExcelResultEntity<T> implements ResultEntity<T> {

    private List<T> list;

    private int correctNum;

    public ExcelResultEntity(List<T> list, int correctNum) {
        this.list = list;
        this.correctNum = correctNum;
    }

    /**
     * 总数
     *
     * @return
     */
    public int size() {
        return list.size();
    }

    /**
     * @return
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 成功的数量
     *
     * @return
     */
    public int getCorrectNum() {
        return correctNum;
    }

}
