package org.moonframework.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/14
 */
public abstract class OptionalThreadPool<T, R> extends ThreadPoolAdapter<T, R> {

    /**
     * 任务阻塞队列
     */
    private BlockingQueue<T> blockingQueue;

    /**
     * @param blockingQueue blocking queue
     * @param n             thread num
     */
    public OptionalThreadPool(BlockingQueue<T> blockingQueue, int n) {
        this.blockingQueue = blockingQueue;
        this.poolSize = n < MIN_THREADS ? DEFAULT_THREADS : n;
        this.pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize);
        setQueue(pool.getQueue());
        setThreshold(poolSize * factor);
    }

    /**
     *
     */
    public void execute() {
        lock.lock();
        try {
            logger.info(() -> "lock pool");
            while (!isShutdown()) {
                T t = get();
                if (t != null) {
                    check();
                    Task<T, R> task = newTask(t);
                    task.accept(t);
                    pool.submit(task);
                }
            }
        } catch (InterruptedException e) {
            logger.error(() -> e);
        } finally {
            lock.unlock();
            logger.info(() -> "unlock pool");
        }
    }

    @Override
    public T get() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            logger.error(() -> e);
            return null;
        }
    }

}
