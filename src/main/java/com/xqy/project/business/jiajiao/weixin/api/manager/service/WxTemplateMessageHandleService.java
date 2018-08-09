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
package com.xqy.project.business.jiajiao.weixin.api.manager.service;

import com.xqy.project.business.gateway.commons.bean.WxTemplateMessageSendRequest;

/**
 * <b>WxTemplateMessageHandleService.java</b></br>
 * 
 * <pre>
 * 微信模板消息处理 - 接口类
 * </pre>
 *
 * @author cpthack 1044559878@qq.com
 * @date May 3, 2018 2:29:30 PM
 * @since JDK 1.8
 */
public interface WxTemplateMessageHandleService {
	
	/**
	 * 
	 * <b>sendMsg</b> <br/>
	 * <br/>
	 * 
	 * 发送模板消息<br/>
	 * 
	 * @author cpthack 1044559878@qq.com
	 * @param message
	 *
	 */
	void sendMsg(WxTemplateMessageSendRequest message);
}
