package com.geral.springboot05.domain.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.geral.springboot05.domain.entity.Department;

//@Mapper 直接在主类上写了
//@CacheNamespace
public interface DepartmentMapper {

	@Select("select * from department where id=#{id}")
	Department findById(Integer id);
}
