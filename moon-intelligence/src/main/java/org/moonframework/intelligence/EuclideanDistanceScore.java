package org.moonframework.intelligence;

/**
 * <p>欧几里得距离评价</p>
 * <p>计算两个T之间的相似度</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/4/6
 */
public class EuclideanDistanceScore {

    /**
     * @param first  first
     * @param second second
     * @param <T>    Type
     * @return score
     */
    public static <T extends Preference> double distance(T first, T second) {
        double[] left = first.items();
        double[] right = second.items();

        if (left.length > 64)
            throw new IllegalArgumentException("Max score items length great than 64");

        if (left.length != right.length)
            throw new IllegalArgumentException();

        long result = value(left) & value(right);
        if (result == 0L)
            return 0D;

        int index = 0;
        double sum = 0;
        while (64 > index) {
            if (((result = result >>> 1) & 1) != 0)
                sum += Math.pow(left[index] - right[index], 2.0D);
            index++;
        }
        return 1D / (1D + Math.sqrt(sum));
    }

    /**
     * @param array array
     * @return long
     */
    private static long value(double[] array) {
        long hash = 0;
        for (int i = 0; i < array.length; i++) {
            long t = array[i] > 0 ? 1 : 0;
            t <<= i;
            hash |= t;
        }
        return hash;
    }

}
