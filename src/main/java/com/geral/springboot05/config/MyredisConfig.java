package com.geral.springboot05.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.geral.springboot05.domain.entity.Department;
import com.geral.springboot05.domain.entity.Employee;

@Configuration
public class MyredisConfig {

	@Bean
	public RedisTemplate<Object,Employee> empredisTemplate(RedisConnectionFactory redisConnectionFactory)
	{
		RedisTemplate<Object,Employee> template=new RedisTemplate<Object,Employee>();
		template.setConnectionFactory(redisConnectionFactory);
		Jackson2JsonRedisSerializer<Employee> ser=new Jackson2JsonRedisSerializer<Employee>(Employee.class);
		template.setDefaultSerializer(ser);
		return template;
	}
	//CacheManagerCustomizers可以来定制缓存的一些规则
	@Primary //将某个缓存管理器作为默认
	@Bean
	public RedisCacheManager employeeCacheManager(RedisTemplate<Object,Employee> empredisTemplate) 
	{
		RedisCacheManager cacheManager=new RedisCacheManager(empredisTemplate);
		//key多了一个前缀，默认会将CacheName作为key的前缀
		cacheManager.setUsePrefix(true);
		return cacheManager;
	}
	@Bean
	public RedisTemplate<Object,Department> deptredisTemplate(RedisConnectionFactory redisConnectionFactory)
	{
		RedisTemplate<Object,Department> template=new RedisTemplate<Object,Department>();
		template.setConnectionFactory(redisConnectionFactory);
		Jackson2JsonRedisSerializer<Department> ser=new Jackson2JsonRedisSerializer<Department>(Department.class);
		template.setDefaultSerializer(ser);
		return template;
	}
	@Bean
	public RedisCacheManager deptCacheManager(RedisTemplate<Object,Department> deptredisTemplate) 
	{
		RedisCacheManager cacheManager=new RedisCacheManager(deptredisTemplate);
		//key多了一个前缀，默认会将CacheName作为key的前缀
		cacheManager.setUsePrefix(true);
		return cacheManager;
	}
}
