package org.moonframework.amqp.rabbit.core;

import org.springframework.amqp.core.Message;
import org.springframework.retry.RetryContext;
import org.moonframework.amqp.rabbit.retry.MessageRecovery;

/**
 * <p>
 * 生产者的默认实现
 * </p>
 * 
 * @author quzile
 *
 */
public class DefaultProducer extends
		AbstractProducer<DefaultProducer.Status, Throwable> {

	/**
	 * 消息恢复
	 */
	private MessageRecovery messageRecovery;

	public DefaultProducer() {
	}

	@Override
	protected Status doSuccess() {
		return Status.SUCCESS;
	}

	@Override
	protected Status doRecovered() {
		return Status.RECOVERED;
	}

	@Override
	protected Status doError() {
		return Status.ERROR;
	}

	@Override
	protected void doRecover(RetryContext context) throws Exception {
		Message message = (Message) context
				.getAttribute(AbstractProducer.MESSAGE);
		messageRecovery.recover(context.getRetryCount(), message,
				context.getLastThrowable());
	}

	/**
	 * 
	 * @author quzile
	 *
	 */
	public enum Status {

		/**
		 * 成功
		 */
		SUCCESS,

		/**
		 * 失败
		 */
		ERROR,

		/**
		 * 从失败中恢复
		 */
		RECOVERED

	}

	public void setMessageRecovery(MessageRecovery messageRecovery) {
		this.messageRecovery = messageRecovery;
	}

}
