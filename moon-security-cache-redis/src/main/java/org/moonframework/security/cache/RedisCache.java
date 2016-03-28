package org.moonframework.security.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.*;

/**
 * <p>http://logging.apache.org/log4j/2.x/manual/api.html</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/1/26
 */
public class RedisCache<K, V> implements Cache<K, V> {

    protected final Logger logger = LogManager.getLogger(RedisCache.class);

    private RedisTemplate<K, V> template;

    public RedisCache(RedisTemplate<K, V> template) {
        if (template == null)
            throw new IllegalArgumentException("Cache argument cannot be null.");
        this.template = template;
    }

    @Override
    public V get(K key) throws CacheException {
        try {
            logger.debug("Getting object from cache for key [{}]", key);
            return template.opsForValue().get(key);
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public V put(K key, V value) throws CacheException {
        logger.debug("Putting object in cache for key [{}]", key);
        try {
            ValueOperations<K, V> valueOperations = template.opsForValue();
            V v = valueOperations.get(key);
            template.opsForValue().set(key, value);
            return v;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public V remove(K key) throws CacheException {
        logger.debug("Removing object from cache for key [{}]", key);
        try {
            V previous = get(key);
            template.delete(key);
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public void clear() throws CacheException {
        logger.debug("Clearing all objects from cache");
//        try {
//            throw new UnsupportedOperationException("Unsupported operation");
//        } catch (Throwable t) {
//            throw new CacheException(t);
//        }
    }

    @Override
    public int size() {
        try {
            // TODO
            return 0;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public Set<K> keys() {
        try {
            // TODO
            @SuppressWarnings({"unchecked"})
            List<K> keys = null; //= cache.getKeys();
            if (!CollectionUtils.isEmpty(keys)) {
                return Collections.unmodifiableSet(new LinkedHashSet<>(keys));
            } else {
                return Collections.emptySet();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public Collection<V> values() {
        // TODO
        return null;
    }
}
