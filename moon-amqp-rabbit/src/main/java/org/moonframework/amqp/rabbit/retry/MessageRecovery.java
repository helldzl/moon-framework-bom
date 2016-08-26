package org.moonframework.amqp.rabbit.retry;

import org.springframework.amqp.core.Message;

/**
 * <p>
 * 消息恢复的API
 * </p>
 *
 * @author quzile
 */
public interface MessageRecovery {

    /**
     * @param retryCount    retryCount
     * @param message       message
     * @param lastThrowable lastThrowable
     * @throws Exception
     */
    void recover(int retryCount, Message message, Throwable lastThrowable) throws Exception;

    /**
     * @param retryCount retryCount
     * @param message    message
     * @param cause      cause
     * @throws Exception
     */
    void recover(int retryCount, Message message, String cause) throws Exception;

}
