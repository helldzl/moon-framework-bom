package org.moonframework.concurrent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/14
 */
public abstract class ThreadPoolAdapter<T, R> extends BlockingPool implements Supplier<T> {

    /**
     * 日志
     */
    protected final Logger logger = LogManager.getLogger(ThreadPoolAdapter.class);

    /**
     * 默认线程数量: 当前系统cpu数量-1个
     */
    protected static final int DEFAULT_THREADS = Runtime.getRuntime().availableProcessors() - 1;

    /**
     * 最小线程数
     */
    protected static final int MIN_THREADS = 1;

    /**
     * 线程池大小
     */
    protected int poolSize;

    /**
     * 缓存队列因子
     */
    protected int factor = 3;

    /**
     * 线程池
     */
    protected ThreadPoolExecutor pool;

    /**
     * LOCK
     */
    protected final ReentrantLock lock = new ReentrantLock();

    /**
     * @return true if is shutdown
     */
    public boolean isShutdown() {
        return pool.isShutdown();
    }

    public void shutdown() {
        pool.shutdown();
    }

    /**
     * <p>
     * 输出当前线程池信息
     * </p>
     * <p>
     * 监控任务在运行时的线程池状态信息, 阻塞队列信息等
     * </p>
     */
    @Override
    protected void info() {
        logger.info(() -> "getTaskCount: " + pool.getTaskCount());
        logger.info(() -> "getCorePoolSize: " + pool.getCorePoolSize());
        logger.info(() -> "getActiveCount: " + pool.getActiveCount());
        logger.info(() -> "getQueue: " + getQueueSize());
        logger.info(() -> String.format("Largest Pool Size: %s", pool.getLargestPoolSize()));
    }

    /**
     * <p>
     * 获得任务处理器
     * </p>
     *
     * @return task
     */
    protected abstract Task<T, R> newTask(T t);

}
