package org.moonframework.crawler.elasticsearch.aggregation;

import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by quzile on 2016/8/11.
 */
public enum AggregationType {

    RANGE {
        @Override
        public boolean add(Aggregation aggregation, List<Map<String, Object>> buckets) {
            if (aggregation instanceof Range) {
                Range agg = (Range) aggregation;
                for (Range.Bucket bucket : agg.getBuckets()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("key", bucket.getKeyAsString());
                    map.put("to", bucket.getTo());
                    map.put("from", bucket.getFrom());
                    map.put("doc_count", bucket.getDocCount());
                    buckets.add(map);
                }
                return true;
            }
            return false;
        }
    },

    TERMS {
        @Override
        public boolean add(Aggregation aggregation, List<Map<String, Object>> buckets) {
            if (aggregation instanceof Terms) {
                Terms terms = (Terms) aggregation;
                for (Terms.Bucket bucket : terms.getBuckets()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("key", bucket.getKeyAsString());
                    map.put("doc_count", bucket.getDocCount());
                    buckets.add(map);
                }
                return true;
            }
            return false;
        }
    };

    AggregationType() {
    }

    public abstract boolean add(Aggregation aggregation, List<Map<String, Object>> buckets);

}
