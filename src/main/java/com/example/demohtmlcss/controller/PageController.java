package com.example.demohtmlcss.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demohtmlcss.UserParties.User;
import com.example.demohtmlcss.UserParties.UserService;
import com.example.demohtmlcss.securityConfig.CurrentDetails;





@Controller
public class PageController {
	
	@Autowired
	private CurrentDetails cd;
	
	@Autowired
	private UserService us;
	
	Logger logger = LoggerFactory.getLogger(PageController.class);
	
	@Autowired
	private EmployeeService empSer;
	
	@GetMapping("/")
	public String home(Model model, String keyword) {
		
		if (keyword != null) {
		List<Employee> list = empSer.findbyemail(keyword);
		model.addAttribute("employee",list);		
		}
		
		else {
		List<Employee> list = empSer.getAll();
		model.addAttribute("employee",list);
		}
		
	
		model.addAttribute("right",cd.authorized());
		model.addAttribute("User",cd.currentUser());
		System.out.println(cd.authorized());
		
		logger.info("Home page is accessed");
		return "home1";
	}
	
	@RequestMapping("/login")
	public String loginpage()
	{
		return "login";
	}
	//****************************************Testing Purpose****************************************************************
	@GetMapping("/employeeTest")
    public ResponseEntity<List<Employee>> getemployee(){
		List<Employee> emp = empSer.getAll();
		return new ResponseEntity<>(emp,HttpStatus.OK);
       
    }
	//********************************************************************************************************************
	
	
	
	@RequestMapping("/login-error")
	public String loginerror(Model m)
	{
		
		m.addAttribute("message","Bad Credentails");
		return "login";
	}
	
	@RequestMapping("/logout")
	public String logout(Model m)
	{
		
		m.addAttribute("logoutMessage","User Successfully Logout.!");
		return "login";
	}

	@GetMapping("/addEmp")
	public String addEmp(Model m) {
		m.addAttribute("User",cd.currentUser());
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
		m.addAttribute("User",cd.currentUser());
		logger.info("Details Page is accessed");
		
		//return "welcome";
		return "redirect:/";
	}
	
	@GetMapping(path="/edit/{id}")
	public String editEmp(Model m, @PathVariable("id") Integer id) {
		Optional<Employee> e = empSer.getOne(id);
		m.addAttribute("User",cd.currentUser());
		m.addAttribute("id",id);
		m.addAttribute("employee",e);
		
		logger.info("Edit Page is accessed");
		
		return "edit";
	}
	
	@RequestMapping(path="/delete/{id}")
	public String deleteEmployee(Model m, @PathVariable("id") Integer id) {
		empSer.deleteEmp(id);
		
		logger.info("Deleted An Employee....");
		return "redirect:/";
	}
	
	
//---------------------User Functions----------------------------------------------------------	
	
	@GetMapping("/usersList")
	public String users(Model model, String keyword) {
		
		if (keyword != null) {
			List<User> list = us.findbymobile(keyword);
			if (list.size()==0) {
				list = us.getall();
				}
				model.addAttribute("users",list);
				}
			else {
				List<User> list = us.getall();
				model.addAttribute("users",list);
				}
			model.addAttribute("User",cd.currentUser());
			model.addAttribute("noofuser",us.getnoofusers());
			model.addAttribute("noofadmins",us.getnoofadmins());
			model.addAttribute("noofcre", us.getnoofcreater());
			return "users";
		}
	
	@RequestMapping(path="/userdelete/{id}")
	public String deleteuser(Model m, @PathVariable("id") Integer id) {
		us.deleteUser(id);
		logger.info("Deleted An User....");
		return "redirect:/usersList";
	}

	@GetMapping(path="/useredit/{id}")
	public String editUser(Model m, @PathVariable("id") Integer id) {
		Optional<User> u = us.getOne(id);
		m.addAttribute("User",cd.currentUser());
		m.addAttribute("id",id);
		m.addAttribute("user",u);
		
		logger.info("Edit User Page is accessed");
		
		return "editUser";
	}
	
	@PostMapping("/reInsertUser")
	public String reInsertUser(@ModelAttribute User u, Model m) {
		System.out.println(u.getUsername());   
	   //u.setPassword(us.encodePassword(u.getPassword()));
		us.insertUser(u);
		m.addAttribute("state","User edited Successfully...!");
		return "redirect:/usersList";
	}
	
	@PostMapping("/insertUser")
	public String insertUser(@ModelAttribute User u, Model m) {
		System.out.println(u.getUsername());   
	   u.setPassword(us.encodePassword(u.getPassword()));
		us.insertUser(u);
		if(cd.currentUser() != "ANONYMOUSUSER") {
			
			return "redirect:/usersList";
		}
		else {
			m.addAttribute("state","User added Successfully...!");
			return "login";
		}
	}
	
	@RequestMapping("/register")
	public String register(Model m)
	{
		User u = new User();
		m.addAttribute("user",u);
		System.out.println(cd.currentUser());
		m.addAttribute("right",cd.authorized());
		m.addAttribute("User",cd.currentUser());
		return "register";
	}
	
}
