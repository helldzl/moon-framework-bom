package org.moonframework.crawler.parse;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/17
 */
@FunctionalInterface
public interface RankUrl {

    /**
     * <p>如果是主题相关的就进行处理, 否则就过滤掉</p>
     *
     * @param url         URL
     * @param pageContent 网页内容
     * @return true 如果是主题相关的
     */
    boolean accept(String url, String pageContent);

}
