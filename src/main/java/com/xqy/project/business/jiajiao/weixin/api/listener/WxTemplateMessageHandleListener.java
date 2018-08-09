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
package com.xqy.project.business.jiajiao.weixin.api.listener;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.xqy.project.base.cache.common.CustomizedCache;
import com.xqy.project.base.commons.utils.crypto.Md5Helper;
import com.xqy.project.base.commons.utils.json.JsonHelper;
import com.xqy.project.business.gateway.commons.bean.WxTemplateMessageSendRequest;
import com.xqy.project.business.jiajiao.weixin.api.manager.service.WxTemplateMessageHandleService;

/**
 * <b>WxTemplateMessageHandleListener.java</b></br>
 * 
 * <pre>
 * 微信模板消息处理相关消息监听器
 * </pre>
 *
 * @author cpthack 1044559878@qq.com
 * @date May 3, 2018 2:23:02 PM
 * @since JDK 1.8
 */
@Service
public class WxTemplateMessageHandleListener {
	
	private final static Logger			   logger = LoggerFactory.getLogger(WxTemplateMessageHandleListener.class);
	
	@Resource
	private CustomizedCache				   customizedCache;
	
	@Resource
	private WxTemplateMessageHandleService wxTemplateMessageHandleService;
	
	@JmsListener(destination = "wxTemplateDestination",
	        containerFactory = "myFactory")
	public void onMessage(String msg) {
		try {
			logger.debug("接收到的MQ消息.{}", msg);
			WxTemplateMessageSendRequest message = JsonHelper.parse(msg, WxTemplateMessageSendRequest.class);
			if (null == message) {
				logger.warn("模板消息发送对象为空,本次发送失败.");
				return;
			}
			
			String uniqueCacheKey = "wxTemplate:unique:" + Md5Helper.encrypt(msg);
			// String uniqueCacheKey = message.getTemplateId() + message.getTouser();
			
			String uniqueCacheValue = "1";
			long expiredSeconds = TimeUnit.DAYS.toSeconds(1);
			boolean isUnique = customizedCache.setnx(uniqueCacheKey, uniqueCacheValue, expiredSeconds);
			if (isUnique) {
				wxTemplateMessageHandleService.sendMsg(message);
			}
			else {
				logger.warn("模板消息发送失败,原因是同个模板消息发送同个用户太多次了.message={}", msg);
			}
		}
		catch (Exception e) {
			logger.error("模板消息发送失败.msg=[" + msg + "]", e);
		}
	}
}