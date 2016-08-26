package org.moonframework.concurrent;

import java.util.concurrent.BlockingQueue;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/14
 */
public abstract class BlockingPool {

    private BlockingQueue<Runnable> queue;
    private int threshold = 64;
    private int n = 0;

    protected BlockingPool() {
    }

    protected void check() throws InterruptedException {
        if ((n = (n + 1) % 32) == 0)
            block();
    }

    protected void setQueue(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    protected synchronized void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    protected synchronized void block() throws InterruptedException {
        while (getQueueSize() >= threshold) {
            info();
            wait();
        }
    }

    protected synchronized void unblock() {
        if (getQueueSize() < threshold)
            notify();
    }

    protected int getQueueSize() {
        return queue.size();
    }

    protected abstract void info();

}
