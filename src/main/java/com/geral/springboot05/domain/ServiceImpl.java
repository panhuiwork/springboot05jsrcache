package com.geral.springboot05.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

import com.geral.springboot05.domain.entity.Department;
import com.geral.springboot05.domain.entity.Employee;
import com.geral.springboot05.domain.mapper.DepartmentMapper;
import com.geral.springboot05.domain.mapper.EmployeeMapper;

@Service
public class ServiceImpl {

	@Autowired
	private DepartmentMapper departmentMapper;
	@Autowired
	private EmployeeMapper employeeMapper;

	/**
	 * 缓存的数据能存入redis
	 * 第二次从缓存中查询就不能反序列化回来
	 * 存的是dept的json数据，CacheManager默认使用RedisTemplate<Object,Employee>操作redis
	 * @param id
	 * @return
	 */
	@Cacheable(value = "department", condition = "#id>0", unless = "#result==null",cacheManager="deptCacheManager")
	public Department getDepartmentById(Integer id) {
		return departmentMapper.findById(id);
	}




	/**
	 * 查询部门 //将发放的运行结果进行缓存，在要相同的数据直接从缓存中获取，不用再查询数据库
	 * CacheManager管理多个Cache组件的，对缓存的整个CRUD操作在cache组件中，每个缓存组件有自己的唯一名字 几个属性：
	 * cacheNames/value 指定缓存的名字 cacheNames={"",""} key：缓存数据使用的key
	 * 可以用它来指定。默认是使用方法参数的值 1-方法的返回值 编写spEl表达式 #id；参数id的值， #a0 #b0 #result返回结果
	 * keyGenerator：key的生成器；可以自己指定key的生成器的组件id key/keyGenerator 二选一
	 * cacheManager:指定缓存管理器，或者cacheResolver指定缓存解析器 condition:指定符合条件的情况下才缓存
	 * unless：否定缓存；当unless指定条件为true 方法才缓存
	 * 
	 * 给容器中注册了一个CacheManager:ConcurrentMapCacheManager
	 * 可以获取和创建ConcurrentMapcache类型的缓存组建；他的作用将数据保存在ConcurrentMap中。 运行流程：
	 * 1.方法运行以前，先去查询cache缓存组建，按照cacheNames指定的名字获取；
	 * （CacheManager先获取相印的缓存），第一次获取缓存没有cache组建会自动创建。
	 * 2.去Cache中查找缓存的内容，使用一个key，默认就是方法的参数 key是按照某种策略生成的。默认是使用keyGenerator生成的，
	 * 默认使用SimpleKeyGenerator生成key 如果没有参数：key=new SimpleKey(); 如果有一个参数：key=参数的值
	 * 如果多个参数，key=new SimpleKey(param); 3.没有查询到缓存就调用目标方法。 4.将目标方法返回的结果，放进缓存中
	 *
	 * @Cacheable标注的方法执行之前先来检查出缓存中有没有数据，默认按照参数的值作为key去查询缓存，如果没有就运行方法并将结果放入缓存 以后再来调用就可以直接使用缓存中的数据。
	 *
	 *                                                                       下面的参数key可以使用spel表达式来写：key="#root.methodName+'['+#id+']'"
	 *                                                                       condition="#id>0
	 *                                                                       and
	 *                                                                       #root.methodName
	 *                                                                       eq
	 *                                                                       'aaa'"
	 * @param id
	 * @return
	 */
	@Cacheable(value = "employee",unless = "#result==null",cacheManager="employeeCacheManager")
	public Employee getEmployeeById(Integer id) {
		return employeeMapper.findById(id);
	}
	
	@Qualifier("deptCacheManager")
	@Autowired
	RedisCacheManager deptCacheManager;
	/**
	 * 使用缓存管理器得到缓存进行api调用
	 * @param id
	 * @return
	 */
	public Department getcodeDepartmentById(Integer id) {
		Department department=departmentMapper.findById(id);
		//获取某个缓存
		Cache dept=deptCacheManager.getCache("department");
		dept.put("department:1",department);
		return department;
	}
	
	
	/**
	 * 更新玩家的信息
	 * @CachePut 既调用方法，又更新缓存数据
	 * 修改了数据库的某个数据，同时更新缓存。
	 * 运行时间：
	 * 1.先调用目标方法
	 * 2.将目标方法的结果缓存起来
	 * @param employee ,key="#result.id" 或者 #employee.id
	 * @return
	 */
	@CachePut(value="employee",key="#result.id")
	public Employee updatEemployee(Employee employee) 
	{
		employeeMapper.updateEmployee(employee);
		return employee;
	}
	
	/**
	 * 删除人员
	 * 清除花奴才能
	 * key:key="#id"
	 * 如果不指定key allEntries=true 就是删除所有缓存的意思
	 * beforeInvocation=false 缓存的清除是否在方法之前执行
	 * 默认代表缓存清楚是在方法之后执行如果出现异常就不会清除
	 * 
	 *  beforeInvocation=true 和上面相反
	 * @param id
	 * @return
	 */
	@CacheEvict(value="employee",key="#id")
	public boolean deleteEmp(Integer id) 
	{
		return employeeMapper.delete(id);
	}
	
	//自定义
	//因为使用的了cacheput所以这个方法肯定要执行数据库查询。 运行后可以根据id来查直接得到的是缓存。
	@Caching
	(
		cacheable= 
		{
			@Cacheable(value="employee",key="#lastName",unless = "#result==null")
		},
		put= 
		{
			@CachePut(value="employee",key="#result.id",unless = "#result==null"),
			@CachePut(value="employee",key="#result.email",unless = "#result==null")
		}
	)
	public Employee getEmpByLastName(String lastName) 
	{
		return employeeMapper.findBylastName(lastName);
	}

}
