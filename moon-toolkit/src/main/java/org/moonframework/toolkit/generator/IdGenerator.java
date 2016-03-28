package org.moonframework.toolkit.generator;

/**
 * Created by Freeman on 2016/1/8.
 */
public interface IdGenerator {

    /**
     * Default Generator for support Database Auto increase
     */
    IdGenerator DEFAULT_GENERATOR = new IdGenerator() {
        @Override
        public Long generateId() {
            return null;
        }

        @Override
        public Long generateId(Long id) {
            return id == null ? null : id;
        }
    };

    Long generateId();

    Long generateId(Long id);

}
