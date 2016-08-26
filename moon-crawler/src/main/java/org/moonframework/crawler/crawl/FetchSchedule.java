package org.moonframework.crawler.crawl;

import org.moonframework.crawler.facade.WebCrawler;
import org.moonframework.crawler.storage.WebPage;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/15
 */
public class FetchSchedule {

    private WebCrawler crawler;

    public <T, R extends WebPage> void fetch(Iterator<T> iterator, Function<T, R> function, Supplier<Iterator<T>> supplier) {
        while (iterator != null) {
            iterator.forEachRemaining(t -> crawler.fetchUrl(function.apply(t)));
            iterator = supplier.get();
        }
    }

    public void setCrawler(WebCrawler crawler) {
        this.crawler = crawler;
    }
}
