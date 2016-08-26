package org.moonframework.crawler.parse;

import org.moonframework.crawler.analysis.PageAnalyzer;
import org.moonframework.crawler.storage.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/16
 */
public abstract class DefaultParser extends AbstractParser {

    /**
     * URL队列
     */
    private BlockingQueue<WebPage> linkQueue;

    /**
     * 解析队列
     */
    private BlockingQueue<WebPage> parseQueue;

    /**
     * 分析器
     */
    private PageAnalyzer analyzer;

    /**
     * 持久化接口
     */
    private Persistence persistence;

    /**
     * 主题相关度计算, 只处理相关的URL
     */
    private RankUrl rankUrl = new PageRankUrl();

    private volatile boolean runnable = true;

    /**
     * start
     */
    public void execute() {
        while (runnable) {
            WebPage webPage = null;
            try {
                // 从队列获取数据
                webPage = parseQueue.take();
                long start = System.currentTimeMillis();
                String url = webPage.getUri().toString();

                // 检查网页相关度
                logger.debug(() -> "Parsing URL [Start] : " + url);
                if (!rankUrl.accept(url, webPage.getHtml())) {
                    logger.info(() -> "Parsing URL [Not accept] : " + url);
                    discard(webPage);
                    continue;
                }

                // 处理解析结果
                process(webPage);

                long end = System.currentTimeMillis();
                logger.info(() -> "Parsing URL [Successful] in (" + (end - start) + ")ms: " + url);
            } catch (Exception e) {
                error(webPage);
                logger.error("Parsing URL [Error]", e);
            }
        }
    }

    /**
     * <p>判断节点类型, 如果是链接节点就执行发现URL的操作, 如果是内容节点就执行持久化的操作</p>
     *
     * @param page page
     */
    protected void process(WebPage page) {
        ParseResult result = apply(page);
        result.getMap().forEach((node, segments) -> {
            switch (node.getNodeType()) {
                case LINKS:
                    addLink(page, node, segments);
                    break;
                case CONTENT:
                    // TODO LINKED CONTENT
                    if (analyzer == null || analyzer.analyze(segments)) {
                        persistence.persist(node.getClassName(), segments);
                    }
                    break;
                default:
                    break;
            }
        });
    }

    // get and set method

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

    public PageAnalyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(PageAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public Persistence getPersistence() {
        return persistence;
    }

    public void setPersistence(Persistence persistence) {
        this.persistence = persistence;
    }

    public RankUrl getRankUrl() {
        return rankUrl;
    }

    public void setRankUrl(RankUrl rankUrl) {
        this.rankUrl = rankUrl;
    }

    public boolean isRunnable() {
        return runnable;
    }

    public void setRunnable(boolean runnable) {
        this.runnable = runnable;
    }

    /**
     * <p>将新链接加入到队列中</p>
     *
     * @param page     page
     * @param node     node
     * @param segments segments
     */
    protected void addLink(WebPage page, Node node, List<PageSegment> segments) {
        for (PageSegment segment : segments) {
            Map<String, Object> map = segment.getData();
            String uri = getUri(map);

            // Add to URL queue, build new URL Request
            WebPage webPage = new WebPage(uri);
            webPage.setHost(page.getHost());
            webPage.setNodes(node.hasNext() ? node.getNext() : page.getNodes());
            // 普通文本数据
            if (!map.isEmpty()) {
                webPage.setData(map);
            }
            // 其他数据
            if (!segment.getMedias().isEmpty()) {
                webPage.setMedias(segment.getMedias());
            }
            linkQueue.add(webPage);
        }
    }

    /**
     * <p>获取LINK</p>
     *
     * @param map map
     * @return URI
     */
    protected String getUri(Map<String, Object> map) {
        Object link = map.remove(LINK);
        return link == null ? null : link.toString();
    }

    /**
     * <p>不符合主题相关度, 丢弃的URL</p>
     *
     * @param webPage webPage
     */
    protected abstract void discard(WebPage webPage);

    /**
     * <p>解析出现错误的URL</p>
     *
     * @param webPage webPage
     */
    protected abstract void error(WebPage webPage);

}