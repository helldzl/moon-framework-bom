package org.moonframework.model.mybatis.support;

import org.moonframework.model.mybatis.domain.BaseEntity;
import org.moonframework.validation.AbstractValidator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/10
 */
public abstract class AbstractGenericEntity<T extends BaseEntity> extends AbstractValidator {

    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public AbstractGenericEntity() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) type;
        this.entityClass = (Class<T>) p.getActualTypeArguments()[0];
    }

    protected T newInstance() {
        try {
            return entityClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected T newInstance(Long id, Date date) {
        T t = newInstance();
        t.setId(id);
        t.setEnabled(0);
        t.setModified(date);
        return t;
    }

    protected String getGenericEntityName() {
        return entityClass.getSimpleName();
    }

}
