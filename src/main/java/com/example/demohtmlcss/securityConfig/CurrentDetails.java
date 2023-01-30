package com.example.demohtmlcss.securityConfig;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentDetails {

	public String currentUser()
	{
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		//String v = loggedInUser.getAuthorities().toString();
		//System.out.println(v);
		return loggedInUser.getName().toUpperCase();
	}
	
	public String authorized()
	{
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String v = loggedInUser.getAuthorities().toString();
		return v;
	}
}
