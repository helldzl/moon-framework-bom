package org.moonframework.crawler.fetcher;

import org.moonframework.crawler.storage.WebPage;

import java.util.function.Consumer;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/11
 */
public interface HttpConnection extends Consumer<WebPage> {

    @Override
    void accept(WebPage page);

}
