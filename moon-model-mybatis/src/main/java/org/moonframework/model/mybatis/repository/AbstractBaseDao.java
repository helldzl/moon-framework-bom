package org.moonframework.model.mybatis.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moonframework.model.mybatis.criterion.Criterion;
import org.moonframework.model.mybatis.criterion.QueryCondition;
import org.moonframework.model.mybatis.domain.BaseEntity;
import org.moonframework.model.mybatis.domain.Field;
import org.moonframework.model.mybatis.support.AbstractGenericEntity;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/11/17
 */
public abstract class AbstractBaseDao<T extends BaseEntity> extends AbstractGenericEntity<T> implements BaseDao<T> {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    protected SqlSessionTemplate session;

    private static final String DOT = ".";

    private final String save;
    private final String saveOrUpdate;
    private final String delete;
    private final String update;
    private final String updateByCriterion;
    private final String findOne;
    private final String findOneByObject;
    private final String findAll;
    private final String findAllByObject;
    private final String findAllByCriterion;
    private final String findPage;
    private final String findPageByCriterion;
    private final String count;
    private final String countByCondition;
    private final String countByCriterion;
    private final String exists;

    private static Iterable<? extends Field> empty = Collections.emptyList();

    @SuppressWarnings("unchecked")
    public AbstractBaseDao() {
        // operation
        save = name("save");
        saveOrUpdate = name("saveOrUpdate");
        delete = name("delete");
        update = name("update");
        updateByCriterion = name("updateByCriterion");
        findOne = name("findOne");
        findOneByObject = name("findOneByObject");
        findAll = name("findAll");
        findAllByObject = name("findAllByObject");
        findAllByCriterion = name("findAllByCriterion");
        findPage = name("findPage");
        findPageByCriterion = name("findPageByCriterion");
        count = name("count");
        countByCondition = name("countByCondition");
        countByCriterion = name("countByCriterion");
        exists = name("exists");
    }

    @Override
    public <S extends T> int saveOrUpdate(S entity) {
        return session.insert(saveOrUpdate, entity);
    }

    @Override
    public <S extends T> int[] saveOrUpdate(Iterable<S> entities) {
        List<Integer> list = new ArrayList<>();
        for (T entity : entities)
            list.add(session.insert(saveOrUpdate, entity));
        return toArray(list);
    }

    @Override
    public <S extends T> int save(S entity) {
        return session.insert(save, entity);
    }

    @Override
    public <S extends T> int[] save(Iterable<S> entities) {
        List<Integer> list = new ArrayList<>();
        for (T entity : entities)
            list.add(session.insert(save, entity));
        return toArray(list);
    }

    @Override
    public <S extends T> int update(S entity) {
        return session.update(update, entity);
    }

    @Override
    public <S extends T> int update(Iterable<S> entities) {
        // @Flush
        int n = 0;
        for (T entity : entities)
            n += session.update(update, entity);
        // List<BatchResult> results = session.flushStatements();
        return n;
    }

    @Override
    public <S extends T> int update(S entity, Criterion criterion) {
        Map<String, Object> map = new HashMap<>();
        map.put("entity", entity);
        map.put("conditions", condition(criterion));
        return session.update(updateByCriterion, map);
    }

    @Override
    public int delete(Long id) {
        return session.delete(delete, id);
    }

    @Override
    public int delete(T entity) {
        return delete(entity.getId());
    }

    @Override
    public int[] delete(Iterable<Long> ids) {
        List<Integer> list = new ArrayList<>();
        for (Long id : ids)
            list.add(session.delete(delete, id));
        // List<BatchResult> results = session.flushStatements();
        return toArray(list);
    }

    @Override
    public int remove(Long id) {
        return update(newInstance(id, new Date()));
    }

    @Override
    public int remove(Iterable<Long> ids) {
        List<T> list = new ArrayList<>();
        Date date = new Date();
        for (Long id : ids)
            list.add(newInstance(id, date));
        return update(list);
    }

    @Override
    public T findOne(Long id) {
        return findOne(id, empty);
    }

