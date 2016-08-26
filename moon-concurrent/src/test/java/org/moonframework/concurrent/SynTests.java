package org.moonframework.concurrent;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/5/27
 */
public class SynTests {

    public synchronized void a(String name){
        System.out.println("IN a");
        try {
            // Thread.sleep(500000);
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + " " );
    }

    public synchronized void b(String name){
        System.out.println(name + " " );
    }

    public static void main(String [] args){
        SynTests t = new SynTests();
        new Thread(){
            @Override
            public void run() {
                t.a("AA");
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                t.b("BB");
                t.a("BB AA");
            }
        }.start();
        System.out.println("FIN");
    }

}
