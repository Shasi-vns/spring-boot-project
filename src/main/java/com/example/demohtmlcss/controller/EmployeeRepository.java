package com.example.demohtmlcss.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	
	Employee findByname(String name);
	
	@Query(value="select * from Employee e where e.email like CONCAT('%',:keyword,'%')",nativeQuery=true)
	List<Employee> findByEmail(@Param("keyword") String keyword);

}
