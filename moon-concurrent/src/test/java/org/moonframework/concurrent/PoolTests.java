package org.moonframework.concurrent;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/14
 */
public class PoolTests {

    public static void a() {
        {
            BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
            BlockingQueue<Integer> result = new LinkedBlockingQueue<>();
            Random random = new Random();


            class Pool extends OptionalThreadPool<Integer, Integer> {
                public Pool(BlockingQueue<Integer> blockingQueue, int n) {
                    super(blockingQueue, n);
                }

                @Override
                protected Task<Integer, Integer> newTask(Integer integer) {
                    Pool o = this;
                    return new TaskAdapter<Integer, Integer>(null) {
                        @Override
                        protected Integer call(Integer integer) throws Exception {
                            int s = random.nextInt(4);

                            TimeUnit.SECONDS.sleep(s);
                            System.out.println("TASK ms : " + integer);
                            o.unblock();
                            return null;
                        }
                    };
                }
            }

            OptionalThreadPool<Integer, Integer> pool = new Pool(queue, 2);
            new Thread() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < 1000; i++) {
                            int s = random.nextInt(10);

                            TimeUnit.MILLISECONDS.sleep(500);
                            queue.add(i);
                            System.out.println("PRODUCER SIZE : " + queue.size());
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

//        new Thread(){
//            @Override
//            public void run() {
//                pool.execute();
//            }
//        }.start();
//
//        new Thread(){
//            @Override
//            public void run() {
//                pool.execute();
//            }
//        }.start();

            pool.execute();
        }
    }

    public static void b() {
        int n = 0;
        for (int i = 0; i < 100; i++) {
            n = ++n % 32;
            System.out.println(n);
        }
    }

    public static void main(String[] args) {
        a();
    }

}
