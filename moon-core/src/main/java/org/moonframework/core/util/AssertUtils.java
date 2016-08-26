package org.moonframework.core.util;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/5/23
 */
public abstract class AssertUtils extends Assert {

    public static void isTrue(boolean expression, Supplier<String> supplier) {
        if (!expression) {
            throw new IllegalArgumentException(supplier.get());
        }
    }

    public static void isNull(Object object, Supplier<String> supplier) {
        if (object != null) {
            throw new IllegalArgumentException(supplier.get());
        }
    }

    public static void notNull(Object object, Supplier<String> supplier) {
        if (object == null) {
            throw new IllegalArgumentException(supplier.get());
        }
    }

    public static void hasLength(String text, Supplier<String> supplier) {
        if (!StringUtils.hasLength(text)) {
            throw new IllegalArgumentException(supplier.get());
        }
    }

    public static void hasText(String text, Supplier<String> supplier) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException(supplier.get());
        }
    }

    public static void doesNotContain(String textToSearch, String substring, Supplier<String> supplier) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
                textToSearch.contains(substring)) {
            throw new IllegalArgumentException(supplier.get());
        }
    }

    public static void notEmpty(Object[] array, Supplier<String> supplier) {
        if (ObjectUtils.isEmpty(array)) {
            throw new IllegalArgumentException(supplier.get());
        }
    }

    public static void noNullElements(Object[] array, Supplier<String> supplier) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new IllegalArgumentException(supplier.get());
                }
            }
        }
    }

    public static void notEmpty(Collection<?> collection, Supplier<String> supplier) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(supplier.get());
        }
    }

    public static void notEmpty(Map<?, ?> map, Supplier<String> supplier) {
        if (CollectionUtils.isEmpty(map)) {
            throw new IllegalArgumentException(supplier.get());
        }
    }

    public static void state(boolean expression, Supplier<String> supplier) {
        if (!expression) {
            throw new IllegalStateException(supplier.get());
        }
    }

}
