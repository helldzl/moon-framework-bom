package org.moonframework.amqp.rabbit.core;

import java.io.Serializable;
import java.text.MessageFormat;

import org.moonframework.amqp.rabbit.support.PayloadData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.moonframework.amqp.rabbit.support.Convertion;

/**
 * <p>
 * 消息生产者
 * </p>
 *
 * @author quzile
 * @since 2015-04-05
 */
public abstract class AbstractProducer<T, E extends Throwable> implements
        Producer {

    /**
     * logger
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Rabbit Template
     */
    protected RabbitTemplate rabbitTemplate;

    /**
     * retry
     */
    protected RetryTemplate retryTemplate;

    /**
     * exchange name
     */
    protected String exchange;

    /**
     * queue name
     */
    protected String routingKey;

    /**
     *
     */
    protected String patterns;

    /**
     *
     */
    protected static final String MESSAGE = "message";

    public AbstractProducer() {
    }

    @Override
    public <M extends Message> void publish(M message) {
        publish(message, null);
    }

    @Override
    public <M extends Message> void publish(String routingKey, M message) {
        publish(routingKey, message, null);
    }

    @Override
    public <M extends Message> void publish(M message,
                                            CorrelationData correlationData) {
        publish(routingKey, message, correlationData);
    }

    @Override
    public <M extends Message> void publish(String routingKey, M message,
                                            CorrelationData correlationData) {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Send Message! Exchange:" + exchange
                        + ", RoutingKey:" + routingKey);
            }
            retryTemplate.execute(retry(routingKey, message, correlationData),
                    recovery());
        } catch (Throwable e) {
            logger.error("publish error!", e);
        }
    }

    @Override
    public <M extends Serializable> void publishMsg(M message) {
        publish(message(exchange, routingKey, message));
    }

    @Override
    public <M extends Serializable> void publishMsg(String routingKey, M message) {
        publish(routingKey, message(exchange, routingKey, message));
    }

    @Override
    public String getRoutingKey(Object argument) {
        return MessageFormat.format(patterns, argument);
    }

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void setRetryTemplate(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public void setPatterns(String patterns) {
        this.patterns = patterns;
    }

    /**
     * <p>
     * 发送失败重试机制
     * </p>
     *
     * @param routingKey      routingKey
     * @param message         message
     * @param correlationData correlationData
     * @return RetryCallback
     */
    protected RetryCallback<T, E> retry(final String routingKey,
                                        final Message message, final CorrelationData correlationData) {
        return context -> {
            context.setAttribute(MESSAGE, message);
            if (correlationData == null) {
                rabbitTemplate.send(exchange, routingKey, message,
                        new PayloadData(message));
            } else {
                rabbitTemplate.send(exchange, routingKey, message,
                        correlationData);
            }
            return doSuccess();
        };
    }

    /**
     * <p>
     * 消息灾难恢复
     * </p>
     *
     * @return RecoveryCallback
     */
    protected RecoveryCallback<T> recovery() {
        return context -> {
            try {
                // Object message = context.getAttribute("message");
                // Throwable t = context.getLastThrowable();
                // Do something with message
                doRecover(context);
                logger.info("message recovered successful!");
                return doRecovered();
            } catch (Exception e) {
                logger.error("message recovered error!", e);
                return doError();
            }
        };
    }

    /**
     * <p>
     * default message builder
     * </p>
     *
     * @param exchange   exchange
     * @param routingKey routingKey
     * @param message    message
     * @return Message
     */
    protected <M extends Serializable> Message message(String exchange,
                                                       String routingKey, M message) {
        try {
            return MessageBuilder
                    .withBody(Convertion.objectToByteArray(message))
                    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                    .setReceivedExchange(exchange)
                    .setReceivedRoutingKey(routingKey).build();
        } catch (Exception e) {
            throw new RuntimeException("message convert error!", e);
        }
    }

    /**
     * <p>
     * 处理发送消息成功的业务逻辑
     * </p>
     *
     * @return the result of the successful operation.
     */
    protected abstract T doSuccess();

    /**
     * <p>
     * 处理恢复消息成功的业务逻辑
     * </p>
     *
     * @return the result of the successful operation.
     */
    protected abstract T doRecovered();

    /**
     * <p>
     * 处理发送消息失败的业务逻辑
     * </p>
     *
     * @return the result of the successful operation.
     */
    protected abstract T doError();

    /**
     * <p>
     * 处理恢复消息的业务逻辑
     * </p>
     *
     * @param context context
     * @throws Exception
     */
    protected abstract void doRecover(RetryContext context) throws Exception;

}
