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
package com.xqy.project.business.jiajiao.weixin.api.annotation;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>WxMsgValidator.java</b></br>
 * 
 * <pre>
 * 微信消息处理相关注解
 * </pre>
 *
 * @author cpthack 1044559878@qq.com
 * @date Apr 28, 2018 2:34:09 PM
 * @since JDK 1.8
 */
@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WxMsgValidator {
	
}
