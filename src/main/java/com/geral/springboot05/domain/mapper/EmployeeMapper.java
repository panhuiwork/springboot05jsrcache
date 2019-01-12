package com.geral.springboot05.domain.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.geral.springboot05.domain.entity.Employee;

//@Mapper  直接在主类上写了
//@CacheNamespace
public interface EmployeeMapper {

	@Select("select * from employee where id=#{id}")
	Employee findById(Integer id) ;
	
	
	@Select("select * from employee where lastName=#{lastName}")
	Employee findBylastName(String lastName) ;
	
	@Update("update employee set lastName=#{lastName},email=#{email},gender=#{gender},d_id=#{dId} where id=#{id}")
	boolean updateEmployee(Employee employee);
	
	@Delete("delete  from employee where id=#{id}")
	boolean delete(Integer id) ;
}
