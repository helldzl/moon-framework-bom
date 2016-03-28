package org.moonframework.remote.function;

import org.moonframework.model.mybatis.domain.BaseEntity;
import org.moonframework.model.mybatis.service.BaseService;

import java.util.function.BiFunction;

/**
 * @param <R> the type of the result of the function
 * @author quzile
 * @version 1.0
 * @see BiFunction
 * @since 2016/3/9
 */
@FunctionalInterface
public interface ServiceFunction<R> extends BiFunction<BaseService<BaseEntity>, BaseEntity, R> {

    /**
     * @param service the type of the first argument to the function
     * @param entity  the type of the second argument to the function
     * @return R
     */
    @Override
    R apply(BaseService<BaseEntity> service, BaseEntity entity);

}
