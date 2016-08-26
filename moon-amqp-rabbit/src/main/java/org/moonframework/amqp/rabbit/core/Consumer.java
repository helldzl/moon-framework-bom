package org.moonframework.amqp.rabbit.core;

import org.springframework.amqp.core.MessageListener;

/**
 * <p>
 * If a MessageListener fails because of a business exception, the exception is
 * handled by the message listener container and then it goes back to listening
 * for another message. If the failure is caused by a dropped connection (not a
 * business exception), then the consumer that is collecting messages for the
 * listener has to be cancelled and restarted. The
 * SimpleMessageListenerContainer handles this seamlessly, and it leaves a log
 * to say that the listener is being restarted. In fact it loops endlessly
 * trying to restart the consumer, and only if the consumer is very badly
 * behaved indeed will it give up. One side effect is that if the broker is down
 * when the container starts, it will just keep trying until a connection can be
 * established.
 * </p>
 * 
 * <p>
 * 消息消费者, 一个标识接口, 实现消息监听器
 * </p>
 * 
 * @author quzile
 *
 */
public interface Consumer extends MessageListener {

}
