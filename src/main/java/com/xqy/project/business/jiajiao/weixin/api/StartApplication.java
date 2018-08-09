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
package com.xqy.project.business.jiajiao.weixin.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * <b>StartApplication.java</b></br>
 * 
 * <pre>
 * 服务启动入口类
 * </pre>
 *
 * @author cpthack 1044559878@qq.com
 * @date 2017年6月5日 上午11:41:43
 * @since JDK 1.8
 */
@ComponentScan(basePackages = { "net.xqy", "com.xqy" })
@SpringBootApplication
@ServletComponentScan
public class StartApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(StartApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}
	
}
