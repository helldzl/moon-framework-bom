package org.moonframework.model.mybatis.repository;

import org.moonframework.model.mybatis.domain.BaseEntity;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/10/7
 */
public interface BaseDao<T extends BaseEntity> extends BaseRepository<T, Long> {
}
