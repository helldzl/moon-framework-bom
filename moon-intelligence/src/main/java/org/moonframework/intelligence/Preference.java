package org.moonframework.intelligence;

/**
 * <p>偏好空间</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/4/6
 */
public interface Preference {

    /**
     * <p>评分项, 使用一个直方图来保存</p>
     * <p>偏好空间中参与评分项目的维度, 最多支持64个维度, 即该数组的最大长度不能超过64</p>
     *
     * @return An array of score items
     */
    double[] items();

}
