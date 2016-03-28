package org.moonframework.model.mybatis.repository;

import org.moonframework.model.mybatis.criterion.Criterion;
import org.moonframework.model.mybatis.domain.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/10/5
 */
public interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

    /**
     * Saves a given entity by insert into on duplicate
     *
     * @param entity entity
     * @param <S>    S
     * @return int
     */
    <S extends T> int saveOrUpdate(S entity);

    /**
     * Saves a given entity by insert into on duplicate
     *
     * @param entities entities
     * @param <S>      S
     * @return int
     */
    <S extends T> int[] saveOrUpdate(Iterable<S> entities);

    /**
     * Saves a given entity.
     *
     * @param entity entity
     * @return int
     */
    <S extends T> int save(S entity);

    /**
     * Saves all given entities.
     *
     * @param entities entities
     * @return int
     */
    <S extends T> int[] save(Iterable<S> entities);

    /**
     * Updates a given entity.
     *
     * @param entity entity
     * @return int
     */
    <S extends T> int update(S entity);

    /**
     * Updates a given entity By criterion.
     *
     * @param entity    entity
     * @param criterion criterion
     * @param <S>       S
     * @return int
     */
    <S extends T> int update(S entity, Criterion criterion);

    /**
     * Updates all given entities.
     *
     * @param entities entities
     * @return S
     */
    <S extends T> int update(Iterable<S> entities);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @return greater then 0 if succeed
     */
    int delete(ID id);

    /**
     * Deletes the entity with the given id.
     *
     * @param entity entity
     * @return int
     */
    int delete(T entity);

    /**
     * Deletes all instances of the type with the given IDs.
     *
     * @param ids IDs
     * @return int
     */
    int[] delete(Iterable<ID> ids);

    /**
     * Removes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @return greater then 0 if success
     */
    int remove(ID id);

    /**
     * Removes all instances of the type with the given IDs.
     *
     * @param ids IDs
     * @return int
     */
    int remove(Iterable<ID> ids);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     */
    T findOne(ID id);

    /**
     * Retrieves an entity by its id.
     *
     * @param id     ID
     * @param fields fields
     * @return entity
     */
    T findOne(ID id, Iterable<? extends Field> fields);

    /**
     * Retrieves an entity by conditions.
     *
     * @param entity entity
     * @return entity
     */
    T findOne(T entity);

    /**
     * Retrieves an entity by conditions.
     *
     * @param entity entity
     * @param fields fields
     * @return entity
     */
    T findOne(T entity, Iterable<? extends Field> fields);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    List<T> findAll(T entity);

    /**
     * Returns all instances of the type.
     *
     * @param entity entity
     * @param fields fields
     * @return List
     */
    List<T> findAll(T entity, Iterable<? extends Field> fields);

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids IDs
     * @return List
     */
    List<T> findAll(Iterable<ID> ids);

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids    IDs
     * @param fields fields
     * @return List
     */
    List<T> findAll(Iterable<ID> ids, Iterable<? extends Field> fields);

    /**
     * <p>Returns all instances of the type with the given IDs of the specific field.</p>
     *
     * @param field field name
     * @param ids   IDs
     * @return List
     */
    List<T> findAll(String field, Iterable<ID> ids);

    /**
     * <p>Returns all instances of the type with the given IDs of the specific field.</p>
     *
     * @param field  field name
     * @param ids    IDs
     * @param fields fields
     * @return List
     */
    List<T> findAll(String field, Iterable<ID> ids, Iterable<? extends Field> fields);

    /**
     * <p>Find by criterion</p>
     *
     * @param criterion criterion
     * @return List
     */
    List<T> findAll(Criterion criterion);

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param entity   entity
     * @param pageable pageable
     * @return a page of entities
     */
    Page<T> findAll(T entity, Pageable pageable);

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param entity   entity
     * @param pageable pageable
     * @param fields   fields
     * @return a page of entities
     */
    Page<T> findAll(T entity, Pageable pageable, Iterable<? extends Field> fields);

    /**
     * <p>Find page by criterion</p>
     *
     * @param criterion criterion
     * @param pageable  pageable
     * @return Page
     */
    Page<T> findAll(Criterion criterion, Pageable pageable);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    long count();

    /**
     * Returns the number of entities available.
     *
     * @param entity entity
     * @return the number of entities
     */
    long count(T entity);

    /**
     * Returns the number of entities available.
     *
     * @param criterion criterion
     * @return the number of entities
     */
    long count(Criterion criterion);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id id must not be {@literal null}.
     * @return true if an entity with the given id exists, {@literal false} otherwise
     */
    boolean exists(ID id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param entity entity
     * @return true if an entity with the given id exists, {@literal false} otherwise
     */
    boolean exists(T entity);

    /**
     * Find related Objects by this entity
     *
     * @param id ID
     * @return entity
     */
    T queryForObject(ID id);

}
