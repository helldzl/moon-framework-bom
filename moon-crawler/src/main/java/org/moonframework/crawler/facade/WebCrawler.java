package org.moonframework.crawler.facade;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moonframework.crawler.fetcher.Fetcher;
import org.moonframework.crawler.parse.DefaultParser;
import org.moonframework.crawler.storage.WebPage;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/16
 */
public class WebCrawler implements Closeable {

    protected final Logger logger = LogManager.getLogger(WebCrawler.class);

    /**
     *
     */
    private ExecutorService service = Executors.newFixedThreadPool(2);

    /**
     * LINK 队列
     */
    private BlockingQueue<WebPage> linkQueue;

    /**
     * 解析 队列
     */
    private BlockingQueue<WebPage> parseQueue;

    /**
     * Url Fetch Producer Module
     */
    private Fetcher fetcher;

    /**
     * Url Parser Consumer Module
     */
    private DefaultParser parser;

    public WebCrawler(DefaultParser parser) {
        this.linkQueue = new LinkedBlockingQueue<>();
        this.parseQueue = new LinkedBlockingQueue<>();
        this.fetcher = new Fetcher(linkQueue, parseQueue, 6);
        this.parser = parser;

        if (parser.getLinkQueue() == null)
            parser.setLinkQueue(linkQueue);
        if (parser.getParseQueue() == null)
            parser.setParseQueue(parseQueue);
    }

    public WebCrawler(BlockingQueue<WebPage> linkQueue, BlockingQueue<WebPage> parseQueue, Fetcher fetcher, DefaultParser parser) {
        this.linkQueue = linkQueue;
        this.parseQueue = parseQueue;
        this.fetcher = fetcher;
        this.parser = parser;
    }

    /**
     * 启动
     */
    public void start() {
        // 启动网页抓取, 生产者
        logger.info(() -> "######### Start Producer! #########");
        service.execute(() -> fetcher.execute());

        // 启动网页解析, 消费者
        logger.info(() -> "######### Start Consumer! #########");
        service.execute(() -> parser.execute());
    }

    /**
     * 释放资源
     */
    @Override
    public void close() throws IOException {
        fetcher.shutdown();
        parser.setRunnable(false);
        service.shutdown();
        logger.info(() -> "Close Producer and Consumer!");
    }

    /**
     * <p>抓取page信息</p>
     *
     * @param page
     */
    public void fetchUrl(WebPage page) {
        linkQueue.add(page);
    }

    // get and set

    public BlockingQueue<WebPage> getLinkQueue() {
        return linkQueue;
    }

    public void setLinkQueue(BlockingQueue<WebPage> linkQueue) {
        this.linkQueue = linkQueue;
    }

    public BlockingQueue<WebPage> getParseQueue() {
        return parseQueue;
    }

    public void setParseQueue(BlockingQueue<WebPage> parseQueue) {
        this.parseQueue = parseQueue;
    }

    public Fetcher getFetcher() {
        return fetcher;
    }

    public void setFetcher(Fetcher fetcher) {
        this.fetcher = fetcher;
    }

    public DefaultParser getParser() {
        return parser;
    }

    public void setParser(DefaultParser parser) {
        this.parser = parser;
    }
}
