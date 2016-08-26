package org.moonframework.concurrent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @param <T> input type
 * @param <R> the return result
 * @author quzile
 * @version 1.0
 * @since 2016/5/27
 */
public abstract class TaskAdapter<T, R> implements Task<T, R> {

    protected final Logger logger = LogManager.getLogger(TaskAdapter.class);

    private BlockingPool pool;
    private T t;

    public TaskAdapter(BlockingPool pool) {
        this.pool = pool;
    }

    @Override
    public R call() throws Exception {
        R result;
        try {
            result = call(t);
        } finally {
            pool.unblock();
        }
        return result;
    }

    @Override
    public void accept(T t) {
        this.t = t;
    }

    /**
     * @param t type of input
     * @return result type
     * @throws Exception
     */
    protected abstract R call(T t) throws Exception;

}
