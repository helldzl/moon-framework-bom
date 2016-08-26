package org.moonframework.crawler.fetcher;

import org.apache.http.impl.client.CloseableHttpClient;
import org.moonframework.concurrent.OptionalThreadPool;
import org.moonframework.concurrent.Task;
import org.moonframework.crawler.http.WebHttpClientUtils;
import org.moonframework.crawler.storage.WebPage;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/3
 */
public class Fetcher extends OptionalThreadPool<WebPage, WebPage> implements Closeable {

    /**
     * HTTP Client
     */
    private CloseableHttpClient httpClient;

    /**
     * 结果队列
     */
    private BlockingQueue<WebPage> result;

    /**
     * @param url    url
     * @param result result
     * @param n      n
     */
    public Fetcher(BlockingQueue<WebPage> url, BlockingQueue<WebPage> result, int n) {
        super(url, n);
        this.result = result;
        this.httpClient = WebHttpClientUtils.getInstance();
        // this.httpClient = HttpClients.createDefault();
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    protected Task<WebPage, WebPage> newTask(WebPage input) {
        return new FetcherJob(input.getConnectionType().getInstance(), this, result);
    }

    /**
     * safe net
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        try {
            close();
        } catch (Exception ignored) {
        }
    }

    //

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

}
