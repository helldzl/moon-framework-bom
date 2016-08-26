package org.moonframework.crawler.filter;

import org.moonframework.core.util.SimpleBloomFilter;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/17
 */
public class BloomFilterAdapter implements LinkFilter {

    private SimpleBloomFilter bloomFilter;

    public BloomFilterAdapter() {
        this(new SimpleBloomFilter());
    }

    public BloomFilterAdapter(SimpleBloomFilter bloomFilter) {
        this.bloomFilter = bloomFilter;
    }

    @Override
    public boolean filter(String url) {
        if (!bloomFilter.contains(url)) {
            bloomFilter.add(url);
            return true;
        }
        return false;
    }

}
