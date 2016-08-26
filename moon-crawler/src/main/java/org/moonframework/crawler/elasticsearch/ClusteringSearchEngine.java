package org.moonframework.crawler.elasticsearch;

import org.carrot2.elasticsearch.ClusteringAction;
import org.carrot2.elasticsearch.LogicalField;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.sort.SortBuilder;
import org.moonframework.core.util.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/8/2
 */
public abstract class ClusteringSearchEngine<T extends Searchable> extends AbstractSearchEngine<T> implements Clustering {

    private String titleFieldName;
    private String contentFieldName;

    public ClusteringSearchEngine(Client client, String titleFieldName, String contentFieldName) {
        super(client);
        this.titleFieldName = titleFieldName;
        this.contentFieldName = contentFieldName;
    }

    public ClusteringSearchEngine(Client client, String index, String type, String titleFieldName, String contentFieldName) {
        super(client, index, type);
        this.titleFieldName = titleFieldName;
        this.contentFieldName = contentFieldName;
    }

    @Override
    public SearchWithClustersResult searchWithClusters(String queryHint, QueryBuilder queryBuilder, SortBuilder sortBuilder, QueryBuilder postFilter, Consumer<SearchRequestBuilder> builderConsumer) {
        return searchWithClusters(queryHint, queryBuilder, sortBuilder, postFilter, builderConsumer, null);
    }

    @Override
    public SearchWithClustersResult searchWithClusters(String queryHint, QueryBuilder queryBuilder, SortBuilder sortBuilder, QueryBuilder postFilter, Consumer<SearchRequestBuilder> builderConsumer, Function<Aggregations, Map<String, Object>> mapFunction) {
        ClusteringAction.ClusteringActionResponse result = new ClusteringAction.ClusteringActionRequestBuilder(client)
                .setQueryHint(queryHint)
                .addFieldMapping(titleFieldName, LogicalField.TITLE)
                .addHighlightedFieldMapping(contentFieldName, LogicalField.CONTENT)
                .setSearchRequest(prepareSearch(queryBuilder, sortBuilder, postFilter, builderConsumer))
                .execute()
                .actionGet();

        // build clusters result
        SearchResponse response = result.getSearchResponse();
        SearchWithClustersResult searchWithClustersResult = new SearchWithClustersResult(response.getHits().getTotalHits());

        // process hits and aggregations
        result(searchWithClustersResult, response, mapFunction);

        // process clustering
        List<Map<String, Object>> clusters = BeanUtils.toList(Arrays.asList(result.getDocumentGroups()));
        searchWithClustersResult.setClusters(clusters);
        return searchWithClustersResult;
    }

}
