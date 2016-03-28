package org.moonframework.remote.support;

import org.moonframework.remote.dto.*;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/8
 */
public interface RemoteService {

    <T extends ServiceBeanNameParam> SimpleResponse<Long> save(CommonRequest<T> request);

    <T extends ServiceBeanNameParam> SimpleResponse<Void> delete(CommonRequest<T> request);

    <T extends ServiceBeanNameParam> SimpleResponse<Void> update(CommonRequest<T> request);

    /**
     * <p>应用语义: 查找某个实体信息, 不查询与其关联的实体信息</p>
     *
     * @param request
     * @param <T>
     * @param <R>
     * @return
     */
    <T extends ServiceBeanNameParam, R extends Param> SimpleResponse<R> findOne(CommonRequest<T> request);

    /**
     * <p>应用语义: 查找某个实体和与其相关的所有实体信息</p>
     * <p>e.g: user 表与 user_profiles 表是一对一关系, 该查方法需要全部查询</p>
     *
     * @param request
     * @param <T>
     * @param <R>
     * @return
     */
    <T extends ServiceBeanNameParam, R extends Param> SimpleResponse<R> queryForObject(CommonRequest<T> request);

    <T extends PageParam, R extends Param> SimpleResponse<PageResp<R>> findPage(CommonRequest<T> request);

    <T extends ServiceBeanNameParam> SimpleResponse<Boolean> exists(CommonRequest<T> request);

}
