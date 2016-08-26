package org.moonframework.amqp.rabbit.core;

import java.io.Serializable;

/**
 * <p>
 * 静态工厂
 * </p>
 *
 * @author quzile
 */
public class Producers {

    /**
     * @param producer producer
     * @param key      key
     * @param message  message
     */
    public static <M extends Serializable> void publish(Producer producer, String key, M message) {
        if (key == null || "0".equals(key))
            throw new RuntimeException("message publish error!");
        producer.publishMsg(producer.getRoutingKey(key), message);
    }

    /**
     * @param producer producer
     * @param key      key
     * @param message  message
     */
    public static <M extends Serializable> void publish(Producer producer, long key, M message) {
        publish(producer, String.valueOf(key), message);
    }

    /**
     * @param producer producer
     * @param message  message
     */
    public static <M extends Serializable> void publish(Producer producer, M message) {
        producer.publishMsg(message);
    }

    /**
     * @param routingKey routingKey
     * @param producer   producer
     * @param message    message
     */
    public static <M extends Serializable> void publish(String routingKey, Producer producer, M message) {
        producer.publishMsg(routingKey, message);
    }

}
