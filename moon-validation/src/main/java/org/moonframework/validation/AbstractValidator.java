package org.moonframework.validation;

import org.moonframework.core.message.MessageSourceAdapter;
import org.moonframework.validation.domain.FieldErrorResource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/16
 */
public abstract class AbstractValidator extends MessageSourceAdapter {

    @Autowired
    protected Validator validator;
    private ConstraintToResource constraintToResource = new ConstraintToResource();

    public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        return validator.validate(object, groups);
    }

    public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
        return validator.validateProperty(object, propertyName, groups);
    }

    public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
        return validator.validateValue(beanType, propertyName, value, groups);
    }

    public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
        return validator.getConstraintsForClass(clazz);
    }

    public <T> T unwrap(Class<T> type) {
        return validator.unwrap(type);
    }

    public ExecutableValidator forExecutables() {
        return validator.forExecutables();
    }

    /**
     * <p>是否有验证错误</p>
     *
     * @param set     set
     * @param message message
     * @param <T>     T
     */
    public <T> void hasError(Set<ConstraintViolation<T>> set, String message) {
        List<FieldErrorResource> resources = new ArrayList<>();
        if (!set.isEmpty()) {
            set.forEach(source -> resources.add(constraintToResource.convert(source)));
            throw new InvalidException(message, resources);
        }
    }

    public <T> void hasError(Set<ConstraintViolation<T>> set) {
        hasError(set, "");
    }

    public <T> void assertValid(T object, String message, Class<?>... groups) {
        hasError(validator.validate(object, groups), message);
    }

    public <T> void assertValid(T object, Class<?>... groups) {
        hasError(validator.validate(object, groups));
    }

}
