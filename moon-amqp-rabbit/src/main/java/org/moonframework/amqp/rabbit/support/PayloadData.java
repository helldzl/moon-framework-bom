package org.moonframework.amqp.rabbit.support;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * <p>
 * 消息有效载荷, 用户消息确认中的消息恢复
 * </p>
 *
 * @author quzile
 */
public class PayloadData extends CorrelationData {

    private Message message;

    public PayloadData(Message message) {
        super("");
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

}
