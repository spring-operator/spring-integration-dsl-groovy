/*
 * Copyright 2002-2012 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.springframework.integration.dsl.groovy.amqp.builder

import groovy.util.FactoryBuilderSupport
import java.util.Map
import org.springframework.integration.dsl.groovy.amqp.AmqpOutbound
import org.springframework.integration.dsl.groovy.builder.IntegrationComponentFactory

/**
 * @author David Turanski
 *
 */
class AmqpOutboundFactory extends IntegrationComponentFactory {
	boolean oneWay

	/* (non-Javadoc)
	 * @see org.springframework.integration.dsl.groovy.builder.IntegrationComponentFactory#doNewInstance(groovy.util.FactoryBuilderSupport, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@Override
	protected Object doNewInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) {
		new AmqpOutbound(attributes << [oneWay:oneWay])
	}

}
