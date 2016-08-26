package org.moonframework.crawler.elasticsearch;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilder;
import org.moonframework.core.util.BeanUtils;
import org.moonframework.crawler.elasticsearch.aggregation.AggregationType;
import org.springframework.core.ResolvableType;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/7
 */
public abstract class AbstractSearchEngine<T extends Searchable> implements SearchEngine<T> {

    // Meta-fields are used to customize how a document’s metadata associated is treated. Examples of meta-fields include the document’s _index, _type, _id, and _source fields.

    public static final String META_FIELD_INDEX = "_index";
    public static final String META_FIELD_TYPE = "_type";
    public static final String META_FIELD_ID = "_id";
    public static final String META_FIELD_SCORE = "_score";
    public static final String META_FIELD_SOURCE = "_source";

    // fields

    public static final String FIELD_HIGHLIGHT = "highlight";
    public static final String FIELD_BUCKETS = "buckets";

    protected Client client;

    @SuppressWarnings("unchecked")
    protected Class<T> entityClass = (Class<T>) ResolvableType.forClass(this.getClass()).as(AbstractSearchEngine.class).getGeneric(0).resolve();

    private String index;

    private String type;

    public AbstractSearchEngine(Client client) {
        this.client = client;
    }

    public AbstractSearchEngine(Client client, String index, String type) {
        this.client = client;
        this.index = index;
        this.type = type;
    }

    @Override
    public SearchResult<T> search(QueryBuilder queryBuilder, int from, int size) {
        return search(queryBuilder, null, null, from, size);
    }

    @Override
    public SearchResult<T> search(QueryBuilder queryBuilder, SortBuilder sortBuilder, int from, int size) {
        return search(queryBuilder, sortBuilder, null, from, size);
    }

