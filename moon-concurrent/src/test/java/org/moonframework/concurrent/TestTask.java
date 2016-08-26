package org.moonframework.concurrent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/5/27
 */
public class TestTask extends TaskAdapter<List<Integer>, Integer> {

    public TestTask(DefaultThreadPool<List<Integer>, Integer> pool) {
        super(pool);
    }

    @Override
    protected Integer call(List<Integer> list) throws Exception {
        Integer result = list.stream().collect(Collectors.summingInt(value -> value));
        System.out.println(list.size() + " " + result);
        return result;
    }

}
