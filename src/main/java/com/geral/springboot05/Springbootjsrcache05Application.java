package com.geral.springboot05;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
@MapperScan("com.geral.springboot05.domain.mapper")// 也可以直接写到类上
@EnableCaching //
/**
 * 搭建环境
 * 1.导入数据库文件
 * 2.创建javabean封装文件。
 * 3.整合mybatis
 * 快速使用缓存
 * 1.开启基础注解的缓存 @EnableCaching
 * 2.标注缓存注解既可
 * @Cacheable 标注缓存
 * @CacheEvict 清除
 * @CachePut 更新
 * 默认使用的是ConcurrenMapcacheManager==ConcurrentMapCache;
 * 将数据保存在ConcurrentMap<Object,Object>中。
 * 开发中使用缓存中间件：redis、memcacheed、ehcache
 * 整合Redis作为缓存。
 * 1.引入starter
 * 2.配置redis
 * 3.测试缓存
 * 	原理：cachemanager==Cache 缓存组件来实际给缓存中存取数据
 * 	（1）引入redis的start 容器中保存的是RedisCachemanager;
 * 	 (2)RedisCacheManager帮我们创建RedisCache来作为缓存组件，RedisCache通过操作redis缓存数据。
 *	 （3）默认保存数据k-v都是object;利用序列化保存；如何保存为json
 *		1.引入了redis的start,cacheManager变为RedisCacheManager;
 *		2.默认创建的RedisCacheManager操作redis的使用的是RedisTemplate<Object,Object>
 *		3.RedisTemplate<Object,Object>是默认使用的jdk序列化机制
 * @author Panhui
 *
 */
public class Springbootjsrcache05Application {
	

	

	
	public static void main(String[] args) {
		SpringApplication.run(Springbootjsrcache05Application.class, args);
	}

}

