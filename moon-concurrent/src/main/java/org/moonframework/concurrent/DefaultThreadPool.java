package org.moonframework.concurrent;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/**
 * <p>
 * 利用java并发包的线程池提高任务执行效率
 * </p>
 * <p>
 * 这是一个线程池的框架, 项目下的所有多线程任务可以继承该类, 实现自己任务逻辑, 该类会帮助你完成对线程池的各种操作。
 * </p>
 * <p>
 * 一对多模型: 单生产者, 多消费者, 当消费能力慢于生产者时可使用这种模型, 线程池自己充当生产者, 如图片下载处理, 消费能力远慢于生产能力
 * </p>
 * <p>
 * 一对一模型：单生产者, 单消费者, 任务类自给自足, 把service的业务处理委派该task类
 * </p>
 *
 * @param <T> input type
 * @param <R> return result
 * @author quzile
 * @version 1.0.0
 * @since 2015-05-27
 */
public abstract class DefaultThreadPool<T, R> extends ThreadPoolAdapter<T, R> implements Closeable {

    /**
     *
     */
    private long start;

    /**
     * 默认
     */
    public DefaultThreadPool() {
        this(DEFAULT_THREADS);
    }

    /**
     * @param n 线程数量, n-1:推荐服务器内核数减1
     */
    public DefaultThreadPool(int n) {
        this.poolSize = n < MIN_THREADS ? DEFAULT_THREADS : n;
        this.pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize);
        setQueue(pool.getQueue());
        setThreshold(poolSize * factor);
    }

    /**
     * <p>
     * 优化了一次, 递归改成while, 避免方法递归调用时最大栈深度的限制(StackOverFlow)
     * </p>
     *
     * @return futures
     */
    public List<Future<R>> execute() {
        lock.lock();
        List<Future<R>> results = new ArrayList<>();
        try {
            if (isShutdown()) {
                throw new IllegalStateException(
                        "Run mode is [RunMode.ONCE], this method only call once! You can set [RunMode.LISTENER] mode to reuse the pool!");
            }

            start = System.currentTimeMillis();
            // 如果线程池shutdown了, 就返回结果, 否则循环获取任务
            while (!isShutdown()) {
                for (Callable<R> callable : check(task()))
                    results.add(pool.submit(callable));
                info();
            }
        } catch (InterruptedException e) {
            logger.error(() -> e);
        } finally {
            lock.unlock();
        }
        // 返回结果
        return doAfter(results);
    }

    /**
     * <p>
     * 阻塞, 等待所有线程执行完毕, 再返回结果
     * </p>
     * <p>
     * 子类可以重写该方法实现非阻塞
     * </p>
     *
     * @param results 结果集合
     * @return futures
     */
    protected List<Future<R>> doAfter(List<Future<R>> results) {
        try {
            try {
                for (Future<R> result : results)
                    result.get();
                long end = System.currentTimeMillis();
                float second = (end - start) / 1_000f;
                logger.info(() -> String.format("task finished in [%s] second!", second));
                return results;
            } finally {
                close();
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            logger.error(() -> "error!", e);
            throw new RuntimeException();
        }
    }

    /**
     * <p>
     * 每次检查生产者集合, 如果为空, 就shutdown线程池, 不再接收新的任务
     * </p>
     * <p>
     * shutdown后线程池会处理完所有在队列中等待的任务, 但是不会允许再往队列插入任务
     * </p>
     * <p>
     * 当队列大于(线程池的大小*因子)时, 就wait, 避免生产者生产过多不能被消费的实例
     * </p>
     * <p>
     * 控制缓存队列的大小, 让生产者与消费者之间达到一种平衡, 在满足充分利用系统资源的同时, 也不会发生内存溢出等情况
     * </p>
     *
     * @param list Callable list
     * @return Callable list
     */
    protected List<Callable<R>> check(List<Callable<R>> list) throws InterruptedException {
        if (list.isEmpty())
            shutdown();
        check();
        return list;
    }

    /**
     * <p>
     * 获取任务集合, 提供待消费的任务数据
     * </p>
     *
     * @return list
     */
    protected List<Callable<R>> task() {
        List<Callable<R>> list = new ArrayList<>();
        for (int i = 0; i < poolSize; i++) {
            T t = get();
            if (t != null) {
                Task<T, R> task = newTask(t);
                task.accept(t);
                list.add(task);
            } else {
                break;
            }
        }
        return list;
    }

    @Override
    public void close() throws IOException {

    }

}
