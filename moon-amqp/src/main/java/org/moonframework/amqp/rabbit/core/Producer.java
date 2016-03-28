package org.moonframework.amqp.rabbit.core;

import java.io.Serializable;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * @author quzile
 */
public interface Producer {

    /**
     * <p>
     * 发布消息
     * </p>
     *
     * @param message message
     */
    <M extends Message> void publish(M message);

    /**
     * <p>
     * 发布消息
     * </p>
     *
     * @param routingKey routingKey
     * @param message    message
     */
    <M extends Message> void publish(String routingKey, M message);

    /**
     * <p>
     * 发布消息
     * </p>
     *
     * @param message         message
     * @param correlationData correlationData
     */
    <M extends Message> void publish(M message, CorrelationData correlationData);

    /**
     * <p>
     * 发布消息
     * </p>
     *
     * @param routingKey      routingKey
     * @param message         message
     * @param correlationData correlationData
     */
    <M extends Message> void publish(String routingKey, M message, CorrelationData correlationData);

    /**
     * <p>
     * 发布消息
     * </p>
     *
     * @param message message
     */
    <M extends Serializable> void publishMsg(M message);

    /**
     * <p>
     * 发送消息到指定的队列中去
     * </p>
     *
     * @param routingKey routingKey
     * @param message    message
     */
    <M extends Serializable> void publishMsg(String routingKey, M message);

    /**
     * <p>
     * 获得routingKey
     * </p>
     *
     * @param argument argument
     * @return RoutingKey
     */
    String getRoutingKey(Object argument);

}
