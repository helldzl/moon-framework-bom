package org.moonframework.crawler.queue;

import org.moonframework.crawler.filter.LinkFilter;
import org.moonframework.crawler.storage.NodeType;
import org.moonframework.crawler.storage.WebPage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/27
 */
public class LinkQueue extends LinkedBlockingQueue<WebPage> {

    /**
     * 过滤器链表
     */
    private List<LinkFilter> filters = new ArrayList<>();

    @Override
    public boolean add(WebPage webPage) {
        // 只验证第一个节点类型是CONTENT的
        if (NodeType.CONTENT == webPage.getNodes().get(0).getNodeType()) {
            for (LinkFilter filter : filters)
                if (!filter.filter(webPage.getUri().toString())) return false;
        }
        return super.add(webPage);
    }

    /**
     * <p>注册过滤器</p>
     *
     * @param linkFilter
     */
    public LinkQueue addFilter(LinkFilter linkFilter) {
        filters.add(linkFilter);
        return this;
    }

}
