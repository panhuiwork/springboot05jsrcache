package com.geral.springboot05.contral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.geral.springboot05.domain.ServiceImpl;
import com.geral.springboot05.domain.entity.Department;
import com.geral.springboot05.domain.entity.Employee;

@RestController
public class InfoContral {
	
	@Autowired
	private ServiceImpl serviceImpl;
	
	@GetMapping("/department/{id}")
	public Department getdepar(@PathVariable Integer id) 
	{
		System.out.println("查询id"+id);
		return serviceImpl.getDepartmentById(id);
	}
	
	@GetMapping("/departmentcode/{id}")
	public Department getdeparcode(@PathVariable Integer id) 
	{
		System.out.println("查询codeid"+id);
		return serviceImpl.getcodeDepartmentById(id);
//		return serviceImpl.getDepartmentById(id);
	}
	
	@GetMapping("/employee/{id}")
	public Employee getemployee(@PathVariable Integer id) 
	{
		System.out.println("查询id"+id);
		return serviceImpl.getEmployeeById(id);
	}
	
	@GetMapping("/employee/lastname/{lastName}")
	public Employee getemp(@PathVariable String lastName) 
	{
		System.out.println("查询lastName"+lastName);
		Employee employee=serviceImpl.getEmpByLastName(lastName);
		System.out.println("employee="+employee);
		return employee;
	}
	
	
	/**
	 * 更新员工数据
	 * @param id
	 * @return
	 */
	@GetMapping("/updateemp")
	public Employee updateemployee(Employee employee) 
	{
		System.out.println("更新："+employee);
		return serviceImpl.updatEemployee(employee);
	}
	
	/**
	 * 删除员工
	 * @param id
	 * @return
	 */
	@GetMapping("/delemp/{id}")
	public boolean deleteemployee(@PathVariable Integer id) 
	{
		System.out.println("删除："+id);
		return serviceImpl.deleteEmp(id);
	}
}
