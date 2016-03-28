package org.moonframework.model.mybatis.criterion;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/19
 */
public class Conjunction extends Junction {

    public Conjunction() {
        super(Nature.AND);
    }

    protected Conjunction(Criterion... criterion) {
        super(Nature.AND, criterion);
    }

}
