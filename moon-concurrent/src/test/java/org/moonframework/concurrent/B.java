package org.moonframework.concurrent;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/2
 */
public class B {
    public static B t1 = new B();
    public static B t2 = new B();

    {
        System.out.println("构造块");
    }

    static {
        System.out.println("静态块");
    }

    private LinkedBlockingQueue<String> q =  new LinkedBlockingQueue<>();
    private Random random = new Random();
    public void a() {
        try {
            while(true){
                int i = random.nextInt(5);
                TimeUnit.SECONDS.sleep(i);
                q.put("time" + i);
                System.out.println("time" + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void b() {
        try {
            while(true){
                System.out.println("take:"+q.take());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final B t = new B();

        new Thread(){
            @Override
            public void run() {
                t.a();
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                t.b();
            }
        }.start();
    }
}
