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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;

/**
 * <b>JFinalWxConfiguration.java</b></br>
 * 
 * <pre>
 * 基于JFinal - weixin的相关配置器
 * </pre>
 *
 * @author cpthack 1044559878@qq.com
 * @date Apr 28, 2018 11:03:35 AM
 * @since JDK 1.8
 */
@Configuration
public class JFinalWxConfiguration {
	
	@Bean
	public String configWx() {
		String token = "delijiajiao";
		String appId = "wx63ec0a89a3ffb9c4";
		String appSecret = "b270f49b473938c1b6738a9373c5e48e";
		boolean encryptMessage = false;
		String encodingAesKey = "D8IGIUGbgPPpoinDsdesj4O2wDuIGqydADeGX0b27oJ";
		ApiConfig ac = new ApiConfig();
		// 配置微信 API 相关参数
		ac.setToken(token);
		ac.setAppId(appId);
		ac.setAppSecret(appSecret);
		
		/**
		 * 是否对消息进行加密，对应于微信平台的消息加解密方式：</br>
		 * 1：true进行加密且必须配置 encodingAesKey</br>
		 * 2：false采用明文模式，同时也支持混合模式</br>
		 */
		ac.setEncryptMessage(encryptMessage);
		ac.setEncodingAesKey(encodingAesKey);
		
		/**
		 * 多个公众号时，重复调用ApiConfigKit.putApiConfig(ac)依次添加即可，第一个添加的是默认。
		 */
		ApiConfigKit.putApiConfig(ac);
		ApiConfigKit.setDevMode(true);
		
		return appId;
	}
}
