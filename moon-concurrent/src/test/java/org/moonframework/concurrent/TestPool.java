package org.moonframework.concurrent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/5/27
 */
public class TestPool extends DefaultThreadPool<List<Integer>, Integer> {

    private static AtomicInteger atomicInteger = new AtomicInteger();

    public TestPool() {
    }

    public TestPool(int n) {
        super(n);
    }

    @Override
    protected Task<List<Integer>, Integer> newTask(List<Integer> integers) {
        return new TestTask(this);
    }

    @Override
    public List<Integer> get() {
        // from db
        if (atomicInteger.get() <= 100) {
            int n = atomicInteger.getAndIncrement();
            System.out.println(n);
            return Collections.singletonList(n * n * n);
        } else {
            return null;
        }
    }

}
