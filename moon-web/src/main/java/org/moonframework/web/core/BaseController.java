package org.moonframework.web.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.moonframework.model.mybatis.domain.BaseEntity;
import org.moonframework.model.mybatis.domain.Pages;
import org.moonframework.model.mybatis.domain.Response;
import org.moonframework.model.mybatis.domain.ResponseData;
import org.moonframework.model.mybatis.service.BaseService;
import org.moonframework.model.mybatis.support.AbstractGenericEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/11/27
 */
public abstract class BaseController<T extends BaseEntity, S extends BaseService<T>> extends AbstractGenericEntity<T> {

    public static final Response SUCCESS = response("success", "success");
    public static final Response ERROR = response("error", "error");
    protected final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    protected S service;

    public BaseController() {
    }

    public static Response response(String code) {
        return new Response(code);
    }

    //

    public static Response response(String code, String message) {
        return new Response(code, message);
    }

    public static Response response(String code, String message, Object data) {
        return new ResponseData(code, message, data);
    }

    public static Response response(Object data) {
        return new ResponseData("success", "success", data);
    }

    /**
     * 默认参数
     *
     * @param model
     */
    protected void model(Map<String, Object> model) {
        model.put("time", new Date());
    }

    protected Map<String, Object> page(Page<T> page) {
        // RequestContextHolder.getRequestAttributes()
        Map<String, Object> map = new HashMap<>();
        map.put("total", page.getTotalElements());
        map.put("rows", page.getContent());
        return map;
    }

    protected List<Long> convert(List<String> list) {
        return list.stream().map(Long::valueOf).collect(Collectors.toList());
    }

    protected Byte getByte(String name) {
        return computeIfAbsent(name, Byte::valueOf, () -> null);
    }

    protected Byte getByte(String name, Supplier<Byte> supplier) {
        return computeIfAbsent(name, Byte::valueOf, supplier);
    }

    protected Short getShort(String name) {
        return computeIfAbsent(name, Short::valueOf, () -> null);
    }

    protected Short getShort(String name, Supplier<Short> supplier) {
        return computeIfAbsent(name, Short::valueOf, supplier);
    }

    protected Integer getInteger(String name) {
        return computeIfAbsent(name, Integer::valueOf, () -> null);
    }

    protected Integer getInteger(String name, Supplier<Integer> supplier) {
        return computeIfAbsent(name, Integer::valueOf, supplier);
    }

    protected Long getLong(String name) {
        return computeIfAbsent(name, Long::valueOf, () -> null);
    }

    protected Long getLong(String name, Supplier<Long> supplier) {
        return computeIfAbsent(name, Long::valueOf, supplier);
    }

    protected Float getFloat(String name) {
        return computeIfAbsent(name, Float::valueOf, () -> null);
    }

    protected Float getFloat(String name, Supplier<Float> supplier) {
        return computeIfAbsent(name, Float::valueOf, supplier);
    }

    protected Double getDouble(String name) {
        return computeIfAbsent(name, Double::valueOf, () -> null);
    }

    protected Double getDouble(String name, Supplier<Double> supplier) {
        return computeIfAbsent(name, Double::valueOf, supplier);
    }

    protected String getString(String name) {
        return computeIfAbsent(name, s -> s, () -> null);
    }

    protected String getString(String name, Supplier<String> supplier) {
        return computeIfAbsent(name, s -> s, supplier);
    }

    protected Boolean getBoolean(String name) {
        return computeIfAbsent(name, Boolean::valueOf, () -> null);
    }

    protected Boolean getBoolean(String name, Supplier<Boolean> supplier) {
        return computeIfAbsent(name, Boolean::valueOf, supplier);
    }

    protected HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) requestAttributes).getRequest();

    }

    protected Subject currentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser;
    }

    protected PageRequest pageRequest() {
        HttpServletRequest request = getHttpServletRequest();
        String[] fields = request.getParameterValues("c_fields");
        String[] orders = request.getParameterValues("c_orders");
        Sort sort = null;
        if (fields != null && orders != null && fields.length != 0 && fields.length == orders.length) {
            Pages.SortBuilder sortBuilder = Pages.sortBuilder();
            for (int i = 0; i < fields.length; i++) {
                String f = fields[i];
                String o = orders[i];
                sortBuilder.add(f, "1".equals(o) || "ASC".equalsIgnoreCase(o));
            }
            sort = sortBuilder.build();
        }
        Integer page = getInteger("page", () -> 1);
        Integer rows = getInteger("rows", () -> 20);
        return Pages.builder().page(page).size(rows).sort(sort).build();
    }

    protected <R> R computeIfAbsent(String name, Function<String, R> fun, Supplier<R> supplier) {
        HttpServletRequest request = getHttpServletRequest();
        String param = request.getParameter(name);
        if (param == null)
            return supplier.get();

        try {
            return fun.apply(param);
        } catch (Exception e) {
            return supplier.get();
        }
    }

}
