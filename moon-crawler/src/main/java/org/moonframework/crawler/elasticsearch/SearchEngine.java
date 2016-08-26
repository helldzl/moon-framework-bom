package org.moonframework.crawler.elasticsearch;

import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.sort.SortBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/7
 */
public interface SearchEngine<T> {

    BulkResponse index(String index, String type, Iterable<T> iterable) throws IOException;

    BulkResponse index(Iterable<T> iterable) throws IOException;

    BulkResponse index(String index, String type, T t) throws IOException;

    BulkResponse index(T t) throws IOException;

    SearchResult<T> search(QueryBuilder queryBuilder, int from, int size);

    SearchResult<T> search(QueryBuilder queryBuilder, SortBuilder sortBuilder, int from, int size);

    SearchResult<T> search(QueryBuilder queryBuilder, SortBuilder sortBuilder, QueryBuilder postFilter, int from, int size);

    SearchResult<Map<String, Object>> search(QueryBuilder queryBuilder, SortBuilder sortBuilder, QueryBuilder postFilter, Consumer<SearchRequestBuilder> builderConsumer);

    SearchResult<Map<String, Object>> search(QueryBuilder queryBuilder, SortBuilder sortBuilder, QueryBuilder postFilter, Consumer<SearchRequestBuilder> builderConsumer, Function<Aggregations, Map<String, Object>> mapFunction);

}