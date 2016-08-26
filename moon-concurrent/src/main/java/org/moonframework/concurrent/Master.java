package org.moonframework.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/5/27
 */
public class Master<T, R> {

    /**
     * 任务队列
     */
    private Queue<T> queue = new ConcurrentLinkedQueue<>();

    /**
     * 进程
     */
    private Map<Integer, Thread> threadMap = new HashMap<>();

    /**
     * 结果
     */
    private Map<Integer, R> resultMap = new ConcurrentHashMap<>();

    /**
     * @param worker worker
     * @param count  n of thread worker
     */
    public Master(Worker<T, R> worker, int count) {
        worker.setMaster(this);
        for (int i = 0; i < count; i++)
            threadMap.put(i, new Thread(worker));
    }

    public boolean isComplete() {
        for (Thread thread : threadMap.values()) {
            if (thread.getState() != Thread.State.TERMINATED)
                return false;
        }
        return true;
    }

    /**
     * 提交任务
     *
     * @param job job
     */
    public void submit(T job) {
        queue.add(job);
    }

    /**
     * 获得结果
     *
     * @return result
     */
    public Map<Integer, R> getResultMap() {
        return resultMap;
    }

    /**
     * 执行
     */
    public void execute() {
        threadMap.values().forEach(Thread::start);
    }

    /**
     * worker获得一个队列中的任务
     *
     * @return T
     */
    protected T poll() {
        return queue.poll();
    }

    /**
     * worker将计算结果存入集合中
     *
     * @param key    key
     * @param result R
     * @return R
     */
    protected R put(Integer key, R result) {
        return resultMap.put(key, result);
    }

}
