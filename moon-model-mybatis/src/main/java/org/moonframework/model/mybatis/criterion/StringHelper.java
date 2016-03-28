package org.moonframework.model.mybatis.criterion;

import java.util.Iterator;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/19
 */
public class StringHelper {

    static String toString(Object[] array) {
        int len = array.length;
        if (len == 0) return "";
        StringBuilder buf = new StringBuilder(len * 12);
        for (int i = 0; i < len - 1; i++) {
            buf.append(array[i]).append(", ");
        }
        return buf.append(array[len - 1]).toString();
    }

    static String join(String seperator, Iterator objects) {
        StringBuilder buf = new StringBuilder();
        if (objects.hasNext()) buf.append(objects.next());
        while (objects.hasNext()) {
            buf.append(seperator).append(objects.next());
        }
        return buf.toString();
    }

    protected StringHelper() {
    }

}
