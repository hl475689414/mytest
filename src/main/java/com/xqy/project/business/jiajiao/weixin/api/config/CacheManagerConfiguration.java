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

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.xqy.project.base.cache.common.CustomizedCache;
import com.xqy.project.base.cache.common.CustomizedCacheManager;
import com.xqy.project.base.cache.redis.manager.CustomizedRedisCacheManager;

/**
 * <b>CacheManagerConfiguration.java</b></br>
 * 
 * <pre>
 * 缓存统一管理器配置类
 * </pre>
 *
 * @author cpthack 1044559878@qq.com
 * @date 2018年1月15日 上午10:47:42
 * @since JDK 1.8
 */
@Configuration
@EnableCaching
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CacheManagerConfiguration extends CachingConfigurerSupport {
	
	/**
	 * cacheManager内部具体依赖实现，为降低耦合度，程序中尽量不要直接使用
	 */
	@Bean
	public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(factory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		// 重新设定缓存操作对象的classloader,避免springboot-devtools的restartClassloader影响
		redisTemplate.setBeanClassLoader(org.springframework.util.ClassUtils.getDefaultClassLoader());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
	
	/**
	 * 定制版本的cacheManager对象
	 */
	@Bean
	public CustomizedCacheManager customizedCacheManager(RedisTemplate redisTemplate) {
		CustomizedCacheManager cacheManager = new CustomizedRedisCacheManager(redisTemplate);// 定制化的缓存管理器
		return cacheManager;
	}
	
	/**
	 * 具体缓存操作类的抽象对象.建议在程序中直接使用该对象操作缓存
	 */
	@Bean
	public CustomizedCache customizedCache(CustomizedCacheManager cacheManager) {
		CustomizedCache cacheHelper = cacheManager.getCache();// 定制化的缓存操作类
		return cacheHelper;
	}
	
}
