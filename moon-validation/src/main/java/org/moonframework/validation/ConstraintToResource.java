package org.moonframework.validation;

import org.moonframework.validation.domain.FieldErrorResource;
import org.springframework.core.convert.converter.Converter;

import javax.validation.ConstraintViolation;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/16
 */
public class ConstraintToResource implements Converter<ConstraintViolation, FieldErrorResource> {

    @Override
    public FieldErrorResource convert(ConstraintViolation source) {
        FieldErrorResource target = new FieldErrorResource();
        target.setCode(doCheck(source.getMessageTemplate()));
        target.setMessage(source.getMessage());
        target.setField(source.getPropertyPath().toString());
        target.setResource(source.getRootBeanClass().getSimpleName());
        return target;
    }

    private String doCheck(String code) {
        if (!code.startsWith("{") && !code.endsWith("}"))
            return code;
        StringBuilder sb = new StringBuilder(code);
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(0);
        return sb.toString();
    }

}
