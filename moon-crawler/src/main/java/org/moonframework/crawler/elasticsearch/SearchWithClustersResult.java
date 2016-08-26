package org.moonframework.crawler.elasticsearch;

import java.util.List;
import java.util.Map;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/8/2
 */
public class SearchWithClustersResult extends SearchResult<Map<String, Object>> {

    private List<Map<String, Object>> clusters;

    public SearchWithClustersResult(long total) {
        super(total);
    }

    public List<Map<String, Object>> getClusters() {
        return clusters;
    }

    public void setClusters(List<Map<String, Object>> clusters) {
        this.clusters = clusters;
    }
}
