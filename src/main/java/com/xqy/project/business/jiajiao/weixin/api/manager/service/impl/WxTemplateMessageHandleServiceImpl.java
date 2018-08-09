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
package com.xqy.project.business.jiajiao.weixin.api.manager.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.jfinal.weixin.sdk.api.TemplateMsgApi;
import com.xqy.project.base.commons.utils.json.JsonHelper;
import com.xqy.project.business.gateway.commons.bean.WxTemplateMessageSendRequest;
import com.xqy.project.business.jiajiao.weixin.api.manager.service.WxTemplateMessageHandleService;

/**
 * <b>WxTemplateMessageHandleServiceImpl.java</b></br>
 * 
 * <pre>
 * 微信模板消息处理 - 具体实现类
 * </pre>
 *
 * @author cpthack 1044559878@qq.com
 * @date May 3, 2018 2:31:13 PM
 * @since JDK 1.8
 */
@Service
public class WxTemplateMessageHandleServiceImpl implements WxTemplateMessageHandleService {
	
	@Override
	public void sendMsg(WxTemplateMessageSendRequest message) {
		Map<String, Object> templateMsgMap = new HashMap<String, Object>();
		templateMsgMap.put("touser", message.getTouser());
		templateMsgMap.put("template_id", message.getTemplateId());
		if (StringUtils.isNotBlank(message.getUrl())) {
			templateMsgMap.put("url", message.getUrl());
		}
		if (message.getMiniprogram() != null) {
			templateMsgMap.put("miniprogram", message.getMiniprogram());
		}
		if (message.getData() != null) {
			templateMsgMap.put("data", message.getData());
		}
		String jsonStr = JsonHelper.toJson(templateMsgMap);
		TemplateMsgApi.send(jsonStr);
	}
	
}
