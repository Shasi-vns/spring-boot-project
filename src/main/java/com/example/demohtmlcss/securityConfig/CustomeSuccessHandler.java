package com.example.demohtmlcss.securityConfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demohtmlcss.UserParties.User;
import com.example.demohtmlcss.UserParties.UserRepository;
import com.example.demohtmlcss.UserParties.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomeSuccessHandler implements AuthenticationSuccessHandler{
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	UserService userSer;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
			
		String redirectUrl=null;
		if (authentication.getPrincipal() instanceof DefaultOAuth2User ) {
			DefaultOAuth2User userDetails = (DefaultOAuth2User ) authentication.getPrincipal();
			String username = userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login")+"@gmail.com" ;
			
			if(userRepo.getUserByUsername(username) == null) {
				User user = new User();
				user.setUsername(userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login"));
				user.setName(userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login"));
				user.setPassword(userSer.encodePassword("password"));
				userSer.insertUser(user);
				
			}
		}
		redirectUrl = "/";
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
		
	}

}
