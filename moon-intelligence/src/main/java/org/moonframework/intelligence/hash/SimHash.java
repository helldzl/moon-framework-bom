package org.moonframework.intelligence.hash;

import java.util.List;
import java.util.function.ToLongFunction;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/8/3
 */
public class SimHash implements ToLongFunction<List<Word>> {

    @Override
    public long applyAsLong(List<Word> words) {
        // 创建直方图
        int[] hist = new int[64];
        for (Word word : words) {
            long hash = CnHash.hash(word.getFeature());
            int weight = word.getWeight();

            // 对于每一个特征向量的hash值的每一位进行, 0就减去权重, 1就加上权重
            for (int j = 0; j < 64; j++) {
                hist[j] += (hash & (1 << j)) == 0 ? -weight : weight;
            }
        }

        // 计算位向量
        long simHash = 0;
        for (int i = 0; i < 64; i++) {
            long t = hist[i] >= 0 ? 1 : 0;
            t <<= i;
            simHash |= t;
        }

        return simHash;
    }

}
