package org.moonframework.model.mybatis.domain;

import org.moonframework.core.support.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/11/18
 */
public class Fields {

    /**
     * @return
     */
    public static FieldBuilder builder() {
        return new FieldBuilder();
    }

    /**
     *
     */
    public static class FieldBuilder implements Builder<List<Field>> {

        private List<Field> fields = new ArrayList<>();

        public FieldBuilder add(String name) {
            fields.add(new FieldImpl(name));
            return this;
        }

        public FieldBuilder add(String name, String alias) {
            fields.add(new FieldImpl(name, alias));
            return this;
        }

        public FieldBuilder add(String name, String alias, String prefix) {
            fields.add(new FieldImpl(name, alias, prefix));
            return this;
        }

        @Override
        public List<Field> build() {
            return fields;
        }

    }

    /**
     *
     */
    protected static class FieldImpl implements Field {

        private String name;
        private String alias;
        private String prefix;
        private String fullname;

        private static final String ALIAS = " AS ";
        private static final String DOT = ".";

        public FieldImpl(String name) {
            this(name, null);
        }

        public FieldImpl(String name, String alias) {
            if (name == null)
                throw new RuntimeException("field name must not be null!");
            this.name = name;
            this.alias = alias;
            this.fullname = name + (alias == null ? "" : ALIAS + alias);
        }

        public FieldImpl(String name, String alias, String prefix) {
            this.name = name;
            this.alias = alias;
            this.prefix = prefix;
            this.fullname = (prefix == null ? "" : prefix + DOT) + name + (alias == null ? "" : ALIAS + alias);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getAlias() {
            return alias;
        }

        @Override
        public String getFullname() {
            return fullname;
        }
    }

    public static void main(String[] args) {
        // Fields.builder().add("username").add("password").add("enabled", "deny").add("id").build();
    }

}
