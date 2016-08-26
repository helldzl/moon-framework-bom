package org.moonframework.crawler.filter;

import org.apache.http.impl.client.CloseableHttpClient;
import org.moonframework.crawler.http.WebHttpClientUtils;
import org.moonframework.crawler.robots.RobotRulesFilterAdapter;

import java.io.IOException;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/27
 */
public class RobotFilterAdapter implements LinkFilter {

    private RobotRulesFilterAdapter robotRulesFilterAdapter = new RobotRulesFilterAdapter();

    public RobotFilterAdapter(CloseableHttpClient closeableHttpClient) {
        robotRulesFilterAdapter.setHttpClient(closeableHttpClient);
    }

    @Override
    public boolean filter(String url) {
        try {
            return robotRulesFilterAdapter.isAllowed(url, WebHttpClientUtils.USER_AGENT);
        } catch (IOException e) {
            return false;
        }
    }

}
