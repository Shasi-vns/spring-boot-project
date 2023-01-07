package com.example.demohtmlcss.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/")
public class PageController {
	
	Logger logger = LoggerFactory.getLogger(PageController.class);
	
	@Autowired
	private EmployeeService empSer;
	
	@RequestMapping
	public String home(Model model) {
		List<Employee> list = empSer.getAll();
		model.addAttribute("employee",list);
		
		logger.info("Home page is accessed");
		
		return "home1";
	}
	
	
	@GetMapping("/addEmp")
	public String addEmp() {
		logger.info("Add Employee Page is accessed");
		return "home";
	}
	
	
	@PostMapping("/insert")
	public String register(@ModelAttribute Employee e, Model m) {
		
		empSer.insertEmp(e);
		
		System.out.println(e.getName());
		m.addAttribute("id", e.getId());
		m.addAttribute("name", e.getName());
		m.addAttribute("email", e.getEmail());
		m.addAttribute("dept", e.getDepartment());
		
		//logger.info("Details Page is accessed");
		
		//return "welcome";
		return "redirect:/";
	}
	

	
	@GetMapping(path="/edit/{id}")
	public String editEmp(Model m, @PathVariable("id") Integer id) {
		Optional<Employee> e = empSer.getOne(id);
		m.addAttribute("id",id);
		m.addAttribute("employee",e);
		empSer.deleteEmp(id);
		
		logger.info("Edit Page is accessed");
		
		return "edit";
	}
	
	
	
	
	@RequestMapping(path="/delete/{id}")
	public String deleteEmployee(Model m, @PathVariable("id") Integer id) {
		empSer.deleteEmp(id);
		
		logger.info("Deleted An Employee....");
		return "redirect:/";
	}
	
	
	


}
