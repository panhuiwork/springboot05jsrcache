package com.geral.springboot05.contral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.geral.springboot05.domain.entity.Employee;

@RestController
public class TestRedisContral {

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	RedisTemplate redisTemplate;
	/**
	 * redis常用五大数据类型
	*String(字符串) List(列表) Set(集合) Hash(散列) ZSet(有序集合)
	*stringRedisTemplate.opsForValue()[String(字符串)]
	*stringRedisTemplate.opsForList()[List(列表)]
	*stringRedisTemplate.opsForSet()[Set(集合)]
	*stringRedisTemplate.opsForHash()[Hash(散列)]
	*stringRedisTemplate.opsForZSet()[ZSet(有序集合)]
	*/
	
	@GetMapping("/redis/{info}")
	public String redredis(@PathVariable String info) 
	{
		stringRedisTemplate.opsForValue().append("msg",info);
		stringRedisTemplate.opsForList().leftPush("mylist", "1");
		stringRedisTemplate.opsForList().leftPush("mylist", "2");
		return "写入redis";
	}
	
	@Autowired
	RedisTemplate  empredisTemplate;
	
	
	@GetMapping("/redis2/{info}")
	public String redredis2(@PathVariable String info) 
	{
		Employee emp=new Employee();
		emp.setLastName(info);
		//默认保存对象，使用jdk序列化机制，序列化后的数据保存到redis中
//		redisTemplate.opsForValue().set("emp-01", emp);
		//1。将数据以json的方式保存
		//1)自己将对象转换为json
		//2）redisTemplate默认的序列化规则，改变默认的序列化规则MyredisConfig类中
		//数据库中数据就是json了
		empredisTemplate.opsForValue().set("emp-01", emp);
		return "写入redis2";
	}
	
	@GetMapping("/redis")
	public String redredis() 
	{
		return stringRedisTemplate.opsForValue().get("msg");
	}
}
