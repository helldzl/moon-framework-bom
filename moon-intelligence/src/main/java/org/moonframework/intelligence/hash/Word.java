package org.moonframework.intelligence.hash;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/8/3
 */
public class Word {

    private String feature;         // 特征
    private int weight;             // 权重
    private int begin;              // 位置

    public Word(String feature, int weight, int begin) {
        this.feature = feature;
        this.weight = weight;
        this.begin = begin;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

}
