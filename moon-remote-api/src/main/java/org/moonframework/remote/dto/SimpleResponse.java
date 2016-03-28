package org.moonframework.remote.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lcj on 2015/12/31.
 */
public class SimpleResponse<T> extends CommonResponse<T,List<SimpleErrorResource>> implements Serializable{
    private static final long serialVersionUID = -8762457118768002640L;

    public static SimpleResponseBuilder builder() {
        SimpleResponseBuilder simpleResponseBuilder = new SimpleResponseBuilder();
        return simpleResponseBuilder;
    }
}
