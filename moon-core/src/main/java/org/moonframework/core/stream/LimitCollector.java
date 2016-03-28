package org.moonframework.core.stream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * <p>limit收集器, 当作为groupingBy收集器的下游收集器时控制集合中元素的上限</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/3/18
 */
public class LimitCollector<U> implements Collector<U, List<U>, List<U>> {

    private static final Set<Characteristics> CHARACTERISTICS = new HashSet<>();

    static {
        CHARACTERISTICS.add(Characteristics.IDENTITY_FINISH);
    }

    @Override
    public Supplier<List<U>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<U>, U> accumulator() {
        return this::add;
    }

    @Override
    public BinaryOperator<List<U>> combiner() {
        return this::merge;
    }

    @Override
    public Function<List<U>, List<U>> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return CHARACTERISTICS;
    }

    private void add(List<U> list, U u) {
        list.add(u);
    }

    private List<U> merge(List<U> first, List<U> second) {
        first.addAll(second);
        return first;
    }

}
