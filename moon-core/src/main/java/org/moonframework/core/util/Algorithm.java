package org.moonframework.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/1
 */
public class Algorithm {

    /**
     * <p>最长公共子序列算法</p>
     *
     * @param x x
     * @param y y
     * @return array
     */
    public static <E> int[][] lcs(List<E> x, List<E> y) {
        int m = x.size();
        int n = y.size();

        int[][] array = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (x.get(i - 1).equals(y.get(j - 1))) {
                    array[i][j] = array[i - 1][j - 1] + 1;
                } else {
                    array[i][j] = Math.max(array[i - 1][j], array[i][j - 1]);
                }
            }
        }

        return array;
    }

    public static <E> List<E> printLcs(List<E> x, List<E> y) {
        int[][] array = lcs(x, y);

        int m = x.size();
        int n = y.size();
        List<E> result = new ArrayList<>();
        while (m != 0 && n != 0) {
            if (x.get(m - 1).equals(y.get(n - 1))) {
                result.add(x.get(m - 1));
                m--;
                n--;
            } else if (array[m][n - 1] >= array[m - 1][n]) {
                n--;
            } else {
                m--;
            }
        }
        return result;
    }

}
