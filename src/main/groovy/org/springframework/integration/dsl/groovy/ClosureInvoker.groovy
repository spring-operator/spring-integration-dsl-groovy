package org.springframework.integration.dsl.groovy

import org.springframework.integration.Message
import org.springframework.integration.support.MessageBuilder
import org.apache.commons.logging.LogFactory
import org.apache.commons.logging.Log

class ClosureInvoker {
	private final closure

	ClosureInvoker(Closure closure){
		this.closure = closure.clone()
	}

	public Object invoke(Object... args) {
		closure.call(args)
	}

	public Object typeSafeInvoke(Object... args) {
		closure.doCall(args)
	}
}

class ClosureInvokingTransformer implements org.springframework.integration.transformer.Transformer {
	private final ClosureInvokingMessageProcessor messageProcessor
	 

	ClosureInvokingTransformer(Closure closure){
		this.messageProcessor = new ClosureInvokingMessageProcessor(closure)
	}

	/* (non-Javadoc)
	 * @see org.springframework.integration.transformer.Transformer#transform(org.springframework.integration.Message)
	 */
	public Message<?> transform(Message<?> message) {
		Object result = this.messageProcessor.processMessage(message)
		return (result==null) ? null:
		MessageBuilder.withPayload(result).copyHeaders(message.headers).build()
	}
}

/**
 * 
 * @author David Turanski
 *
 * @param <T>
 */

class ClosureInvokingMessageProcessor  {
    private static Log logger = LogFactory.getLog(ClosureInvokingMessageProcessor)
	private Class parameterType
	private Closure closure

	ClosureInvokingMessageProcessor(Closure closure){
		assert closure.getParameterTypes().size() == 1, 'Closure must specify exactly one parameter'
		this.closure = closure.clone()
		this.parameterType = closure.getParameterTypes()[0]
	}
	
	public Object processMessage(Message message) {
	
		Object result = null
		if (parameterType == Message) {
			if (logger.isDebugEnabled()) {
				logger.debug("invoking closure on message")
			}
			result =  this.closure.doCall(message)
		}

		else if (parameterType == Map && !(message.payload instanceof Map)) {
			if (logger.isDebugEnabled()) {
				logger.debug("invoking closure on message headers")
			}
			result =  this.closure.doCall(message.headers)
		}

		else {
			if (logger.isDebugEnabled()) {
				logger.debug("invoking closure on message payload")
			}
			result =  this.closure.doCall(message.payload)
		}
			
		result
		
	}
}

