package org.moonframework.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.*;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/7
 */
public final class BeanUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
    }

    /**
     * <p>将源对象的属性复制到目标类型中</p>
     *
     * @param source source
     * @param target target
     * @param <S>    SOURCE
     * @param <T>    TARGET
     * @return T
     */
    public static <S, T> T copyProperties(S source, Class<T> target) {
        try {
            return mapper.readValue(mapper.writeValueAsBytes(source), target);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Bean转换成Map</p>
     *
     * @param obj obj
     * @return map
     */
    public static Map<String, Object> toMap(Object obj) {
        if (obj == null)
            return Collections.emptyMap();
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
                String key = descriptor.getName();
                if (!"class".equals(key)) {
                    Object value = descriptor.getReadMethod().invoke(obj);
                    if (value != null && !"".equals(value))
                        map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * <p>Iterable对象转换成List</p>
     *
     * @param iterable iterable
     * @param <E>      E
     * @return List
     */
    public static <E> List<Map<String, Object>> toList(Iterable<E> iterable) {
        if (iterable == null)
            return Collections.emptyList();
        List<Map<String, Object>> list = new ArrayList<>();
        for (E e : iterable) {
            Map<String, Object> map = toMap(e);
            if (!map.isEmpty())
                list.add(map);
        }
        return list;
    }

}
