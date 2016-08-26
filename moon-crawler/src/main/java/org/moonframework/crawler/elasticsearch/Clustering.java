package org.moonframework.crawler.elasticsearch;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.sort.SortBuilder;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/8/2
 */
public interface Clustering {

    SearchWithClustersResult searchWithClusters(String queryHint, QueryBuilder queryBuilder, SortBuilder sortBuilder, QueryBuilder postFilter, Consumer<SearchRequestBuilder> builderConsumer);

    SearchWithClustersResult searchWithClusters(String queryHint, QueryBuilder queryBuilder, SortBuilder sortBuilder, QueryBuilder postFilter, Consumer<SearchRequestBuilder> builderConsumer, Function<Aggregations, Map<String, Object>> mapFunction);

}
