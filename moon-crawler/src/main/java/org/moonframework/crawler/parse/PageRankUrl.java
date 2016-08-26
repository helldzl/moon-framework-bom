package org.moonframework.crawler.parse;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/17
 */
public class PageRankUrl implements RankUrl {

    @Override
    public boolean accept(String url, String pageContent) {
        // TODO page rank 算法, 参考google实现
        return true;
    }

}
