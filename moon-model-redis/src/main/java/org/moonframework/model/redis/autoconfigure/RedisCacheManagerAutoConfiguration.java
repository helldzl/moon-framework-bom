package org.moonframework.model.redis.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/26
 */
@Configuration
@ConditionalOnMissingBean(CacheManager.class)
@EnableCaching
public class RedisCacheManagerAutoConfiguration {

    @Bean(name = "redisCacheManager")
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        return new RedisCacheManager(redisTemplate);
    }

}
