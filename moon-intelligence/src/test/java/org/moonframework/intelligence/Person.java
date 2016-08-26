package org.moonframework.intelligence;

import java.util.Random;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/4/6
 */
public class Person implements Preference {

    private static Random random = new Random();
    private double[] items;

    {
        items = new double[64];
        for (int i = 0; i < items.length; i++) {
            int n = (int) (Math.random() * 2);
            if (n == 1) {
                items[i] = random.nextDouble() * 10;
            }
        }
    }

    @Override
    public double[] items() {
        return items;
    }

}
