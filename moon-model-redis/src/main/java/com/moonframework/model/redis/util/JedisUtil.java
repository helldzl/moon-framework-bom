package com.moonframework.model.redis.util;

import org.moonframework.toolkit.SerializeUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lcj on 2015/12/22.
 */
public class JedisUtil {

    private static JedisPoolConfig config = new JedisPoolConfig();
    private static String host;
    private static int port;
    private static int timeout;
    private static String password;
    private static JedisPool jedisPool = null;

    /** 缓存生存时间 */
    private final static int expire = 60000;

    private static void init() {
        if(password != null && !"".equals(password)) {
            jedisPool = new JedisPool(config, host, port,timeout, password);
        } else {
            jedisPool = new JedisPool(config, host, port);
        }
    }

    public static JedisPool getPool() {

        if(jedisPool == null) {
            init();
        }

        return jedisPool;
    }

    /**
     * 从jedis连接池中获取获取jedis对象
     */
    public static Jedis getJedis() {
        return getPool().getResource();
    }

    /**
     * 回收jedis
     */
    public static void returnJedis(Jedis jedis) {
        if(jedis!=null)
            getPool().returnResource(jedis);
    }

    /**
     * 设置过期时间
     */
    public static void expire(String key, int seconds) {
        if (seconds <= 0) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.expire(key, seconds);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }

    }

    /**
     * 设置过期时间
     */
    public static void expireAt(String key, long timeMillis) {
        long currentMillis = new Date().getTime();
        if ( currentMillis >= timeMillis) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.expireAt(key, timeMillis);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    /**
     * 设置默认过期时间
     */
    public static void expire(String key) {
        expire(key, expire);
    }

    public static void set(String key,String value){
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        };
    }

    public static Boolean exists(String key){
        Jedis jedis = getJedis();
        try {
            return jedis.exists(key);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    public static void set(String key,Object value){
        Jedis jedis = getJedis();
        try {
            jedis.set(key.getBytes(), SerializeUtil.serialize(value));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    /**
     * 添加sortedset数据排序值
     * @param key
     * @param score
     * @param member
     */
    public static double zincrby(String key,double score,String member){
        Jedis jedis = getJedis();
        try {
            return jedis.zincrby(key, score, member);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    /**
     * 删除list数据排序值
     * @param key
     * @param count
     * @param member
     */
    public static long  lrem(final String key, long count, final String member) {
        Jedis jedis = getJedis();
        try {
            return jedis.lrem(key, count, member);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    /**
     * 删除sortedset数据排序值
     * @param key
     * @param key
     * @param values
     */
    public static long zrem(final String key,final String ...values) {
        Jedis jedis = getJedis();
        try {
            return jedis.zrem(key, values);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    /**
     * 排序
     * @param key
     * @param start zero based
     * @param end
     */
    public static Set<String> zrange(final String key,final int start,final int end) {
        Jedis jedis = getJedis();
        try {
            return jedis.zrange(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    /**
     * 倒叙排序
     * @param key
     * @param start zero based
     * @param end
     */
    public static Set<String> zrevrange(final String key,final int start,final int end) {
        Jedis jedis = getJedis();
        try {
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }



    public static void set(String key,int value){
        set(key, String.valueOf(value));
    }

    public static void set(String key,long value){
        set(key, String.valueOf(value));
    }

    public static void set(String key,float value){
        set(key, String.valueOf(value));
    }

    public static void set(String key,double value){
        Jedis jedis = getJedis();
        try {
            set(key, String.valueOf(value));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    public static void remove(String key){
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    public static void remove(String... keys){
        Jedis jedis = getJedis();
        try {
            jedis.del(keys);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    public static Float getFloat(String key){
        Jedis jedis = getJedis();
        try {
            String val = getStr(key);

            if(val == null) {
                return null;
            }
            return Float.valueOf(val);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    public static Double getDouble(String key){
        Jedis jedis = getJedis();
        try {

            String val = getStr(key);

            if(val == null) {
                return null;
            }
            return Double.valueOf(val);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    public static Long getLong(String key){
        Jedis jedis = getJedis();
        try {

            String val = getStr(key);

            if(val == null) {
                return null;
            }

            return Long.valueOf(val);
        }  catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    public static Integer getInt(String key){
        Jedis jedis = getJedis();
        try {
            String val = getStr(key);

            if(val == null) {
                return null;
            }
            return Integer.valueOf(val);
        }  catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    public static String getStr(String key){
        Jedis jedis = getJedis();
        try {
            String value = jedis.get(key);
            return value;
        }  catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    public static Object getObj(String key){
        Jedis jedis = getJedis();
        try {
            byte[] bits = jedis.get(key.getBytes());
            Object obj = SerializeUtil.unserialize(bits);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    public static long incr(String key, long val){

        Jedis jedis = getJedis();
        try {
            return jedis.incrBy(key, val);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }

    }

    public static Long hsetnx(String key, String field, String value) {

        Jedis jedis = getJedis();
        try {
            return jedis.hsetnx(key, field, value);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    public static String hmset(String key, Map<String, String> hash) {

        Jedis jedis = getJedis();
        try {
            return jedis.hmset(key, hash);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }

    }

    public static List<String> hmget(String key, String... fields) {
        Jedis jedis = getJedis();
        try {
            return jedis.hmget(key, fields);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    public static Long hincrBy(String key, String field, long value) {
        Jedis jedis = getJedis();
        try {
            return jedis.hincrBy(key, field, value);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    public static Long lpush(final String key, final String... strings) {
        Jedis jedis = getJedis();
        try {
            return jedis.lpush(key, strings);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    public static String ltrim(final String key,final long start,final long end) {
        Jedis jedis = getJedis();
        try {
            return jedis.ltrim(key,start,end);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            returnJedis(jedis);
        }
    }

    public static boolean isBlank(String str){
        return str==null||"".equals(str.trim());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        JedisUtil.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        JedisUtil.timeout = timeout;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        JedisUtil.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        JedisUtil.host = host;
    }

    public JedisPoolConfig getConfig() {
        return config;
    }

    public void setConfig(JedisPoolConfig config) {
        JedisUtil.config = config;
    }

    public static void main(String args[]) {
        JedisUtil jedisUtil = new JedisUtil();
        jedisUtil.setHost("192.168.1.138");
        jedisUtil.setPort(6379);
    }
}
