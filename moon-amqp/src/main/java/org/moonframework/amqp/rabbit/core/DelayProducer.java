package org.moonframework.amqp.rabbit.core;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 实现异步调用, 客户端不需要等待, 避免客户端因为retry长时间等待的局面
 * </p>
 * <p>
 * <p>
 * 延迟消息
 * </p>
 *
 * @author quzile
 */
public class DelayProducer extends DefaultProducer {

    /**
     * 延迟时间, 单位毫秒
     */
    private long delay;

    @Override
    public <M extends Serializable> void publishMsg(final M message) {

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(delay);
                    send(message);
                } catch (InterruptedException e) {
                    logger.error("publish error!", e);
                }
            }
        };

        t.start();

    }

    private <M extends Serializable> void send(M message) {
        super.publishMsg(message);
    }

    /**
     * 设置时延
     *
     * @param delay delay
     */
    public void setDelay(long delay) {
        this.delay = delay;
    }

}
