package org.moonframework.concurrent;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/5/27
 */
public class BlockedTests {

    private static Random random = new Random(47);
    public RequestQueue queue = new RequestQueue();
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static class RequestQueue {

        // private Queue<Integer> queue = new ArrayBlockingQueue<>(80000000);
        private Queue<Integer> queue = new LinkedBlockingQueue<>();

        public synchronized Integer getRequest() {
            while (queue.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            Integer remove = queue.poll();
            return remove;
        }

        public synchronized void addRequest(Integer integer) {
            queue.add(integer);
            notifyAll();
        }

        public synchronized int size() {
            return queue.size();
        }
    }

    static class C extends Thread {
        public RequestQueue queue;

        public C(RequestQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                Integer request = queue.getRequest();
                atomicInteger.incrementAndGet();
                System.out.println(request);
            }
        }
    }

    static class P extends Thread {
        public RequestQueue queue;

        public P(RequestQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.addRequest(random.nextInt());
            }
        }
    }

    public static void a(BlockedTests t) {
        IntStream.range(0, 6).forEach(value -> {
            new P(t.queue).start();
        });

        IntStream.range(0, 6).forEach(value -> {
            new C(t.queue).start();
        });
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        BlockedTests t = new BlockedTests();
        a(t);

        while (true) {
            // System.out.println(atomicInteger.get());
            if (atomicInteger.get() >= 6000000) {
                break;
            }
        }

        long end = System.currentTimeMillis();
        System.out.println(String.format("ms : %s", (end - start)));

    }
}
