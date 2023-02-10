package com.example.demohtmlcss.securityConfig;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import com.example.demohtmlcss.controller.Employee;
import com.example.demohtmlcss.controller.EmployeeService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public AuthenticationProvider authProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());         //	provider.setPasswordEncoder(passwordEncoder());
		return provider;
		
	}
	    
	@Bean
	SecurityFilterChain web(HttpSecurity http) throws Exception
	{
	
		http
		.csrf().disable()
        .authorizeHttpRequests()
        .requestMatchers("/").hasAnyAuthority("USER","ADMIN","CREATER")
        .requestMatchers("/addEmp").hasAnyAuthority("USER","ADMIN","CREATER")
        .requestMatchers("/insert").hasAnyAuthority("USER","ADMIN","CREATER")
		.requestMatchers("/edit/{id}").hasAnyAuthority("ADMIN")
		.requestMatchers("/delete/{id}").hasAnyAuthority("ADMIN")
		.requestMatchers("/users").hasAnyAuthority("ADMIN")
		.requestMatchers("/usersList").hasAnyAuthority("ADMIN")
        .requestMatchers("/login").permitAll()
        .requestMatchers("/insertUser").permitAll()
        .requestMatchers("/register").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login").permitAll()
        .failureUrl("/login-error").permitAll()
        .and()
        .logout().permitAll()
        .logoutUrl("/logout").permitAll()
//        .and()
//        .oauth2Login()
//        .loginPage("/login")
        ;
		
	return http.build();
	}
	
	
	
//	@Autowired
//	EmployeeService empSer;
//	@Bean
//	protected UserDetailsService userDetails() {
//		List<UserDetails> users = new ArrayList<>();
//		List<Employee> emps = empSer.getAll();
//		for (Employee i : emps) {
//			users.add(User.withDefaultPasswordEncoder().username(i.getName()).password(i.getEmail()).roles("USER").build());
//		}
//		//code to have admin rights
//		users.add(User.withDefaultPasswordEncoder().username("shasi").password("1234").roles("ADMIN").build());
//		return new InMemoryUserDetailsManager(users);
//	}
//	
//	@Bean
//	SecurityFilterChain web(HttpSecurity http) throws Exception
//	//protected void configure(HttpSecurity http) throws Exception
//	{
//		http.csrf().disable()
//		.authorizeRequests().
//		requestMatchers("/","/addEmp","/insert").hasAnyRole("USER","ADMIN")
//		.requestMatchers("/edit/{id}","/delete/{id}").hasRole("ADMIN")
//		.and()
//		.formLogin().loginPage("/login");
//		
//	return http.build();
//	}
	
	
	
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
