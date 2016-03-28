package org.moonframework.web.core;

import org.moonframework.model.mybatis.domain.BaseEntity;
import org.moonframework.model.mybatis.domain.Response;
import org.moonframework.model.mybatis.service.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * <p>GET /users：列出所有用户</p>
 * <p>POST /users：新建一个用户</p>
 * <p>GET /users/{id}：获取某个指定用户的信息</p>
 * <p>PUT /users/{id}：更新某个指定用户的信息（提供该用户的全部信息）</p>
 * <p>PATCH /users/{id}：更新某个指定用户的信息（提供该用户的部分信息）</p>
 * <p>DELETE /users/{id}：删除某个用户</p>
 * <p>GET /users/{id}/accounts：列出某个指定用户的所有账户</p>
 * <p>DELETE /users/{id}/accounts/{id}：删除某个指定用户的指定账户</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2015/12/9
 */
public abstract class RestfulController<T extends BaseEntity, S extends BaseService<T>> extends BaseController<T, S> {

    /**
     * <p>GET</p>
     * <p>新增资源</p>
     *
     * @param entity entity
     * @return ResponseEntity
     */
    public abstract ResponseEntity<Response> doSave(T entity);

    /**
     * <p>DELETE</p>
     * <p>允许/禁止资源</p>
     *
     * @param id      ID
     * @param enabled enabled
     * @return ResponseEntity
     */
    public abstract ResponseEntity<Void> doDelete(Long id, Integer enabled);

    /**
     * <p>PUT</p>
     * <p>更新资源</p>
     *
     * @param id     ID
     * @param entity entity
     * @return ResponseEntity
     */
    public abstract ResponseEntity<Void> doUpdate(Long id, T entity);

    /**
     * <p>GET</p>
     * <p>获取资源</p>
     *
     * @param id ID
     * @return ResponseEntity
     */
    public abstract ResponseEntity<Response> doFind(Long id);

    /**
     * <p>GET</p>
     * <p>资源列表</p>
     *
     * @param page   page index, from 1 start
     * @param rows   rows
     * @param entity entity
     * @return ResponseEntity
     */
    public abstract ResponseEntity<Map<String, Object>> doPage(Integer page, Integer rows, T entity);

}