    @Override
    public SearchResult<T> search(QueryBuilder queryBuilder, SortBuilder sortBuilder, QueryBuilder postFilter, int from, int size) {
        SearchRequestBuilder builder = prepareSearch(queryBuilder, sortBuilder, postFilter, from, size);
        SearchResponse response = builder.execute().actionGet();

        SearchHits hits = response.getHits();
        List<T> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            T t = BeanUtils.copyProperties(hit.getSource(), entityClass);
            if (t != null) {
                setId(t, hit.getId());
                list.add(t);
            }
        }
        return new SearchResult<>(hits.getTotalHits(), list);
    }

    @Override
    public SearchResult<Map<String, Object>> search(QueryBuilder queryBuilder, SortBuilder sortBuilder, QueryBuilder postFilter, Consumer<SearchRequestBuilder> builderConsumer) {
        return search(queryBuilder, sortBuilder, postFilter, builderConsumer, null);
    }

    @Override
    public SearchResult<Map<String, Object>> search(QueryBuilder queryBuilder, SortBuilder sortBuilder, QueryBuilder postFilter, Consumer<SearchRequestBuilder> builderConsumer, Function<Aggregations, Map<String, Object>> mapFunction) {
        SearchRequestBuilder builder = prepareSearch(queryBuilder, sortBuilder, postFilter, builderConsumer);
        SearchResponse response = builder.execute().actionGet();
        SearchResult<Map<String, Object>> searchResult = new SearchResult<>(response.getHits().getTotalHits());
        result(searchResult, response, mapFunction);
        return searchResult;
    }

    @Override
    public BulkResponse index(String index, String type, Iterable<T> iterable) throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (T t : iterable)
            bulkRequest.add(prepareIndex(index, type, t));
        return bulkRequest.get();
    }

    @Override
    public BulkResponse index(Iterable<T> iterable) throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (T t : iterable)
            bulkRequest.add(prepareIndex(index, type, t));
        return bulkRequest.get();
    }

    @Override
    public BulkResponse index(String index, String type, T t) throws IOException {
        return index(index, type, Collections.singletonList(t));
    }

    @Override
    public BulkResponse index(T t) throws IOException {
        return index(Collections.singletonList(t));
    }

    //

    /**
     * <p>new instance</p>
     *
     * @return T
     */
    protected T newInstance() {
        try {
            return entityClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected SearchRequestBuilder prepareSearch(QueryBuilder queryBuilder, SortBuilder sortBuilder, QueryBuilder postFilter, int from, int size) {
        return prepareSearch(queryBuilder, sortBuilder, postFilter, builder -> builder.setFrom(from).setSize(size));
    }

    protected SearchRequestBuilder prepareSearch(QueryBuilder queryBuilder, SortBuilder sortBuilder, QueryBuilder postFilter, Consumer<SearchRequestBuilder> consumer) {
        SearchRequestBuilder builder = client.prepareSearch(index);
        builder.setTypes(type);
        builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

        if (consumer != null)
            consumer.accept(builder);
        if (queryBuilder != null)
            builder.setQuery(queryBuilder);
        if (sortBuilder != null)
            builder.addSort(sortBuilder);
        if (postFilter != null)
            builder.setPostFilter(postFilter);

        return builder;
    }

    protected void result(SearchResult<Map<String, Object>> searchResult, SearchResponse response, Function<Aggregations, Map<String, Object>> responseMapFunction) {
        // About Hits
        SearchHits hits = response.getHits();
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> map = new HashMap<>();
            // meta field
            map.put(META_FIELD_ID, hit.getId());
            map.put(META_FIELD_SCORE, hit.getScore());
            map.put(META_FIELD_SOURCE, hit.getSource());

            // other field
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (!CollectionUtils.isEmpty(highlightFields)) {
                Map<String, Object> highlight = new HashMap<>();
                highlightFields.entrySet().forEach(entry -> highlight.put(entry.getValue().getName(), Arrays.asList(entry.getValue().getFragments()).stream().map(Text::string).collect(Collectors.toList())));
                map.put(FIELD_HIGHLIGHT, highlight);
            }
            list.add(map);
        }

        // About Aggregations
        Map<String, Object> map;
        Aggregations aggregations = response.getAggregations();
        if (aggregations == null)
            map = null;
        else if (responseMapFunction != null)
            map = responseMapFunction.apply(aggregations);
        else
            map = aggregations(aggregations);


        // set
        searchResult.setHits(list);
        searchResult.setAggregations(map);
    }

    protected Map<String, Object> aggregations(Aggregations aggregations) {
        Map<String, Object> result = new HashMap<>();
        aggregations.getAsMap().entrySet().forEach(entry -> {
            Aggregation aggregation = entry.getValue();
            String name = aggregation.getName();

            List<Map<String, Object>> buckets = new ArrayList<>();
            Map<String, Object> bucketsMap = new HashMap<>();
            bucketsMap.put(FIELD_BUCKETS, buckets);
            result.put(name, bucketsMap);

            for (AggregationType aggregationType : AggregationType.values()) {
                if (aggregationType.add(aggregation, buckets))
                    break;
            }
        });
        return result;
    }

    protected void setId(T t, String docId) {
        t.setDocId(docId);
    }

    /**
     * <p>构建Index Request</p>
     *
     * @param index index
     * @param type  Mapping type
     * @param t     entity
     * @return IndexRequestBuilder
     * @throws IOException
     */
    protected IndexRequestBuilder prepareIndex(String index, String type, T t) throws IOException {
        return client.prepareIndex(index, type, t.getDocId()).setSource(getSource(t));
    }

    /**
     * <p>构建source</p>
     *
     * @param t entity
     * @return XContentBuilder
     * @throws IOException
     */
    protected XContentBuilder getSource(T t) throws IOException {
        XContentBuilder builder = jsonBuilder();
        builder.startObject();
        buildSource(builder, t);
        builder.endObject();
        return builder;
    }

    protected abstract void buildSource(XContentBuilder builder, T t) throws IOException;

    // get and set method

    public void setClient(Client client) {
        this.client = client;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
