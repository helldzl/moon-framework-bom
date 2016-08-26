package org.moonframework.crawler.parse;

import java.util.function.Function;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/15
 */
public interface Parser<T, R> extends Function<T, R> {
}
