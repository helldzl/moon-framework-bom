package org.moonframework.model.mybatis.criterion;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/19
 */
public class Disjunction extends Junction {

    protected Disjunction() {
        super(Nature.OR);
    }

    protected Disjunction(Criterion[] conditions) {
        super(Nature.OR, conditions);
    }

}
