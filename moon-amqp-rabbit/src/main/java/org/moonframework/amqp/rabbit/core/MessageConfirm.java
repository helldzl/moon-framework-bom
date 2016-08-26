package org.moonframework.amqp.rabbit.core;

import org.moonframework.amqp.rabbit.retry.MessageRecovery;
import org.moonframework.amqp.rabbit.support.PayloadData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * <p>
 * 消息确认, 消息publish成功后, 服务器会告知客户端是否接收到消息[ACK], 一般会发生在exchange连接断掉的时候.
 * </p>
 * <p>
 * <p>
 * 消息确认中需要处理那些失败的消息.
 * </p>
 *
 * @author quzile
 * @since 2015-04-06
 */
public class MessageConfirm implements ConfirmCallback {

    protected Logger logger = LoggerFactory.getLogger(MessageConfirm.class);

    private MessageRecovery messageRecovery;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("消息确认, ACK:" + ack);

        // 如果消息没有收到, 需要持久化这些失败的消息
        if (!ack) {
            if (correlationData instanceof PayloadData) {
                PayloadData data = (PayloadData) correlationData;
                try {
                    messageRecovery.recover(0, data.getMessage(), cause);
                    logger.info("消息恢复成功!");
                } catch (Exception e) {
                    logger.error("在消息确认中发生消息恢复错误!", e);
                }
            }
        }

    }

    public void setMessageRecovery(MessageRecovery messageRecovery) {
        this.messageRecovery = messageRecovery;
    }

}
