package org.moonframework.model.mybatis.service;

import org.moonframework.model.mybatis.domain.BaseEntity;
import org.moonframework.model.mybatis.repository.BaseRepository;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/11/26
 */
public interface BaseService<T extends BaseEntity> extends BaseRepository<T, Long> {
}
