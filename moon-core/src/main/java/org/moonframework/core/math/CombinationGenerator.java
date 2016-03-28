package org.moonframework.core.math;

import java.math.BigInteger;

/**
 * 组合生成器, 用户查找r距离的所有可能性, 数学中排列组合
 *
 * @author quzile
 */
public class CombinationGenerator {
    private int[] a;
    private int n;
    private int r;
    private int numLeft;
    private int total;

    public CombinationGenerator(int n, int r) {
        if (r > n)
            throw new IllegalArgumentException();
        if (n < 1)
            throw new IllegalArgumentException();
        this.n = n;
        this.r = r;
        a = new int[r];
        BigInteger nFact = getFactorial(n);
        BigInteger rFact = getFactorial(r);
        BigInteger nminusrFact = getFactorial(n - r);
        total = nFact.divide(rFact.multiply(nminusrFact)).intValue();
        reset();
    }

    private static BigInteger getFactorial(int n) {
        BigInteger fact = BigInteger.ONE;
        for (int i = n; i > 1; i--) {
            fact = fact.multiply(new BigInteger(Integer.toString(i)));
        }
        return fact;
    }

    public void reset() {
        for (int i = 0; i < a.length; i++)
            a[i] = i;
        numLeft = total;
    }

    public int getNumLeft() {
        return numLeft;
    }

    public boolean hasMore() {
        return numLeft > 0;
    }

    public int getTotal() {
        return total;
    }

    public int[] getNext() {
        if (numLeft == total) {
            numLeft--;
            return a;
        }

        int i = r - 1;
        while (a[i] == n - r + i) {
            i--;
        }
        a[i] = a[i] + 1;
        for (int j = i + 1; j < r; j++) {
            a[j] = a[i] + j - i;
        }

        numLeft--;
        return a;
    }
}
