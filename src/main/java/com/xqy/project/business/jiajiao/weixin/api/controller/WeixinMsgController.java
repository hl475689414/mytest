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
package com.xqy.project.business.jiajiao.weixin.api.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.in.event.InFollowEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import com.jfinal.weixin.sdk.msg.in.event.InQrCodeEvent;
import com.xqy.project.base.commons.utils.encoded.UrlEncodedHelper;
import com.xqy.project.base.commons.utils.json.JsonData;
import com.xqy.project.base.commons.utils.json.JsonHelper;
import com.xqy.project.base.exception.utils.AssertHelper;
import com.xqy.project.business.jiajiao.commons.service.Des3Service;
import com.xqy.project.business.jiajiao.dao.finance.model.TeacherInfo;
import com.xqy.project.business.jiajiao.dao.finance.service.TeacherInfoDaoService;
import com.xqy.project.business.jiajiao.weixin.api.annotation.WxMsgValidator;
import com.xqy.project.business.jiajiao.weixin.api.base.AbstractWxMsgControllerAdapter;

/**
 * <b>WeixinMsgController.java</b></br>
 * 
 * <pre>
 * 微信公众号
 * </pre>
 *
 * @author cpthack 1044559878@qq.com
 * @date Apr 28, 2018 11:18:19 AM
 * @since JDK 1.8
 */
@RestController
public class WeixinMsgController extends AbstractWxMsgControllerAdapter {
	
	private final static Logger	  logger = LoggerFactory.getLogger(WeixinMsgController.class);
	
	@Resource
	private TeacherInfoDaoService teacherInfoDaoService;
	@Resource
	private Des3Service des3Service;
	
	@WxMsgValidator
	@RequestMapping(value = "/msg")
	public void index() {
		super.index();
	}
	
	@Override
	protected void processInFollowEvent(InFollowEvent inFollowEvent) {
		logger.info("关注/取消关注事件:{}", JsonHelper.toJson(inFollowEvent));
		renderDefault();
	}
	
	@Override
	protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
		/**
		 * {"createTime":1524900334,"event":"SCAN","eventKey":"T786788086","fromUserName":"oZscOv2Uqt3Oa2f4jRp7dZ-lzmHU","msgType":"event","ticket":"gQG68DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAybVJWSVJOQzc4ZV8xUklzSk5xY0IAAgTsIeRaAwSAOgkA","toUserName":"gh_470f20d6d700"}
		 */
		String inQrCodeEventToJson = JsonHelper.toJson(inQrCodeEvent);
		logger.info("接收到的扫描带参数二维码事件:{}", inQrCodeEventToJson);
		JsonData jsonData = JsonData.createObject(inQrCodeEventToJson);
		String tCode = jsonData.getStr("eventKey");
		TeacherInfo teacherInfo = teacherInfoDaoService.findByTCode(tCode);
		if (null == teacherInfo) {
			renderOutTextMsg("您的二维码已经失效,请联系客服重新获取.");
			return;
		}
		String status = "未绑定";
		if (StringUtils.isNotBlank(teacherInfo.getOpenID())) {
			status = "已绑定";
		}
		StringBuilder content = new StringBuilder("Hello栗子们~您正在绑定栗智家教账号~\n");
		content.append("您的基本信息如下:\n");
		content.append("编号:").append(tCode).append("\n");
		content.append("姓名:").append(teacherInfo.getCNName()).append("\n");
		content.append("地区:").append(teacherInfo.getBusinessCity()).append("\n");
		content.append("绑定状态:").append(status).append(".\n");
		
		if (StringUtils.isBlank(teacherInfo.getOpenID())) {
			//对 tCode 进行加密
			tCode = des3Service.encryptMode(tCode);
			tCode = UrlEncodedHelper.encode(tCode, "utf-8");
			AssertHelper.notNull(tCode, "对 tCode 加密失败");
			content.append("\n <a href=\"http://m.delijiajiao.com/#/binding?tcode=" +tCode+ "\">点击绑定身份</a>");
		}
		renderOutTextMsg(content.toString());
	}
	
	@Override
	protected void processInTextMsg(InTextMsg inTextMsg) {
		logger.info("接收到文本事件:{}", JsonHelper.toJson(inTextMsg));
		
		// 转发到客服中心
		renderCustomMsg(inTextMsg);
	}
	
	@Override
	protected void processInMenuEvent(InMenuEvent inMenuEvent) {
		logger.info("接收到自定义菜单事件:{}", JsonHelper.toJson(inMenuEvent));
		String content = "Hello栗子们~欢迎关注栗智家教~认证教员请前往个人中心报到哟~请确认你的个人资料，等待祖国花朵的召唤！另，德栗校园合伙人正火热招聘中！虚位以待，等你~详询 \nhttp://cn.mikecrm.com/3eLXB7s";
		renderOutTextMsg(content);
	}
	
}
