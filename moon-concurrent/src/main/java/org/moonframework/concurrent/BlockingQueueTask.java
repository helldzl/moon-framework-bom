package org.moonframework.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * <p>将任务结果放入处理队列中</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/6/14
 */
public abstract class BlockingQueueTask<T, R> extends TaskAdapter<T, R> {

    /**
     * 共享内容缓冲区
     */
    private BlockingQueue<R> queue;

    /**
     * 超时时间
     */
    private int timeout = 2;

    /**
     * @param pool  pool
     * @param queue blocking queue
     */
    public BlockingQueueTask(BlockingPool pool, BlockingQueue<R> queue) {
        super(pool);
        this.queue = queue;
    }

    @Override
    public R call() throws Exception {
        R result = super.call();
        if (result == null)
            return null;

        if (!queue.offer(result, timeout, TimeUnit.SECONDS)) {
            logger.error(() -> "failed to put data : " + result);
        }
        return result;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
