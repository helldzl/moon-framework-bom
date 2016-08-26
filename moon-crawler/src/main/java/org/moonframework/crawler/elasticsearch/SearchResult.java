package org.moonframework.crawler.elasticsearch;

import java.util.*;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/7
 */
public class SearchResult<T> implements Iterable<T> {

    private static final String HITS = "hits";
    private static final String TOTAL = "total";

    private Map<String, Object> hits = new HashMap<>();
    private Map<String, Object> aggregations;

    public SearchResult(long total) {
        this.hits.put(TOTAL, total);
    }

    public SearchResult(long total, List<T> hits) {
        this.hits.put(TOTAL, total);
        this.hits.put(HITS, hits);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<T> iterator() {
        if (!hits.containsKey(HITS))
            return Collections.emptyIterator();
        return ((List<T>) hits.get(HITS)).iterator();
    }

    public void setTotal(long total) {
        this.hits.put(TOTAL, total);
    }

    public void setHits(List<T> hits) {
        this.hits.put(HITS, hits);
    }

    //

    public Map<String, Object> getHits() {
        return hits;
    }

    public Map<String, Object> getAggregations() {
        return aggregations;
    }

    public void setAggregations(Map<String, Object> aggregations) {
        this.aggregations = aggregations;
    }

    //

    protected static class EmptySearchResult<T> extends SearchResult<T> {

        protected EmptySearchResult(long total) {
            super(total);
        }

        @Override
        public Map<String, Object> getHits() {
            return Collections.emptyMap();
        }

        @Override
        public Iterator<T> iterator() {
            return Collections.emptyIterator();
        }

        @Override
        public void setTotal(long total) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setHits(List<T> hits) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAggregations(Map<String, Object> aggregations) {
            throw new UnsupportedOperationException();
        }

    }

}
