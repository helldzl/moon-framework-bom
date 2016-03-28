package org.moonframework.toolkit.generator;

import org.moonframework.toolkit.idworker.IdWorkerUtil;

/**
 * Created by Freeman on 2016/1/8.
 */
public class IdWorkerGenerator implements IdGenerator {

    @Override
    public Long generateId() {
        return IdWorkerUtil.nextLong();
    }

    @Override
    public Long generateId(Long id) {
        return id == null ? IdWorkerUtil.nextLong() : id;
    }
}
