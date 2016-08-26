package org.moonframework.crawler.analysis;

import java.util.Map;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/27
 */
public interface Analysable {

    /**
     * <p>获得待分析的数据</p>
     *
     * @return
     */
    Map<String, Object> getContent();

}
