package org.moonframework.model.mybatis.criterion;

import java.util.*;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/19
 */
public class Junction implements Criterion {

    private final Nature nature;
    private final List<Criterion> conditions = new ArrayList<>();

    protected Junction(Nature nature) {
        this.nature = nature;
    }

    protected Junction(Nature nature, Criterion... criterion) {
        this(nature);
        Collections.addAll(conditions, criterion);
    }

    /**
     * Adds a criterion to the junction (and/or)
     *
     * @param criterion The criterion to add
     * @return {@code this}, for method chaining
     */
    public Junction add(Criterion criterion) {
        conditions.add(criterion);
        return this;
    }

    public Nature getNature() {
        return nature;
    }

    /**
     * Access the conditions making up the junction
     *
     * @return the criterion
     */
    public Iterable<Criterion> conditions() {
        return conditions;
    }

    @Override
    public void toSqlString(QueryCondition condition) {
        if (conditions.size() == 0) {
            condition.stringToken("1=1");
            return;
        }

        condition.stringToken("(");
        Iterator<Criterion> it = conditions.iterator();
        boolean hasNext = it.hasNext();
        while (hasNext) {
            it.next().toSqlString(condition);
            if (hasNext = it.hasNext()) {
                condition.stringToken(" " + nature.getOperator() + " ");
            }
        }
        condition.stringToken(")");
    }

    @Override
    public String toString() {
        return '(' + StringHelper.join(' ' + nature.getOperator() + ' ', conditions.iterator()) + ')';
    }

    /**
     * The type of junction
     */
    public enum Nature {

        /**
         * An AND
         */
        AND,
        /**
         * An OR
         */
        OR;

        /**
         * <p>The corresponding SQL operator</p>
         *
         * @return SQL operator
         */
        public String getOperator() {
            return name().toUpperCase(Locale.ROOT);
        }
    }

}
