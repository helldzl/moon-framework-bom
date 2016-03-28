package org.moonframework.web.core;

import org.moonframework.model.mybatis.domain.BaseEntity;
import org.moonframework.model.mybatis.domain.Pages;
import org.moonframework.model.mybatis.domain.Response;
import org.moonframework.model.mybatis.service.BaseService;
import org.moonframework.validation.ValidationGroups;
import org.springframework.data.domain.Page;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/10
 */
public abstract class RestfulControllerAdapter<T extends BaseEntity, S extends BaseService<T>> extends RestfulController<T, S> {

    private final String createURI;

    public RestfulControllerAdapter() {
        createURI = getGenericEntityName();
    }

    @Override
    public ResponseEntity<Response> doSave(T entity) {
        hasError(validate(entity, ValidationGroups.Post.class));

        if (0 == service.save(entity))
            throw new RuntimeException(getMessage("error.doSave"));
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(createURI + "/" + entity.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(SUCCESS);
    }

    @Override
    public ResponseEntity<Void> doDelete(Long id, Integer enabled) {
        T entity = newInstance();
        entity.setId(id);
        entity.setEnabled(enabled == 1 ? 1 : 0);
        if (0 == service.update(entity))
            throw new RuntimeException(getMessage("error.doDelete"));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> doUpdate(Long id, T entity) {
        entity.setId(id);
        hasError(validate(entity, ValidationGroups.Put.class));

        if (0 == service.update(entity))
            throw new RuntimeException(getMessage("error.doUpdate"));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Response> doFind(Long id) {
        T entity = service.queryForObject(id);
        if (entity == null)
            throw new RuntimeException(getMessage("error.doFind"));
        else {
            Response response = response(entity);
            return ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS)).lastModified(entity.getModified().getTime()).body(response);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> doPage(Integer page, Integer rows, T entity) {
        Page<T> result = service.findAll(entity, Pages.builder().page(page).size(rows).build());
        return ResponseEntity.ok().body(page(result));
    }
}
