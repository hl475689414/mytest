/**
 * Copyright (c) 2013-2020, cpthack 成佩涛 (1044559878@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xqy.project.business.jiajiao.weixin.api.config;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.destination.BeanFactoryDestinationResolver;

/**
 * <b>ActiveMQConfiguration.java</b></br>
 * 
 * <pre>
 * ActiveMQ消息队列配置类
 * </pre>
 *
 * @author cpthack 1044559878@qq.com
 * @date Aug 23, 2017 9:51:28 PM
 * @since JDK 1.8
 */
@Configuration
public class ActiveMQConfiguration {
	
	private static final Logger	logger = LoggerFactory.getLogger(ActiveMQConfiguration.class);
	
	@Value("${mq.queue.wxTemplateQueueName}")
	private String				wxTemplateQueueName;
	
	@Resource
	private BeanFactory			springContextBeanFactory;
	
	@Bean
	public DefaultJmsListenerContainerFactory myFactory(ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setSessionTransacted(false);
		factory.setDestinationResolver(new BeanFactoryDestinationResolver(springContextBeanFactory));
		return factory;
	}
	
	@Bean(name = "wxTemplateDestination")
	public Destination wxTemplateDestination() {
		logger.info("wxTemplateQueueName:{}", wxTemplateQueueName);
		return new ActiveMQQueue(wxTemplateQueueName);
	}
	
}
