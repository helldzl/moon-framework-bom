package org.moonframework.model.mybatis.domain;

import org.moonframework.core.support.Builder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/11/18
 */
public class Pages {

    public static PageRequestBuilder builder() {
        return new PageRequestBuilder();
    }

    public static SortBuilder sortBuilder() {
        return new SortBuilder();
    }

    public static class PageRequestBuilder implements Builder<PageRequest> {

        private int page = 0;
        private int size = 20;
        private Sort sort;

        /**
         * @param page 从1开始
         * @return
         */
        public PageRequestBuilder page(Integer page) {
            if (page != null)
                this.page = page - 1;
            return this;
        }

        public PageRequestBuilder size(Integer size) {
            if (size != null)
                this.size = size;
            return this;
        }

        public PageRequestBuilder sort(Sort sort) {
            this.sort = sort;
            return this;
        }

        @Override
        public PageRequest build() {
            return new PageRequest(page, size, sort);
        }
    }

    public static class SortBuilder implements Builder<Sort> {

        private List<Sort.Order> orders = new ArrayList<>();

        public SortBuilder add(String property, boolean asc) {
            if (property != null && !"".equals(property))
                orders.add(new Sort.Order(asc ? Sort.Direction.ASC : Sort.Direction.DESC, property));
            return this;
        }

        @Override
        public Sort build() {
            return new Sort(orders);
        }
    }

    public static void main(String[] args) {
        Pages.builder().page(2).size(20).sort(Pages.sortBuilder().add("", true).build()).build();
    }

}
