package org.moonframework.core;

import org.moonframework.core.util.SimpleBloomFilter;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/17
 */
public class BloomFilterTests {

    public static void main(String[] args) {
        String fileName = "C:\\Quzile\\bloomfilter.txt";
        String value = "http://www.google.com";
        SimpleBloomFilter filter = new SimpleBloomFilter();
        filter.readBit(fileName);

        System.out.println(filter.contains(value));
        filter.add(value);
        System.out.println(filter.contains(value));
        filter.saveBit(fileName);

    }

}
