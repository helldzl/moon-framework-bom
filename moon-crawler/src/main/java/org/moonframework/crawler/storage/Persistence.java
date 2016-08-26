package org.moonframework.crawler.storage;

import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/16
 */
public interface Persistence {

    /**
     * <p>持久化</p>
     *
     * @param className class name
     * @param segments  segments为待执行持久化的数据记录集合, 根据class name进行映射
     */
    void persist(String className, List<PageSegment> segments);

}
