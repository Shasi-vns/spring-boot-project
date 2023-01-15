package com.example.demohtmlcss.securityConfig;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import com.example.demohtmlcss.controller.Employee;
import com.example.demohtmlcss.controller.EmployeeService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	EmployeeService empSer;
	
	@Bean
	protected UserDetailsService userDetails() {
		
		List<UserDetails> users = new ArrayList<>();
		
		List<Employee> emps = empSer.getAll();
		
		for (Employee i : emps) {
			users.add(User.withDefaultPasswordEncoder().username(i.getName()).password(i.getEmail()).roles("USER").build());
		}
		//code to have admin rights
		users.add(User.withDefaultPasswordEncoder().username("Shasi").password("1234").roles("ADMIN").build());
		
		
		
		return new InMemoryUserDetailsManager(users);
		
	}
	
	
	@Bean
	SecurityFilterChain web(HttpSecurity http) throws Exception
	
	//protected void configure(HttpSecurity http) throws Exception
	{
		
		http.authorizeRequests().requestMatchers("/","/addEmp","/insert").hasAnyRole("USER","ADMIN")
		.requestMatchers("/edit/{id}","/delete/{id}").hasRole("ADMIN")
		.and().formLogin();
		
		
	return http.build();
	}
	
	
	
	
	

	
	// working
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//            .username("user")
//            .password("password")
//            .roles("USER")
//            .build();
//        return new InMemoryUserDetailsManager(user);
//    }


}
