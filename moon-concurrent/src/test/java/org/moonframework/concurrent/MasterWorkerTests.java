package org.moonframework.concurrent;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/5/27
 */
public class MasterWorkerTests {

    public static void a() throws Exception {
        TestPool pool = new TestPool(2);
        List<Future<Integer>> execute = pool.execute();
        int sum = 0;
        for (Future<Integer> f : execute) {
            sum += f.get();
        }
        System.out.println(sum);
    }

    public static void b() {
        Worker worker = new Worker<Integer, Integer>() {
            @Override
            public Integer apply(Integer value) {
//                try {
//                    Thread.sleep(400);
//                } catch (InterruptedException e) {
//                }
                System.out.println(String.format("Value : %s", value));
                return value * value * value;
            }
        };

        Master<Integer, Integer> master = new Master<>(worker, 5);
        // 计算 1 到 100 的立方和
        IntStream.range(1, 101).forEach(value -> master.submit(value));
        // 执行
        master.execute();

        System.out.println("start");
        Map<Integer, Integer> resultMap = master.getResultMap();
        int sum = 0;
        while (resultMap.size() > 0 || !master.isComplete()) {
            Set<Map.Entry<Integer, Integer>> entries = resultMap.entrySet();
            for (Map.Entry<Integer, Integer> entry : entries) {
                sum += entry.getValue();
                resultMap.remove(entry.getKey());
            }
        }
        System.out.println(sum);
        System.out.println("---");
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        a();
    }

}
