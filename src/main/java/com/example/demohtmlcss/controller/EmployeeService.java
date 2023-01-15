package com.example.demohtmlcss.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository empRep;
    
    public List<Employee> getAll(){
    	
    	List<Employee> list = empRep.findAll();
    	
    	if (list.size() >0) {
    		return list;
    	}
    	else {
    		return new ArrayList<Employee>();
    	}
       
    }

    public Optional<Employee> getOne(Integer id){
    	
        return empRep.findById(id);
    }

    public String insertEmp(Employee e) {
        empRep.save(e);
        return "Employee details save Successfully";
        }
    
    public String updateEmp(Employee e, Integer id){
    	if (empRep.existsById(id)) {
        empRep.save(e);
        return "Employee with Id: "+id+" is Updated Successfully";
    }else {
    	return "Employee do not Exist with Id: "+id;
    }
    }

    public String deleteEmp(Integer id){
    	if (empRep.existsById(id)) {
        empRep.deleteById(id);
        return "Employee with Id: "+id+" is Deleted Successfully";}
    	else {
    		return "Employee with Id: "+id+" do not exist";
    	}
    }

    
    public List<Employee> findbyemail(String keyword){
    	return empRep.findByEmail(keyword);
    }
    
    
}
