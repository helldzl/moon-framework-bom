package org.moonframework.crawler.fetcher;

import org.moonframework.concurrent.BlockingQueueTask;
import org.moonframework.crawler.storage.WebPage;

import java.util.concurrent.BlockingQueue;

/**
 * <p>只负责下载任务</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/6/3
 */
public class FetcherJob extends BlockingQueueTask<WebPage, WebPage> {

    private HttpConnection connection;

    /**
     * @param connection connection
     * @param fetcher    fetcher
     * @param queue      将结果放入队列中
     */
    public FetcherJob(HttpConnection connection, Fetcher fetcher, BlockingQueue<WebPage> queue) {
        super(fetcher, queue);
        this.connection = connection;
    }


    @Override
    protected WebPage call(WebPage page) throws Exception {
        connection.accept(page);
        if (page.getStatusCode() >= 200 && page.getStatusCode() < 300) {
            return page;
        }
        return null;
    }

}
