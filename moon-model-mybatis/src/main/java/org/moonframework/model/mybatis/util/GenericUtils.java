package org.moonframework.model.mybatis.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * <p>http://wiki.fasterxml.com/JacksonInFiveMinutes#Full_Data_Binding_.28POJO.29_Example</p>
 *
 * @author quzile
 * @version 1.0
 * @see org.moonframework.core.util.BeanUtils
 * @since 2015/12/28
 * @deprecated use BeanUtils instead
 */
@Deprecated
public class GenericUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
    }

    /**
     * <p>数据转换</p>
     *
     * @param value     源对象
     * @param valueType 目标类型
     * @param <S>       Source
     * @param <T>       Target
     * @return Target
     */
    public static <S, T> T bind(S value, Class<T> valueType) {
        try {
            return mapper.readValue(mapper.writeValueAsBytes(value), valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
