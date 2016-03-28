package org.moonframework.security.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;

import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/26
 */
public class RedisCacheManager implements CacheManager {

    protected final Logger logger = LogManager.getLogger(RedisCacheManager.class);

    private RedisTemplate template;

    @SuppressWarnings("unchecked")
    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        List<RedisClientInfo> list = template.getClientList();
        for (RedisClientInfo redisClientInfo : list) {
            if (name.equals(redisClientInfo.getName()))
                return new RedisCache<>(template);
        }
        return null;
    }

    public void setTemplate(RedisTemplate template) {
        this.template = template;
    }
}
