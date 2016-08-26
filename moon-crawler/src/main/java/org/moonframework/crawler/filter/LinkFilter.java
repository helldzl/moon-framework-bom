package org.moonframework.crawler.filter;

/**
 * <p>链接过滤器</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/6/17
 */
public interface LinkFilter {

    /**
     * <p>URL过滤</p>
     *
     * @param url url
     * @return true if accept
     */
    boolean filter(String url);

}
