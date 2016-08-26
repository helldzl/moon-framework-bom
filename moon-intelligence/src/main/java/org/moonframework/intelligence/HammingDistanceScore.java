package org.moonframework.intelligence;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/8/3
 */
public class HammingDistanceScore {

    public static int hamming(long l1, long l2) {
        return Long.bitCount(l1 ^ l2);
    }

}
