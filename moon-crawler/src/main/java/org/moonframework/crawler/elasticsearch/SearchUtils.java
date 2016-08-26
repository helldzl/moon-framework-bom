package org.moonframework.crawler.elasticsearch;

/**
 * Created by quzile on 2016/8/11.
 */
public class SearchUtils {

    private static final SearchResult.EmptySearchResult EMPTY_SEARCH_RESULT = new SearchResult.EmptySearchResult(0);

    @SuppressWarnings("unchecked")
    public static <T> SearchResult<T> emptySearchResult() {
        return (SearchResult<T>) EMPTY_SEARCH_RESULT;
    }

}
