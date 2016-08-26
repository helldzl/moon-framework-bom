package org.moonframework.concurrent.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.BooleanSupplier;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/8/10
 */
public class LockUtils {

    /**
     * @param lock     lock
     * @param time     seconds
     * @param supplier supplier
     * @return if success return true
     */
    public static boolean tryLock(Lock lock, long time, BooleanSupplier supplier) {
        try {
            if (lock.tryLock(time, TimeUnit.SECONDS)) {
                try {
                    return supplier.getAsBoolean();
                } finally {
                    lock.unlock();
                }
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            return false;
        }
    }

}