    @Override
    public T findOne(Long id, Iterable<? extends Field> fields) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("fields", fields);
        return session.selectOne(findOne, map);
    }

    @Override
    public T findOne(T entity) {
        return findOne(entity, empty);
    }

    @Override
    public T findOne(T entity, Iterable<? extends Field> fields) {
        Map<String, Object> map = new HashMap<>();
        map.put("entity", entity);
        map.put("fields", fields);
        return session.selectOne(findOneByObject, map);
    }

    @Override
    public List<T> findAll(T entity) {
        return findAll(entity, empty);
    }

    @Override
    public List<T> findAll(T entity, Iterable<? extends Field> fields) {
        Map<String, Object> map = new HashMap<>();
        map.put("entity", entity);
        map.put("fields", fields);
        return session.selectList(findAllByObject, map);
    }

    @Override
    public List<T> findAll(Iterable<Long> ids) {
        return findAll(null, ids, empty);
    }

    @Override
    public List<T> findAll(Iterable<Long> ids, Iterable<? extends Field> fields) {
        return findAll(null, ids, fields);
    }

    @Override
    public List<T> findAll(String field, Iterable<Long> ids) {
        return findAll(field, ids, empty);
    }

    @Override
    public List<T> findAll(String field, Iterable<Long> ids, Iterable<? extends Field> fields) {
        Map<String, Object> map = new HashMap<>();
        map.put("field", field == null ? BaseEntity.ID : field);
        map.put("ids", ids);
        map.put("fields", fields);
        return session.selectList(findAll, map);
    }

    @Override
    public List<T> findAll(Criterion criterion) {
        Map<String, Object> map = new HashMap<>();
        map.put("conditions", condition(criterion));
        return session.selectList(findAllByCriterion, map);
    }

    @Override
    public Page<T> findAll(T entity, Pageable pageable) {
        return findAll(entity, pageable, empty);
    }

    @Override
    public Page<T> findAll(T entity, Pageable pageable, Iterable<? extends Field> fields) {
        Map<String, Object> map = pageMap(pageable);
        map.put("entity", entity);
        map.put("fields", fields);
        List<T> content = session.selectList(findPage, map);
        return new PageImpl<>(content, pageable, count(entity));
    }

    @Override
    public Page<T> findAll(Criterion criterion, Pageable pageable) {
        Map<String, Object> map = pageMap(pageable);
        map.put("conditions", condition(criterion));
        List<T> content = session.selectList(findPageByCriterion, map);
        return new PageImpl<>(content, pageable, session.selectOne(countByCriterion, map));
    }

    @Override
    public long count() {
        return session.selectOne(count);
    }

    @Override
    public long count(T entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("entity", entity);
        return session.selectOne(countByCondition, map);
    }

    @Override
    public long count(Criterion criterion) {
        Map<String, Object> map = new HashMap<>();
        map.put("conditions", condition(criterion));
        return session.selectOne(countByCriterion, map);
    }

    @Override
    public boolean exists(Long id) {
        long count = session.selectOne(exists, id);
        return count != 0;
    }

    @Override
    public boolean exists(T entity) {
        return count(entity) != 0;
    }

    @Override
    public T queryForObject(Long id) {
        // this is a adapter method, default to findOne()
        return findOne(id);
    }

    /**
     * <p>构造分页映射对象, 子类可以使用该方法来直接创建分页map对象</p>
     *
     * @param pageable pageable
     * @return Map
     */
    protected Map<String, Object> pageMap(Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        map.put("offset", pageable.getOffset());
        map.put("pageSize", pageable.getPageSize());
        map.put("orders", pageable.getSort() == null ? Collections.emptyList() : pageable.getSort());
        return map;
    }

    // private method

    private static QueryCondition condition(Criterion criterion) {
        QueryCondition condition = new QueryCondition();
        criterion.toSqlString(condition);
        return condition;
    }

    private static int[] toArray(List<Integer> list) {
        int[] result = new int[list.size()];
        int length = result.length;
        for (int i = 0; i < length; i++)
            result[i] = list.get(i);
        return result;
    }

    private String name(String operation) {
        return entityClass.getName() + DOT + operation;
    }

    // get and set method

    public SqlSessionTemplate getSession() {
        return session;
    }

    public void setSession(SqlSessionTemplate session) {
        this.session = session;
    }

}
