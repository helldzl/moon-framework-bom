package org.moonframework.concurrent;

import java.util.function.Function;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/5/27
 */
public abstract class Worker<T, R> implements Runnable, Function<T, R> {

    private int timeout = 60 * 1_000;

    private Master<T, R> master;

    @Override
    public void run() {
        while (true) {
            T job = master.poll();
            if (job == null) break;
            master.put(job.hashCode(), apply(job));
        }
    }

    protected void setMaster(Master<T, R> master) {
        this.master = master;
    }

}
