package org.moonframework.model.mybatis.criterion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/19
 */
public class QueryCondition {

    private List<QueryToken> tokens = new ArrayList<>();

    public QueryCondition token(QueryToken queryToken) {
        tokens.add(queryToken);
        return this;
    }

    public QueryCondition stringToken(String value) {
        tokens.add(new StringToken(value));
        return this;
    }

    public QueryCondition valueToken(Object value) {
        tokens.add(new ValueToken(value));
        return this;
    }

    public List<QueryToken> getTokens() {
        return tokens;
    }

}
