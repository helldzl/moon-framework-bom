package org.moonframework.crawler.robots;

import java.io.IOException;

/**
 * <p>有道德的爬虫：遵循robots.txt过滤器</p>
 *
 * @author Freeman
 * @version 1.0
 * @since 2016/6/20
 */
public interface RobotRulesFilter {
    /**
     * <p>URL,robotName</p>
     *
     * @param url   url
     * @param agent robotName
     * @return the url is or not allowed
     */
    boolean isAllowed(String url, String agent) throws IOException;
}
