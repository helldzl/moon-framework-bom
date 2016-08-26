package org.moonframework.remote.support;

import org.moonframework.model.mybatis.domain.BaseEntity;
import org.moonframework.model.mybatis.domain.Pages;
import org.moonframework.model.mybatis.service.BaseService;
import org.moonframework.model.mybatis.util.GenericUtils;
import org.moonframework.remote.dto.*;
import org.moonframework.remote.function.ServiceFunction;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>提供远程API的一些默认行为</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/1/8
 */
public class RemoteServiceAdapter implements RemoteService, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public <T extends ServiceBeanNameParam> SimpleResponse<Long> save(CommonRequest<T> request) {
        // Effectively final
        long[] id = new long[1];
        Integer rows = service(request, (service, entity) -> {
            int n = service.save(entity);
            if (entity.getId() != null)
                id[0] = entity.getId();
            return n;
        });
        return result(rows, id[0]);
    }

    @Override
    public <T extends ServiceBeanNameParam> SimpleResponse<Void> delete(CommonRequest<T> request) {
        return result(service(request, (service, entity) -> service.delete(entity.getId())));
    }

    @Override
    public <T extends ServiceBeanNameParam> SimpleResponse<Void> update(CommonRequest<T> request) {
        return result(service(request, BaseService::update));
    }

    @Override
    public <T extends ServiceBeanNameParam, R extends Param> SimpleResponse<R> findOne(CommonRequest<T> request) {
        return service(request, (service, entity) -> result(request, service.findOne(entity)));
    }

    @Override
    public <T extends ServiceBeanNameParam, R extends Param> SimpleResponse<R> queryForObject(CommonRequest<T> request) {
        return service(request, (service, entity) -> result(request, service.queryForObject(entity.getId())));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends PageParam, R extends Param> SimpleResponse<PageResp<R>> findPage(CommonRequest<T> request) {
        return service(request, (service, entity) -> {
            T t = request.getParam();
            Sort sort = Pages.sortBuilder().add(BaseEntity.ID, t.isAsc()).build();
            Page<BaseEntity> page = service.findAll(entity, Pages.builder().page(t.getPageNumber()).size(t.getPageSize()).sort(sort).build());

            // List<Param> list = new ArrayList<>();
            // page.getContent().forEach(baseEntity -> list.add(GenericUtils.bind(baseEntity, t.getResponseClass())));

            List<R> list = page.getContent().stream()
                    .map(baseEntity -> (R) GenericUtils.bind(baseEntity, t.getResponseClass()))
                    .collect(Collectors.toList());

            PageResp<R> pageResp = new PageResp<>();
            pageResp.setTotal(page.getTotalElements());
            pageResp.setRows(list);
            return SimpleResponse.builder().success().build(pageResp);
        });
    }

    @Override
    public <T extends ServiceBeanNameParam> SimpleResponse<Boolean> exists(CommonRequest<T> request) {
        return success(service(request, (service, entity) -> service.exists(entity)));
    }

    /**
     * <p>Data Bind</p>
     *
     * @param request request
     * @param service service
     * @param <T>     Param Type
     * @param <E>     Result Type
     * @return E
     */
    @SuppressWarnings("unchecked")
    protected <T extends ServiceBeanNameParam, E> E service(CommonRequest<T> request, ServiceFunction<E> service) {
        T t = request.getParam();
        if (t.getTargetClass() == null)
            throw new UnsupportedOperationException("Target class must not be null");

        BaseEntity entity = GenericUtils.bind(t, (Class<? extends BaseEntity>) t.getTargetClass());

        // Find Base Service by service name and save entity.
        BaseService<BaseEntity> baseService = applicationContext.getBean(t.getServiceBeanName(), BaseService.class);
        E e = service.apply(baseService, entity);
        return e;
    }

    @SuppressWarnings("unchecked")
    private <T extends ServiceBeanNameParam, R extends Param> SimpleResponse<R> result(CommonRequest<T> request, BaseEntity baseEntity) {
        Param param;
        if (baseEntity == null)
            param = null;
        else
            param = GenericUtils.bind(baseEntity, request.getParam().getResponseClass());
        return SimpleResponse.builder().success().build((R) param);
    }

    /**
     * @param rows rows
     * @param t    T
     * @param <T>  T
     * @return SimpleResponse
     */
    private <T> SimpleResponse<T> result(int rows, T t) {
        if (0 >= rows)
            return SimpleResponse.builder().errors().build(t);
        return SimpleResponse.builder().success().build(t);
    }

    /**
     * @param rows rows
     * @return SimpleResponse
     */
    private SimpleResponse<Void> result(int rows) {
        if (0 >= rows)
            return SimpleResponse.builder().errors().build();
        return SimpleResponse.builder().success().build();
    }

    private <T> SimpleResponse<T> success(T t) {
        return SimpleResponse.builder().success().build(t);
    }

}
