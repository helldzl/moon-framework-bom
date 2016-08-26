package org.moonframework.intelligence;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/4/6
 */
public class Test {

    public void a() {
        Person zhang = new Person();
        Person cheng = new Person();

        double distance = EuclideanDistanceScore.distance(zhang, cheng);
        System.out.println(distance);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Test t = new Test();
        t.a();
        t.a();
        t.a();
        t.a();
    }

}
