package org.moonframework.model.mybatis.service;

import org.moonframework.model.mybatis.criterion.Criterion;
import org.moonframework.model.mybatis.domain.BaseEntity;
import org.moonframework.model.mybatis.domain.Field;
import org.moonframework.model.mybatis.repository.BaseRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/3/10
 */
public class Services implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    private static Map<Class<?>, String> map = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <S extends BaseEntity> int saveOrUpdate(Class<S> clazz, S entity) {
        return service(clazz, service -> service.saveOrUpdate(entity));
    }

    public static <S extends BaseEntity> int[] saveOrUpdate(Class<S> clazz, Iterable<S> entities) {
        return service(clazz, service -> service.saveOrUpdate(entities));
    }

    public static <S extends BaseEntity> int save(Class<S> clazz, S entity) {
        return service(clazz, service -> service.save(entity));
    }

    public static <S extends BaseEntity> int[] save(Class<S> clazz, Iterable<S> entities) {
        return service(clazz, service -> service.save(entities));
    }

    public static <S extends BaseEntity> int update(Class<S> clazz, S entity) {
        return service(clazz, service -> service.update(entity));
    }

    public static <S extends BaseEntity> int update(Class<S> clazz, S entity, Criterion criterion) {
        return service(clazz, service -> service.update(entity, criterion));
    }

    public static <S extends BaseEntity> int update(Class<S> clazz, Iterable<S> entities) {
        return service(clazz, service -> service.update(entities));
    }

    public static <S extends BaseEntity> int delete(Class<S> clazz, Long id) {
        return service(clazz, service -> service.delete(id));
    }

    public static <S extends BaseEntity> int delete(Class<S> clazz, S entity) {
        return service(clazz, service -> service.delete(entity));
    }

    public static <S extends BaseEntity> int[] delete(Class<S> clazz, Iterable<Long> longs) {
        return service(clazz, service -> service.delete(longs));
    }

    public static <S extends BaseEntity> int remove(Class<S> clazz, Long id) {
        return service(clazz, service -> service.remove(id));
    }

    public static <S extends BaseEntity> int remove(Class<S> clazz, Iterable<Long> longs) {
        return service(clazz, service -> service.remove(longs));
    }

    public static <S extends BaseEntity> S findOne(Class<S> clazz, Long id) {
        return service(clazz, service -> service.findOne(id));
    }

    public static <S extends BaseEntity> S findOne(Class<S> clazz, Long id, Iterable<? extends Field> fields) {
        return service(clazz, service -> service.findOne(id, fields));
    }

    public static <S extends BaseEntity> S findOne(Class<S> clazz, S entity) {
        return service(clazz, service -> service.findOne(entity));
    }

    public static <S extends BaseEntity> S findOne(Class<S> clazz, S entity, Iterable<? extends Field> fields) {
        return service(clazz, service -> service.findOne(entity, fields));
    }

    public static <S extends BaseEntity> List<S> findAll(Class<S> clazz, S entity) {
        return service(clazz, service -> service.findAll(entity));
    }

    public static <S extends BaseEntity> List<S> findAll(Class<S> clazz, S entity, Iterable<? extends Field> fields) {
        return service(clazz, service -> service.findAll(entity, fields));
    }

    public static <S extends BaseEntity> List<S> findAll(Class<S> clazz, Iterable<Long> longs) {
        return service(clazz, service -> service.findAll(longs));
    }

    public static <S extends BaseEntity> List<S> findAll(Class<S> clazz, Iterable<Long> longs, Iterable<? extends Field> fields) {
        return service(clazz, service -> service.findAll(longs, fields));
    }

    public static <S extends BaseEntity> List<S> findAll(Class<S> clazz, String field, Iterable<Long> longs) {
        return service(clazz, service -> service.findAll(field, longs));
    }

    public static <S extends BaseEntity> List<S> findAll(Class<S> clazz, String field, Iterable<Long> longs, Iterable<? extends Field> fields) {
        return service(clazz, service -> service.findAll(field, longs, fields));
    }

    public static <S extends BaseEntity> List<S> findAll(Class<S> clazz, Criterion criterion) {
        return service(clazz, service -> service.findAll(criterion));
    }

    public static <S extends BaseEntity> Page<S> findAll(Class<S> clazz, S entity, Pageable pageable) {
        return service(clazz, service -> service.findAll(entity, pageable));
    }

    public static <S extends BaseEntity> Page<S> findAll(Class<S> clazz, S entity, Pageable pageable, Iterable<? extends Field> fields) {
        return service(clazz, service -> service.findAll(entity, pageable, fields));
    }

    public static <S extends BaseEntity> Page<S> findAll(Class<S> clazz, Criterion criterion, Pageable pageable) {
        return service(clazz, service -> service.findAll(criterion, pageable));
    }

    public static <S extends BaseEntity> long count(Class<S> clazz) {
        return service(clazz, BaseRepository::count);
    }

    public static <S extends BaseEntity> long count(Class<S> clazz, S entity) {
        return service(clazz, service -> service.count(entity));
    }

    public static <S extends BaseEntity> long count(Class<S> clazz, Criterion criterion) {
        return service(clazz, service -> service.count(criterion));
    }

    public static <S extends BaseEntity> boolean exists(Class<S> clazz, Long id) {
        return service(clazz, service -> service.exists(id));
    }

    public static <S extends BaseEntity> boolean exists(Class<S> clazz, S entity) {
        return service(clazz, service -> service.exists(entity));
    }

    public static <S extends BaseEntity> S queryForObject(Class<S> clazz, Long id) {
        return service(clazz, service -> service.queryForObject(id));
    }

    /**
     * @param clazz    clazz
     * @param function function
     * @param <T>      T
     * @param <R>      R
     * @return R
     */
    @SuppressWarnings("unchecked")
    protected static <T extends BaseEntity, R> R service(Class<T> clazz, Function<? super BaseService<T>, R> function) {
        BaseService<T> baseService = applicationContext.getBean(map.computeIfAbsent(clazz, Services::getBeanName), BaseService.class);
        return function.apply(baseService);
    }

    /**
     * @param clazz clazz
     * @return service bean name
     */
    private static String getBeanName(Class<?> clazz) {
        StringBuilder sb = new StringBuilder(clazz.getSimpleName());
        if (!isUpperCase(sb, 1)) {
            char c = Character.toLowerCase(sb.charAt(0));
            sb.deleteCharAt(0);
            sb.insert(0, c);
        }
        sb.append("ServiceImpl");
        return sb.toString();
    }

    private static boolean isUpperCase(StringBuilder sb, int index) {
        return sb.length() > index && Character.isUpperCase(sb.charAt(index));
    }

}
