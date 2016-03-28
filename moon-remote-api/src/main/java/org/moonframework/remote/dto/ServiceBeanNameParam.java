package org.moonframework.remote.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/11
 */
public class ServiceBeanNameParam extends Param {

    private static final long serialVersionUID = 621881749543761948L;
    private static final String SERVICE_BEAN_NAME_SUFFIX = "ServiceImpl";

    private String targetClassName;
    private Class<?> targetClass;
    private Class<? extends Param> responseClass;

    private String serviceBeanName;

    public ServiceBeanNameParam() {
    }

    public ServiceBeanNameParam(String targetClassName) {
        this(targetClassName, null);
    }

    public ServiceBeanNameParam(String targetClassName, Class<? extends Param> responseClass) {
        this.targetClassName = targetClassName;
        this.responseClass = responseClass;
    }

    private void init() {
        if (targetClassName != null && targetClass == null) {
            try {
                this.targetClass = Class.forName(targetClassName);
                this.serviceBeanName = getServiceBeanNameFromClass(targetClass);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Class not found");
            }
        }
    }

    @JsonIgnore
    public Class<?> getTargetClass() {
        init();
        return targetClass;
    }

    @JsonIgnore
    public Class<? extends Param> getResponseClass() {
        return responseClass;
    }

    @JsonIgnore
    public String getServiceBeanName() {
        init();
        return serviceBeanName;
    }

    @JsonIgnore
    private String getServiceBeanNameFromClass(Class<?> clazz) {
        StringBuilder sb = new StringBuilder(clazz.getSimpleName());
        char c = Character.toLowerCase(sb.charAt(0));
        sb.deleteCharAt(0);
        sb.insert(0, c);
        sb.append(SERVICE_BEAN_NAME_SUFFIX);
        return sb.toString();
    }

}
