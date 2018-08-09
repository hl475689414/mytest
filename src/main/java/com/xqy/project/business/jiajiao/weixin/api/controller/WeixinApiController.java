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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.MenuApi;
import com.xqy.project.business.jiajiao.weixin.api.base.AbstractJiajiaoBaseWxController;

/**
 * <b>WeixinApiController.java</b></br>
 * 
 * <pre>
 * 微信相关API接口控制器
 * </pre>
 *
 * @author cpthack 1044559878@qq.com
 * @date Apr 28, 2018 5:05:46 PM
 * @since JDK 1.8
 */
@RestController
public class WeixinApiController extends AbstractJiajiaoBaseWxController {
	
	/**
	 * 获取公众号菜单
	 */
	@RequestMapping(value = "/getMenu")
	public void getMenu() {
		ApiResult apiResult = MenuApi.getMenu();
		if (apiResult.isSucceed())
			renderText(apiResult.getJson());
		else
			renderText(apiResult.getErrorMsg());
	}
	
}
