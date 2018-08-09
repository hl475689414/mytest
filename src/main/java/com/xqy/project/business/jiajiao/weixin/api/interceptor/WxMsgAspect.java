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
package com.xqy.project.business.jiajiao.weixin.api.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.kit.SignatureCheckKit;
import com.xqy.project.base.web.controller.AbstractController;
import com.xqy.project.business.jiajiao.weixin.api.annotation.WxMsgValidator;

/**
 * <b>WxMsgAspect.java</b></br>
 * 
 * <pre>
 * Msg 拦截器
 * 1：通过 MsgController.getApiConfig() 得到 ApiConfig 对象，并将其绑定到当前线程之上(利用了 ApiConfigKit 中的 ThreadLocal 对象)
 * 2：响应开发者中心服务器配置 URL 与 Token 请求
 * 3：签名检测
 * </pre>
 *
 * @author cpthack 1044559878@qq.com
 * @date Apr 28, 2018 2:26:55 PM
 * @since JDK 1.8
 */
@Aspect
@Component
public class WxMsgAspect {
	
	private final static Logger logger = LoggerFactory.getLogger(WxMsgAspect.class);
	
	// private static AppIdParser _parser = new AppIdParser.DefaultParameterAppIdParser();
	//
	// public static void setAppIdParser(AppIdParser parser) {
	// _parser = parser;
	// }
	
	@Around("execution(* com.xqy..*.controller..*(..)) && @annotation(wxMsgValidator)")
	public Object validate(ProceedingJoinPoint pjp, WxMsgValidator wxMsgValidator) throws Throwable {
		Object result = null;
		AbstractController controller = null;
		try {
			controller = (AbstractController) pjp.getTarget();
		}
		catch (Exception e) {
			throw new RuntimeException("实例化控制器类失败.", e);
		}
		
		// 如果是服务器配置请求，则配置服务器并返回
		if (isConfigServerRequest(controller)) {
			configServer(controller);
			return result;
		}
		
		// 对开发测试更加友好
		if (ApiConfigKit.isDevMode()) {
			logger.info("调用1");
			result = pjp.proceed();
		}
		else {
			logger.info("调用2");
			result = pjp.proceed();
			// 签名检测
			// if (checkSignature(controller)) {
			// result = pjp.proceed();
			// }
			// else {
			// controller.renderText("签名验证失败，请确定是微信服务器在发送消息过来");
			// }
		}
		
		return result;
	}
	
	/**
	 * 检测签名
	 */
	private boolean checkSignature(AbstractController controller) {
		String signature = controller.getPara("signature");
		String timestamp = controller.getPara("timestamp");
		String nonce = controller.getPara("nonce");
		if (StrKit.isBlank(signature) || StrKit.isBlank(timestamp) || StrKit.isBlank(nonce)) {
			controller.renderText("check signature failure");
			return false;
		}
		
		if (SignatureCheckKit.me.checkSignature(signature, timestamp, nonce)) {
			return true;
		}
		else {
			logger.error("check signature failure: " + " signature = " + controller.getPara("signature") + " timestamp = " + controller.getPara("timestamp") + " nonce = " + controller.getPara("nonce"));
			return false;
		}
	}
	
	/**
	 * 是否为开发者中心保存服务器配置的请求
	 */
	private boolean isConfigServerRequest(AbstractController controller) {
		logger.info("echostr={}", controller.getPara("echostr"));
		return StrKit.notBlank(controller.getPara("echostr"));
	}
	
	/**
	 * 配置开发者中心微信服务器所需的 url 与 token
	 *
	 * @param c
	 *            控制器
	 */
	public void configServer(AbstractController c) {
		// 通过 echostr 判断请求是否为配置微信服务器回调所需的 url 与 token
		String echostr = c.getPara("echostr");
		String signature = c.getPara("signature");
		String timestamp = c.getPara("timestamp");
		String nonce = c.getPara("nonce");
		boolean isOk = SignatureCheckKit.me.checkSignature(signature, timestamp, nonce);
		if (isOk)
			c.renderText(echostr);
		else
			logger.error("验证失败：configServer");
	}
	
}
