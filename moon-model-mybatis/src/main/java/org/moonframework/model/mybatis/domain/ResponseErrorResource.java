package org.moonframework.model.mybatis.domain;

import com.fasterxml.jackson.annotation.JsonView;
import org.moonframework.validation.domain.FieldErrorResource;

import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/16
 */
public class ResponseErrorResource extends Response {

    private static final long serialVersionUID = 3680154423318852736L;

    @JsonView(BaseEntity.View.class)
    private List<FieldErrorResource> fieldErrorResources;

    public ResponseErrorResource(String code, String message) {
        super(code, message);
    }

    public List<FieldErrorResource> getFieldErrorResources() {
        return fieldErrorResources;
    }

    public void setFieldErrorResources(List<FieldErrorResource> fieldErrorResources) {
        this.fieldErrorResources = fieldErrorResources;
    }
}
