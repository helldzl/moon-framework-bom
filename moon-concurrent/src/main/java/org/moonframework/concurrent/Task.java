package org.moonframework.concurrent;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * <p>
 * Concurrent thread tasks
 * </p>
 *
 * @param <T> input type
 * @param <R> the return result
 * @author quzile
 * @version 1.0
 * @since 2016/5/27
 */
public interface Task<T, R> extends Callable<R>, Consumer<T> {
}
