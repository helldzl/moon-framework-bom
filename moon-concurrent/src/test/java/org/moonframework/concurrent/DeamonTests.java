package org.moonframework.concurrent;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/5/24
 */
public class DeamonTests {


    /**
     * 守护进程
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> {
            String daemon = (Thread.currentThread().isDaemon() ? "daemon" : "not daemon");
            while (true) {
                System.out.println("I'm running at " + new Date() + ", I am " + daemon);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("I was interrupt, I am " + daemon);
                    break;
                }
            }
        };

        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("main thread exits");
    }


}
