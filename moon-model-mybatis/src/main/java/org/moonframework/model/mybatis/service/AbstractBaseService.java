package org.moonframework.model.mybatis.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moonframework.model.mybatis.criterion.Criterion;
import org.moonframework.model.mybatis.domain.BaseEntity;
import org.moonframework.model.mybatis.domain.Field;
import org.moonframework.model.mybatis.repository.BaseDao;
import org.moonframework.model.mybatis.support.AbstractGenericEntity;
import org.moonframework.toolkit.generator.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/11/26
 */
@Transactional
public abstract class AbstractBaseService<T extends BaseEntity, E extends BaseDao<T>> extends AbstractGenericEntity<T> implements BaseService<T> {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    protected static final int ERROR = 0;
    protected static final int SUCCESS = 1;

    @Autowired(required = false)
    private IdGenerator idGenerator = IdGenerator.DEFAULT_GENERATOR;

    @Autowired
    protected E baseDao;

    @Override
    public <S extends T> int saveOrUpdate(S entity) {
        Date date = new Date();
        entity.setCreated(date);
        entity.setModified(date);
        entity.setId(idGenerator.generateId(entity.getId()));
        return baseDao.saveOrUpdate(entity);
    }

    @Override
    public <S extends T> int[] saveOrUpdate(Iterable<S> entities) {
        Date date = new Date();
        for (S entity : entities) {
            entity.setCreated(date);
            entity.setModified(date);
            entity.setId(idGenerator.generateId(entity.getId()));
        }
        return baseDao.saveOrUpdate(entities);
    }

    @Override
    public <S extends T> int save(S entity) {
        Date date = new Date();
        entity.setCreated(date);
        entity.setModified(date);
        entity.setId(idGenerator.generateId(entity.getId()));
        return baseDao.save(entity);
    }

    @Override
    public <S extends T> int[] save(Iterable<S> entities) {
        Date date = new Date();
        for (S entity : entities) {
            entity.setCreated(date);
            entity.setModified(date);
            entity.setId(idGenerator.generateId(entity.getId()));
        }
        return baseDao.save(entities);
    }

    @Override
    public <S extends T> int update(S entity) {
        Date date = new Date();
        entity.setModified(date);
        return baseDao.update(entity);
    }

    @Override
    public <S extends T> int update(Iterable<S> entities) {
        Date date = new Date();
        for (S entity : entities) {
            entity.setModified(date);
        }
        return baseDao.update(entities);
    }

    @Override
    public <S extends T> int update(S entity, Criterion criterion) {
        Date date = new Date();
        entity.setModified(date);
        return baseDao.update(entity, criterion);
    }

    @Override
    public int delete(Long id) {
        notNull(id);
        return baseDao.delete(id);
    }

    @Override
    public int delete(T entity) {
        notNull(entity);
        return baseDao.delete(entity);
    }

    @Override
    public int[] delete(Iterable<Long> ids) {
        notNull(ids);
        return baseDao.delete(ids);
    }

    @Override
    public int remove(Long id) {
        notNull(id);
        return baseDao.remove(id);
    }

    @Override
    public int remove(Iterable<Long> ids) {
        notNull(ids);
        return baseDao.remove(ids);
    }

    @Transactional(readOnly = true)
    @Override
    public T findOne(Long id) {
        notNull(id);
        return baseDao.findOne(id);
    }

    @Transactional(readOnly = true)
    @Override
    public T findOne(Long id, Iterable<? extends Field> fields) {
        notNull(id);
        return baseDao.findOne(id, fields);
    }

    @Transactional(readOnly = true)
    @Override
    public T findOne(T entity) {
        notNull(entity);
        return baseDao.findOne(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public T findOne(T entity, Iterable<? extends Field> fields) {
        notNull(entity);
        return baseDao.findOne(entity, fields);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll(T entity) {
        notNull(entity);
        return baseDao.findAll(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll(T entity, Iterable<? extends Field> fields) {
        notNull(entity);
        return baseDao.findAll(entity, fields);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll(Iterable<Long> ids) {
        notNull(ids);
        return baseDao.findAll(ids);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll(Iterable<Long> ids, Iterable<? extends Field> fields) {
        notNull(ids);
        return baseDao.findAll(ids, fields);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll(String field, Iterable<Long> ids) {
        notNull(ids);
        return baseDao.findAll(field, ids);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll(String field, Iterable<Long> ids, Iterable<? extends Field> fields) {
        notNull(ids);
        return baseDao.findAll(field, ids, fields);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll(Criterion criterion) {
        notNull(criterion);
        return baseDao.findAll(criterion);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> findAll(T entity, Pageable pageable) {
        notNull(entity);
        notNull(pageable);
        return baseDao.findAll(entity, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> findAll(T entity, Pageable pageable, Iterable<? extends Field> fields) {
        notNull(entity);
        notNull(pageable);
        return baseDao.findAll(entity, pageable, fields);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> findAll(Criterion criterion, Pageable pageable) {
        notNull(pageable);
        notNull(criterion);
        return baseDao.findAll(criterion, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return baseDao.count();
    }

    @Transactional(readOnly = true)
    @Override
    public long count(T entity) {
        notNull(entity);
        return baseDao.count(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public long count(Criterion criterion) {
        notNull(criterion);
        return baseDao.count(criterion);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean exists(Long id) {
        notNull(id);
        return baseDao.exists(id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean exists(T entity) {
        notNull(entity);
        return baseDao.exists(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public T queryForObject(Long id) {
        notNull(id);
        return baseDao.queryForObject(id);
    }

    // assist method

    /**
     * <p>数组中有一条执行成功则返回成功</p>
     *
     * @param array array
     * @return is successful
     */
    protected static int result(int[] array) {
        for (int n : array)
            if (n > 0)
                return SUCCESS;
        return ERROR;
    }

    // check method

    protected boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    protected boolean matches(String regex, String input) {
        return Pattern.matches(regex, input);
    }

}
