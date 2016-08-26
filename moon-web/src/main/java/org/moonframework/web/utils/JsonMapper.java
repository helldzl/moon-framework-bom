package org.moonframework.web.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by DXZ on 2016/4/22.
 */
public class JsonMapper {
    private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS");
    private ObjectMapper mapper;

    public JsonMapper() {
        this(null);
    }

    public JsonMapper(JsonInclude.Include include) {
        mapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        if (include != null) {
            mapper.setSerializationInclusion(include);
        }
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 设置日期格式化
        mapper.setDateFormat(dateFormat);
    }

    /**
     *
     *
     * @Function: com.budee.uc.util.JsonMapper.nonEmptyMapper
     * @Description: 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用
     *
     * @return
     *
     * @version:v1.0.0
     * @author:Doman
     * @date:2015年7月24日 上午10:16:23
     *
     */
    public static JsonMapper nonEmptyMapper() {
        return new JsonMapper(JsonInclude.Include.NON_EMPTY);
    }

    /**
     *
     *
     * @Function: com.budee.uc.util.JsonMapper.nonDefaultMapper
     * @Description: 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用
     *
     * @return
     *
     * @version:v1.0.0
     * @author:Doman
     * @date:2015年7月24日 上午10:17:45
     *
     */
    public static JsonMapper nonDefaultMapper() {
        return new JsonMapper(JsonInclude.Include.NON_DEFAULT);
    }

    /**
     *
     *
     * @Function: com.budee.uc.util.JsonMapper.toJson
     * @Description: Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]"
     *
     * @param object
     * @return
     *
     * @version:v1.0.0
     * @author:Doman
     * @date:2015年7月24日 上午10:18:09
     *
     */
    public String toJson(Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     *
     *
     * @Function: com.budee.uc.util.JsonMapper.fromJson
     * @Description: 反序列化POJO或简单Collection如List<String>,如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String,JavaType)
     *
     * @param jsonString
     * @param clazz
     * @return 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合
     * @see #fromJson(String, JavaType)
     *
     * @version:v1.0.0
     * @author:Doman
     * @date:2015年7月24日 上午10:18:38
     *
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     *
     *
     * @Function: com.budee.uc.util.JsonMapper.fromJson
     * @Description: 反序列化复杂Collection如List<Bean>, 先使用函数createCollectionType构造类型,然后调用本函数
     *
     * @param jsonString
     * @param javaType
     * @return
     * @see #createCollectionType(Class, Class...)
     *
     * @version:v1.0.0
     * @author:Doman
     * @date:2015年7月24日 上午10:20:26
     *
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     *
     *
     * @Function: com.budee.uc.util.JsonMapper.createCollectionType
     * @Description: 构造泛型的Collection Type如: ArrayList<MyBean>,
     * 则调用constructCollectionType(ArrayList.class,MyBean.class)
     * HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
     *
     * @param collectionClass
     * @param elementClasses
     * @return
     *
     * @version:v1.0.0
     * @author:Doman
     * @date:2015年7月24日 上午10:21:37
     *
     */
    @SuppressWarnings("deprecation")
    public JavaType createCollectionType(Class<?> collectionClass,
                                         Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass,
                elementClasses);
    }

    /**
     *
     *
     * @Function: com.budee.uc.util.JsonMapper.update
     * @Description: 当JSON里只含有Bean的部分属性时，更新一个已存在Bean，只覆盖该部分的属性
     *
     * @param jsonString
     * @param object
     * @return
     *
     * @version:v1.0.0
     * @author:Doman
     * @date:2015年7月24日 上午10:22:42
     *
     */
    public <T> T update(String jsonString, T object) {
        try {
            return mapper.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
            logger.warn("update json string:" + jsonString + " to object:"
                    + object + " error.", e);
        } catch (IOException e) {
            logger.warn("update json string:" + jsonString + " to object:"
                    + object + " error.", e);
        }
        return null;
    }

    /**
     *
     *
     * @Function: com.budee.uc.util.JsonMapper.toJsonP
     * @Description: 输出JSONP格式数据
     *
     * @param functionName
     * @param object
     * @return
     *
     * @version:v1.0.0
     * @author:Doman
     * @date:2015年7月24日 上午10:23:09
     *
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     *
     *
     * @Function: com.budee.uc.util.JsonMapper.enableEnumUseToString
     * @Description: 设定是否使用Enum的toString函数来读写Enum, 为False时使用Enum的name()函数來读写Enum, 默认为False.
     * 注意本函数一定要在Mapper创建后, 所有的读写动作之前调用.
     *
     * @version:v1.0.0
     * @author:Doman
     * @date:2015年7月24日 上午10:23:41
     *
     */
    public void enableEnumUseToString() {
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }

    /**
     *
     *
     * @Function: com.budee.uc.util.JsonMapper.getMapper
     * @Description: 取出Mapper做进一步的设置或使用其他序列化API
     *
     * @return
     *
     * @version:v1.0.0
     * @author:Doman
     * @date:2015年7月24日 上午10:24:47
     *
     */
    public ObjectMapper getMapper() {
        return mapper;
    }
}

