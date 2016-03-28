package org.moonframework.model.mybatis.util;

import org.moonframework.model.mybatis.domain.BaseEntity;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.*;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/2
 */
public class EntityClassifier<T extends BaseEntity> {

    private List<T> updates = new ArrayList<>();
    private List<T> inserts = new ArrayList<>();
    private List<Long> deletes = new ArrayList<>();

    /**
     * @param primaryList   数据库中已经存在的数据
     * @param secondaryList 待处理的数据
     * @param field         比较字段属性, 必须是Long类型且在list中是unique的
     */
    public EntityClassifier(List<T> primaryList, List<T> secondaryList, String field) throws IllegalAccessException {
        Map<Long, T> primaryMap = new HashMap<>();
        for (T t : primaryList) {
            BeanWrapper wrapper = new BeanWrapperImpl(t);
            primaryMap.put((Long) wrapper.getPropertyValue(field), t);
        }

        // 找出不存在的记录, 放入inserts集合中. 找到已经存在的记录, 放入updates集合中并复制主键修改标志位.
        for (T t : secondaryList) {
            BeanWrapper wrapper = new BeanWrapperImpl(t);
            Long value = (Long) wrapper.getPropertyValue(field);
            if (primaryMap.containsKey(value)) {
                t.setId(primaryMap.get(value).getId());
                t.setEnabled(1);
                updates.add(t);
                primaryMap.remove(value);
            } else {
                inserts.add(t);
            }
        }

        Iterator<Map.Entry<Long, T>> it = primaryMap.entrySet().iterator();
        it.forEachRemaining(entry -> deletes.add(entry.getValue().getId()));
    }

    public List<T> getUpdates() {
        return updates;
    }

    public List<T> getInserts() {
        return inserts;
    }

    public List<Long> getDeletes() {
        return deletes;
    }
}
